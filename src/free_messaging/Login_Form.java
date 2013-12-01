package free_messaging;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Mahfuz
 */
class Login_Form implements ActionListener {

    JFrame f = new JFrame("Login To Gmail");
    JLabel jl_user = new JLabel("Username");
    JLabel jl_end = new JLabel("@gmail.com");
    JTextField jf_username = new JTextField("");
    JLabel jl_pass = new JLabel("Password");
    JPasswordField jp_password = new JPasswordField("");
    JButton submit = new JButton("Submit");
    Controler controler = null;

    public static int GetScreenWorkingWidth() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    }

    public static int GetScreenWorkingHeight() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    }

    public Login_Form() {
        controler = Controler.getInstance();
        view();
    }

    public Controler getControler() {
        return controler;
    }

    public void setControler(Controler controler) {
        this.controler = controler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        String username = jf_username.getText();
        String password = new String(jp_password.getPassword());
        try {
            this.controler.setClient(new CalendarClient(username, password));
            JOptionPane.showMessageDialog(jl_user, "Login Successfull");
        } catch (Exception ex) {
            Logger.getLogger(Login_Form.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(jl_user, "Login Failed");
        }

        f.setVisible(false);

        //JOptionPane.showMessageDialog(jl_user, "Login Failed");

    }

    private void view() {

        int width = GetScreenWorkingWidth() / 2 - 150;
        int height = GetScreenWorkingHeight() / 2 - 85;

        f.setBounds(width, height, 300, 170);

        //f.setSize(500, 500);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLayout(null);

        f.add(jl_user);
        jl_user.setBounds(20, 20, 80, 20);

        f.add(jf_username);
        jf_username.setBounds(100, 20, 100, 20);

        f.add(jl_end);
        jl_end.setBounds(200, 20, 80, 20);

        f.add(jl_pass);
        jl_pass.setBounds(20, 50, 80, 20);

        f.add(jp_password);
        jp_password.setBounds(100, 50, 100, 20);

        f.add(submit);
        submit.setBounds(100, 80, 100, 30);
        submit.addActionListener(this);


    }
}
