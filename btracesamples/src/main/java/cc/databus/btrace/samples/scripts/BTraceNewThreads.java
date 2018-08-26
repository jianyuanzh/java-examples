package cc.databus.btrace.samples.scripts;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;

import java.util.concurrent.atomic.AtomicLong;

import static com.sun.btrace.BTraceUtils.println;

@BTrace
public class BTraceNewThreads {

    private static long newThreadCount = 0;
    private static long startThreadCount = 0;

    @OnMethod(clazz = "java.lang.Thread", method = "<init>")
    public static void onNewThread(@ProbeClassName String probeName, @ProbeMethodName String pmn) {
        newThreadCount++;
        println("Construct new thread, total=" + newThreadCount );
        println(probeName + "#" + pmn);
    }

    @OnMethod(clazz = "java.lang.Thread", method = "start")
    public static void onStartThread() {
        startThreadCount++;
        println("Start a thread, total=" + startThreadCount);
    }
}
