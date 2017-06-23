package com.mobapphome.mahandroidupdater.sample

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

import com.mobapphome.mahandroidupdater.tools.Constants
import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController

import java.util.ArrayList
import java.util.Arrays

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // For MAHUpdater init
        MAHUpdaterController.init(this,
                "https://project-943403214286171762.firebaseapp.com/mah_android_updater_dir/mah_android_updater_sample.json")
        // METHOD 1

        val imageView = findViewById(R.id.ivMAHForkMeOnGithub) as ImageView
        val forkMeImg = resources.getDrawable(R.drawable.forkme_green)
        // setting the opacity (alpha)
        forkMeImg.alpha = 180
        // setting the images on the ImageViews
        imageView.setImageDrawable(forkMeImg)

        findViewById(R.id.mahBtnRestricterDlgTest).setOnClickListener(this)
        findViewById(R.id.mahBtnUpdaterDlgTest).setOnClickListener(this)
        imageView.setOnClickListener(this)

        (findViewById(R.id.tvMAHAULibGithubUrl) as TextView).movementMethod = LinkMovementMethod.getInstance()
        (findViewById(R.id.tvMAHAULibJCenterURL) as TextView).movementMethod = LinkMovementMethod.getInstance()
        (findViewById(R.id.tvMAHAdsLibContrubute) as TextView).movementMethod = LinkMovementMethod.getInstance()


        val langsArray = arrayOf("Azerbaijani", "English", "German", "Hindi", "Portuguese", "Russian", "Turkish")


        val langSpinner = findViewById(R.id.langSpinner) as Spinner
        val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                ArrayList<CharSequence>(Arrays.asList(*langsArray)))

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        langSpinner.adapter = adapter

        //Setting local.
        LocaleHelper.onCreate(this, "en")
        var currentLang = LocaleHelper.getLanguage(this)


        if (currentLang == "az") {
            currentLang = "azerbaijani"
        } else if (currentLang == "en") {
            currentLang = "english"
        } else if (currentLang == "de") {
            currentLang = "german"
        } else if (currentLang == "hi") {
            currentLang = "hindi"
        } else if (currentLang == "pt") {
            currentLang = "portuguese"
        } else if (currentLang == "ru") {
            currentLang = "russian"
        } else if (currentLang == "tr") {
            currentLang = "turkish"
        }

        //Setting spinner to right language

        for (i in langsArray.indices) {
            if (langsArray[i].toLowerCase().startsWith(currentLang)) {
                langSpinner.setSelection(i)
            }

        }
        langSpinner.onItemSelectedListener = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        MAHUpdaterController.end()
        super.onDestroy()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.mahBtnUpdaterDlgTest) {
            MAHUpdaterController.testUpdaterDlg(this)
        } else if (v.id == R.id.mahBtnRestricterDlgTest) {
            MAHUpdaterController.testRestricterDlg(this)
        } else if (v.id == R.id.ivMAHForkMeOnGithub) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MAH_UPD_GITHUB_LINK))
            startActivity(browserIntent)
        }
    }

    //Selection event for language spinner
    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        val item = parent.getItemAtPosition(pos).toString()

        // Showing selected spinner item
        Toast.makeText(parent.context, "Selected: $item id:$id", Toast.LENGTH_LONG).show()
        if (item.toLowerCase().startsWith("azerbaijani")) {
            LocaleHelper.setLocale(this, "az")
        } else if (item.toLowerCase().startsWith("english")) {
            LocaleHelper.setLocale(this, "en")
        } else if (item.toLowerCase().startsWith("german")) {
            LocaleHelper.setLocale(this, "de")
        } else if (item.toLowerCase().startsWith("hindi")) {
            LocaleHelper.setLocale(this, "hi")
        } else if (item.toLowerCase().startsWith("portuguese")) {
            LocaleHelper.setLocale(this, "pt")
        } else if (item.toLowerCase().startsWith("russian")) {
            LocaleHelper.setLocale(this, "ru")
        } else if (item.toLowerCase().startsWith("turkish")) {
            LocaleHelper.setLocale(this, "tr")
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }
}

