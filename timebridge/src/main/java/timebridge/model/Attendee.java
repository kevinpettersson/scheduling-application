package timebridge.model;


public class Attendee {
    private String name;
    private String mail;

    public Attendee() {
        this.name = "";
        this.mail = "";
    }

    public Attendee(String mail, String name) {
        this.name = name;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String code) {
        this.mail = mail;
    }
}
