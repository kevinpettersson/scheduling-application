package timebridge.model;

public class EventFormat {
    private String summary;
    private String description;
    private String location;

    public EventFormat() {
        this.summary = "";
        this.description = "";
        this.location = "";
    }

    public EventFormat(String summary, String description, String location) {
        this.summary = summary;
        this.description = description;
        this.location = location;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
