// // not using

import com.sun.jna.Native
import com.sun.jna.Library

interface WindowsAppsThemeLib :Library {
    fun windowsAppsTheme() :Int
}

object WindowsAppsTheme {
    enum class AppsTheme{
        Dark,
        Light,
        Unknown
    }

    val appsTheme :AppsTheme
        get()  {
            val library: WindowsAppsThemeLib = Native.load("WindowsAppsTheme", WindowsAppsThemeLib::class.java)
            return when (library.windowsAppsTheme()) {
                0 -> AppsTheme.Dark
                1 -> AppsTheme.Light
                else -> AppsTheme.Unknown
        }
    }
}

fun main() {
    println(WindowsAppsTheme.appsTheme)
}

