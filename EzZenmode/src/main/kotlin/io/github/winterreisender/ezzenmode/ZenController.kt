package io.github.winterreisender.ezzenmode

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import java.lang.Long.parseLong


class ZenController {
    @FXML private lateinit var timeLeftLabel :Label
    @FXML private lateinit var exitButton : Button
    @FXML private lateinit var startButton : Button
    @FXML private lateinit var zenTimeInput : TextField
    @FXML private lateinit var zenTimeHBox : HBox

    private fun Long.toTimeString() = if(this < 0) {
            "00:00:00"
        }else{
            String.format("%02d:%02d:%02d",this/3600,this/60%60,this%60)
        }

    fun initialize() {

    }

    @FXML fun onExitButtonClicked() {
        Platform.exit();
    }

    @FXML fun onStartButtonClicked() {
        val initTimeLeft = kotlin.runCatching {
            parseLong(zenTimeInput.text) * 60
        }.fold(
            onSuccess = {
                it
            },
            onFailure = {
                Alert(Alert.AlertType.INFORMATION,"输入错误: $it").show()
                return@onStartButtonClicked
            }
        )

        Thread {
            Platform.runLater { zenTimeHBox.isVisible = false; }
            var timeLeft = initTimeLeft;
            while (timeLeft >= 0) {
                Platform.runLater {
                    timeLeftLabel.text = "剩余 ${timeLeft.toTimeString()}"
                }
                Thread.sleep(1000);
                timeLeft--
            }
            Platform.runLater {
                exitButton.isVisible = true;
            }
        }.start()
    }

}
