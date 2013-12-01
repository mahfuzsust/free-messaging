package free_messaging;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Mahfuz
 */
public class Send_Message implements ActionListener {

    JFrame f = new JFrame("Send Message");
    JMenuBar menubar = new JMenuBar();
    
    JMenu menu_file = new JMenu("File");
    JMenuItem menuitem_login = new JMenuItem("Login");
    JMenuItem menuitem_logout = new JMenuItem("Logout");
    JMenuItem menuitem_exit = new JMenuItem("Exit");
    
    JMenu menu_group = new JMenu("Group");
    JMenuItem menuitem_addgroup = new JMenuItem("Add Group");
    
    JMenu menu_about = new JMenu("About");
    JMenuItem menuitem_about = new JMenuItem("About");
    
    JLabel l_msg = new JLabel("Message");
    JTextArea msg = new JTextArea("HI");
    JButton ad = new JButton("ADD");
    JButton send = new JButton("SEND");
    JButton login = new JButton("Login");
    JLabel l_rec = new JLabel("Recepient");
    JTextField rec = new JTextField("don.mahfuz@gmail.com");
    Controler controler = null;
    Login_Form lf = null;
    private javax.swing.JList lstRecipients = null;
    Object[] recipients;
    List<String> arrl;

    public Send_Message() {


        recipients = null;
        controler = Controler.getInstance();
        controler.setNumOfSMSMessages(20);
        controler.setNumOfChars(50);
        lf = new Login_Form();
        lf.setControler(controler);
        lf.f.setAlwaysOnTop(true);

        view();

    }

    private void view() {

        f.setVisible(true);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(500, 500);
        
        //f.add(menubar);
        
        
        f.setJMenuBar(menubar);
        
        menubar.add(menu_file);
        menu_file.add(menuitem_login);
        menu_file.add(menuitem_logout);
        menu_file.add(menuitem_exit);
        menuitem_exit.addActionListener(this);
        
        menubar.add(menu_group);
        menu_group.add(menuitem_addgroup);
        menuitem_addgroup.addActionListener(this);

        
        menubar.add(menu_about);
        menu_about.add(menuitem_about);

        f.add(l_msg);
        l_msg.setBounds(20, 20, 100, 30);

        f.add(msg);
        msg.setBounds(225, 20, 200, 100);

        f.add(l_rec);
        l_rec.setBounds(20, 230, 100, 30);

        f.add(rec);
        rec.setBounds(225, 230, 100, 30);

        f.add(ad);
        ad.setBounds(225, 300, 100, 30);
        ad.addActionListener(this);

        f.add(send);
        send.setBounds(225, 400, 100, 30);
        send.addActionListener(this);

        f.add(login);
        login.setBounds(20, 400, 100, 30);
        login.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String command = e.getActionCommand();
 
        if (command.equals("Exit")) {
            System.exit(0);
        }
        if (command.equals("Login")) {
            
            Login_Form lf = new Login_Form();
            lf.setControler(controler);
        }
        if (command.equals("Add Group")) {
            Add_Group ag = new Add_Group();
        }
        
        

        if (e.getSource() == ad) {

            String recipient = rec.getText();

            if (!recipient.endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(lstRecipients, "Enter Valid gmail address");
            } else {
                arrl.add(recipient);
            }
        }

        if (e.getSource() == send) {
            BufferedReader br = null;
            try {
                if (lf == null) {

                    Login_Form lf = new Login_Form();
                    lf.setControler(controler);
                }
                System.out.println("Lf " + lf);
                String s = rec.getText();
                String message = msg.getText();
                System.out.println("Message " + message);
                arrl = new ArrayList<>();
                arrl.clear();
                
                /*Sending in a list
                 */ 
                br = new BufferedReader(new FileReader("file.txt"));
                try {
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    line = br.readLine();

                    while (line != null) {
                        arrl.add(line);
                        line = br.readLine();
                    }

                } catch (IOException ex) {
                    Logger.getLogger(Send_Message.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        br.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Send_Message.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
               
                arrl.add(s);
                Object[] x = arrl.toArray();
                try {
                    controler.getClient().addEvent(message, x);


                } catch (Exception ex) {
                    Logger.getLogger(Send_Message.class.getName()).log(Level.SEVERE, null, ex);
                }


            } catch (FileNotFoundException ex) {
                Logger.getLogger(Send_Message.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(Send_Message.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
