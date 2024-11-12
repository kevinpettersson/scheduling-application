package timebridge.model;

import org.springframework.data.annotation.Id;

public class Schedule {

    @Id
    private String id;

    private String url;
    private String courseId;

    public Schedule() {}

    public Schedule(String url, String courseId) {
        this.url = url;
        this.courseId = courseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
