package timebridge.model.event.schema;

import java.util.ArrayList;
import java.util.List;

public class EventSchema {
    private List<SummaryAttribute> summarySchema;
    private List<DescriptionAttribute> descriptionSchema;
    private List<LocationAttribute> locationSchema;

    public enum SummaryAttribute {
        COURSE_NAME,
        COURSE_CODE,
        ACTIVITY,
    }

    public enum DescriptionAttribute {
        COURSE_NAME,
        COURSE_CODE,
        ACTIVITY,
    }

    public enum LocationAttribute {
        BUILDING,
        ROOM,
    }

    public EventSchema() {
        this.summarySchema = new ArrayList<>();
        this.descriptionSchema = new ArrayList<>();
        this.locationSchema = new ArrayList<>();
        this.setDefaultSchema();
    }

    public void setDefaultSchema() {
        this.summarySchema.clear();
        this.descriptionSchema.clear();
        this.locationSchema.clear();

        this.summarySchema.add(SummaryAttribute.COURSE_CODE);
        this.summarySchema.add(SummaryAttribute.ACTIVITY);
        this.descriptionSchema.add(DescriptionAttribute.COURSE_NAME);
        this.locationSchema.add(LocationAttribute.ROOM);
    }

    public List<SummaryAttribute> getSummarySchema() {
        return this.summarySchema;
    }

    public List<DescriptionAttribute> getDescriptionSchema() {
        return this.descriptionSchema;
    }

    public List<LocationAttribute> getLocationSchema() {
        return this.locationSchema;
    }

    public void setSummarySchema(List<SummaryAttribute> summarySchema) {
        this.summarySchema = summarySchema;
    }

    public void setDescriptionSchema(List<DescriptionAttribute> descriptionSchema) {
        this.descriptionSchema = descriptionSchema;
    }

    public void setLocationSchema(List<LocationAttribute> locationSchema) {
        this.locationSchema = locationSchema;
    }
}
