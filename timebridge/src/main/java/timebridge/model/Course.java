package timebridge.model;

import org.springframework.data.annotation.Id;

public class Course {
    @Id
    private String id;
    private String name;
    private String code;

    public Course(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
