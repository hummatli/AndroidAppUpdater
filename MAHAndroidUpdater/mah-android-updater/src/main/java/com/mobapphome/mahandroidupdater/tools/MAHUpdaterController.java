package com.mobapphome.mahandroidupdater.tools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import com.mobapphome.mahandroidupdater.MAHAndrUpdaterDlg;
import com.mobapphome.mahandroidupdater.R;
import com.mobapphome.mahandroidupdater.types.ProgramInfo;
import com.mobapphome.mahandroidupdater.types.UpdaterDlgTypeEnum;

public class MAHUpdaterController {
	public static String urlRootOnServer;
	private static SharedPreferences sharedPref;
	private static String fontName = null;
	private static FragmentActivity act;
	private static boolean initCalled = false;


	public static void init(final FragmentActivity act, String urlRootOnServer) throws NullPointerException{
		if(initCalled){
			return;
		}

		MAHUpdaterController.urlRootOnServer = urlRootOnServer;
		MAHUpdaterController.act = act;
		if(urlRootOnServer == null){
			throw new NullPointerException("urlRootOnServer not set call init(final Activity act, String urlRootOnServer) constructor");
		}

		sharedPref = act.getPreferences(Context.MODE_PRIVATE);

		Updater updater = new Updater();
		updater.setUpdaterListiner(new UpdaterListener() {

			@Override
			public void onResponse(ProgramInfo programInfo, final String errorStr) {
				if(programInfo == null) {
					Log.i("Test", "MAhUpdater Program info is null");
					return;
				}else if(!programInfo.isRunMode()){
					Log.i("Test", "MAHUpdter run mode is false");
					return;
				}else if(programInfo.getUriCurrent() == null){
					Log.i("Test", "MAHUpdter uri_current is null in service. check servcie");
					return;
				}else if(programInfo.getVersionOnPlayMarket() == null){
					Log.i("Test", "MAHUpdter version_on_market is null in service. check servcie");
					return;
				}else{
//					Log.i("Test", "Uri current from service = " + programInfo.getUriCurrent() + "  Uri from package" + act.getApplicationContext().getPackageName());
//					Log.i("Test", "Version from service = " + programInfo.getVersionOnPlayMarket() + "  Version from package" + Utils.getVersionNumber(act));

					if(!programInfo.getUriCurrent().equals(act.getApplicationContext().getPackageName())){
						showDlg(act, UpdaterDlgTypeEnum.INSTALL, programInfo);
					}	else if(!programInfo.getVersionOnPlayMarket().equals(Utils.getVersionNumber(act))){
						showDlg(act, UpdaterDlgTypeEnum.UPDATE, programInfo);
					}else {
						Log.i("Test", "MAHUpdater: There are not any update in service");
					}
				}
			}
		});
		updater.updateProgramList(act);
		initCalled = true;
	}

	public static void callUpdate() throws NullPointerException{
		if(MAHUpdaterController.urlRootOnServer == null){
			throw new NullPointerException("urlRootOnServer not set call init(final Activity act, String urlRootOnServer) constructor");
		}


		Updater updater = new Updater();
		updater.setUpdaterListiner(new UpdaterListener() {

			@Override
			public void onResponse(ProgramInfo programInfo, final String errorStr) {

			}
		});
		updater.updateProgramList(act);
	}


	static public void showDlg(final FragmentActivity act, UpdaterDlgTypeEnum type, final ProgramInfo programInfo){
		FragmentTransaction transaction = act.getSupportFragmentManager().beginTransaction();
		MAHAndrUpdaterDlg mahAndrUpdaterDlg = MAHAndrUpdaterDlg.newInstance(programInfo.getUpdateInfo(), type, new ExitListiner() {
			@Override
			public void onYes() {
				if(!programInfo.getUriCurrent().isEmpty()){
					Intent marketIntent = new Intent(Intent.ACTION_VIEW);
					marketIntent.setData(Uri.parse("market://details?id="+programInfo.getUriCurrent()));
					act.startActivity(marketIntent);
				}
			}

			@Override
			public void onNo() {

			}
		});

		//transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
		// This animation works only when fragment adds to view.
		// If fragment does not add to view then other type animation is available
		transaction.add(mahAndrUpdaterDlg,  "fragment_android_updater_dlg");


		transaction.commitAllowingStateLoss();
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
