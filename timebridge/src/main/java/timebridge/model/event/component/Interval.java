package timebridge.model.event.component;


import java.time.ZonedDateTime;

public class Interval {
    private ZonedDateTime start;
    private ZonedDateTime end; 

    public Interval(ZonedDateTime start, ZonedDateTime end) {
        this.start = start;
        this.end = end;
    }

    public Interval(){
        this.start = ZonedDateTime.now();
        this.end = ZonedDateTime.now();
    };

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }
}
