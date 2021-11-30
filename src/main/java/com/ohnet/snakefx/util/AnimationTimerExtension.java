package com.ohnet.snakefx.util;

import javafx.animation.AnimationTimer;

/**
 * Util class to get an delay to the handle method
 */
public abstract class AnimationTimerExtension extends AnimationTimer {

    private long sleepNs = 0;
    private long prevTime = 0;
    private static final int  MILLISEC_TO_NANOSEC = 1_000_000;

    protected AnimationTimerExtension( long sleepMs) {
        this.sleepNs = sleepMs * MILLISEC_TO_NANOSEC;
    }

    @Override
    public void handle(long now) {
        // some delay
        if ((now - prevTime) < sleepNs) {
            return;
        }

        prevTime = now;

        handle();
    }

    public abstract void handle();
}
