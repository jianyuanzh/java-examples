import org.joda.time.DateTime;
import org.junit.Test;

public class TestDateTime {

    @Test
    public void test() {
        DateTime time = new DateTime();
        System.out.println(time);
        DateTime after = time.minusHours(3);
        System.out.println(after);
    }
}
