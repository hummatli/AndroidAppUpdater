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
import com.mobapphome.mahandroidupdater.tools.Constants
import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController
import com.mobapphome.mahandroidupdater.tools.DlgModeEnum
import com.mobapphome.mahandroidupdater.tools.ProgramInfo

class MAHUpdaterDlg private constructor(): DialogFragment(), android.view.View.OnClickListener {

    internal var programInfo: ProgramInfo? = null
    internal var type: DlgModeEnum? = null

    internal var btnInfoVisibility: Boolean = false
    internal var btnInfoMenuItemTitle: String? = null
    internal var btnInfoActionURL: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MAHUpdaterDlg)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "MAH Dld exit Created ")
        val arg = arguments
        val gson = Gson()
        programInfo = gson.fromJson(arg.getString("programInfo"), ProgramInfo::class.java)
        type = arg.getSerializable("type") as DlgModeEnum
        btnInfoVisibility = arg.getBoolean("btnInfoVisibility")
        btnInfoMenuItemTitle = arg.getString("btnInfoMenuItemTitle")
        btnInfoActionURL = arg.getString("btnInfoActionURL")

        Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, "Updateinfo from bundle " + programInfo?.updateInfo)

        val view = inflater!!.inflate(R.layout.mah_updater_dlg, container)

        dialog.window!!.attributes.windowAnimations = R.style.MAHUpdaterDialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {

                onNo()
                return@OnKeyListener true
            }
            false
        })


        val tvInfo = view.findViewById(R.id.tvInfoTxt) as TextView

        val tvUpdateInfo = view.findViewById(R.id.tvUpdateInfo) as TextView
        if (programInfo?.updateInfo != null) {
            tvUpdateInfo.text = programInfo?.updateInfo
            tvUpdateInfo.visibility = View.VISIBLE
        } else {
            tvUpdateInfo.visibility = View.GONE
        }

        val btnYes = view.findViewById(R.id.mah_updater_dlg_btn_update) as Button
        btnYes.setOnClickListener(this)

        val btnNo = view.findViewById(R.id.mah_updater_dlg_btn_dont_update) as Button
        btnNo.text = resources.getText(R.string.mah_android_upd_dlg_btn_no_later_txt)
        btnNo.setOnClickListener(this)

        view.findViewById(R.id.mah_updater_dlg_btnCancel).setOnClickListener(this)
        val ivBtnInfo = view.findViewById(R.id.mah_updater_dlg_btnInfo) as ImageView
        ivBtnInfo.setOnClickListener(this)

        if (btnInfoVisibility) {
            ivBtnInfo.visibility = View.VISIBLE
        } else {
            ivBtnInfo.visibility = View.INVISIBLE
        }

        when (type) {
            DlgModeEnum.UPDATE -> {
                btnYes.text = resources.getText(R.string.cmnd_verb_mah_android_upd_dlg_btn_yes_update_txt)
                tvInfo.text = resources.getText(R.string.mah_android_upd_updater_info_update)
            }
            DlgModeEnum.INSTALL -> {
                btnYes.text = resources.getText(R.string.cmnd_verb_mah_android_upd_dlg_btn_yes_install_txt)
                tvInfo.text = resources.getText(R.string.mah_android_upd_updater_info_install)
            }
            DlgModeEnum.TEST -> {
                btnYes.text = resources.getText(R.string.cmnd_verb_mah_android_upd_dlg_btn_yes_update_txt)
                tvInfo.text = resources.getText(R.string.mah_android_upd_updater_info_update)
            }
            else -> {
            }
        }

        MAHUpdaterController.setFontTextView(view.findViewById(R.id.tvTitle) as TextView)
        MAHUpdaterController.setFontTextView(view.findViewById(R.id.tvInfoTxt) as TextView)
        MAHUpdaterController.setFontTextView(btnYes)
        MAHUpdaterController.setFontTextView(btnNo)

        return view
    }

    fun onYes() {
        when (type) {
            DlgModeEnum.TEST -> {}
            else -> if (!programInfo?.uriCurrent!!.isEmpty()) {
                val marketIntent = Intent(Intent.ACTION_VIEW)
                marketIntent.data = Uri.parse("market://details?id=" + programInfo?.uriCurrent)
                try {
                    activity.startActivity(marketIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, getString(R.string.mah_android_upd_play_service_not_found), Toast.LENGTH_LONG).show()
                    Log.e(Constants.MAH_ANDROID_UPDATER_LOG_TAG, getString(R.string.mah_android_upd_play_service_not_found) + e.message)
                }

            }
        }
    }

    fun onNo() {
        dismissAllowingStateLoss()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.mah_updater_dlg_btnCancel) {
            onNo()
        } else if (v.id == R.id.mah_updater_dlg_btn_update) {
            onYes()
        } else if (v.id == R.id.mah_updater_dlg_btn_dont_update) {
            onNo()
        } else if (v.id == R.id.mah_updater_dlg_btnInfo) {
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
                        btnInfoActionURL: String?): MAHUpdaterDlg {
            val dialog = MAHUpdaterDlg()

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