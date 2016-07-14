package com.mobapphome.mahandroidupdater.tools;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;

public class Utils {
	
	public static boolean checkPackageIfExists(Context context, String pckgName)
	{
	    try{
	        ApplicationInfo info = context.getPackageManager().getApplicationInfo(pckgName, 0 );
	        return true;
	    } catch( PackageManager.NameNotFoundException e ){
	        return false;
	    }
	}

	public static int getVersionCode(FragmentActivity act){
		try {
			PackageInfo pInfo = act.getPackageManager().getPackageInfo(act.getPackageName(), 0);
			int versionCode = pInfo.versionCode;
			return versionCode;
		}catch (PackageManager.NameNotFoundException e ){
			return -1;
		}
	}
}





