package com.company;

import java.util.Timer;

/**
 * Created by zZzZz on 17.07.2018.
 */
public class Time {
    private Timer timer = new Timer();

    void start() {
        timer.schedule(new LogListener(), 0, 5000);
    }

    void stop() {
        timer.cancel();
    }

}
