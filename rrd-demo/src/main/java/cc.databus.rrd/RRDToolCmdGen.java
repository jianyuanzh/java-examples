package cc.databus.rrd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Example out:
 * <code>
 *  Operate RRD with:
 *   rrdtool create puggo.rrd  --start 1537367623  DS:speed:COUNTER:600:U:U  RRA:AVERAGE:0.5:1:24  RRA:AVERAGE:0.5:6:10
 *   rrdtool update puggo.rrd 1537367923:12535
 *   rrdtool update puggo.rrd 1537368223:12593
 *   rrdtool update puggo.rrd 1537368523:12631
 *   rrdtool update puggo.rrd 1537368823:12791
 *   rrdtool update puggo.rrd 1537369123:12860
 *   rrdtool update puggo.rrd 1537369423:12974
 *   rrdtool update puggo.rrd 1537369723:12991
 *   rrdtool update puggo.rrd 1537370023:13039
 *   rrdtool update puggo.rrd 1537370323:13133
 *   rrdtool update puggo.rrd 1537370623:13320
 *   rrdtool fetch puggo.rrd AVERAGE --start 1537367623 --end 1537370623
 *
 *  Final the query rrd will get:
 *  jianyuan@s01:~$ rrdtool fetch puggo.rrd AVERAGE --start 1537367623 --end 1537370623
 *                           speed
 *
 * 1537367700: -nan
 * 1537368000: -nan
 * 1537368300: 1.7622222222e-01
 * 1537368600: 2.3104444444e-01
 * 1537368900: 4.5547777778e-01
 * 1537369200: 2.6850000000e-01
 * 1537369500: 2.9701111111e-01
 * 1537369800: 8.3188888889e-02
 * 1537370100: 1.9935555556e-01
 * 1537370400: 3.9290000000e-01
 * 1537370700: -nan
 * </code>
 */
public class RRDToolCmdGen {

    private static String CREATE_TEMPLATE = "rrdtool create %s.rrd" +
            "  --start %s" +
            "  DS:speed:COUNTER:600:U:U" +
            "  RRA:AVERAGE:0.5:1:24" +
            "  RRA:AVERAGE:0.5:6:10";

    private static String UPDATE_TEMPLATE = "rrdtool update %s.rrd %s:%s";

    public static void main(String[] args) {
        long start = System.currentTimeMillis() / 1000;
        long current = 12345;
        String rrdName = "puggo";
        String createStr = String.format(CREATE_TEMPLATE, rrdName, start);
        List<String> updateStr = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            current = current + 1000 +  Math.abs(random.nextInt(500));
            updateStr.add(String.format(UPDATE_TEMPLATE, rrdName, start + 300 * (i + 1), current));
        }
        String query = String.format("rrdtool fetch %s.rrd AVERAGE --start %s --end %s", rrdName, start, start + (10 * 300));
        String graph = String.format("rrdtool graph %s.png --start %s --end %s DEF:myspeed=puggo.rrd:speed:AVERAGE LINE 2:myspeed#F^C000",
                rrdName, start, start + (10 * 300));
        System.out.println("Operate RRD with: ");
        System.out.println(" " + createStr);
        for (String update : updateStr) {
            System.out.println(" " + update);
        }
        System.out.println(" " + query);
        System.out.println(graph);

    }
}
