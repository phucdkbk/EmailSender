/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and openDataFile the template in the editor.
 */
package com.phucdk.emailsender;

import com.phucdk.emailsender.object.Canho;
import com.phucdk.emailsender.utils.EmailUtils;
import com.phucdk.emailsender.utils.EmailValidator;
import com.phucdk.emailsender.utils.ExcelUtils;
import com.phucdk.emailsender.utils.TemplateUtils;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EmailSenderMain extends JFrame {

    private final JTextField dataFileLabel = new JTextField();
    private final JTextField dataFile = new JTextField();

    private final JTextField templateFileLabel = new JTextField();
    private final JTextField templateFile = new JTextField();

    private final JTextField result = new JTextField();
    private String textResult = "";

    private final JButton openDataFile = new JButton("Open data file");
    private final JButton openTemplateFile = new JButton("Open template file");
    private final JButton processFilter = new JButton("Send email");

    public EmailSenderMain() {
        this.setName("Email sender");
        //Where the GUI is created:
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("Config");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);
        //a group of JMenuItems
        menuItem = new JMenuItem("Config email", KeyEvent.VK_T);

        menuItem.getAccessibleContext().setAccessibleDescription(
                "Config email");
        menuItem.addActionListener(new EmailConfig());
        menu.add(menuItem);

        //Build second menu in the menu bar.
        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription(
                "This menu does nothing");
        menuBar.add(menu);

        this.setJMenuBar(menuBar);
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 1));
        result.setEditable(false);
        p.add(result);
        processFilter.addActionListener(new ProcessSendEmail());
        p.add(processFilter);
        Container cp = getContentPane();
        cp.add(p, BorderLayout.SOUTH);

        p = new JPanel();
        p.setLayout(new GridLayout(12, 1));

        dataFileLabel.setEditable(false);
        dataFileLabel.setText("Input file:");
        p.add(dataFileLabel);
        dataFile.setEditable(false);
        p.add(dataFile);
        openDataFile.addActionListener(new OpenL());
        p.add(openDataFile);
                
        templateFileLabel.setEditable(false);
        templateFileLabel.setText("Template file:");
        p.add(templateFileLabel);
        templateFile.setEditable(false);
        p.add(templateFile);
        openTemplateFile.addActionListener(new OpenL());
        p.add(openTemplateFile);

        cp.add(p, BorderLayout.NORTH);
    }

    class OpenL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            int rVal = c.showOpenDialog(EmailSenderMain.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                dataFile.setText(c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName());
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
                dataFile.setText("");
            }
        }
    }

    class OpenTemplate implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            int rVal = c.showOpenDialog(EmailSenderMain.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                templateFile.setText(c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName());
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
                templateFile.setText("");
            }
        }
    }

    class ProcessSendEmail implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                List<Canho> listCanhos = ExcelUtils.readData(dataFile.getText());
                for (int i = 0; i < 1; i++) {
                    Canho aCanho = listCanhos.get(i);
                    HashMap<String, String> beans = new HashMap<>();
                    EmailSender.setValueToBeans(beans, aCanho);
                    com.phucdk.emailsender.object.EmailConfig emailConfig = new com.phucdk.emailsender.object.EmailConfig();
                    getEmailConfig(emailConfig);
                    String content = TemplateUtils.getContent(templateFile.getText(), beans);
                    if (aCanho.getEmail() != null && EmailValidator.validate(aCanho.getEmail())) {
                        EmailUtils.sendEmail(emailConfig, content, "Thông báo nộp tiền", "phucdkbk@gmail.com", aCanho.getEmail());
                        textResult += "Gửi thành công email tới: " + aCanho.getEmail() + " tương ứng căn hộ: " + aCanho.getSoCanho() + "\n";
                    } else {
                        textResult += "Gửi thất bại do email không hợp lệ, căn hộ tương ứng: " + aCanho.getEmail() + "\n";
                    }
                }
                result.setText(textResult);
            } catch (Exception ex) {
                result.setText(ex.getMessage());
                Logger.getLogger(EmailSenderMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void getEmailConfig(com.phucdk.emailsender.object.EmailConfig emailConfig) {
            emailConfig.setSmtpAuth("true");
            emailConfig.setSmtpClass("javax.net.ssl.SSLSocketFactory");

            Properties prop = new Properties();
            InputStream input = null;
            try {
                input = new FileInputStream("config.properties");
                // load a properties file
                prop.load(input);
                emailConfig.setSmtpHost(prop.getProperty("mail.smtp.host"));
                emailConfig.setSmtpPort(prop.getProperty("mail.smtp.port"));
                emailConfig.setSmtpSocketPort(prop.getProperty("mail.smtp.socketFactory.port"));
                emailConfig.setEmailAddress(prop.getProperty("emailaddress"));
                emailConfig.setPassword(prop.getProperty("password"));

            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    class EmailConfig implements ActionListener {

        private JTextField emailAdress = new JTextField();
        private JTextField password = new JTextField();
        private JTextField smtpHost = new JTextField();
        private JTextField smtpPort = new JTextField();

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JFrame frame = new JFrame("Email config");
                Container cp = frame.getContentPane();
                frame.setSize(500, 400);
                frame.setVisible(true);

                JPanel p = new JPanel();
                JButton save = new JButton("Save");
                save.addActionListener(new SaveEmailConfig());
                p.add(save);
                cp.add(p, BorderLayout.SOUTH);

                p = new JPanel();
                p.setLayout(new GridLayout(8, 1));
                JTextField emailAdressLabel = new JTextField();
                emailAdressLabel.setEditable(false);
                emailAdressLabel.setText("Email Adress:");
                p.add(emailAdressLabel);
                emailAdress.setEditable(true);
                p.add(emailAdress);

                JTextField passwordLabel = new JTextField();
                passwordLabel.setEditable(false);
                passwordLabel.setText("Password:");
                p.add(passwordLabel);
                password.setEditable(true);
                p.add(password);

                JTextField smtpHostLabel = new JTextField();
                smtpHostLabel.setEditable(false);
                smtpHostLabel.setText("SMTP host:");
                p.add(smtpHostLabel);
                smtpHost.setEditable(true);
                p.add(smtpHost);

                JTextField smtpPortLabel = new JTextField();
                smtpPortLabel.setEditable(false);
                smtpPortLabel.setText("SMTP port:");
                p.add(smtpPortLabel);
                smtpPort.setEditable(true);
                p.add(smtpPort);

                cp.add(p, BorderLayout.NORTH);
            } catch (Exception ex) {
                result.setText(ex.getMessage());
                Logger.getLogger(EmailSenderMain.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        class SaveEmailConfig implements ActionListener {

            public SaveEmailConfig() {

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                OutputStream out = null;
                try {
                    Properties props = new Properties();
                    props.setProperty("emailaddress", emailAdress.getText());
                    props.setProperty("password", password.getText());
                    props.setProperty("mail.smtp.host", smtpHost.getText());
                    props.setProperty("mail.smtp.socketFactory.port", smtpPort.getText());
                    props.setProperty("mail.smtp.port", smtpPort.getText());
                    File f = new File("config.properties");
                    out = new FileOutputStream(f);
                    props.store(out, "This is an optional header comment string");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EmailSenderMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(EmailSenderMain.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        out.close();
                    } catch (IOException ex) {
                        Logger.getLogger(EmailSenderMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        run(new EmailSenderMain(), 550, 550);
    }

    public static void run(JFrame frame, int width, int height) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
    }
}
