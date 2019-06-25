package com.neoress.typics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

/**
 * Created by neoreul on 3/28/17.
 */

public class TrackingSpan extends ReplacementSpan {
    private float mTrackingPx;

    public TrackingSpan(float tracking) {
        mTrackingPx = tracking;
    }

    @Override
    public int getSize(Paint paint, CharSequence text,
                       int start, int end, Paint.FontMetricsInt fm) {
        return (int) (paint.measureText(text, start, end)
                + mTrackingPx * (end - start - 1));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text,
                     int start, int end, float x, int top, int y,
                     int bottom, Paint paint) {
        float dx = x;
        for (int i = start; i < end; i++) {
            canvas.drawText(text, i, i + 1, dx, y, paint);
            dx += paint.measureText(text, i, i + 1) + mTrackingPx;
        }
    }
}