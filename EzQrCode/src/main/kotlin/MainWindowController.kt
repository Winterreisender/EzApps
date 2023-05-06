import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageConfig
import com.google.zxing.client.j2se.MatrixToImageWriter
import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.image.BufferedImage
import javax.swing.ImageIcon

object MainWindowController {
    fun getQrCode(text :String) : BufferedImage {
        val bitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE,512,512)!!
        return MatrixToImageWriter.toBufferedImage(bitMatrix, MatrixToImageConfig(Color.BLACK.rgb, Color.WHITE.rgb))!!
    }

    @JvmStatic
    fun onGenerateButtonClicked(form :MainWindow,e : ActionEvent) {
        form.qrCodeLabel.icon = ImageIcon(getQrCode(form.contentTextArea.text))
    }
}