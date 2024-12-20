package timebridge.model.event.component;


import java.time.ZonedDateTime;

/**
 * This class represents an interval of time, with a start and end time.
 *
 * @since 2024-12-19
 * @author Group 12
 */
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
