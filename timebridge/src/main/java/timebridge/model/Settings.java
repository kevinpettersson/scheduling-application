package timebridge.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Iterator;

public class Settings {
    private ArrayList<String> courseFilter;
    private ArrayList<String> activityFilter;
    private ArrayList<String> summaryFormat;
    private ArrayList<String> descriptionFormat;
    private ArrayList<String> locationFormat;

    // Constructor that takes a JSON object
    public Settings(String jsonSettings) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonSettings);

        this.courseFilter = parseArray(root, "courseFilter");
        this.activityFilter = parseArray(root, "activityFilter");
        this.summaryFormat = parseArray(root, "summaryFormat");
        this.descriptionFormat = parseArray(root, "descriptionFormat");
        this.locationFormat = parseArray(root, "locationFormat");
    }

    // Helper method to parse an array field from the JSON node
    private ArrayList<String> parseArray(JsonNode root, String fieldName) {
        ArrayList<String> list = new ArrayList<>();
        JsonNode arrayNode = root.get(fieldName);
        if (arrayNode != null && arrayNode.isArray()) {
            for (Iterator<JsonNode> it = arrayNode.elements(); it.hasNext();) {
                list.add(it.next().asText());
            }
        }
        return list;
    }

    public ArrayList<String> getCourseFilter() {
        return courseFilter;
    }

    public void setCourseFilter(ArrayList<String> courseFilter) {
        this.courseFilter = courseFilter;
    }

    public ArrayList<String> getActivityFilter() {
        return activityFilter;
    }

    public void setActivityFilter(ArrayList<String> activityFilter) {
        this.activityFilter = activityFilter;
    }

    public ArrayList<String> getSummaryFormat() {
        return summaryFormat;
    }

    public void setSummaryFormat(ArrayList<String> summaryFormat) {
        this.summaryFormat = summaryFormat;
    }

    public ArrayList<String> getDescriptionFormat() {
        return descriptionFormat;
    }

    public void setDescriptionFormat(ArrayList<String> descriptionFormat) {
        this.descriptionFormat = descriptionFormat;
    }

    public ArrayList<String> getLocationFormat() {
        return locationFormat;
    }

    public void setLocationFormat(ArrayList<String> locationFormat) {
        this.locationFormat = locationFormat;
    }
}
