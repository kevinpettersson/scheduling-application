package timebridge;
import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import timebridge.model.Calendar;
import timebridge.services.CalendarParser;
import timebridge.services.CalendarSerializer;

@SpringBootTest
class ControllerTests {
    @Mock
    private CalendarParser mockParser; 
     // Mock parsing service
    @Mock
    private CalendarSerializer mockSerializer;  // Mock serializer service
    
    @InjectMocks
    private Controller controller;  // The controller to test

    @Test
    void testControllerInitialization() {
        assertThat(controller).isNotNull();
    }

    @Test
    void testUploadCalendarSuccess() throws Exception {
    
    String icalData = "BEGIN:VCALENDAR\nVERSION:2.0\n...";
    Calendar calendar = new Calendar();  // Mock calendar object 
    
    when(mockParser.parse(icalData)).thenReturn(calendar);
    

    URL mockUrl = mock(URL.class);
    HttpURLConnection mockConnection = mock(HttpURLConnection.class);
    when(mockUrl.openConnection()).thenReturn(mockConnection);
    when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
    when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream(icalData.getBytes(StandardCharsets.UTF_8)));

    
    ResponseEntity<Calendar> response = controller.uploadCalendar("http://mockurl.com/calendar.ics");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertSame(calendar, response.getBody());
}
}
