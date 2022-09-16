package io.github.winterreisender.ezzenmode

import javafx.fxml.FXML
import javafx.stage.Stage
import java.awt.Desktop
import java.net.URI

class MainController {

    @FXML fun initialize() {
    }

    @FXML private fun onZenButtonClicked() {
        ZenApplication().start(Stage())
        //navigateHBox.scene.window.hide()
    }

    @FXML fun onMenuHomepageClicked(){
        Desktop.getDesktop().browse(URI("https://github.com/Winterreisender/EzZenmode"))
    }
}
