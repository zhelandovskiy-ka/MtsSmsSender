package api;

public class MessageIds {
    private String number;
    private String mesId;

    public MessageIds(String number, String mesId) {
        this.number = number;
        this.mesId = mesId;
    }

    @Override
    public String toString() {
        return "MessageIds{" +
                "number='" + number + '\'' +
                ", mesId='" + mesId + '\'' +
                '}';
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMesId() {
        return mesId;
    }

    public void setMesId(String mesId) {
        this.mesId = mesId;
    }
}
