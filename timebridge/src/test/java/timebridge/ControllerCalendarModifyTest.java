package timebridge;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import timebridge.model.Calendar;
import timebridge.model.event.Event;
import timebridge.model.event.EventDecoratorType;
import timebridge.model.event.component.Course;

@SpringBootTest
@AutoConfigureMockMvc

class ControllerCalendarModifyTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @InjectMocks
    private Controller controller; 

    private final String baseURL = "https://cloud.timeedit.net/chalmers/web/public/";


    @Test
    void testControllerInitialization() {
        assertThat(controller).isNotNull();
    }

    @Test
    void calendarModifyTestsValid() throws Exception{
        MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
            .param("ical", baseURL + "ri657QQQY81Zn6Q5308636Z6y6Z55.ics"))
            .andExpect(status().isOk())
            .andReturn();  

        // Step 2: Prepare the modified event DTO
        String uploadResponse = uploadResult.getResponse().getContentAsString();
        Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
        assertNotNull(calendar);
        String calendarID = calendar.getId();

        ArrayList<Event> events = calendar.getEvents(); 

        ArrayList<String> courseFilter = new ArrayList<>();
        ArrayList<String> activites = new ArrayList<>();

        activites.add("Föreläsning");
        activites.add("Laboration");

        String coursecode = "";
        if (!events.isEmpty() && events.get(0).getDecorators().containsKey(EventDecoratorType.COURSE)) {
            Object courseObject = events.get(0).getDecorators().get(EventDecoratorType.COURSE);
            Course course = objectMapper.convertValue(courseObject, Course.class); // Convert LinkedHashMap to Course
            coursecode = course.getCode(); // Retrieve the course code
        }
        courseFilter.add(coursecode);
    }
        @Test
    void calendarModifyTestsInvalidIfInvalidId() throws Exception{
        MvcResult uploadResult = mockMvc.perform(post("/calendar/upload")
            .param("ical", baseURL + "ri657QQQY81Zn6Q5308636Z6y6Z55.ics"))
            .andExpect(status().isOk())
            .andReturn();  

        // Step 2: Prepare the modified event DTO
        String uploadResponse = uploadResult.getResponse().getContentAsString();
        Calendar calendar = objectMapper.readValue(uploadResponse, Calendar.class);
        assertNotNull(calendar);
        String calendarID = "InvalidId";

        ArrayList<Event> events = calendar.getEvents(); 

        ArrayList<String> courseFilter = new ArrayList<>();
        ArrayList<String> activites = new ArrayList<>();

        activites.add("Föreläsning");
        activites.add("Laboration");

        String coursecode = "";
        if (!events.isEmpty() && events.get(0).getDecorators().containsKey(EventDecoratorType.COURSE)) {
            Object courseObject = events.get(0).getDecorators().get(EventDecoratorType.COURSE);
            Course course = objectMapper.convertValue(courseObject, Course.class); // Convert LinkedHashMap to Course
            coursecode = course.getCode(); // Retrieve the course code
        }
        courseFilter.add(coursecode);
            MvcResult modifyResult = mockMvc.perform(put("/calendar/modify/" + calendarID)
            .param("activityFilter", activites.toString())
            .param("courseFilter", courseFilter.toString()))
            .andExpect(status().isNotFound())
            .andReturn();           
    }
    

}
