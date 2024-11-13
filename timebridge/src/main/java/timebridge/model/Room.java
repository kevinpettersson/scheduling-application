package timebridge.model;

import org.springframework.data.annotation.Id;

public class Room implements Location {

    @Id
    private String id;
    private String name;

    public Room(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}