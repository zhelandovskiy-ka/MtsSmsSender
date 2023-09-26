package gui;

import api.User;
import com.formdev.flatlaf.FlatLightLaf;
import db.TableUsers;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FormLogin extends JFrame implements KeyListener {
    JTextField tfLogin = new JTextField("Логин");
    JPasswordField tfPassword = new JPasswordField("Пароль");
    public FormLogin() throws HeadlessException {
        setLookAndFeel();
        initGui();
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void initGui() {
        setSize(300, 160);
        setTitle("Авторизация");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setForeground(Color.WHITE);

        JButton butLogin = new JButton("Войти");

        tfLogin.setFont(Fonts.FONT_TF_LOGIN_OUT);
        tfLogin.setForeground(Color.GRAY);
        tfPassword.setFont(Fonts.FONT_TF_LOGIN_OUT);
        tfPassword.setForeground(Color.GRAY);
        tfPassword.setEchoChar((char) 0);

        butLogin.setFont(Fonts.FONT_BASIC);

        butLogin.addActionListener(e -> {
            tryLogin(tfLogin.getText(), tfPassword.getPassword());
        });

        tfLogin.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tfPassword.grabFocus();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        tfPassword.addKeyListener(this);

        tfLogin.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tfLogin.getText().equals("Логин"))
                    tfLogin.setText("");
                tfLogin.setFont(Fonts.FONT_TF_LOGIN_IN);
                tfLogin.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tfLogin.getText().equals("")) {
                    tfLogin.setText("Логин");
                    tfLogin.setFont(Fonts.FONT_TF_LOGIN_OUT);
                    tfLogin.setForeground(Color.GRAY);
                }
            }
        });

        tfPassword.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(tfPassword.getPassword()).equals("Пароль"))
                    tfPassword.setText("");
                tfPassword.setFont(Fonts.FONT_TF_PASS_IN);
                tfPassword.setForeground(Color.BLACK);
                tfPassword.setEchoChar('●');
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(tfPassword.getPassword()).equals("")) {
                    tfPassword.setText("Пароль");
                    tfPassword.setFont(Fonts.FONT_TF_LOGIN_OUT);
                    tfPassword.setForeground(Color.GRAY);
                    tfPassword.setEchoChar((char) 0);
                }
            }
        });

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.85;
        c.insets = new Insets(5, 5, 0, 5);
        add(tfLogin, c);

        c.gridy = 1;
        add(tfPassword, c);

        c.gridy = 2;
        add(butLogin, c);

        setLocationRelativeTo(null);
    }

    private void tryLogin(String login, char[] password) {
        if (!login.equals("") && !String.valueOf(password).equals("")) {
            int result = TableUsers.getInstance().checkUser(login, password);
            if (result != -1) {
                hideWindow();
                new Form().show();
                TableUsers.getInstance().updateLoginDate(login);
                Main.currentUser = new User(result, login);
            }
        } else
            JOptionPane.showMessageDialog(null, "Введите логин и пароль", "Информация", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            tryLogin(tfLogin.getText(), tfPassword.getPassword());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * показ окна добавления контакта
     */
    public void showWindow() {
        setVisible(true);
    }

    public void hideWindow() {
        setVisible(false);
    }
}
