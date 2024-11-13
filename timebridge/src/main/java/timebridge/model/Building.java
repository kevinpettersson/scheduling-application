package timebridge.model;

import org.springframework.data.annotation.Id;

public class Building implements Location {

    @Id
    private String id;
    private String name;

    public Building(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}