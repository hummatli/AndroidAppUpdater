package com.mobapphome.androidappupdater

/**
 * Created by settar on 7/12/16.
 */


import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.PopupMenu
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.Toast
import com.google.gson.Gson
import com.mobapphome.androidappupdater.commons.setControllerFont
import com.mobapphome.androidappupdater.tools.Constants
import com.mobapphome.androidappupdater.tools.DlgModeEnum
import com.mobapphome.androidappupdater.tools.AAUpdaterController
import com.mobapphome.androidappupdater.tools.ProgramInfo
import kotlinx.android.synthetic.main.aaupdater_restricter_dlg.*

class AAUpdaterRestricterDlg : DialogFragment() {

    internal var programInfo: ProgramInfo? = null
    internal var type: DlgModeEnum? = null

    internal var btnInfoVisibility: Boolean = false
    internal var btnInfoMenuItemTitle: String? = null
    internal var btnInfoActionURL: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MAHRestricterDlg)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAH Restricter Dlg Created ")
        val gson = Gson()
        programInfo = gson.fromJson(arguments!!.getString("programInfo"), ProgramInfo::class.java)
        type = arguments!!.getSerializable("type") as DlgModeEnum
        btnInfoVisibility = arguments!!.getBoolean("btnInfoVisibility")
        btnInfoMenuItemTitle = arguments!!.getString("btnInfoMenuItemTitle")
        btnInfoActionURL = arguments!!.getString("btnInfoActionURL")

        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Updateinfo from bundle " + programInfo?.updateInfo)

        dialog.window!!.attributes.windowAnimations = R.style.MAHUpdaterDialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnKeyListener { dialog, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                onClose()
                true
            }
            false
        }

        return inflater.inflate(R.layout.aaupdater_restricter_dlg, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tvUpdateInfo.text = programInfo?.updateInfo
        tvUpdateInfo.visibility = if (programInfo?.updateInfo != null) View.VISIBLE else View.GONE
        imgBtnInfo.visibility = if (btnInfoVisibility) View.VISIBLE else View.INVISIBLE


        imgBtnCancel.setOnClickListener { onClose() }
        btnUpdate.setOnClickListener { onYes() }
        btnDontUpdate.setOnClickListener { onNo() }
        imgBtnInfo.setOnClickListener { v ->
            val itemIdForInfo = 1
            val popup = PopupMenu(context!!, v)
            popup.menu.add(Menu.NONE, itemIdForInfo, 1, btnInfoMenuItemTitle)

            // registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener { item ->
                if (item.itemId == itemIdForInfo) {
                    showMAHlib()
                }
                true
            }

            popup.show()// showing popup menu
        }


        when (type) {
            DlgModeEnum.UPDATE -> {
                btnUpdate.text = resources.getText(R.string.cmnd_verb_android_app_upd_dlg_btn_yes_update_txt)
                btnDontUpdate.text = resources.getText(R.string.cmnd_verb_android_app_upd_dlg_btn_no_close_txt)
                tvInfoTxt.text = resources.getText(R.string.android_app_upd_restricter_info_update)
            }

            DlgModeEnum.INSTALL -> {
                btnUpdate.text = resources.getText(R.string.cmnd_verb_android_app_upd_dlg_btn_yes_install_txt)
                btnDontUpdate.text = resources.getText(R.string.cmnd_verb_android_app_upd_dlg_btn_no_close_txt)
                tvInfoTxt.text = resources.getText(R.string.android_app_upd_restricter_info_install)
            }

            DlgModeEnum.OPEN_NEW -> {
                btnUpdate.text = resources.getText(R.string.android_app_upd_dlg_btn_yes_open_new_txt)
                btnDontUpdate.text = resources.getText(R.string.cmnd_verb_android_app_upd_dlg_btn_no_uninstall_old_txt)
                tvInfoTxt.text = resources.getText(R.string.android_app_upd_restricter_info_open_new_version)
                tvUpdateInfo.visibility = View.GONE
            }

            DlgModeEnum.TEST -> {
                btnUpdate.text = resources.getText(R.string.cmnd_verb_android_app_upd_dlg_btn_yes_update_txt)
                btnDontUpdate.text = resources.getText(R.string.cmnd_verb_android_app_upd_dlg_btn_no_close_txt)
                tvInfoTxt.text = resources.getText(R.string.android_app_upd_restricter_info_update)
            }

            else -> {
            }
        }

        //Minimize the lines of question textview in  languages where question str is longer
        if (getString(R.string.noun_android_app_upd_dlg_title).length > 20) {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        }

        btnUpdate.setControllerFont()
        btnDontUpdate.setControllerFont()
        tvTitle.setControllerFont()
        tvInfoTxt.setControllerFont()
    }

    fun onYes() =
            when (type) {
                DlgModeEnum.OPEN_NEW -> {
                    val pack = activity!!.packageManager
                    val app = pack.getLaunchIntentForPackage(programInfo!!.uriCurrent)
                    activity!!.startActivity(app)
                }

                DlgModeEnum.INSTALL, DlgModeEnum.UPDATE ->
                    if (!programInfo?.uriCurrent!!.isEmpty()) {
                        val marketIntent = Intent(Intent.ACTION_VIEW)
                        marketIntent.data = Uri.parse("market://details?id=" + programInfo?.uriCurrent)
                        try {
                            activity!!.startActivity(marketIntent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(context, getString(R.string.android_app_upd_play_service_not_found), Toast.LENGTH_LONG).show()
                            Log.e(Constants.MAH_ANDROID_UPDATER_LOG_TAG, getString(R.string.android_app_upd_play_service_not_found) + e.message)
                        }

                    } else {
                    }
                DlgModeEnum.TEST -> {
                }
                else -> {
                }
            }


    fun onNo() =
            when (type) {
                DlgModeEnum.OPEN_NEW -> {
                    val intent = Intent(Intent.ACTION_DELETE)
                    intent.data = Uri.parse("package:" + activity!!.packageName)
                    activity!!.startActivity(intent)
                }
                DlgModeEnum.TEST, DlgModeEnum.INSTALL, DlgModeEnum.UPDATE -> onClose()
                else -> {
                }
            }


    fun onClose() {
        dismissAllowingStateLoss()
        AAUpdaterController.end()
        activity!!.finish()
    }

    private fun showMAHlib() =
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(btnInfoActionURL))
                context!!.startActivity(browserIntent)
            } catch (nfe: ActivityNotFoundException) {
                val str = "You haven't set correct url to btnInfoActionURL, your url = " + btnInfoActionURL
                Toast.makeText(context, str, Toast.LENGTH_LONG).show()
                Log.d(Constants.MAH_ANDROID_UPDATER_LOG_TAG, str, nfe)
            }


    companion object {

        fun newInstance(programInfo: ProgramInfo,
                        type: DlgModeEnum,
                        btnInfoVisibility: Boolean,
                        btnInfoMenuItemTitle: String?,
                        btnInfoActionURL: String?): AAUpdaterRestricterDlg {
            val dialog = AAUpdaterRestricterDlg()
            val args = Bundle()
            val gson = Gson()

            args.putString("programInfo", gson.toJson(programInfo))
            args.putSerializable("type", type)
            args.putBoolean("btnInfoVisibility", btnInfoVisibility)
            args.putString("btnInfoMenuItemTitle", btnInfoMenuItemTitle)
            args.putString("btnInfoActionURL", btnInfoActionURL)
            dialog.arguments = args
            return dialog
        }
    }
}// Empty constructor required for DialogFragment