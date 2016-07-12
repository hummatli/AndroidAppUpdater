package com.mobapphome.mahandroidupdater.tools.gui;

import com.mobapphome.mahandroidupdater.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class Loading extends ProgressDialog {
  private Animation animation;
  private ImageView image;
  private Context m_context;

  public static ProgressDialog ctor(Context context) {
    Loading dialog = new Loading(context);
    dialog.setIndeterminate(true);
    dialog.setCancelable(false);
    return dialog;
  }

  public Loading(Context context) {
    super(context);
    m_context = context;
  }

  public Loading(Context context, int theme) { 
    super(context, theme);
    m_context = context;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.progress_dialog);

    image  = (ImageView) findViewById(R.id.progressAnimationImage);
	animation = new RotateAnimation(0.0f, 360.0f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f);
	//anticipate_interpolator
	//anticipate_overshoot_interpolator
	//animation.setInterpolator(,android.R.anim.anticipate_overshoot_interpolator);
	//animation.setDuration(600);
	animation.setDuration(350);
	animation.setInterpolator(new LinearInterpolator());
    animation.setRepeatCount(Animation.INFINITE);
    
    
  }

  
  
  @Override
  public void show() {
    super.show();
    //animation.start();
    image.startAnimation(animation);
  }

  @Override
  public void dismiss() {
    super.dismiss();
   
  }
}
