/**
 * @author dingdj
 * Date:2014-3-18上午8:23:52
 *
 */
package com.ddj.launcher2.core;

import android.animation.TimeInterpolator;

/*
 * This interpolator emulates the rate at which the perceived scale of an object changes
 * as its distance from a camera increases. When this interpolator is applied to a scale
 * animation on a view, it evokes the sense that the object is shrinking due to moving away
 * from the camera.
 */
public class ZInterpolator implements TimeInterpolator {
    private float focalLength;

    public ZInterpolator(float foc) {
        focalLength = foc;
    }

    public float getInterpolation(float input) {
        return (1.0f - focalLength / (focalLength + input)) /
            (1.0f - focalLength / (focalLength + 1.0f));
    }
}
