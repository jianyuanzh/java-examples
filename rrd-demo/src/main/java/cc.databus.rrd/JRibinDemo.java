//package cc.databus.rrd;
//
//import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;
//import org.jrobin.core.RrdDef;
//import org.jrobin.core.RrdException;
//import sun.jvm.hotspot.gc_implementation.g1.HeapRegion;
//
//public class JRibinDemo {
//    public static void main(String[] args) throws RrdException {
//        // rrdtool create puggo.rrd  --start 1537367623  DS:speed:COUNTER:600:U:U  RRA:AVERAGE:0.5:1:24  RRA:AVERAGE:0.5:6:10
//        long pollingIntervalInSec = 300;
//        long start = (System.currentTimeMillis() + 500) / 1000;
//        long heartBeat = pollingIntervalInSec * 3;
//
//        RrdDef rrdDef = new RrdDef(".", start - 1, pollingIntervalInSec);
//        rrdDef.addDatasource("data" + 0, "AVERATE", heartBeat, 0, Double.MAX_VALUE);
//        createArchieveFamily(rrdDef, 1, 300); // 5min * 12 * 5 = 5hr
//        createArchieveFamily(rrdDef, ); //  8d
//
//    }
//
//    private static void createArchieveFamily(final RrdDef rrdDef, final int steps, final int rows) throws RrdException {
//        rrdDef.addArchive("AVERAGE", 0.5, steps, rows);
//        rrdDef.addArchive("MIN", 0.5, steps, rows);
//        rrdDef.addArchive("MAX", 0.5, steps, rows);
//    }
//}
