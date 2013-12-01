package free_messaging;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
class Add_Group extends JFrame implements ActionListener {

    JLabel jl_name = new JLabel("Group Name");
    JTextField group = new JTextField("");
    JLabel jl_address = new JLabel("Add Address");
    JTextField address = new JTextField("");
    JTextArea jt = new JTextArea();
    JScrollPane jp = new JScrollPane(jt);
    JButton add = new JButton("Add");
    JButton submit = new JButton("Submit");
    String s = "";
    List<String> arrl = new ArrayList<>();
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
    JButton main_menu = new JButton("Main Menu");

    public Add_Group() throws HeadlessException {
        view();
        arrl.clear();
        controler = Controler.getInstance();
        controler.setNumOfSMSMessages(20);
        controler.setNumOfChars(50);

    }

    public static int GetScreenWorkingWidth() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    }

    public static int GetScreenWorkingHeight() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    }

    private void view() {

        int width = GetScreenWorkingWidth() / 2 - 200;
        int height = GetScreenWorkingHeight() / 2 - 200;

        setBounds(width, height, 400, 400);
        setTitle("Add Group");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(rootPaneCheckingEnabled);
        setLayout(null);

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

        add(jl_name);
        jl_name.setBounds(20, 20, 80, 20);
        add(group);
        group.setBounds(110, 20, 150, 20);

        add(jl_address);
        jl_address.setBounds(20, 60, 80, 20);
        add(address);
        address.setBounds(110, 60, 150, 20);

        add(add);
        add.setBounds(270, 60, 80, 20);
        add.addActionListener(this);

        add(jp);
        jp.setBounds(110, 100, 150, 130);
        jt.setEditable(false);

        add(submit);
        submit.setBounds(270, 270, 80, 30);
        submit.addActionListener(this);

        add(main_menu);
        main_menu.setBounds(20, 270, 100, 30);
        main_menu.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String recipient = address.getText();
        String command = e.getActionCommand();

        if (command.equals("Exit")) {
            System.exit(0);
        }
        if (command.equals("Login")) {
            if (lf == null) {
                lf = new Login_Form();
                lf.setControler(controler);
            } else {
                JOptionPane.showMessageDialog(menuitem_logout, "Logout First");
            }
        }
        if (command.equals("Logout")) {
            if (lf != null) {
                controler.setClient(null);
                lf = null;
                JOptionPane.showMessageDialog(menuitem_logout, "Logout Successfull");
            } else {
                JOptionPane.showMessageDialog(menuitem_logout, "Login First");
            }
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



        if (e.getSource() == main_menu) {
            dispose();
            Free_Messaging fm = new Free_Messaging();
            
        }

        if (e.getSource() == add) {
            if (!recipient.endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(add, "Enter Valid gmail address");
            } else {
                arrl.add(recipient);
                s = s.concat(recipient + "\n");
                jt.setText("");
                jt.setText(s);
            }
        }
        String abc = group.getText();
        String def = jt.getText();

        if (e.getSource() == submit) {

            if (abc == null) {
                JOptionPane.showMessageDialog(submit, "Please enter valid group name");
            } else {

                String content = s;
                String name = group.getText();
                File file = new File(name + ".txt");
                {
                    FileWriter fw = null;
                    try // if file doesnt exists, then create it
                    {
                        if (!file.exists()) {
                            try {
                                file.createNewFile();
                            } catch (IOException ex) {
                                Logger.getLogger(Add_Group.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        fw = new FileWriter(file.getAbsoluteFile());
                        BufferedWriter bw = new BufferedWriter(fw);

                        for (int i = 0; i < arrl.size(); i++) {
                            bw.write(arrl.get(i));
                            bw.newLine();
                        }

                        bw.close();
                        JOptionPane.showConfirmDialog(rootPane, "Save?");
                        group.setText("");
                        address.setText("");
                        jt.setText("");

                    } catch (IOException ex) {
                        Logger.getLogger(Add_Group.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            fw.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Add_Group.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }
}
