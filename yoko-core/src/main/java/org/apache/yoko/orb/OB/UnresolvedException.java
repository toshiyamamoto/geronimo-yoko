package org.apache.yoko.orb.OB;

import static org.apache.yoko.orb.OCI.GiopVersion.GIOP1_2;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.yoko.orb.CORBA.InputStream;
import org.apache.yoko.orb.OCI.Buffer;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.UNKNOWN;
import org.omg.CORBA.portable.UnknownException;
import org.omg.SendingContext.CodeBase;

public class UnresolvedException extends UnknownException {
    private static final Logger LOGGER = Logger.getLogger(UnresolvedException.class.getName());
    private final UNKNOWN ex;
    private final byte[] data;
    private final CodeConverters converters;
    private final CodeBase sendingContextRuntime;
    private final String codebase;

    UnresolvedException(UNKNOWN ex, byte[] data, InputStream is) {
        super(ex);
        super.completed = ex.completed;
        super.minor = ex.minor;
        this.ex = ex;
        this.data = data;
        this.converters = is._OB_codeConverters();
        this.sendingContextRuntime = is.__getSendingContextRuntime();
        this.codebase = is.__getCodeBase();
    }

    public SystemException resolve() {
        Buffer buf = new Buffer(data, data.length);
        try (org.apache.yoko.orb.CORBA.InputStream in =
                new org.apache.yoko.orb.CORBA.InputStream(buf, 0, false, converters, GIOP1_2)) {
            if (LOGGER.isLoggable(Level.FINE))
                LOGGER.fine(String.format("Unpacking Unknown Exception Info%n%s", in.dumpData()));
            try {
                in.__setSendingContextRuntime(sendingContextRuntime);
                in.__setCodeBase(codebase);
                in._OB_readEndian();
                Throwable t = (Throwable) in.read_value();
                UnknownException x = new UnknownException(t);
                x.completed = ex.completed;
                x.minor = ex.minor;
                return x;
            } catch (Exception e) {
                final String dump = in.dumpData();
                final int curPos = in.buf_.pos();
                in.buf_.pos(0);
                final String fullDump = in.dumpData();
                in.buf_.pos(curPos);
                try (StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw)) {
                    e.printStackTrace(pw);
                    LOGGER.severe(String.format("%s:%n%s:%n%s%n%s:%n%s%n%s:%n%s",
                            "Exception reading UnknownExceptionInfo service context",
                            "Remaining data", dump, "Full data", fullDump,
                            "Exception thrown", sw.toString()));
                }
            }
        } catch (IOException e) {
            //ignore IOException from AutoCloseable.close()
        }
        return ex;
    }

    private void readObject(ObjectInputStream ois) throws IOException {
        throw new NotSerializableException();
    }
    private void writeObject(ObjectOutputStream oos) throws IOException {
        throw new NotSerializableException();
    }
}
