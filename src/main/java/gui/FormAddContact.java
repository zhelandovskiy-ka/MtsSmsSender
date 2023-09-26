package gui;

import api.Contact;
import com.formdev.flatlaf.FlatLightLaf;
import db.BaseConnection;
import db.TableContacts;
import main.Crypto;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * инициализация окна добавления контакта
 */
public class FormAddContact extends JFrame {

    public FormAddContact() throws HeadlessException {
        initGui();
    }

    private void initGui() {
        setSize(300, 340);
        setTitle("Новый контакт");
        setResizable(false);
        setLookAndFeel();
        setForeground(Color.WHITE);

        JLabel labName = new JLabel("Имя");
        JTextField tfName = new JTextField();
        JLabel labNumber = new JLabel("Телефон");

        JTextField tfNumber = new JTextField("Номер вводить с 7");
        tfNumber.setFont(Fonts.FONT_TF_SEARCH_OUT);
        tfNumber.setForeground(Color.GRAY);

        JLabel labGroup = new JLabel("Группа");
        JTextField tfGroup = new JTextField();
        JLabel labTags = new JLabel("Теги");
        JTextField tfTags = new JTextField();
        JButton butAdd = new JButton("Создать");

//        Font font = new Font("Gill Sans", 0, 18);
        Font font = Fonts.FONT_BASIC;
        labName.setFont(font);
        labGroup.setFont(font);
        labNumber.setFont(font);
        labTags.setFont(font);
        tfGroup.setFont(font);
        tfName.setFont(font);
        tfNumber.setFont(font);
        tfTags.setFont(font);
        butAdd.setFont(font);

        butAdd.addActionListener(e -> {
            Contact contact = new Contact();
            contact.setName(Crypto.cryptText(tfName.getText()));
            contact.setNumber(Crypto.cryptText(tfNumber.getText()));
            contact.setGroup(Crypto.cryptText(tfGroup.getText()));
            contact.setTags(Crypto.cryptText(tfTags.getText()));

            TableContacts.getInstance().addContact(contact);

            Main.contacts.refreshContactList();

            Form.loadData();

            setVisible(false);

            tfName.setText("");
            tfGroup.setText("");
            tfNumber.setText("");
            tfTags.setText("");
        });


        tfNumber.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tfNumber.getText().equals("Номер вводить с 7"))
                    tfNumber.setText("");
                tfNumber.setFont(Fonts.FONT_TF_SEARCH_IN);
                tfNumber.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tfNumber.getText().equals("")) {
                    tfNumber.setText("Номер вводить с 7");
                    tfNumber.setFont(Fonts.FONT_TF_SEARCH_OUT);
                    tfNumber.setForeground(Color.GRAY);
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
        add(labName, c);

        c.gridy = 1;
        add(tfName, c);

        c.gridy = 2;
        add(labNumber, c);

        c.gridy = 3;
        add(tfNumber, c);

        c.gridy = 4;
        add(labGroup, c);

        c.gridy = 5;
        add(tfGroup, c);

        c.gridy = 6;
        add(labTags, c);

        c.gridy = 7;
        add(tfTags, c);

        c.gridy = 9;
        add(butAdd, c);

        setLocationRelativeTo(null);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    /**
     * показ окна добавления контакта
     * */
    public void showWindow() {
        setVisible(true);
    }
}
