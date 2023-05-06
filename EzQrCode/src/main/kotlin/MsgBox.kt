/**
 * @author Winterreisender
 * @license Apache-2.0
 */

import java.awt.BorderLayout
import java.util.concurrent.Semaphore
import javax.swing.*

object MsgBox {
    /**
     * MsgBox not like VB with callback
     *
     * @author Winterreisender
     * @param text The text to show in Dialog
     * @param title The title of JDialog window
     * @param owner The parent JFrame of Dialog
     * @param yesText The text of "Yes" button
     * @param noText The text of "No" button
     * @param onYes callback when "Yes" clicked
     * @param onNo callback when "No" clicked
     * @return A showing JDialog with Yes and No button
     */
    fun msgBoxCallback(
        text: String,
        title: String = "",
        owner: JFrame? = null,
        yesText: String = "确认",
        noText: String? = null,
        onYes: () -> Unit = {},
        onNo: () -> Unit = {}
    ) = JDialog(owner, title).apply {
        layout = BorderLayout()
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        setLocationRelativeTo(owner)


        JLabel(text, JLabel.CENTER)
            .also { add(it, BorderLayout.CENTER) }

        Box(BoxLayout.X_AXIS).apply {
            Box.createHorizontalGlue().also(::add)
            JButton(yesText).apply {
                pack()
                addActionListener {
                    onYes()
                    dispose()
                }
            }.also(::add)
            if (noText != null) JButton(noText).apply {
                pack()
                addActionListener {
                    onNo()
                    dispose()
                }
            }.also(::add)
            Box.createHorizontalGlue().also(::add)


            pack()
            isVisible = true
        }.also { add(it, BorderLayout.SOUTH) }
    }.apply { pack(); isVisible = true }

    /**
     * Synchronized MsgBox like VB
     *
     * 垃圾Java,连MsgBox都没有
     *
     * @author Winterreisender
     * @param text The text to show in Dialog
     * @param title The title of JDialog window
     * @param owner The parent JFrame of Dialog
     * @param yesText The text of "Yes" button
     * @param noText The text of "No" button
     * @return A showing JDialog with Yes and No button
     */
    fun msgBoxSync (
        text: String,
        title: String = "",
        owner: JFrame? = null,
        yesText: String = "确认",
        noText: String = "取消"
    ) :Boolean {
        var r = false
        val sem = Semaphore(0)
            msgBoxCallback(text, title, owner, yesText, noText, { r = true;sem.release() }, { r = false; sem.release() })
        sem.acquire()
        return r
    }

}
/*
fun test() {
    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")
    println(MsgBox.msgBoxSync("Text"))
}

fun test2() {
    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")
    JFrame().apply {
        JButton().apply {
            text = "Test"
            size = Dimension(100,50)
            addActionListener {
                CoroutineScope(Dispatchers.Default).launch {
                    val r = MsgBox.msgBox("Hello!", "Title", null, "确认", "取消")
                    if (r) {
                        MsgBox.msgBox("You clicked Yes")
                    }
                }
            }
        }.also(::add)

        pack()
        isVisible = true
    }
}


fun test3() {
    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")
JFrame().apply {
    //设置Dialog属性
    title = "Title"
    layout = GridLayout(3,1)
    size = Dimension(80,180)
    defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE

    //设置子组件
    val label1 = JLabel("这是消息").also(::add)

    var setButton2Text :((text :String)->Unit)? = null
    JButton("确认").apply{
        size = Dimension(30,15)
        addActionListener {
            setButton2Text!!("Good")
        }
    }.also(::add)

    JButton("取消").apply{
        size = Dimension(30,15)
    }.also(::add).let { setButton2Text = { text -> it.text = text } }


    //设置Dialog属性
    isVisible = true
}
}

fun main() = test3()
 */