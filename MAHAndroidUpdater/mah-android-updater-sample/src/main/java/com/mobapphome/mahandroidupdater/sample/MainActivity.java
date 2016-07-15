package com.mobapphome.mahandroidupdater.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // For MAHUpdater init
        MAHUpdaterController.init(this,"http://ubuntu1mah.cloudapp.net/mah_android_updater_dir/mah_android_updater_sample.php");
        // METHOD 1
        findViewById(R.id.mahBtnRestricterDlgTest).setOnClickListener(this);
        findViewById(R.id.mahBtnUpdaterDlgTest).setOnClickListener(this);
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
    public void onBackPressed() {
        MAHUpdaterController.end();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.mahBtnUpdaterDlgTest){
            MAHUpdaterController.testUpdaterDlg(this);
        }else if(v.getId() == R.id.mahBtnRestricterDlgTest){
            MAHUpdaterController.testRestricterDlg(this);
        }
    }
}

