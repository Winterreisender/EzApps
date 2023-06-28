import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {
    public JTabbedPane tabbedPane1;
    public JPanel panel1;
    public JButton generateButton;
    public JButton clearButton;
    public JTextArea contentTextArea;
    public JPanel homePanel;
    public JPanel aboutPane;
    public JLabel qrCodeLabel;

    public MainWindow() {
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindowController.onGenerateButtonClicked(MainWindow.this,e);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
