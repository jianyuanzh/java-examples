package cc.databus.aspect;

import org.joda.time.DateTime;

public aspect DateTimeAspect {
    pointcut minusHours(int hours, DateTime time):
            call(DateTime DateTime.minusHours(int)) && args(hours) && target(time);

    before(int hours, DateTime time): minusHours(hours, time) {
        System.out.println("minus " + hours + " hours for " + time);
    }

    after(int hours, DateTime time) returning(Object r): minusHours(hours, time) {
        System.out.println("After minus " + hours + " hours, it is " + r + " now");
    }
}
