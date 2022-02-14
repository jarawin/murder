package utils;

import java.util.concurrent.TimeUnit;

public class USleep {

    public USleep(int msec) {
        try {
            TimeUnit.MICROSECONDS.sleep(msec);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
