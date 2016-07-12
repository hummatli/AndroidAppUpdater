package com.mobapphome.mahandroidupdater.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import com.mobapphome.mahandroidupdater.MAHAndrUpdaterDlg;
import com.mobapphome.mahandroidupdater.R;
import com.mobapphome.mahandroidupdater.types.ProgramInfo;

public class MAHUpdaterController {
	public static String urlRootOnServer;
	private static SharedPreferences sharedPref;
	private static String fontName = null;


	public static void init(final FragmentActivity act, String urlRootOnServer) throws NullPointerException{
		MAHUpdaterController.urlRootOnServer = urlRootOnServer;
		if(urlRootOnServer == null){
			throw new NullPointerException("urlRootOnServer not set call init(final Activity act, String urlRootOnServer) constructor");
		}

		sharedPref = act.getPreferences(Context.MODE_PRIVATE);

		Updater updater = new Updater();
		updater.setUpdaterListiner(new UpdaterListener() {

			@Override
			public void onResponse(ProgramInfo programInfo, final String errorStr) {
				if(programInfo != null){
					Log.i("Test", "Uri current from service = " + programInfo.getUriCurrent() + "  Uri from package" + act.getApplicationContext().getPackageName());
					Log.i("Test", "Version from service = " + programInfo.getVersionOnPlayMarket() + "  Version from package" + Utils.getVersionNumber(act));

					if(!programInfo.getUriCurrent().equals(act.getApplicationContext().getPackageName())){
						showDlg(act, "Application has moved to new place");
					}	else if(!programInfo.getVersionOnPlayMarket().equals(Utils.getVersionNumber(act))){
						showDlg(act, "Version Number is different");
					}else {
						Log.i("Test", "All is ok with packages");
					}
				}else {
					Log.i("Test", "Program info is null");
				}
			}
		});
		updater.updateProgramList(act);
	}

	static public void showDlg(FragmentActivity act, String updateInfo){
		FragmentTransaction transaction = act.getSupportFragmentManager().beginTransaction();
		MAHAndrUpdaterDlg mahAndrUpdaterDlg = MAHAndrUpdaterDlg.newInstance(updateInfo, new ExitListiner() {
			@Override
			public void onYes() {

			}

			@Override
			public void onNo() {

			}
		});

		//transaction.setTransitionStyle(R.style.DialogAnimation);
		transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
		transaction.add(mahAndrUpdaterDlg,  "fragment_edit_name");


		transaction.commitAllowingStateLoss();
		Log.i("Test", "Update info"+ updateInfo);
	}


	protected static SharedPreferences getSharedPref() {
		return sharedPref;
	}

	public static void setFontTextView(TextView tv) {
		if(fontName == null){
			return;
		}
		try{
			Typeface font = Typeface.createFromAsset(tv.getContext().getAssets(),fontName);
			tv.setTypeface(font);
		}catch(RuntimeException r){
			Log.e("test", "Error " + r.getMessage());
		}
	}

	public static String getFontName() {
		return fontName;
	}

	public static void setFontName(String fontName) {
		MAHUpdaterController.fontName = fontName;
	}
}
