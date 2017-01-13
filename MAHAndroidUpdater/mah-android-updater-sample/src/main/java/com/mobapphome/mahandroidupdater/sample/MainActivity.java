package com.mobapphome.mahandroidupdater.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // For MAHUpdater init
        MAHUpdaterController.init(this,
                "https://project-943403214286171762.firebaseapp.com/mah_android_updater_dir/mah_android_updater_sample.json");
        // METHOD 1

        findViewById(R.id.mahBtnRestricterDlgTest).setOnClickListener(this);
        findViewById(R.id.mahBtnUpdaterDlgTest).setOnClickListener(this);

        ((TextView)findViewById(R.id.tvMAHAULibGithubUrl)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)findViewById(R.id.tvMAHAULibJCenterURL)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)findViewById(R.id.tvMAHAdsLibContrubute)).setMovementMethod(LinkMovementMethod.getInstance());

        Spinner langSpinner = (Spinner) findViewById(R.id.langSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.langs_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langSpinner.setAdapter(adapter);

        //Setting local.
        LocaleHelper.onCreate(this, "en");
        String currentLang = LocaleHelper.getLanguage(this);
        if (currentLang.equals("en")) {
            currentLang = "english";
        } else if (currentLang.equals("az")) {
            currentLang = "azerbaijani";
        } else if (currentLang.equals("pt")) {
            currentLang = "portuguese";
        } else if (currentLang.equals("ru")) {
            currentLang = "russian";
        } else if (currentLang.equals("tr")) {
            currentLang = "turkish";
        }

        //Setting spinner to right language
        String[] langsArray = getResources().getStringArray(R.array.langs_array);
        for (int i = 0; i < langsArray.length; i++) {
            if (langsArray[i].toLowerCase().startsWith(currentLang)) {
                langSpinner.setSelection(i);
            }

        }
        langSpinner.setOnItemSelectedListener(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        MAHUpdaterController.end();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.mahBtnUpdaterDlgTest){
            MAHUpdaterController.testUpdaterDlg(this);
        }else if(v.getId() == R.id.mahBtnRestricterDlgTest){
            MAHUpdaterController.testRestricterDlg(this);
        }
    }

    //Selection event for language spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String item = parent.getItemAtPosition(pos).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item + " id:" + id, Toast.LENGTH_LONG).show();
        if (item.toLowerCase().startsWith("english")) {
            LocaleHelper.setLocale(this, "en");
        } else if (item.toLowerCase().startsWith("azerbaijani")) {
            LocaleHelper.setLocale(this, "az");
        } else if (item.toLowerCase().startsWith("portuguese")) {
            LocaleHelper.setLocale(this, "pt");
        } else if (item.toLowerCase().startsWith("russian")) {
            LocaleHelper.setLocale(this, "ru");
        } else if (item.toLowerCase().startsWith("turkish")) {
            LocaleHelper.setLocale(this, "tr");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

