package com.mobapphome.androidappupdater.sample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.mobapphome.androidappupdater.commons.decorateAsLink
import com.mobapphome.androidappupdater.tools.Constants
import com.mobapphome.androidappupdater.tools.AAUpdaterController
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class SampleActivityKotlin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // For MAHUpdater init
        AAUpdaterController.init(this,
                "https://project-943403214286171762.firebaseapp.com/mah_android_updater_dir/mah_android_updater_sample.json")
        // METHOD 1


        //This block is only in Kotlin sample
        run {
            title = getString(R.string.title_kotlin_sample)
            mahBtnOpenJavaSample.setOnClickListener {
                val intent = Intent(this, SampleActivityJava::class.java)
                startActivity(intent)
            }
        }


        val forkMeImg = ContextCompat.getDrawable(this, R.drawable.forkme_green)
        // setting the opacity (alpha)
        forkMeImg!!.alpha = 180
        // setting the images on the ImageViews
        ivMAHForkMeOnGithub.setImageDrawable(forkMeImg)

        mahBtnRestricterDlgTest.setOnClickListener { AAUpdaterController.testRestricterDlg(this) }
        mahBtnUpdaterDlgTest.setOnClickListener { AAUpdaterController.testUpdaterDlg(this) }
        ivMAHForkMeOnGithub.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MAH_UPD_GITHUB_LINK)))
        }

        tvMAHAULibGithubUrl.decorateAsLink()
        tvMAHAULibJCenterURL.decorateAsLink()
        tvMAHAdsLibContrubute.decorateAsLink()


        val langsList = listOf("Azerbaijani", "English", "German", "Hindi", "Portuguese", "Russian", "Turkish")

        val adapter = ArrayAdapter( this, android.R.layout.simple_spinner_item, ArrayList<CharSequence>(langsList))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        langSpinner.adapter = adapter

        //Setting local.
        LocaleHelper.onCreate(this, "en")
        val currentLang = when (LocaleHelper.getLanguage(this)) {
            "az" -> "azerbaijani"
            "en" -> "english"
            "de" -> "german"
            "hi" -> "hindi"
            "pt" -> "portuguese"
            "ru" -> "russian"
            "tr" -> "turkish"
            else -> "lang has not specified"
        }


        //Setting spinner to right language
        for (i in langsList.indices) {
            if (langsList[i].toLowerCase().startsWith(currentLang)) {
                langSpinner.setSelection(i)
                break
            }
        }


        langSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val item = parent.getItemAtPosition(pos).toString()

                // Showing selected spinner item
                Toast.makeText(parent.context, "Selected: $item id:$id", Toast.LENGTH_LONG).show()

                val itemLowerCase = item.toLowerCase()

                LocaleHelper.setLocale(baseContext, when {
                    itemLowerCase.startsWith("azerbaijani") -> "az"
                    itemLowerCase.startsWith("english") -> "en"
                    itemLowerCase.startsWith("german") -> "de"
                    itemLowerCase.startsWith("hindi") -> "hi"
                    itemLowerCase.startsWith("portuguese") -> "pt"
                    itemLowerCase.startsWith("russian") -> "ru"
                    itemLowerCase.startsWith("turkish") -> "tr"
                    else -> "does not specfied"
                })
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
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
        AAUpdaterController.end()
        super.onDestroy()
    }
}

