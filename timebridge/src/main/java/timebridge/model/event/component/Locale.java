package timebridge.model.event.component;

/**
 * This class represents a locale with building and room.
 *
 * @since 2024-12-19
 * @author Group 12
 */
public class Locale {
    private String building;
    private String room;

    public Locale(String building, String room) {
        this.building = building;
        this.room = room;
    }
    
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}