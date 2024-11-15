package timebridge.model;

import org.springframework.data.annotation.Id;

public class Building implements Location {
    private String name;

    public Building(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}