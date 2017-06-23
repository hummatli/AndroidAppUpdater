package com.mobapphome.mahandroidupdater

/**
 * Created by settar on 7/12/16.
 */


import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.PopupMenu
import android.util.Log
import android.util.TypedValue
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.google.gson.Gson
import com.mobapphome.mahandroidupdater.commons.inflate
import com.mobapphome.mahandroidupdater.tools.Constants
import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController
import com.mobapphome.mahandroidupdater.tools.DlgModeEnum
import com.mobapphome.mahandroidupdater.tools.ProgramInfo

class MAHRestricterDlg private constructor(): DialogFragment(), View.OnClickListener {

    internal var programInfo: ProgramInfo? = null
    internal var type: DlgModeEnum? = null

    internal var btnInfoVisibility: Boolean = false
    internal var btnInfoMenuItemTitle: String? = null
    internal var btnInfoActionURL: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MAHRestricterDlg)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAH Restricter Dlg Created ")
        val arg = arguments
        val gson = Gson()
        programInfo = gson.fromJson(arg.getString("programInfo"), ProgramInfo::class.java)
        type = arg.getSerializable("type") as DlgModeEnum
        btnInfoVisibility = arg.getBoolean("btnInfoVisibility")
        btnInfoMenuItemTitle = arg.getString("btnInfoMenuItemTitle")
        btnInfoActionURL = arg.getString("btnInfoActionURL")

        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Updateinfo from bundle " + programInfo?.updateInfo)


        val view = inflater!!.inflate(R.layout.mah_restricter_dlg, container) //container?.inflate(R.layout.mah_restricter_dlg)

        dialog.window!!.attributes.windowAnimations = R.style.MAHUpdaterDialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {

                onClose()
                return@OnKeyListener true
            }
            false
        })

        val btnYes = view.findViewById(R.id.mah_updater_dlg_btn_update) as Button
        btnYes.setOnClickListener(this)

        val btnNo = view.findViewById(R.id.mah_updater_dlg_btn_dont_update) as Button
        btnNo.setOnClickListener(this)

        val tvInfo = view.findViewById(R.id.tvInfoTxt) as TextView

        val tvUpdateInfo = view.findViewById(R.id.tvUpdateInfo) as TextView
        if (programInfo?.updateInfo != null) {
            tvUpdateInfo.text = programInfo?.updateInfo
            tvUpdateInfo.visibility = View.VISIBLE
        } else {
            tvUpdateInfo.visibility = View.GONE
        }

        view.findViewById(R.id.mah_updater_dlg_btnCancel).setOnClickListener(this)
        val ivBtnInfo = view.findViewById(R.id.mah_updater_dlg_btnInfo) as ImageView
        ivBtnInfo.setOnClickListener(this)

        ivBtnInfo.visibility = if (btnInfoVisibility)  View.VISIBLE else View.INVISIBLE

        when (type) {
            DlgModeEnum.UPDATE -> {
                btnYes.text = resources.getText(R.string.cmnd_verb_mah_android_upd_dlg_btn_yes_update_txt)
                btnNo.text = resources.getText(R.string.cmnd_verb_mah_android_upd_dlg_btn_no_close_txt)
                tvInfo.text = resources.getText(R.string.mah_android_upd_restricter_info_update)
            }

            DlgModeEnum.INSTALL -> {
                btnYes.text = resources.getText(R.string.cmnd_verb_mah_android_upd_dlg_btn_yes_install_txt)
                btnNo.text = resources.getText(R.string.cmnd_verb_mah_android_upd_dlg_btn_no_close_txt)
                tvInfo.text = resources.getText(R.string.mah_android_upd_restricter_info_install)
            }

            DlgModeEnum.OPEN_NEW -> {
                btnYes.text = resources.getText(R.string.mah_android_upd_dlg_btn_yes_open_new_txt)
                btnNo.text = resources.getText(R.string.cmnd_verb_mah_android_upd_dlg_btn_no_uninstall_old_txt)
                tvInfo.text = resources.getText(R.string.mah_android_upd_restricter_info_open_new_version)
                tvUpdateInfo.visibility = View.GONE
            }

            DlgModeEnum.TEST -> {
                btnYes.text = resources.getText(R.string.cmnd_verb_mah_android_upd_dlg_btn_yes_update_txt)
                btnNo.text = resources.getText(R.string.cmnd_verb_mah_android_upd_dlg_btn_no_close_txt)
                tvInfo.text = resources.getText(R.string.mah_android_upd_restricter_info_update)
            }

            else -> {}
        }


        //Minimize the lines of question textview in  languages where question str is longer
        val tvQuestionTxt = view.findViewById(R.id.tvTitle) as TextView
        val strQuest = getString(R.string.noun_mah_android_upd_dlg_title)
        if (strQuest.length > 20) {
            tvQuestionTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        }


        MAHUpdaterController.setFontTextView(view.findViewById(R.id.tvTitle) as TextView)
        MAHUpdaterController.setFontTextView(view.findViewById(R.id.tvInfoTxt) as TextView)
        MAHUpdaterController.setFontTextView(btnYes)
        MAHUpdaterController.setFontTextView(btnNo)

        return view
    }

    fun onYes() {

        when (type) {
            DlgModeEnum.OPEN_NEW -> {
                val pack = activity.packageManager
                val app = pack.getLaunchIntentForPackage(programInfo?.uriCurrent)
                activity.startActivity(app)
            }

            DlgModeEnum.INSTALL, DlgModeEnum.UPDATE -> if (!programInfo?.uriCurrent!!.isEmpty()) {
                val marketIntent = Intent(Intent.ACTION_VIEW)
                marketIntent.data = Uri.parse("market://details?id=" + programInfo?.uriCurrent)
                try {
                    activity.startActivity(marketIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, getString(R.string.mah_android_upd_play_service_not_found), Toast.LENGTH_LONG).show()
                    Log.e(Constants.MAH_ANDROID_UPDATER_LOG_TAG, getString(R.string.mah_android_upd_play_service_not_found) + e.message)
                }

            }
            DlgModeEnum.TEST -> return
            else -> {}
        }
    }

    fun onNo() {
        when (type) {
            DlgModeEnum.OPEN_NEW -> {
                val intent = Intent(Intent.ACTION_DELETE)
                intent.data = Uri.parse("package:" + activity.packageName)
                activity.startActivity(intent)
            }
            DlgModeEnum.TEST, DlgModeEnum.INSTALL, DlgModeEnum.UPDATE -> onClose()
            else -> {}
        }
    }


    fun onClose() {
        dismissAllowingStateLoss()
        MAHUpdaterController.end()
        activity.finish()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.mah_updater_dlg_btnCancel -> onClose()
            R.id.mah_updater_dlg_btn_update -> onYes()
            R.id.mah_updater_dlg_btn_dont_update -> onNo()
            R.id.mah_updater_dlg_btnInfo -> {
                val itemIdForInfo = 1
                val popup = PopupMenu(context, v)
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
        }
    }

    private fun showMAHlib() {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(btnInfoActionURL))
            context.startActivity(browserIntent)
        } catch (nfe: ActivityNotFoundException) {
            val str = "You haven't set correct url to btnInfoActionURL, your url = " + btnInfoActionURL
            Toast.makeText(context, str, Toast.LENGTH_LONG).show()
            Log.d(Constants.MAH_ANDROID_UPDATER_LOG_TAG, str, nfe)
        }

    }

    companion object {

        fun newInstance(programInfo: ProgramInfo,
                        type: DlgModeEnum,
                        btnInfoVisibility: Boolean,
                        btnInfoMenuItemTitle: String?,
                        btnInfoActionURL: String?): MAHRestricterDlg {
            val dialog = MAHRestricterDlg()

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