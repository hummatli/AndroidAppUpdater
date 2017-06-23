package com.mobapphome.mahandroidupdater.tools

/**
 * Created by settar on 6/22/17.
 */


import java.io.Serializable

data class ProgramInfo( val isRunMode: Boolean = false,
                        val name: String = "",
                        val versionCodeCurrent: Int = -1,
                        val versionCodeMin: Int = -1,
                        val uriCurrent: String = "",
                        val updateInfo: String = "",
                        val updateDate: String = "")
    : Serializable

enum class DlgModeEnum {
    UPDATE, INSTALL, OPEN_NEW, TEST
}


interface IUpdateInfoResolver {
    fun resolveInfo(): ProgramInfo
}

interface UpdaterListener {
    fun onResponse(programInfo: ProgramInfo, errorStr: String = "Default error")
}
