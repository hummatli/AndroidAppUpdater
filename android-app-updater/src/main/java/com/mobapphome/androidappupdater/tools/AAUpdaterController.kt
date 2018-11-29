package com.mobapphome.androidappupdater.tools

import android.content.Context
import android.content.SharedPreferences
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.Log

import com.mobapphome.androidappupdater.AAUpdaterRestricterDlg
import com.mobapphome.androidappupdater.AAUpdaterDlg
import com.mobapphome.androidappupdater.R

object AAUpdaterController {
    var urlService: String? = null
    var updateInfoResolver: IUpdateInfoResolver? = null
    var sharedPref: SharedPreferences? = null
    var fontName: String? = null
    private var act: FragmentActivity? = null
    private var initCalled = false

    private var btnInfoVisibility: Boolean = false
    private var btnInfoMenuItemTitle: String? = null
    private var btnInfoActionURL: String? = null

    /**
     * Initializes MAHAndroidUpdater library
     * @param act Activity which init calls
     * *
     * @param urlService Url for services which data about update has placed.
     * *
     * @param updateInfoResolver Object wich implementing it you can get data from your own structed web service.
     * *
     * @param btnInfoVisibility If true shows info button
     * *
     * @param btnInfoMenuItemTitle Title of menu item for info button
     * *
     * @param btnInfoActionURL Url to open when clicking to info button or info menu item
     */
    @Throws(NullPointerException::class)
    @JvmStatic
    @JvmOverloads
    fun init(
            act: FragmentActivity,
            urlService: String?,
            updateInfoResolver: IUpdateInfoResolver? = null,
            btnInfoVisibility: Boolean = true,
            btnInfoMenuItemTitle: String = act.getString(R.string.android_app_upd_info_popup_text),
            btnInfoActionURL: String = Constants.MAH_UPD_GITHUB_LINK) {
        if (initCalled) {
            return
        }

        AAUpdaterController.btnInfoVisibility = btnInfoVisibility
        AAUpdaterController.btnInfoMenuItemTitle = btnInfoMenuItemTitle
        AAUpdaterController.btnInfoActionURL = btnInfoActionURL

        AAUpdaterController.urlService = urlService
        AAUpdaterController.updateInfoResolver = updateInfoResolver
        AAUpdaterController.act = act

        if (urlService == null && updateInfoResolver == null) {
            throw NullPointerException("At least one of these urlService or updateInfoResolver variables " + "\n has to be set in init(final Activity act, String urlService, IUpdateInfoResolver updateInfoResolver) method")
        }

        if (urlService != null && updateInfoResolver != null) {
            throw RuntimeException("Can't use urlService and updateInfoResolver at the same time, choose one")
        }

        sharedPref = act.getPreferences(Context.MODE_PRIVATE)

        val updater = Updater()
        updater.updaterListiner = object : UpdaterListener {

            override fun onResponse(programInfo: ProgramInfo?, errorStr: String) {
                when {
                    programInfo == null -> Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAhUpdater Program info is null")
                    !programInfo.isRunMode -> Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAHUpdter run mode is false")
                    programInfo.uriCurrent == null -> Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAHUpdter uri_current is null in service. check service")
                    programInfo.versionCodeCurrent == -1 -> Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAHUpdter version_code_current is -1 in service. check service")
                    else -> {
                        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Uri current from service = " + programInfo.uriCurrent + "  Uri from package" + act.applicationContext.packageName)
                        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Version from service = " + programInfo.versionCodeCurrent + "  Version from package" + getVersionCode(act))

                        val isRestrictedDlg = if (getVersionCode(act) < programInfo.versionCodeMin) true else false

                        when {
                            programInfo.uriCurrent != act.applicationContext.packageName ->
                                when {
                                    checkPackageIfExists(act, programInfo.uriCurrent) -> showRestricterDlg(act, DlgModeEnum.OPEN_NEW, programInfo)
                                    isRestrictedDlg -> showRestricterDlg(act, DlgModeEnum.INSTALL, programInfo)
                                    else -> showUpdaterDlg(act, DlgModeEnum.INSTALL, programInfo)
                                }
                            getVersionCode(act) < programInfo.versionCodeCurrent ->
                                when {
                                    isRestrictedDlg -> showRestricterDlg(act, DlgModeEnum.UPDATE, programInfo)
                                    else -> showUpdaterDlg(act, DlgModeEnum.UPDATE, programInfo)
                                }
                            else -> Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAHUpdater: There are not any update in service")
                        }
                    }
                }
            }
        }

        updater.updateProgramList(act)
        initCalled = true
    }

    @Throws(NullPointerException::class)
    fun callUpdate() {
        if (AAUpdaterController.urlService == null && AAUpdaterController.updateInfoResolver == null) {
            return
            //throw new NullPointerException("urlService not set call init(final Activity act, String urlService) constructor");
        }

        val updater = Updater()
        updater.updaterListiner = null /*object : UpdaterListener {

            override fun onResponse(programInfo: ProgramInfo?, errorStr: String) {
            }
        }*/

        updater.updateProgramList(act!!)
    }

    @JvmStatic
    @JvmOverloads
    fun end() {
        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAHUpdater end called")
        callUpdate()
        initCalled = false
    }

    @JvmStatic
    fun testUpdaterDlg(act: FragmentActivity) {
        val prInfoTest = ProgramInfo(updateInfo = "Update info test mode.")
        showUpdaterDlg(act, DlgModeEnum.TEST, prInfoTest)
    }

    @JvmStatic
    fun testRestricterDlg(act: FragmentActivity) {
        val prInfoTest = ProgramInfo(updateInfo = "Update info test mode. ")
        showRestricterDlg(act, DlgModeEnum.TEST, prInfoTest)
    }

    private fun showUpdaterDlg(act: FragmentActivity, mode: DlgModeEnum, programInfo: ProgramInfo) {
        showDlg(act,
                AAUpdaterDlg.newInstance(programInfo, mode, btnInfoVisibility, btnInfoMenuItemTitle, btnInfoActionURL),
                "fragment_android_updater_dlg")
    }

    private fun showRestricterDlg(act: FragmentActivity, mode: DlgModeEnum, programInfo: ProgramInfo) {
        showDlg(act,
                AAUpdaterRestricterDlg.newInstance(programInfo, mode, btnInfoVisibility, btnInfoMenuItemTitle, btnInfoActionURL),
                "fragment_restricter_dlg")
    }

    private fun showDlg(activity: FragmentActivity, frag: Fragment, fragTag: String) {

        if (!activity.isFinishing) {
            val fragmentManager = activity.supportFragmentManager
            val fr = fragmentManager.findFragmentByTag(fragTag)
            if (fr != null && !fr.isHidden) {
                Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "showDlg  dismissed")
                (fr as DialogFragment).dismissAllowingStateLoss()
            }

            val transaction = fragmentManager.beginTransaction()
            transaction.add(frag, fragTag)
            transaction.commitAllowingStateLoss()
        }
    }
}
