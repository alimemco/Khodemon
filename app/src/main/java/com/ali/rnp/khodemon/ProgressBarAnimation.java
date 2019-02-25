package com.ali.rnp.khodemon;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class ProgressBarAnimation extends Animation {
    private MaterialProgressBar progressBar;
    private float from;
    private float  to;

    public ProgressBarAnimation(MaterialProgressBar progressBar, float from, float to) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
    }
}