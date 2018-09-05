package cc.databus.aspect.intertype;

import org.junit.Test;

public class TestPointAspect {

    @Test
    public void test() {
        Point point = new Point(1,1);
        point.setName("test");
        point.setX(12);
        point.setY(123);
        System.out.println(point);
    }
}
