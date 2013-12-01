package free_messaging;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Mahfuz
 */
public class Free_Messaging extends JFrame implements ActionListener {

    JButton single = new JButton("Single");
    JButton multiple = new JButton("Multiple");
    JLabel text = new JLabel("No. of Recipient");
    JLabel text_2 = new JLabel("Welcome to Free Messaging");

    public Free_Messaging() throws HeadlessException {
        view();
    }

    public static int GetScreenWorkingWidth() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    }

    public static int GetScreenWorkingHeight() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    }

    public static void main(String[] args) {

        Free_Messaging fm = new Free_Messaging();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == single) {
            Single s = new Single();
            dispose();
        }
        if (e.getSource() == multiple) {
            Group g = new Group();
            dispose();
        }
    }

    private void view() {
        
        int width = GetScreenWorkingWidth()/2-150;
        int height = GetScreenWorkingHeight()/2-50;
        
        setBounds(width, height, 300, 220);
        setTitle("Free Messaging");
        setVisible(rootPaneCheckingEnabled);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(text_2);
        text_2.setBounds(30, 20, 200, 20);
        
        add(text);
        text.setBounds(30, 70, 100, 20);
        
        add(single);
        single.setBounds(30, 110, 100, 30);
        single.addActionListener(this);

        add(multiple);
        multiple.setBounds(140, 110, 100, 30);
        multiple.addActionListener(this);
    }
}
