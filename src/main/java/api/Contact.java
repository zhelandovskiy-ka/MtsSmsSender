package api;

import main.Crypto;

/**создание контакта*/
public class Contact {
    private int id;
    private String name;
    private String number;
    private String group;
    private String tags;

    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", group='" + group + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }

    public String[] asArray() {
        return new String[]{name, number, group, tags};
    }

    public Contact() {
    }

    public Contact(String name, String number, String group) {
        this.name = name;
        this.number = number;
        this.group = group;
    }

    public Contact(String name, String number, String group, String tags) {
        this.name = name;
        this.number = number;
        this.group = group;
        this.tags = tags;
    }

    public String getName() {
        return Crypto.decryptText(name);
    }

    public String getNameCrypt() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return Crypto.decryptText(number);
    }

    public String getNumberCrypt() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGroup() {
        return Crypto.decryptText(group);
    }
    public String getGroupCrypt() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTags() {
        return Crypto.decryptText(tags);
    }

    public String getTagsCrypt() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
