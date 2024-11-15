package timebridge.model;

public class Location {
    private String building;
    private String room;

    public Location(String building, String room) {
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