package com.mobapphome.mahandroidupdater;

/**
 * Created by settar on 7/12/16.
 */


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobapphome.mahandroidupdater.tools.ExitListiner;
import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController;
// ...

public class MAHAndrUpdaterDlg extends DialogFragment implements
        android.view.View.OnClickListener {

    ExitListiner exitListiner;
    String updateInfo;

    public MAHAndrUpdaterDlg() {
        // Empty constructor required for DialogFragment
    }

    public static MAHAndrUpdaterDlg newInstance(String updateInfo, ExitListiner exitListiner) {
        MAHAndrUpdaterDlg dialog = new MAHAndrUpdaterDlg();
        dialog.setExitListiner(exitListiner);

        Bundle args = new Bundle();
        args.putString("updateInfo", updateInfo);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Test", "MAH Dld exit Created ");
        Bundle arg = getArguments();
        updateInfo = arg.getString("updateInfo");
        Log.i("Test", "Update info from bundle " + updateInfo);

        View view = inflater.inflate(R.layout.mah_ads_dialog_exit, container);

        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);

        Button btnYes = ((Button) view.findViewById(R.id.mah_updater_dlg_btn_update));
        btnYes.setOnClickListener(this);

        Button btnNo = (Button) view.findViewById(R.id.mah_updater_dlg_btn_dont_update);
        btnNo.setOnClickListener(this);

        ((TextView)view.findViewById(R.id.tvUpdateInfoTxt)).setText(updateInfo);

        ((ImageButton) view.findViewById(R.id.mah_updater_dlg_btnCancel)).setOnClickListener(this);


        MAHUpdaterController.setFontTextView((TextView) view.findViewById(R.id.tvTitle));
        MAHUpdaterController.setFontTextView((TextView) view.findViewById(R.id.tvUpdateInfoTxt));
        MAHUpdaterController.setFontTextView(btnYes);
        MAHUpdaterController.setFontTextView(btnNo);

        return view;
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
        }
    }

    public void setExitListiner(ExitListiner exitListiner) {
        this.exitListiner = exitListiner;
    }

}