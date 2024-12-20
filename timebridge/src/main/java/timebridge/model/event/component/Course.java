package timebridge.model.event.component;


/**
 * This class represents a course, with name and course code.
 *
 * @since 2024-12-19
 * @author Group 12
 */
public class Course {
    private String name;
    private String code;

    public Course(String code, String name) {
        this.name = name;
        this.code = code;
    }

    public Course() {
        this.name = new String();
        this.code = new String();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
