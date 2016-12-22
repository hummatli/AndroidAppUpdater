package com.mobapphome.mahandroidupdater.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import com.mobapphome.mahandroidupdater.MAHRestricterDlg;
import com.mobapphome.mahandroidupdater.MAHUpdaterDlg;
import com.mobapphome.mahandroidupdater.R;
import com.mobapphome.mahandroidupdater.types.ProgramInfo;
import com.mobapphome.mahandroidupdater.types.DlgModeEnum;

public class MAHUpdaterController {
    public static String urlService;
    private static SharedPreferences sharedPref;
    private static String fontName = null;
    private static FragmentActivity act;
    private static boolean initCalled = false;

    private static boolean btnInfoVisibility;
    private static String btnInfoMenuItemTitle;
    private static String btnInfoActionURL;

    /**
     * Initializes MAHAndroidUpdater library
     * @param act Activity which init calls
     * @param urlService Url for services which data about update has placed.
     */
    public static void init(final FragmentActivity act,
                            String urlService) throws NullPointerException {
        MAHUpdaterController.init(act, urlService,true);
    }

    /**
     * Initializes MAHAndroidUpdater library
     * @param act Activity which init calls
     * @param urlService Url for services which data about update has placed.
     * @param btnInfoVisibility If true shows info button
     */
    public static void init(final FragmentActivity act,
                            String urlService,
                            boolean btnInfoVisibility) throws NullPointerException {
        MAHUpdaterController.init(
                act,
                urlService,
                btnInfoVisibility,
                act.getString(R.string.mah_android_upd_info_popup_text),
                Constants.MAH_UPD_GITHUB_LINK);
    }

    /**
     * Initializes MAHAndroidUpdater library
     * @param act Activity which init calls
     * @param urlService Url for services which data about update has placed.
     * @param btnInfoVisibility If true shows info button
     * @param btnInfoMenuItemTitle Title of menu item for info button
     * @param btnInfoActionURL Url to open when clicking to info button or info menu item
     */
    public static void init(
            final FragmentActivity act,
            String urlService,
            boolean btnInfoVisibility,
            String btnInfoMenuItemTitle,
            @NonNull String btnInfoActionURL) throws NullPointerException {
        if (initCalled) {
            return;
        }

        MAHUpdaterController.btnInfoVisibility = btnInfoVisibility;
        MAHUpdaterController.btnInfoMenuItemTitle = btnInfoMenuItemTitle;
        MAHUpdaterController.btnInfoActionURL = btnInfoActionURL;

        MAHUpdaterController.urlService = urlService;
        MAHUpdaterController.act = act;
        if (urlService == null) {
            throw new NullPointerException("urlService not set call init(final Activity act, String urlService) constructor");
        }

        sharedPref = act.getPreferences(Context.MODE_PRIVATE);

        Updater updater = new Updater();
        updater.setUpdaterListiner(new UpdaterListener() {

            @Override
            public void onResponse(ProgramInfo programInfo, final String errorStr) {
                if (programInfo == null) {
                    Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAhUpdater Program info is null");
                    return;
                } else if (!programInfo.isRunMode()) {
                    Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAHUpdter run mode is false");
                    return;
                } else if (programInfo.getUriCurrent() == null) {
                    Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAHUpdter uri_current is null in service. check service");
                    return;
                } else if (programInfo.getVersionCodeCurrent() == -1) {
                    Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAHUpdter version_code_current is -1 in service. check service");
                    return;
                } else {
					Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Uri current from service = " + programInfo.getUriCurrent() + "  Uri from package" + act.getApplicationContext().getPackageName());
					Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Version from service = " + programInfo.getVersionCodeCurrent() + "  Version from package" + Utils.getVersionCode(act));

                    boolean isRestrictedDlg = false;
                    if (Utils.getVersionCode(act) < programInfo.getVersionCodeMin()) {
                        isRestrictedDlg = true;
                    }
                    if (!programInfo.getUriCurrent().equals(act.getApplicationContext().getPackageName())) {
                        if (Utils.checkPackageIfExists(act, programInfo.getUriCurrent())) {
                            showRestricterDlg(act, DlgModeEnum.OPEN_NEW, programInfo);
                        } else if (isRestrictedDlg) {
                            showRestricterDlg(act, DlgModeEnum.INSTALL, programInfo);
                        } else {
                            showUpdaterDlg(act, DlgModeEnum.INSTALL, programInfo);
                        }
                    } else if (Utils.getVersionCode(act) < programInfo.getVersionCodeCurrent()) {
                        if (isRestrictedDlg) {
                            showRestricterDlg(act, DlgModeEnum.UPDATE, programInfo);
                        } else {
                            showUpdaterDlg(act, DlgModeEnum.UPDATE, programInfo);
                        }
                    } else {
                        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAHUpdater: There are not any update in service");
                    }
                }
            }
        });
        updater.updateProgramList(act);
        initCalled = true;
    }

    public static void callUpdate() throws NullPointerException {
        if (MAHUpdaterController.urlService == null) {
            throw new NullPointerException("urlService not set call init(final Activity act, String urlService) constructor");
        }


        Updater updater = new Updater();
        updater.setUpdaterListiner(new UpdaterListener() {

            @Override
            public void onResponse(ProgramInfo programInfo, final String errorStr) {

            }
        });
        updater.updateProgramList(act);
    }


    public static void end() {
        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAHUpdater end called");
        callUpdate();
        initCalled = false;
    }

    static public void testUpdaterDlg(final FragmentActivity act){
        final ProgramInfo prInfoTest = new ProgramInfo();
        prInfoTest.setUpdateInfo("Update info test mode.");
        showUpdaterDlg(act, DlgModeEnum.TEST, prInfoTest);
    }

    static public void testRestricterDlg(final FragmentActivity act){
        final ProgramInfo prInfoTest = new ProgramInfo();
        prInfoTest.setUpdateInfo("Update info test mode. ");
        showRestricterDlg(act, DlgModeEnum.TEST, prInfoTest);
    }

    static private void showUpdaterDlg(final FragmentActivity act, DlgModeEnum mode, final ProgramInfo programInfo) {
        showDlg(act,
                MAHUpdaterDlg.newInstance(programInfo, mode, btnInfoVisibility, btnInfoMenuItemTitle, btnInfoActionURL),
                "fragment_android_updater_dlg");
    }

    static private void showRestricterDlg(final FragmentActivity act, DlgModeEnum mode, final ProgramInfo programInfo) {
        showDlg(act,
                MAHRestricterDlg.newInstance(programInfo, mode, btnInfoVisibility, btnInfoMenuItemTitle, btnInfoActionURL),
                "fragment_restricter_dlg");
    }

    static private void showDlg(FragmentActivity activity, Fragment frag, String fragTag) {

        if (!activity.isFinishing()) {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            Fragment fr = fragmentManager.findFragmentByTag(fragTag);
            if (fr != null && !fr.isHidden()) {
                Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "showDlg  dismissed");
                ((DialogFragment) fr).dismissAllowingStateLoss();
            }

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frag, fragTag);
            transaction.commitAllowingStateLoss();
        }
    }

    protected static SharedPreferences getSharedPref() {
        return sharedPref;
    }

    public static void setFontTextView(TextView tv) {
        if (fontName == null) {
            return;
        }
        try {
            Typeface font = Typeface.createFromAsset(tv.getContext().getAssets(), fontName);
            tv.setTypeface(font);
        } catch (RuntimeException r) {
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
