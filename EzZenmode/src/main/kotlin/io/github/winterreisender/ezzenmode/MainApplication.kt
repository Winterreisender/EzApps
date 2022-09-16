package io.github.winterreisender.ezzenmode

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import kotlin.system.exitProcess

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("main-view.fxml"))
        stage.apply {
            scene = Scene(fxmlLoader.load())

            title = "EzZenMode"
            icons.add(Image("application.png"))

            onCloseRequest = EventHandler {
                exitProcess(0)
            }
        }
        stage.show()
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}