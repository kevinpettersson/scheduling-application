package timebridge.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.BufferedReader;
import java.util.ArrayList;
import timebridge.model.Event;
import timebridge.model.Interval;

public abstract class Parser {
    
    // Formatting of datetimes in ICS file.
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

    static public void CalendarToIcal() throws IOException {
        // Create an ical and return it!! should NOT be VOID. return type should be ICAL!!!!!
    } //SUIIIII / nils
   

    // Method to parse iCal-file to our model
    static public Calendar icalToCalendar(BufferedReader input) throws IOException {
        ArrayList<Event> events = getEvents(input);
        return null;
    } 

    private static ArrayList<Event> getEvents(BufferedReader input) throws IOException {
        ArrayList<Event> events = new ArrayList<Event>();
        try {
            String line;
    
            while ((line = input.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("BEGIN:VEVENT")){ 
                    // basics needed for creation of event object
                    Course course = null;
                    String activity = null;
                    Interval interval = new Interval(null, null);
                    ArrayList<Location> locations = new ArrayList<>();

                    while((line = input.readLine()) != "END:VEVENT") {

                        // Find start time and add it to interval.
                        if (line.startsWith("DTSTART:")){ 
                            line.replace("DTSTART:", "");
                            interval.setStart(ZonedDateTime.parse(line, formatter.withZone(ZoneOffset.UTC)));
                        } 

                        // Find end time and add it to interval.
                        else if (line.startsWith("DTEND:")){ 
                            line.replace("DTEND:", "");
                            interval.setEnd(ZonedDateTime.parse(line, formatter.withZone(ZoneOffset.UTC)));
                        }

                        // Find summary and write it to a String.
                        else if (line.startsWith("SUMMARY:")){
                            String summary = "";
                            line.replace("SUMMARY:","");
                            while(!line.startsWith("LOCATION:")){
                                summary += line;
                                input.mark(100);
                                line = input.readLine();
                            }
                            input.reset();
                        }

                        // Find location and write it to activity.
                        else if(line.startsWith("LOCATION:")) {
                            String location = "";
                            line.replace("LOCATION:","");
                            while(!line.startsWith("DESCRIPTION:")){
                                location += line;
                                input.mark(100);
                                line = input.readLine();
                            }
                            input.reset();
                        }
                        
                    }

                    Event event = new Event(course, activity, interval, locations);
                    events.add(event);
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }
}
