import com.formdev.flatlaf.FlatLightLaf
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageConfig
import com.google.zxing.client.j2se.MatrixToImageWriter
import java.awt.*
import java.awt.image.BufferedImage
import java.net.URI
import java.net.URL
import javax.swing.*

fun getQrCode(text :String) : BufferedImage {
    val bitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE,512,512)!!
    return MatrixToImageWriter.toBufferedImage(bitMatrix, MatrixToImageConfig(Color.BLACK.rgb, Color.WHITE.rgb))!!
}

fun getResource(resPath :String): URL = object {}.javaClass.getResource(resPath)!!

fun main(args: Array<String>) {
    //UIManager.setLookAndFeel(getSystemLookAndFeelClassName())
    UIManager.setLookAndFeel(FlatLightLaf())
    JFrame().apply {
        //设置Dialog属性
        title = "EzQrCode"
        iconImage = toolkit.getImage(getResource("ezqrcode.png")) // 淦 ImageIcon的路径相对路径是相对于工程根目录的位置,不是resource
        location = GraphicsEnvironment.getLocalGraphicsEnvironment().centerPoint


        size = Dimension(600,760)
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE

        //设置子组件
        JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            alignmentX = Component.CENTER_ALIGNMENT

            val qrImg = JLabel(ImageIcon()).apply {
                size = Dimension(512,512)
                border = BorderFactory.createLineBorder(Color.black)
                alignmentX = Component.CENTER_ALIGNMENT
            }.also(::add)

            val inputText = JTextArea("请输入内容",5,30).apply{
                    size = Dimension(512,50)
                    foreground = Color.black
                    lineWrap = true
                    alignmentX = Component.CENTER_ALIGNMENT
                }.also { add(JScrollPane(it).apply {
                    alignmentX = Component.CENTER_ALIGNMENT
                }) }

            JButton("生成二维码").apply{
                size = Dimension(30,15)
                alignmentX = Component.CENTER_ALIGNMENT
                addActionListener {
                    qrImg.icon = ImageIcon(getQrCode(inputText.text))
                }
            }.also(::add)
            JButton("关于").apply{
                size = Dimension(30,15)
                alignmentX = Component.CENTER_ALIGNMENT
                addActionListener {
                    MsgBox.msgBoxCallback(getResource("about.htm").readText(),"关于",null,"官网","确定",{
                        Desktop.getDesktop().browse(URI("https://gitee.com/winter_reisender/EzQrCode")) })
                }
            }.also(::add)
        }.also(::add)

        //设置Dialog属性
        isVisible = true
    }
}