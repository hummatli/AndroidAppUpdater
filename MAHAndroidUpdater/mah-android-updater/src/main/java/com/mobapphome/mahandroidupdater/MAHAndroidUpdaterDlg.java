package com.mobapphome.mahandroidupdater;

import com.mobapphome.mahandroidupdater.tools.ExitListiner;
import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

class AlertDialogClassExit extends Dialog implements
		android.view.View.OnClickListener {

	public FragmentActivity parent;
	ExitListiner exitListiner;
	String updateInfo;

	public AlertDialogClassExit(FragmentActivity a, String updateInfo) {
		super(a);
		// TODO Auto-generated constructor stub
		this.updateInfo = updateInfo;
		this.parent = a;
	}

	@Override
	public Bundle onSaveInstanceState() {
		Bundle bdl = super.onSaveInstanceState();
		bdl.putString("updateInfo", updateInfo);
		return bdl;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("Test", "MAH Dld exit Created ");
		super.onCreate(savedInstanceState);

		if(savedInstanceState != null){
			updateInfo = savedInstanceState.getString("updateInfo");
			Log.i("Test", "Update info from bundle " + updateInfo);
		}
		Log.i("Test", "Update 2 info from bundle " + updateInfo);

		getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mah_ads_dialog_exit);

		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

		Button btnYes = ((Button) findViewById(R.id.mah_updater_dlg_btn_update));
		btnYes.setOnClickListener(this);

		Button btnNo = (Button) findViewById(R.id.mah_updater_dlg_btn_dont_update);
		btnNo.setOnClickListener(this);

		((TextView)findViewById(R.id.tvUpdateInfoTxt)).setText(updateInfo);

		((ImageButton) findViewById(R.id.mah_updater_dlg_btnCancel)).setOnClickListener(this);


		MAHUpdaterController.setFontTextView((TextView) findViewById(R.id.tvTitle));
		MAHUpdaterController.setFontTextView((TextView) findViewById(R.id.tvUpdateInfoTxt));
		MAHUpdaterController.setFontTextView(btnYes);
		MAHUpdaterController.setFontTextView(btnNo);
	}

	public void setExitListiner(ExitListiner exitListiner) {
		this.exitListiner = exitListiner;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.mah_updater_dlg_btnCancel) {
			dismiss();
		} else if (v.getId() == R.id.mah_updater_dlg_btn_update) {
			if (exitListiner != null) {
				exitListiner.onYes();
			}
		} else if (v.getId() == R.id.mah_updater_dlg_btn_dont_update) {
			dismiss();
			if (exitListiner != null) {
				exitListiner.onNo();
			}


//			if(Utils.checkPackageIfExists(parent, pckgName)){
//				PackageManager pack = parent.getPackageManager();
//				Intent app = pack.getLaunchIntentForPackage(pckgName);
//				app.putExtra(MAHUpdaterController.MAH_ADS_INTERNAL_CALLED, true);
//				parent.startActivity(app);
//			}else {
//				if(!pckgName.isEmpty()){
//					Intent marketIntent = new Intent(Intent.ACTION_VIEW);
//					marketIntent.setData(Uri.parse("market://details?id="+pckgName));
//					parent.startActivity(marketIntent);
//				}
//			}
		}
	}
}


public class MAHAndroidUpdaterDlg extends DialogFragment {

	public static String TAG = "DateDialogFragment";
	static Context mContext; 
	ExitListiner exitListiner;

	public void setExitListiner(ExitListiner exitListiner) {
		this.exitListiner = exitListiner;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_BACK) {

                    dismiss();
                    if(exitListiner != null){
                        exitListiner.onNo();
                    }
                    return true;
                }
                return false;
            }
        });

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public static MAHAndroidUpdaterDlg newInstance(Context context, String updateInfo, ExitListiner exitListiner) {
		MAHAndroidUpdaterDlg dialog = new MAHAndroidUpdaterDlg();
		mContext = context;
		dialog.setExitListiner(exitListiner);

		Bundle args = new Bundle();
		args.putString("updateInfo", updateInfo);
		dialog.setArguments(args);
		return dialog;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Bundle args = getArguments();
	    AlertDialogClassExit dlg= new AlertDialogClassExit(getActivity(), args.getString("updateInfo"));
	    dlg.setExitListiner(exitListiner);
	    return dlg;
	}
	
}