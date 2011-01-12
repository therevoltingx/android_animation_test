package com.solrpg.animationtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DefaultActivity extends Activity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MyAnimationView anim_view = (MyAnimationView) this.findViewById(R.id.anim_view);
        anim_view.loadAnimation("shark", 16);
        
        Button play_button = (Button) this.findViewById(R.id.play_button);
        
        final MyAnimationView f_anim_view = anim_view; //to avoid warning, may be wrong
        play_button.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v) 
			{
				f_anim_view.playAnimation();
			}
		});
    }
}