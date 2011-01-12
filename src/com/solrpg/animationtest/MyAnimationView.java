/**
 * 
 */
package com.solrpg.animationtest;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.ImageView;

/**
 * @author miguelmorales
 *
 */
public class MyAnimationView extends ImageView
{
	private static final String TAG = "AnimationTest:AnimationView";
	private Context mContext = null;
	
	private static final int DELAY = 100; //delay between frames in milliseconds
	
	private boolean mIsPlaying = false;
	private boolean mStartPlaying = false;
	
	private ArrayList<Bitmap> mBitmapList = new ArrayList<Bitmap>();
	
	private int play_frame = 0;
    private long last_tick = 0;
    
    public MyAnimationView(Context context, AttributeSet attrs)
    {
    	super(context, attrs);
    	mContext = context;
    }
    
    @Override
    protected void onDraw(Canvas c)
    {
        Log.d(TAG, "onDraw called");
        if (mStartPlaying)
        {
            Log.d(TAG, "starting animation...");
            play_frame = 0;
            mStartPlaying = false;
            mIsPlaying = true;
            postInvalidate();
        }
        else if (mIsPlaying)
        {
            
            if (play_frame >= mBitmapList.size())
            {
                mIsPlaying = false;
            }
            else
            {
                long time = (System.currentTimeMillis() - last_tick);
                int draw_x = 0;
                int draw_y = 0;
                if (time >= DELAY) //the delay time has passed. set next frame
                {
                    last_tick = System.currentTimeMillis();
                    c.drawBitmap(mBitmapList.get(play_frame), draw_x, draw_y, null);
                    play_frame++;
                    postInvalidate();
                }
                else //still within delay.  redraw current frame
                {
                    c.drawBitmap(mBitmapList.get(play_frame), draw_x, draw_y, null);
                    postInvalidate();
                }
            }
        }
    }
    
    /*ideally this should be in a background thread*/
    public void loadAnimation(String prefix, int nframes)
    {
    	mBitmapList.clear();
        for (int x = 0; x < nframes; x++)
        {
        	String name = prefix + "_" + x;
        	Log.d(TAG, "loading animation frame: " + name);
        	int res_id = mContext.getResources().getIdentifier(name, "drawable", mContext.getPackageName());
        	BitmapDrawable d = (BitmapDrawable) mContext.getResources().getDrawable(res_id);
        	mBitmapList.add(d.getBitmap());
        }
    }
    
    public void playAnimation()
    {
    	mStartPlaying = true;
    	postInvalidate();
    }
}
