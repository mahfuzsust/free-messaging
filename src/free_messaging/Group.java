package free_messaging;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Mahfuz
 */
public class Group extends JFrame implements ActionListener {

    JLabel label_rec = new JLabel("Recipient");
    JLabel label_msg = new JLabel("Message");
    JTextField rec = new JTextField();
    JTextArea msg = new JTextArea();
    JScrollPane jp = new JScrollPane(msg);
    JButton send = new JButton("Send");
    JMenuBar menubar = new JMenuBar();
    JMenu menu_file = new JMenu("File");
    JMenuItem menuitem_login = new JMenuItem("Login");
    JMenuItem menuitem_logout = new JMenuItem("Logout");
    JMenuItem menuitem_exit = new JMenuItem("Exit");
    JMenu menu_group = new JMenu("Group");
    JMenuItem menuitem_addgroup = new JMenuItem("Add Group");
    JMenuItem menuitem_deletegroup = new JMenuItem("Delete Group");
    JMenu menu_about = new JMenu("About");
    JMenuItem menuitem_about = new JMenuItem("About");
    Controler controler = null;
    Login_Form lf = null;
    Object[] recipients;
    List<String> arrl;
    JComboBox<Object> jcombo;
    JButton main_menu = new JButton("Main Menu");

    public static int GetScreenWorkingWidth() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    }

    public static int GetScreenWorkingHeight() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    }

    public List<String> textFiles() {

        List<String> textFiles = new ArrayList<>();
        File dir = new File(System.getProperty("user.dir"));
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith((".txt"))) {
                textFiles.add(file.getName());
            }
        }
        System.out.println(textFiles);
        return textFiles;
    }

    public Group() throws HeadlessException {
        controler = Controler.getInstance();
        controler.setNumOfSMSMessages(20);
        controler.setNumOfChars(50);
        view();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Exit")) {
            System.exit(0);
        }
        if (command.equals("Login")) {

            lf = new Login_Form();
            lf.setControler(controler);
        }
        if (command.equals("Add Group")) {
            dispose();
            Add_Group ag = new Add_Group();

        }
        if (command.equals("About")) {
            dispose();
            About ag = new About();

        }

        if (command.equals("Delete Group")) {
            dispose();
            Delete_File ag = new Delete_File();

        }
        if (command.equals("Logout")) {

            if (lf == null) {
                JOptionPane.showMessageDialog(menuitem_logout, "Login First");
            } else {
                controler.setClient(null);
                lf = null;
                JOptionPane.showMessageDialog(menuitem_logout, "Logout Successfull");
            }
        }


        if (e.getSource() == main_menu) {
            Free_Messaging fm = new Free_Messaging();
            dispose();
        }

        if (e.getSource() == send) {

            if (lf == null) {

                lf = new Login_Form();
                lf.setControler(controler);
            } else {
                String recipient = rec.getText();
                String message = msg.getText();
                arrl = new ArrayList<>();
                arrl.clear();

                String selected_file = (String) jcombo.getSelectedItem();
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(selected_file));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Group.class.getName()).log(Level.SEVERE, null, ex);
                }
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

                Object[] x = arrl.toArray();

                int numOfChars = controler.getNumOfChars();
                if (message.length() > numOfChars) {
                    int start = 0;
                    int end = numOfChars;
                    for (int i = 0; i < (message.length() / numOfChars) + 1; i++) {
                        String part = message.substring(start, end);
                        start = end;
                        if ((message.length() - end) > numOfChars) {
                            end += numOfChars;
                        } else {
                            end += message.length() - end;
                        }
                        try {
                            controler.getClient().addEvent(part, x);
                            JOptionPane.showMessageDialog(send, "Sent Successfully");
                        } catch (Exception ex) {
                            Logger.getLogger(Single.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(send, "Sending Failed");
                        }
                    }
                } else {
                    try {
                        controler.getClient().addEvent(message, x);
                        JOptionPane.showMessageDialog(send, "Sent Successfully");
                    } catch (Exception ex) {
                        Logger.getLogger(Single.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(send, "Sending Failed");
                    }
                }
            }
        }

    }

    private void view() {

        int width = GetScreenWorkingWidth() / 2 - 200;
        int height = GetScreenWorkingHeight() / 2 - 200;

        setBounds(width, height, 400, 400);
        setTitle("Group Text");
        setVisible(rootPaneCheckingEnabled);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setJMenuBar(menubar);

        menubar.add(menu_file);
        menu_file.add(menuitem_login);
        menuitem_login.addActionListener(this);
        menu_file.add(menuitem_logout);
        menuitem_logout.addActionListener(this);
        menu_file.add(menuitem_exit);
        menuitem_exit.addActionListener(this);

        menubar.add(menu_group);
        menu_group.add(menuitem_addgroup);
        menuitem_addgroup.addActionListener(this);
        menu_group.add(menuitem_deletegroup);
        menuitem_deletegroup.addActionListener(this);

        menubar.add(menu_about);
        menu_about.add(menuitem_about);
        menuitem_about.addActionListener(this);

        add(label_rec);
        label_rec.setBounds(20, 20, 80, 30);

        Object[] com = textFiles().toArray();
        jcombo = new JComboBox<>(com);
        add(jcombo);
        jcombo.setBounds(130, 20, 100, 30);

        add(label_msg);
        label_msg.setBounds(20, 60, 80, 30);

        add(jp);
        jp.setBounds(130, 60, 200, 200);

        add(send);
        send.setBounds(230, 280, 100, 30);
        send.addActionListener(this);

        add(main_menu);
        main_menu.setBounds(20, 280, 100, 30);
        main_menu.addActionListener(this);

    }
}
