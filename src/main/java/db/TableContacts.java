package db;

import api.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TableContacts extends BaseConnection {
    private String[] columns;
    private String[][] data;

    private static TableContacts tableContacts;

    public TableContacts() {
        super();
    }

    public static TableContacts getInstance() {
        if (tableContacts == null)
            return tableContacts = new TableContacts();
        else
            return tableContacts;
    }

    /**
     * загрузка списка контактов из базы
     */
    public List<Contact> loadContacts() {
        String sql = "SELECT * FROM contacts;";
        List<Contact> listContact = new ArrayList<>();

        try (Statement statement = this.connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                String[] columns = getColumns(resultSet.getMetaData());
                while (resultSet.next()) {
                    Contact contact = new Contact();
                    for (String column : columns) {
                        switch (column) {
                            case "id": {
                                contact.setId((int) resultSet.getObject(column));
                                break;
                            }
                            case "name": {
                                contact.setName((String) resultSet.getObject(column));
                                break;
                            }
                            case "number": {
                                contact.setNumber((String) resultSet.getObject(column));
                                break;
                            }
                            case "group_name": {
                                contact.setGroup((String) resultSet.getObject(column));
                                break;
                            }
                            case "tags": {
                                contact.setTags((String) resultSet.getObject(column));
                                break;
                            }
                        }
                    }

                    listContact.add(contact);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listContact;
    }

    /**
     * добавление контакта в базу
     */
    public void addContact(Contact contact) {
        String sql = String.format(
                "INSERT INTO contacts VALUES(NULL, " +
                        "\"%s\", \"%s\", \"%s\", \"%s\");"
                , contact.getNameCrypt(), contact.getNumberCrypt(), contact.getGroupCrypt(), contact.getTagsCrypt()
        );

        updateSQL(sql);
    }

    /**
     * удаление контакта из базы по имени и телефону
     */
    public void delContactByNameNumber(String name, String number) {
        String sql = String.format(
                "DELETE FROM contacts WHERE name=\"%s\" AND number=\"%s\";"
                , name, number
        );

        updateSQL(sql);
    }

    /**
     * удаление контакта из базы по id
     */
    public void delContactById(int id) {
        String sql = String.format("DELETE FROM contacts WHERE id=%s;", id);

        updateSQL(sql);
    }

    /**
     * удаление группы из базы по названию группы
     */
    public void delGroupByName(String group) {
        String sql = String.format(
                "DELETE FROM contacts WHERE group_name=\"%s\";"
                , group
        );

        updateSQL(sql);
    }
}
