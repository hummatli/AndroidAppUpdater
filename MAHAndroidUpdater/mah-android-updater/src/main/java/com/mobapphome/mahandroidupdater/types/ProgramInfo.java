package com.mobapphome.mahandroidupdater.types;

import android.util.Log;

import com.mobapphome.mahandroidupdater.tools.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ProgramInfo implements Serializable {
    private boolean isRunMode = false;
    private String name;
    private int versionCodeCurrent = -1;
    private int versionCodeMin = -1;
    private String uriCurrent;
    private String updateInfo;
    private String updateDate;

    public ProgramInfo() {

    }

    public ProgramInfo(boolean isRunMode, String name, String updateDate, String updateInfo, String uriCurrent, int versionCodeCurrent, int versionCodeMin) {
        this.isRunMode = isRunMode;
        this.name = name;
        this.updateDate = updateDate;
        this.updateInfo = updateInfo;
        this.uriCurrent = uriCurrent;
        this.versionCodeCurrent = versionCodeCurrent;
        this.versionCodeMin = versionCodeMin;
    }

    public boolean isRunMode() {
        return isRunMode;
    }

    public void setRunMode(boolean runMode) {
        isRunMode = runMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    public String getUriCurrent() {
        return uriCurrent;
    }

    public void setUriCurrent(String uriCurrent) {
        this.uriCurrent = uriCurrent;
    }

    public int getVersionCodeCurrent() {
        return versionCodeCurrent;
    }

    public void setVersionCodeCurrent(int versionCodeCurrent) {
        this.versionCodeCurrent = versionCodeCurrent;
    }

    public int getVersionCodeMin() {
        return versionCodeMin;
    }

    public void setVersionCodeMin(int versionCodeMin) {
        this.versionCodeMin = versionCodeMin;
    }

    public String toJson() {

        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("isRunMode", this.isRunMode);
            jsonObj.put("name", this.name);
            jsonObj.put("updateDate", this.updateDate);
            jsonObj.put("updateInfo", this.updateInfo);
            jsonObj.put("uriCurrent", this.uriCurrent);
            jsonObj.put("versionCodeCurrent", this.versionCodeCurrent);
            jsonObj.put("versionCodeMin", this.versionCodeMin);

        } catch (JSONException e) {
            Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, e.toString());
        }

        return jsonObj.toString();
    }

    public static ProgramInfo fromJson(String json) {

        ProgramInfo programInfo = new ProgramInfo();

        try {
            JSONObject jObj = new JSONObject(json);
            programInfo.isRunMode = jObj.getBoolean("isRunMode");
            programInfo.name = jObj.getString("name");
            programInfo.updateDate = jObj.getString("updateDate");
            programInfo.updateInfo = jObj.getString("updateInfo");
            programInfo.uriCurrent = jObj.getString("uriCurrent");
            programInfo.versionCodeCurrent = jObj.getInt("versionCodeCurrent");
            programInfo.versionCodeMin = jObj.getInt("versionCodeMin");

        } catch (JSONException e) {
            Log.i(Constants.MAH_ANDROID_UPDATER_LOG_TAG, e.toString());
        }

        return programInfo;

    }
}
