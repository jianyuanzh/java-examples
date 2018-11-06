package cc.databus.parnew;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * https://bugs.openjdk.java.net/browse/JDK-8079274
 *
 * -Xmx2G -Xms2G -XX:+PrintGCTimeStamps -XX:+PrintGCDetails -XX:+UseParNewGC -XX:+UseConcMarkSweepGC
 */
public class AbnormalParnew {
    static byte[] bigStaticObject;

    public static void main(String[] args) throws Exception {

        System.out.println("Process Id: " + getProcessID());

        int bigObjSize = args.length > 0 ? Integer.parseInt(args[0]) : 524288000;
        int littleObjSize = args.length > 1 ? Integer.parseInt(args[1]) : 100;
        int saveFraction = args.length > 2 ? Integer.parseInt(args[2]) : 1000;
        System.out.println("Start allocate large memory");
        bigStaticObject = new byte[bigObjSize];
        System.out.println("Allocate done");
        List<byte[]> holder = new ArrayList<byte[]>();

        long end = System.currentTimeMillis() + 60000; // trigger full gc 1min later
        int i = 0;
        while (true) {
            byte[] local = new byte[littleObjSize];
            if (i++ % saveFraction == 0) {
                holder.add(local);
            }
        }
    }

    public static final int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0]);
    }

}
