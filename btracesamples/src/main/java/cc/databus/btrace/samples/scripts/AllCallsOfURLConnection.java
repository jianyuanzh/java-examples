package cc.databus.btrace.samples.scripts;

import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.println;

@BTrace
public class AllCallsOfURLConnection {

    @OnMethod(clazz = "", method = "/.*/",
            location = @Location(value = Kind.CALL, clazz = "/.*/", method = "/.*/"))
    public static void m(@Self Object self,
                         @TargetMethodOrField String method,
                         @ProbeMethodName String probeMethod) { // all calls to the methods with signature "()"
        println(method + " in " + probeMethod);
    }

}
