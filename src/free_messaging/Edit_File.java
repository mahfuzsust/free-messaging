package free_messaging;

import java.awt.BorderLayout;
import java.awt.Container;
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
public class Edit_File extends JFrame implements ActionListener {

    JLabel label_rec = new JLabel("Select File");
    JButton delete = new JButton("Delete");
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
    JComboBox<Object> jcombo;
    JButton main_menu = new JButton("Main Menu");
//    JList list;
    JList<Object> list;
    JScrollPane jp;
    JButton show = new JButton("Show");
    ArrayList<String> arrl; 
    
    JScrollPane scrollPane;

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

    public Edit_File() throws HeadlessException {
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
            Add_Group ag = new Add_Group();
        }
        if (command.equals("Logout")) {

            controler.setClient(null);
            lf = null;
            JOptionPane.showMessageDialog(menuitem_logout, "Logout Successfull");
        }

        if (command.equals("About")) {
            About ag = new About();
            dispose();
        }
        if (command.equals("Delete Group")) {
            Delete_File ag = new Delete_File();
            dispose();
        }


        if (e.getSource() == main_menu) {
            Free_Messaging fm = new Free_Messaging();
            dispose();
        }

        if (e.getSource() == show) {
            String selected_file = (String) jcombo.getSelectedItem();
            arrl = new ArrayList<>();
            arrl.clear();
            
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(selected_file));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Edit_File.class.getName()).log(Level.SEVERE, null, ex);
            }

            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                line = br.readLine();
            } catch (IOException ex) {
                Logger.getLogger(Edit_File.class.getName()).log(Level.SEVERE, null, ex);
            }

            while (line != null) {
                try {
                    arrl.add(line);
                    line = br.readLine();
                } catch (IOException ex) {
                    Logger.getLogger(Edit_File.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Object[] xy = null;
            xy = arrl.toArray();
            System.out.println(xy[0]);
            list = new JList(xy);
            
            
            list.setSelectedIndex(0);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            Container contentpane = getContentPane();
            contentpane.add(new JScrollPane(list));
            
            JScrollPane scrollpane;
            scrollPane = new JScrollPane(list);
            scrollPane.getViewport().add(list);

            //add(scrollpane);
            //scrollpane.setBounds(130, 60, 220, 100);
            
            //add(list);
            list.setBounds(130, 60, 220, 100);
            
            contentpane.add(scrollPane,BorderLayout.CENTER);;
            contentpane.add(list);



        }

        if (e.getSource() == delete) {
            String selected_file = (String) jcombo.getSelectedItem();
            File f = new File(selected_file);
            Boolean flag = false;
            if (f.exists()) {
                flag = f.delete();
                JOptionPane.showConfirmDialog(delete, "Delete Group?");
                Free_Messaging fm = new Free_Messaging();
                dispose();
            } else {
                System.out.println("File not found to delete");
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

//        add(jp);
//        jp.setBounds(130, 60, 100, 100);

        add(show);
        show.setBounds(240, 20, 100, 30);
        show.addActionListener(this);


        add(delete);
        delete.setBounds(230, 280, 100, 30);
        delete.addActionListener(this);

        add(main_menu);
        main_menu.setBounds(20, 280, 100, 30);
        main_menu.addActionListener(this);

    }
}
