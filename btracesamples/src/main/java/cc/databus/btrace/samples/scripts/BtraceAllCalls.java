package cc.databus.btrace.samples.scripts;

import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.println;

@BTrace
public class BtraceAllCalls {
    /**
     * This script demonstrates the possibility to intercept
     * method calls that are about to be executed from the body of
     * a certain method. This is achieved by using the {@linkplain Kind#CALL}
     * location value.
     */
    @OnMethod(clazz = "+java.lang.Runnable", method = "run")
    public static void m() { // all calls to the methods with signature "()"
        println("Runnable start");
    }
}
