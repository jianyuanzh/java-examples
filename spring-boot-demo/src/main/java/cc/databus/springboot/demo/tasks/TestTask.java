package cc.databus.springboot.demo.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TestTask {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    // schedule every 30 seconds
    @Scheduled(fixedRate = 30000)
    public void reportCurrentTime() {
        System.out.println("now: " + dateFormat.format(new Date()) ) ;
    }
}
