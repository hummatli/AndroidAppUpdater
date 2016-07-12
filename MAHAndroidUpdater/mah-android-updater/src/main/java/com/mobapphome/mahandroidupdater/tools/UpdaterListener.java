package com.mobapphome.mahandroidupdater.tools;

import com.mobapphome.mahandroidupdater.types.ProgramInfo;

public interface UpdaterListener {
	public void onResponse(ProgramInfo programInfo, String errorStr);
}
