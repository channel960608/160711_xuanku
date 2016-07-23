package com.shiguang.timefarmer.myapplication3.tool;

/**
 * Created by channel on 2016/7/23.
 */

import android.widget.ImageView;

public class SceneAnimation {
    private ImageView mImageView;
    private int[] mFrameRess;
    private int[] mDurations;
    private int mDuration;
    private Boolean isContinue=true;
    private int mLastFrameNo;
    private long mBreakDelay;

    public SceneAnimation(ImageView pImageView, int[] pFrameRess,
                          int[] pDurations) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDurations = pDurations;
        mLastFrameNo = pFrameRess.length-1;

        mImageView.setBackgroundResource(mFrameRess[0]);
        //play(1);
    }

    public SceneAnimation(ImageView pImageView, int[] pFrameRess, int pDuration) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDuration = pDuration;
        mLastFrameNo = pFrameRess.length-1;

        mImageView.setBackgroundResource(mFrameRess[0]);
        //playConstant(1);
    }

    public SceneAnimation(ImageView pImageView, int[] pFrameRess,
                          int pDuration, long pBreakDelay) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDuration = pDuration;
        mLastFrameNo = pFrameRess.length-1;
        mBreakDelay = pBreakDelay;

        mImageView.setBackgroundResource(mFrameRess[0]);
        playConstant(1);
    }

    public void play(final int pFrameNo) {
        mImageView.postDelayed(new Runnable() {
            public void run() {
                if(isContinue){
                mImageView.setBackgroundResource(mFrameRess[pFrameNo]);
                if (pFrameNo == mLastFrameNo)
                    play(0);
                else
                    play(pFrameNo + 1);
                }
            }
        }, mDurations[pFrameNo]);
    }

    private void playConstant(final int pFrameNo) {
        mImageView.postDelayed(new Runnable() {
            public void run() {
                if(isContinue){
                mImageView.setBackgroundResource(mFrameRess[pFrameNo]);

                if (pFrameNo == mLastFrameNo)
                    playConstant(0);
                else
                    playConstant(pFrameNo + 1);
                }
            }
        }, pFrameNo == mLastFrameNo && mBreakDelay > 0 ? mBreakDelay
                : mDuration);
    }

    public void stop(){
        isContinue=false;
    }
    public void restart(){
        isContinue=true;
        mImageView.setBackgroundResource(mFrameRess[0]);
        play(1);
    }
};