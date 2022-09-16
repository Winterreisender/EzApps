package io.github.winterreisender.ezzenmode

import javafx.application.Application
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.input.KeyCombination
import javafx.stage.Stage
import kotlin.system.exitProcess

class ZenApplication : Application() {

    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(ZenApplication::class.java.getResource("zen-view.fxml"))
        stage.apply {
            scene = Scene(fxmlLoader.load())

            title = "EzZenMode"
            icons.add(Image("application.png"))
            isFullScreen = true
            fullScreenExitHint = ""
            fullScreenExitKeyCombination = KeyCombination.NO_MATCH
            onCloseRequest = EventHandler {
                exitProcess(0)
            }
        }
        stage.show()
    }
}