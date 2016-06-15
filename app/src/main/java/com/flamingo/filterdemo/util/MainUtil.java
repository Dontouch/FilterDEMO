package com.flamingo.filterdemo.util;

/**
 * Created by Dontouch on 16/6/15.
 */
public class MainUtil{

        public static Object evaluate(float fraction, Object startValue,
                                      Object endValue) {
            int startInt = (Integer) startValue;
            int startA = (startInt >> 24) & 0xff;
            int startR = (startInt >> 16) & 0xff;
            int startG = (startInt >> 8) & 0xff;
            int startB = startInt & 0xff;
            int endInt = (Integer) endValue;
            int endA = (endInt >> 24) & 0xff;
            int endR = (endInt >> 16) & 0xff;
            int endG = (endInt >> 8) & 0xff;
            int endB = endInt & 0xff;
            return (int) ((startA + (int) (fraction * (endA - startA))) << 24)
                    | (int) ((startR + (int) (fraction * (endR - startR))) << 16)
                    | (int) ((startG + (int) (fraction * (endG - startG))) << 8)
                    | (int) ((startB + (int) (fraction * (endB - startB))));
        }
}
