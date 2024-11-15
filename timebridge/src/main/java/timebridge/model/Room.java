package timebridge.model;

import org.springframework.data.annotation.Id;

public class Room implements Location {
    private String name;

    public Room(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}