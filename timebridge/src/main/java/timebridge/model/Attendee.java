package timebridge.model;

public class Attendee {
    private String name;
    private String mail;

    public Attendee() {
        this.name = new String();
        this.mail = new String();
    }

    public Attendee(String name, String mail) {
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

    public void setMail(String mail) {
        this.mail = mail;
    }
}
