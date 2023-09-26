package api;

import db.BaseConnection;
import db.TableContacts;
import gui.NonEditTableModel;
import main.Crypto;
import main.Main;

import java.util.*;

public class Contacts {
    private List<Contact> contactList = new ArrayList<>();

    public Contacts() {
        refreshContactList();
    }

    /**
     * обновление списка контактов
     */
    public void refreshContactList() {
        contactList = TableContacts.getInstance().loadContacts();
    }

    /**
     * получение списка имен по названию группы
     */
    public List<String> getNamesListByGroup(String group) {
        List<String> list = new ArrayList<>();
        for (Contact contact : contactList) {
            if (contact.getGroup().equals(group)) {
                list.add(contact.getName());
            }
        }

        return list;
    }

    /**
     * получение id контакта по имени и телефону
     */
    public int getIdByNameNumber(String name, String number) {
        for (Contact contact : contactList) {
            if (contact.getName().equals(name) && contact.getNumber().equals(number)) {
                return contact.getId();
            }
        }

        return -1;
    }

    /**
     * получение массива контактов по названию группы
     */
    public String[][] getContactsArrayByGroup(String group, int countCol) {
        List<Contact> list = new ArrayList<>();

        if (group.equals("Все контакты"))
            list = contactList;
        else
            for (Contact contact : contactList) {
                if (contact.getGroup().equals(group)) {
                    list.add(contact);
                }
            }

        String[][] arrays = new String[list.size()][countCol];
        for (int i = 0; i < list.size(); i++) {
            arrays[i][0] = list.get(i).getName();
            arrays[i][1] = list.get(i).getNumber();
            arrays[i][2] = list.get(i).getGroup(); //fix
            if (countCol == 3)
                arrays[i][2] = list.get(i).getGroup(); //fix
        }

        return arrays;
    }

    /**
     * получение уникального списка групп
     */
    public List<String> getListUniqGroups() {
        Set<String> setGroups = new HashSet<>();
        for (Contact contact : contactList) {
            setGroups.add(contact.getGroup());
        }

        List<String> sortedList = new ArrayList<>(setGroups);
        Collections.sort(sortedList);

        return sortedList;
    }

    /**
     * получение группы по номеру контакта
     */
    public String getGroupByNumber(String number) {
        for (Contact contact : contactList) {
            if (contact.getNumber().equals(number))
                return contact.getGroup();
        }

        return "null";
    }

    /**
     * получение имени по номеру номеру телефона
     */
    public String getNameByNumber(String number) {
        for (Contact contact : contactList) {
            if (contact.getNumber().equals(number))
                return contact.getName();
        }

        return number;
    }

    /**
     * удаление контакта по имени и телефону
     */
    public int delContactByNameNumber(String name, String number) {
        int n = -1;
        for (int i = 0; i < contactList.size(); i++) {
            Contact contact = contactList.get(i);
            if (contact.getName().equals(name) && contact.getNumber().equals(number))
                n = i;
        }

        if (n != -1) {
            contactList.remove(n);
            return 1;
        }

        return 0;
    }

    /**
     * удаление контактов по группе
     */
    public int delContactsByGroup(String group) {
        int n = -1;
        List<Integer> listToRemove = new ArrayList<>();

        for (int i = 0; i < contactList.size(); i++) {
            Contact contact = contactList.get(i);

            if (contact.getGroup().equals(group))
                listToRemove.add(i);
        }

        int count = 0;
        for (Integer i : listToRemove) {
            i -= count;
            contactList.remove(i);
            count++;

            return 1;
        }

        return 0;
    }

    public void printContactList() {
        for (Contact contact : contactList) {
            System.out.println(contact.toString());
        }
    }

    public List<Contact> getContactList() {
        return contactList;
    }
}
