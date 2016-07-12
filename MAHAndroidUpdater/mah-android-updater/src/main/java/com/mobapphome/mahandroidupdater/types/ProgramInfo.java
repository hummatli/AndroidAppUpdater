package com.mobapphome.mahandroidupdater.types;

import java.io.Serializable;

public class ProgramInfo implements Serializable{
	String name;
	String versionOnPlayMarket;
	String uriCurrent;
	String updateInfo;
	String updateDate;

	public ProgramInfo(){

	}

	public ProgramInfo(String name, String updateDate, String updateInfo, String uriCurrent, String versionOnPlayMarket) {
		this.name = name;
		this.updateDate = updateDate;
		this.updateInfo = updateInfo;
		this.uriCurrent = uriCurrent;
		this.versionOnPlayMarket = versionOnPlayMarket;
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

	public String getVersionOnPlayMarket() {
		return versionOnPlayMarket;
	}

	public void setVersionOnPlayMarket(String versionOnPlayMarket) {
		this.versionOnPlayMarket = versionOnPlayMarket;
	}
}
