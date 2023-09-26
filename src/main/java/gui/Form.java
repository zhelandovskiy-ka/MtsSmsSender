package gui;

import api.API;
import api.HistoryMessage;
import api.Response;
import com.formdev.flatlaf.FlatLightLaf;
import db.Actions;
import db.TableContacts;
import db.TableHistoryActions;
import db.TableHistoryMessage;
import main.Crypto;
import main.Main;
import main.Utilities;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * инициализация gui
 */
public class Form {
    static JComboBox cbGroups = new JComboBox();
    DefaultListModel<String> listNamesModel = new DefaultListModel<>();
    GroupEditForm groupEditForm = new GroupEditForm();
    FormAddContact formAddContact = new FormAddContact();
    JTextArea areaMessage = new JTextArea();
    JTextArea areaHistoryMessage = new JTextArea(TableHistoryMessage.getInstance().loadHistory());
    JButton butAddNumber;
    JButton butAddGroup;
    JButton butSend;
    JButton butAddUser;
    JButton butDelContact;
    JButton butDelGroup;
    JButton butDelGroupFromSending;
    JButton butClearSending;
    JLabel labelSendingList;
    JLabel labelStatus;
    JTable tableGroupEdit;
    JTable tableCurSending;
    JTextField tfSearch;

    /**
     * отображение окна
     */
    public void show() {
        setLookAndFeel();

        initButtons();
        initLabels();
        initTables();
        initTextFields();
        initTextArea();
        initComboBox();

        initButtonsActions();
        initTableActions();
        initComboboxActions();
        initTextFieldsActions();

        loadData();

        initForm();
    }

    private JPanel getPanelRight() {
        BasicPanel panel = new BasicPanel();
        panel.setNullBorder();
        panel.setLayout(new BorderLayout());
        panel.add(getPanelRight1(), BorderLayout.CENTER);
        panel.add(getPanelRight2(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel getPanelLeft() {
        BasicPanel panel = new BasicPanel();
        panel.setNullBorder();

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.5;
        panel.add(getPanelLeftTop(), c);
        c.gridy = 1;
        panel.add(getPanelLeftBottom(), c);

        return panel;
    }

    private JPanel getPanelLeftTop() {
        BasicPanel panel = new BasicPanel();

        GridBagConstraints c = new GridBagConstraints();
//        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0;
        panel.add(getPanelLeft1(), c);
        c.gridy = 1;
        c.weighty = 1;
        panel.add(getPanelLeft2(), c);
        c.gridy = 2;
        c.weighty = 0;
        panel.add(getPanelLeft3(), c);

        return panel;
    }

    private JPanel getPanelLeftBottom() {
        BasicPanel panel = new BasicPanel();

        GridBagConstraints c = new GridBagConstraints();
//        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(getPanelLeft4(), c);
        c.weighty = 0;
        c.gridy = 1;
        panel.add(getPanelLeft5(), c);

        return panel;
    }

    private JPanel getPanelLeft1() {
        BasicPanel panel = new BasicPanel();
        panel.setNullBorder();

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.95;
        panel.add(cbGroups, c);
        c.gridx = 1;
        c.weightx = 0.05;
        panel.add(butAddGroup, c);
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        panel.add(tfSearch, c);

        return panel;
    }

    private JPanel getPanelLeft2() {
        BasicPanel panel = new BasicPanel();
        panel.setNullBorder();

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        panel.add(new JScrollPane(tableGroupEdit), c);

        return panel;
    }

    private JPanel getPanelLeft3() {
        BasicPanel panel = new BasicPanel();
        panel.setNullBorder();

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1;
        c.weightx = 1;
        panel.add(butAddUser, c);
        c.gridx = 1;
        panel.add(butDelContact, c);
        c.gridx = 2;
        panel.add(butDelGroup, c);

        return panel;
    }

    private JPanel getPanelLeft4() {
        BasicPanel panel = new BasicPanel();
        panel.setNullBorder();

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5);
        c.fill = GridBagConstraints.BOTH;

        c.weighty = 0;
        c.weightx = 1;
        panel.add(labelSendingList, c);
        c.weighty = 1;
        c.gridy = 1;
        panel.add(new JScrollPane(tableCurSending), c);

        return panel;
    }

    private JPanel getPanelLeft5() {
        BasicPanel panel = new BasicPanel();
        panel.setNullBorder();

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5);

        c.gridx = 0;
        butClearSending.setSize(new Dimension(tableCurSending.getWidth() / 2 - 50, 20));
        panel.add(butClearSending, c);

        c.gridx = 1;
        butDelGroupFromSending.setSize(new Dimension(tableCurSending.getWidth() / 2 - 50, 20));
        panel.add(butDelGroupFromSending, c);

        return panel;
    }

    private JPanel getPanelRight1() {
        BasicPanel panel = new BasicPanel();
        panel.setNullBorder();

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.85;
        panel.add(new JScrollPane(areaHistoryMessage), c);
        c.gridy = 1;
        c.weighty = 0.15;
        panel.add(new JScrollPane(areaMessage), c);

        return panel;
    }

    private JPanel getPanelRight2() {
        BasicPanel panel = new BasicPanel();
        panel.setNullBorder();

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.7;
        panel.add(labelStatus, c);
        c.gridx = 1;
        c.weightx = 0.3;
        panel.add(butSend, c);

        return panel;
    }

    /**
     * инициализация формы
     */
    private void initForm() {
        JFrame frame = new JFrame("SMS Sender");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        frame.add(getPanelLeft(), BorderLayout.WEST);
        frame.add(getPanelRight(), BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        scrollTextAreaToBottom();
    }

    /**
     * инициализация действий для таблиц
     */
    private void initTableActions() {
        /*
        действие при двойном щелчке мыши по таблице tableGroupEdit
        */
        tableGroupEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String name = (String) tableGroupEdit.getValueAt(tableGroupEdit.getSelectedRow(), 0);
                    String phone = (String) tableGroupEdit.getValueAt(tableGroupEdit.getSelectedRow(), 1);
                    String group = Main.contacts.getGroupByNumber(phone);

                    NonEditTableModel model = (NonEditTableModel) tableCurSending.getModel();
                    model.addRow(new String[]{name, phone, group});
                }
            }
        });

        /*
        действие при двойном щелчке мыши по таблице tableCurSending
        */
        tableCurSending.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    NonEditTableModel model = (NonEditTableModel) tableCurSending.getModel();
                    model.removeRow(tableCurSending.getSelectedRow());
                }

            }
        });
    }

    /**
     * инициализация действий для выпадающих списков
     */
    private void initComboboxActions() {
        cbGroups.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED && cbGroups.getSelectedItem() != null) {
                String[][] data = Main.contacts.getContactsArrayByGroup((String) cbGroups.getSelectedItem(), 3);
                tableGroupEdit.setModel(new NonEditTableModel(data, new String[]{"Имя", "Телефон", "Группа"}));
                if (!tfSearch.getText().equals("Поиск..."))
                    ((TableSearch) tableGroupEdit).search(tfSearch.getText());
                else
                    ((TableSearch) tableGroupEdit).search("");
            }
        });
    }

    /**
     * инициализация действий для кнопок
     */
    private void initButtonsActions() {
        /*
          добавить группу в список рассылки
         */
        butAddGroup.addActionListener(e -> {
            String groupName = (String) cbGroups.getSelectedItem();
            boolean groupHas = false;

            for (int i = 0; i < tableCurSending.getModel().getRowCount(); i++) {
                if (tableCurSending.getModel().getValueAt(i, 2).equals(groupName)) {
                    groupHas = true;
                    break;
                }
            }

            if (!groupHas) {
                assert groupName != null;
                String[][] data = Main.contacts.getContactsArrayByGroup(groupName, 3);

                NonEditTableModel model = (NonEditTableModel) tableCurSending.getModel();
                for (String[] strings : data) {
                    model.addRow(strings);
                }
                tableCurSending.setModel(model);
            } else
                Notifications.showWarning("Группа уже есть в списке рассылки");
        });

        /*
          очистить список рассылки
         */
        butClearSending.addActionListener(e -> {
            NonEditTableModel model = (NonEditTableModel) tableCurSending.getModel();
            model.setRowCount(0);
        });

        /*
          отправить сообщение
         */
        butSend.addActionListener(e -> {
            if (tableCurSending.getRowCount() != 0) {
                if (!areaMessage.getText().equals("")) {
                    SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
                        @Override
                        protected Boolean doInBackground() {
                            butSend.setEnabled(false);

                            Main.getSendList().clear();

                            labelStatus.setText("Выполняется отправка...");
                            publish("Выполняется отправка...");

                            for (int i = 0; i < tableCurSending.getRowCount(); i++) {
                                String number = tableCurSending.getModel().getValueAt(i, 1).toString();
                                Main.getSendList().add(number);
                            }

/*                            Рабочий код отправки сообщения (при наличии токена)
                            Response response = API.sendMessage(areaMessage.getText());
                            int responseCode = response.getCode();*/

                            // загулшка
                            int responseCode = 200;

                            if (responseCode == 200) {
                                addToHistory();
                                labelStatus.setText("");
                                areaMessage.setText("");
                                Notifications.showInfo("Сообщение успешно отправлено");

                                return true;
                            }

                            if (responseCode == -1) {
                                labelStatus.setText("");
                                Notifications.showError("Ошибка отправки");

                                return false;
                            }

                            labelStatus.setText("");

                            return false;
                        }

                        @Override
                        protected void done() {
                            butSend.setEnabled(true);

                            super.done();
                        }
                    };

                    worker.execute();
                } else {
                    Notifications.showWarning("Введите текст сообщения");
                }
            } else {
                Notifications.showWarning("Список рассылки пуст");
            }
        });

        /*
          новый контакт
         */
        butAddUser.addActionListener(e -> {
            formAddContact.showWindow();
        });

        /*
          удалить контакт
         */
        butDelContact.addActionListener(e -> {
            int count = tableGroupEdit.getRowCount();
            int row = tableGroupEdit.getSelectedRow();
            String name = tableGroupEdit.getModel().getValueAt(row, 0).toString();
            String number = tableGroupEdit.getModel().getValueAt(row, 1).toString();

            int id = Main.contacts.getIdByNameNumber(name, number);

            TableContacts.getInstance().delContactById(id);
            TableHistoryActions.getInstance().addAction(Main.currentUser.getId(), Actions.DEL_CONTACT);

            NonEditTableModel model = (NonEditTableModel) tableGroupEdit.getModel();
            model.removeRow(row);

            Main.contacts.refreshContactList();

            if (count == 1) {
                loadData();
                cbGroups.setSelectedIndex(0);
            }

        });

        /*
          удалить группу из рассылки
         */
        butDelGroupFromSending.addActionListener(e -> {
            String group = tableCurSending.getModel().getValueAt(tableCurSending.getSelectedRow(), 2).toString();
            List<Integer> listToRemove = new ArrayList<>();

            for (int i = 0; i < tableCurSending.getRowCount(); i++) {
                String s = tableCurSending.getModel().getValueAt(i, 2).toString();
                if (s.equals(group))
                    listToRemove.add(i);
            }

            int count = 0;
            for (Integer i : listToRemove) {
                i -= count;

                ((NonEditTableModel) tableCurSending.getModel()).removeRow(i);

                count++;
            }
        });

        /*
          удалить группу из базы
         */
        butDelGroup.addActionListener(e -> {
            String group = cbGroups.getSelectedItem().toString();
            TableContacts.getInstance().delGroupByName(Crypto.cryptText(group));

            Main.contacts.delContactsByGroup(group);

            NonEditTableModel model = (NonEditTableModel) tableGroupEdit.getModel();
            model.setRowCount(0);

            Main.contacts.refreshContactList();

            loadData();

            cbGroups.setSelectedIndex(0);
        });
    }

    /**
     * инициализация действий для текстовых полей
     */
    private void initTextFieldsActions() {
        tfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = tfSearch.getText();
                if (!text.equals("Поиск..."))
                    ((TableSearch) tableGroupEdit).search(text);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = tfSearch.getText();
                if (!text.equals("Поиск..."))
                    ((TableSearch) tableGroupEdit).search(text);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String text = tfSearch.getText();
                if (!text.equals("Поиск..."))
                    ((TableSearch) tableGroupEdit).search(text);
            }
        });
        tfSearch.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tfSearch.getText().equals("Поиск..."))
                    tfSearch.setText("");
                tfSearch.setFont(Fonts.FONT_TF_SEARCH_IN);
                tfSearch.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tfSearch.getText().equals("")) {
                    tfSearch.setText("Поиск...");
                    tfSearch.setFont(Fonts.FONT_TF_SEARCH_OUT);
                    tfSearch.setForeground(Color.GRAY);
                }
            }
        });
    }

    /**
     * инициализация выпадающих списков
     */
    private void initComboBox() {
        cbGroups.setFont(Fonts.FONT_BASIC);
    }

    /**
     * инициализация текстовых областей
     */
    private void initTextArea() {
        areaMessage.setFont(Fonts.FONT_BASIC);

        areaHistoryMessage.setFont(Fonts.FONT_BASIC);
        areaHistoryMessage.setLineWrap(true);
        areaHistoryMessage.setEditable(false);
        areaHistoryMessage.setBackground(Color.WHITE);
    }

    /**
     * инициализация полей ввода
     */
    private void initTextFields() {
        tfSearch = new JTextField("Поиск...");
        tfSearch.setFont(Fonts.FONT_TF_SEARCH_OUT);
        tfSearch.setForeground(Color.GRAY);
    }

    /**
     * инициализация таблиц
     */
    private void initTables() {
        tableGroupEdit = new TableSearch(new NonEditTableModel(new String[0][0], new String[]{"Имя", "Телефон"}));
        tableGroupEdit.setFont(Fonts.FONT_BASIC);
        tableGroupEdit.getTableHeader().setFont(Fonts.FONT_BASIC);
        tableGroupEdit.getTableHeader().setBackground(Color.WHITE);


        tableCurSending = new JTable(new NonEditTableModel(new String[0][0], new String[]{"Имя", "Телефон", "Группа"}));
        tableCurSending.setFont(Fonts.FONT_BASIC);
        tableCurSending.getTableHeader().setFont(Fonts.FONT_BASIC);
        tableCurSending.getTableHeader().setBackground(Color.WHITE);
    }

    /**
     * инициализация надписей
     */
    private void initLabels() {
        labelSendingList = new JLabel("Текущий список рассылки");
        labelSendingList.setFont(Fonts.FONT_BASIC);

        labelStatus = new JLabel();
        labelStatus.setFont(Fonts.FONT_BASIC);
    }

    /**
     * инициализация кнопок
     */
    private void initButtons() {
        butAddNumber = new JButton("+");
        butDelContact = new JButton("Удалить контакт");
        butDelGroup = new JButton("Удалить группу");
        butDelGroupFromSending = new JButton("Удалить группу из рассылки");
        butClearSending = new JButton("Очистить");

        butAddGroup = new JButton("+");
        butAddGroup.setPreferredSize(new Dimension(40, 29));

        butSend = new JButton("Отправить");
        butAddUser = new JButton("Новый контакт");

        butAddNumber.setFont(Fonts.FONT_BASIC);
        butDelContact.setFont(Fonts.FONT_BASIC);
        butDelGroup.setFont(Fonts.FONT_BASIC);
        butDelGroupFromSending.setFont(Fonts.FONT_BASIC);
        butClearSending.setFont(Fonts.FONT_BASIC);
        butAddGroup.setFont(Fonts.FONT_BASIC);
        butSend.setFont(Fonts.FONT_BASIC);
        butAddUser.setFont(Fonts.FONT_BASIC);
    }

    /**
     * создание пустой границы
     */
    private static Border getEmptyBorder(int size) {
        return BorderFactory.createEmptyBorder(size, size, size, size);
    }

    /**
     * загрузка списка групп в ComboBox
     */
    public static void loadData() {
        cbGroups.removeAllItems();

        cbGroups.addItem("Все контакты");
        for (String group : Main.contacts.getListUniqGroups()) {
            cbGroups.addItem(group);
        }
    }

    /**
     * добавление в историю сообщений
     */
    private void addToHistory() {
        HistoryMessage message = new HistoryMessage(areaMessage.getText());

        areaHistoryMessage.append(message.toString());
        TableHistoryMessage.getInstance().addHistory(message);

        scrollTextAreaToBottom();
    }

    /**
     * скролинг окна истории сообщений вниз
     */
    private void scrollTextAreaToBottom() {
        areaHistoryMessage.setCaretPosition(areaHistoryMessage.getDocument().getLength());
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private DefaultTableModel getTableModel() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return model;
    }
}
