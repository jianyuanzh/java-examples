package cc.databus.btrace.samples.scripts;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@BTrace
public class FileMonitor {

    @TLS
    private static String name = null;

    @OnMethod(clazz = "java.io.FileInputStream", method = "<init>")
    public static void onNewFileInputStream(@Self FileInputStream self, File file) {
        name = BTraceUtils.Strings.str(file);
    }

    @OnMethod(
            clazz = "java.io.FileInputStream",
            method = "<init>",
            type = "void (java.io.File)",
            location = @Location(Kind.RETURN)
    )
    public static void onNewFileInputStreamReturn() {
        if (name != null) {
            BTraceUtils.println(BTraceUtils.name(BTraceUtils.currentThread()) + " opened for read " + name);
            name = null;
        }
    }

    @OnMethod(
            clazz = "java.io.FileOutputStream",
            method = "<init>"
    )
    public static void onNewFileOutputStream(@Self FileOutputStream self, File f, boolean b) {
        name = BTraceUtils.Strings.str(f);
        BTraceUtils.println(BTraceUtils.name(BTraceUtils.currentThread()) + " open file " + name + " for writing, append=" + b);
    }

    @OnMethod(
            clazz = "java.io.FileOutputStream",
            method = "<init>",
            type = "void (java.io.File, boolean)",
            location = @Location(Kind.RETURN)
    )
    public static void onNewFileOutputStreamReturn() {
        if (name != null) {
            BTraceUtils.println("openned for writing " + name);
            name = null;
        }
    }
}
