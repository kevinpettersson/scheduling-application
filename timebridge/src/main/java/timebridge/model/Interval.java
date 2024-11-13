package timebridge.model;

import org.springframework.data.annotation.Id;
import java.time.ZonedDateTime;
import java.time.ZoneId;

public class Interval {

    @Id
    private String id;
    private ZonedDateTime start;
    private ZonedDateTime end; 
    private ZoneId zoneId;

    public Interval(ZonedDateTime start, ZonedDateTime end, ZoneId zoneId) {
        this.start = start;
        this.end = end;
        this.zoneId = zoneId;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }
}
