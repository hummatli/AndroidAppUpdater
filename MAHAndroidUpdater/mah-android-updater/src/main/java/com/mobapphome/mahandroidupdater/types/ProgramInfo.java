package com.mobapphome.mahandroidupdater.types;

import java.io.Serializable;

public class ProgramInfo implements Serializable{
	private boolean isRunMode = false;
	private String name;
	private int versionCodeCurrent = -1;
	private int versionCodeMin = -1;
	private String uriCurrent;
	private String updateInfo;
	private String updateDate;

	public ProgramInfo(){

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
}
