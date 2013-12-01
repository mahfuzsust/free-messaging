/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package free_messaging;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Mahfuz
 */
class About extends JFrame implements ActionListener{
    
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
    
    JLabel name = new JLabel("Md. Mahfuzur Rahman");
    JLabel reg = new JLabel("Reg. No: 2008331060");
    JLabel dept = new JLabel("CSE, SUST");
    JLabel mail = new JLabel("E-mail: mahfuz@student.sust.edu");
    JLabel credits = new JLabel("Credits: ");
    
    ImageIcon image = new ImageIcon("mahfuz.jpg");
    JLabel imageLabel = new JLabel(image);
    
    Controler controler = null;
    Login_Form lf = null;
    
    JButton main_menu = new JButton("Main Menu");


    public About() throws HeadlessException {
        view();
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
    
    public final void view(){
        
        int width = GetScreenWorkingWidth() / 2 - 200;
        int height = GetScreenWorkingHeight() / 2 - 200;

        setBounds(width, height, 350, 300);
        setTitle("About");
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
        
        //add(imageLabel);
        //imageLabel.setBounds(20, 20, 500, 500);
        add(credits);
        credits.setBounds(120, 20, 200, 30);
        add(name);
        name.setBounds(120, 60, 200, 20);
        add(reg);
        reg.setBounds(120, 80, 200, 20);
        add(dept);
        dept.setBounds(120, 100, 200, 20);
        add(mail);
        mail.setBounds(120, 120, 200, 20);
        
        add(main_menu);
        main_menu.setBounds(30, 190, 100, 30);
        main_menu.addActionListener(this);
        
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String command = e.getActionCommand();

        if (command.equals("Exit")) {
            System.exit(0);
        }
        if (command.equals("Login")) {
            if(lf == null) {
                lf = new Login_Form();
                lf.setControler(controler);
            }else JOptionPane.showMessageDialog(menuitem_logout, "Logout First");
        }
        if (command.equals("Logout")) {
            if (lf != null) {
                controler.setClient(null);
                lf = null;
                JOptionPane.showMessageDialog(menuitem_logout, "Logout Successfull");
            }
            else JOptionPane.showMessageDialog(menuitem_logout, "Login First");
        }
        if (command.equals("Add Group")) {
            Add_Group ag = new Add_Group();
            dispose();
        }
        if (command.equals("About")) {
            About ag = new About();
            dispose();
        }
        
        if (command.equals("Delete Group")) {
            Delete_File ag = new Delete_File();
            dispose();
        }
        
        if(e.getSource() == main_menu)
        {
            Free_Messaging fm = new Free_Messaging();
            dispose();
        }
        
    }
    
}
