/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
*  contributor license agreements.  See the NOTICE file distributed with
*  this work for additional information regarding copyright ownership.
*  The ASF licenses this file to You under the Apache License, Version 2.0
*  (the "License"); you may not use this file except in compliance with
*  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.yoko.orb.OCI.IIOP;

import org.apache.yoko.orb.CORBA.InputStream;
import org.apache.yoko.orb.CORBA.OutputStream;
import org.apache.yoko.orb.OCI.Buffer;
import org.apache.yoko.orb.OCI.ProfileInfo;
import org.apache.yoko.orb.OCI.ProfileInfoHolder;
import org.apache.yoko.orb.OCI.ProfileInfoSeqHolder;
import org.omg.CORBA.Any;
import org.omg.CSIIOP.CompoundSecMech;
import org.omg.CSIIOP.CompoundSecMechList;
import org.omg.CSIIOP.CompoundSecMechListHelper;
import org.omg.CSIIOP.TAG_CSI_SEC_MECH_LIST;
import org.omg.CSIIOP.TAG_TLS_SEC_TRANS;
import org.omg.CSIIOP.TLS_SEC_TRANS;
import org.omg.CSIIOP.TLS_SEC_TRANSHelper;
import org.omg.CSIIOP.TransportAddress;
import org.omg.IIOP.ProfileBody_1_0;
import org.omg.IIOP.ProfileBody_1_0Helper;
import org.omg.IOP.*;
import org.omg.IOP.CodecPackage.FormatMismatch;
import org.omg.IOP.CodecPackage.TypeMismatch;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final public class Util {
    static public IOR createIOR(String host, int port, String id, ProfileInfo profileInfo) {
        IOR ior = new IOR();
        ior.type_id = id;
        ior.profiles = new org.omg.IOP.TaggedProfile[1];
        ior.profiles[0] = new org.omg.IOP.TaggedProfile();
        ior.profiles[0].tag = TAG_INTERNET_IOP.value;

        if (profileInfo.major == 1 && profileInfo.minor == 0) {
            ProfileBody_1_0 body = new ProfileBody_1_0();
            body.iiop_version = new org.omg.IIOP.Version((byte) 1, (byte) 0);
            body.host = host;
            if (port >= 0x8000)
                body.port = (short) (port - 0xffff - 1);
            else
                body.port = (short) port;
            body.object_key = profileInfo.key;
            Buffer buf = new Buffer();
            OutputStream out = new OutputStream(buf);
            out._OB_writeEndian();
            ProfileBody_1_0Helper.write(out, body);
            ior.profiles[0].profile_data = new byte[buf.length()];
            System.arraycopy(buf.data(), 0, ior.profiles[0].profile_data, 0,
                    buf.length());
        } else {
            org.omg.IIOP.ProfileBody_1_1 body = new org.omg.IIOP.ProfileBody_1_1();
            body.iiop_version = new org.omg.IIOP.Version(profileInfo.major,
                    profileInfo.minor);
            body.host = host;
            if (port >= 0x8000)
                body.port = (short) (port - 0xffff - 1);
            else
                body.port = (short) port;
            body.object_key = profileInfo.key;
            body.components = profileInfo.components;
            Buffer buf = new Buffer();
            OutputStream out = new OutputStream(buf);
            out._OB_writeEndian();
            org.omg.IIOP.ProfileBody_1_1Helper.write(out, body);
            ior.profiles[0].profile_data = new byte[buf.length()];
            System.arraycopy(buf.data(), 0, ior.profiles[0].profile_data, 0,
                    buf.length());
        }

        return ior;
    }

    static public IOR createIOR(IOR ior, String id, byte[] key) {
        //
        // Extract the IIOP profile information from the provided IOR
        //
        int profile;
        for (profile = 0; profile < ior.profiles.length; profile++)
            if (ior.profiles[profile].tag == TAG_INTERNET_IOP.value)
                break;

        // TODO: Internal error?
        org.apache.yoko.orb.OB.Assert._OB_assert(profile < ior.profiles.length);

        Buffer buf = new Buffer(
                ior.profiles[profile].profile_data,
                ior.profiles[profile].profile_data.length);
        InputStream in = new InputStream(buf, 0, false, null, null);
        in._OB_readEndian();
        ProfileBody_1_0 body = ProfileBody_1_0Helper
                .read(in);

        ProfileInfo profileInfo = new ProfileInfo();
        profileInfo.key = key;
        profileInfo.major = body.iiop_version.major;
        profileInfo.minor = body.iiop_version.minor;
        profileInfo.components = new TaggedComponent[0];
        return createIOR(body.host, body.port, id, profileInfo);
    }

    static public boolean extractProfileInfo(IOR ior, ProfileInfoHolder profileInfo) {
        ProfileInfoSeqHolder profileInfoSeq = new ProfileInfoSeqHolder();
        profileInfoSeq.value = new ProfileInfo[0];
        extractAllProfileInfos(ior, profileInfoSeq, false, null, 0, false, null);
        if (profileInfoSeq.value.length > 0) {
            profileInfo.value = profileInfoSeq.value[0];
            return true;
        }

        return false;
    }

    static public boolean hostMatch(String host1, String host2, boolean matchLoopback) {
        //
        // Direct host name comparison
        //
        if (!host1.equals(host2)) {
            //
            //
            // Direct host name comparision failed - must look up
            // addresses to be really sure if the hosts differ
            //
            try {
                InetAddress addr1 = InetAddress.getByName(host1);

                InetAddress addr2 = InetAddress.getByName(host2);

                if (!addr1.equals(addr2)) {
                    //
                    // Address comparison failed - shall I extract
                    // the key if the profile body contains the
                    // loopback address?
                    //
                    if (matchLoopback) {
                        InetAddress loopback = InetAddress.getByName("127.0.0.1");

                        if (!addr2.equals(loopback))
                            return false;
                    } else
                        return false;
                }
            } catch (java.net.UnknownHostException ex) {
                //
                // Continue on hostname lookup failure
                //
                return false;
            }
        }

        return true;
    }

    static public void extractAllProfileInfos(IOR ior, ProfileInfoSeqHolder profileInfoSeq,
            boolean performMatch, String host, int port, boolean matchLoopback, Codec codec) {
        short portNo = (short) port;

        List<ProfileInfo> list = new ArrayList<>();

        for (int i = 0; i < ior.profiles.length; i++) {
            if (ior.profiles[i].tag != TAG_INTERNET_IOP.value) continue;
            //
            // Get the IIOP profile body
            //
            byte[] data = ior.profiles[i].profile_data;
            Buffer buf = new Buffer(data, data.length);
            InputStream in = new InputStream(buf, 0, false, null, null);
            in._OB_readEndian();
            ProfileBody_1_0 body = ProfileBody_1_0Helper.read(in);

            //
            // Read components if the IIOP version is > 1.0
            //
            TaggedComponent[] components;
            if (body.iiop_version.major > 1 || body.iiop_version.minor > 0) {
                int len = in.read_ulong();
                components = new TaggedComponent[len];
                for (int j = 0; j < len; j++) {
                    components[j] = TaggedComponentHelper.read(in);
                }
            } else {
                components = new TaggedComponent[0];
            }

            // add this profile to the list unless
            // A) the caller requested matching
            // B) the profile doesn't match the supplied host and port
            if (!!!performMatch
                    || hostAndPortMatch(host, portNo, body.host, body.port, matchLoopback)
                    || taggedComponentsMatch(components, host, portNo, codec, matchLoopback)) {
                ProfileInfo profileInfo = new ProfileInfo();
                profileInfo.key = body.object_key;
                profileInfo.minor = body.iiop_version.minor;
                profileInfo.major = body.iiop_version.major;
                profileInfo.id = ior.profiles[i].tag;
                profileInfo.index = i;
                profileInfo.components = components;

                list.add(profileInfo);
            }
        }

        if (!!!list.isEmpty()) {
            List<ProfileInfo> bigList = new ArrayList<>(Arrays.asList(profileInfoSeq.value));
            bigList.addAll(list);
            profileInfoSeq.value = bigList.toArray(profileInfoSeq.value);
        }
    }

    private static boolean hostAndPortMatch(String host, short portNo, String bodyHost, short bodyPort, boolean matchLoopback) {
        return portNo == bodyPort && hostMatch(host, bodyHost, matchLoopback);
    }

    private static boolean taggedComponentsMatch(TaggedComponent[] components, String host, short port, Codec codec, boolean matchLoopback) {
        for (final TaggedComponent component : components) {
            if (component.tag == TAG_ALTERNATE_IIOP_ADDRESS.value) {
                byte[] d = component.component_data;
                Buffer b = new Buffer(d, d.length);
                InputStream s = new InputStream(b, 0, false, null, null);
                s._OB_readEndian();
                String altHost = s.read_string();
                short altPort = s.read_ushort();
                if (hostAndPortMatch(host, port, altHost, altPort, matchLoopback)) {
                    return true;
                }
            } else if (component.tag == TAG_CSI_SEC_MECH_LIST.value) {
                Any any;
                try {
                    any = codec.decode_value(component.component_data, CompoundSecMechListHelper.type());
                    CompoundSecMechList csml = CompoundSecMechListHelper.extract(any);

                    for (CompoundSecMech csm : csml.mechanism_list) {
                        TaggedComponent tc = csm.transport_mech;
                        if (tc.tag == TAG_TLS_SEC_TRANS.value) {
                            Any tstAny = codec.decode_value(tc.component_data, TLS_SEC_TRANSHelper.type());
                            TLS_SEC_TRANS tst = TLS_SEC_TRANSHelper.extract(tstAny);
                            TransportAddress[] transportAddresses = tst.addresses;
                            if (transportAddressesMatch(transportAddresses, host, port, matchLoopback))
                                return true;
                        }
                    }
                } catch (FormatMismatch | TypeMismatch ignored) {
                }
            }
        }
        return false;
    }

    private static boolean transportAddressesMatch(TransportAddress[] addrs, String host, short port, boolean loopbackMatches) {
        for (TransportAddress addr: addrs) {
            final short addrPort = addr.port;
            if (hostAndPortMatch(host, port, addr.host_name, addrPort, loopbackMatches)) {
                return true;
            }
        }
        return false;
    }

    static public boolean equivalent(IOR ior1, IOR ior2) {
        int p1, p2, b1, b2;
        int cnt1 = 0, cnt2 = 0;
        ProfileBody_1_0[] bodies1;
        ProfileBody_1_0[] bodies2;

        //
        // Calculate number of IIOP profiles in ior1
        //
        for (p1 = 0; p1 < ior1.profiles.length; p1++)
            if (ior1.profiles[p1].tag == TAG_INTERNET_IOP.value)
                cnt1++;

        //
        // Calculate number of IIOP profiles in ior2
        //
        for (p2 = 0; p2 < ior2.profiles.length; p2++)
            if (ior2.profiles[p2].tag == TAG_INTERNET_IOP.value)
                cnt2++;

        //
        // Return false now if the number of IIOP profile bodies do not
        // match
        //
        if (cnt1 != cnt2)
            return false;

        //
        // Create an array with all IIOP profile bodies of ior1
        //
        bodies1 = new ProfileBody_1_0[cnt1];
        for (p1 = 0, b1 = 0; p1 < ior1.profiles.length; p1++)
            if (ior1.profiles[p1].tag == TAG_INTERNET_IOP.value) {
                byte[] data = ior1.profiles[p1].profile_data;
                Buffer buf = new Buffer(
                        data, data.length);
                InputStream in = new InputStream(buf, 0, false, null, null);
                in._OB_readEndian();
                bodies1[b1++] = ProfileBody_1_0Helper.read(in);
            }

        if (b1 != cnt1)
            throw new InternalError();

        //
        // Create an array with all IIOP profile bodies of ior2
        //
        bodies2 = new ProfileBody_1_0[cnt2];
        for (p2 = 0, b2 = 0; p2 < ior2.profiles.length; p2++)
            if (ior2.profiles[p2].tag == TAG_INTERNET_IOP.value) {
                byte[] data = ior2.profiles[p2].profile_data;
                Buffer buf = new Buffer(
                        data, data.length);
                InputStream in = new InputStream(buf, 0, false, null, null);
                in._OB_readEndian();
                bodies2[b2++] = ProfileBody_1_0Helper.read(in);
            }

        if (b2 != cnt2)
            throw new InternalError();

        //
        // Check for profile body matches
        //
        for (b1 = 0; b1 < cnt1; b1++) {
            for (b2 = 0; b2 < cnt2; b2++) {
                if (bodies2[b2] == null)
                    continue;

                //
                // Compare profile bodies
                //
                if (compareBodies(bodies1[b1], bodies2[b2])) {
                    //
                    // OK, found a match
                    //
                    bodies1[b1] = null;
                    bodies2[b2] = null;
                    break;
                }
            }
        }

        //
        // Check whether there are any unmatched IIOP profile bodies
        //
        for (b1 = 0; b1 < cnt1; b1++)
            if (bodies1[b1] != null)
                return false;

        for (b2 = 0; b2 < cnt2; b2++)
            if (bodies2[b2] != null)
                return false;

        return true;
    }

    static boolean compareBodies(ProfileBody_1_0 body1,
            ProfileBody_1_0 body2) {
        //
        // Compare versions
        //
        if (body1.iiop_version.major != body2.iiop_version.major
                || body1.iiop_version.minor != body2.iiop_version.minor)
            return false;

        //
        // Compare ports
        //
        if (body1.port != body2.port)
            return false;

        //
        // Compare object keys
        //
        if (body1.object_key.length != body2.object_key.length)
            return false;

        int k;
        for (k = 0; k < body1.object_key.length; k++)
            if (body1.object_key[k] != body2.object_key[k])
                return false;

        //
        // Direct host name comparison
        //
        if (!body1.host.equals(body2.host)) {
            //
            // Direct host name comparision failed - must look up
            // addresses to be really sure if the hosts differ
            //
            try {
                InetAddress addr1 = InetAddress
                        .getByName(body1.host);

                InetAddress addr2 = InetAddress
                        .getByName(body2.host);

                if (!addr1.equals(addr2))
                    return false;
            } catch (java.net.UnknownHostException ex) {
                //
                // Return false on hostname lookup failure
                //
                return false;
            }
        }

        //
        // OK, found a match
        //
        return true;
    }

    //
    // Calculate a hash for an IOR containing IIOP profiles
    //
    static public int hash(IOR ior, int maximum) {
        int hash = 0;

        for (TaggedProfile profile : ior.profiles) {
            if (profile.tag == TAG_INTERNET_IOP.value) continue;
            //
            // Get the first IIOP profile body
            //
            byte[] data = profile.profile_data;
            Buffer buf = new Buffer(data, data.length);
            InputStream in = new InputStream(buf, 0, false, null, null);
            in._OB_readEndian();
            ProfileBody_1_0 body = ProfileBody_1_0Helper.read(in);

            //
            // Add port to hash
            //
            hash ^= body.port;

            //
            // Add object key to hash
            //
            for (int j = 0; j + 1 < body.object_key.length; j += 2)
                hash ^= body.object_key[j + 1] * 256 + body.object_key[j];
        }

        return hash % (maximum + 1);
    }
}
