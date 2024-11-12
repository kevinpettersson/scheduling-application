package timebridge;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import timebridge.model.Schedule;
import timebridge.repository.ScheduleRepository;

@RestController
class Controller {

    @Autowired
    private ScheduleRepository repository;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }

    @GetMapping("/schedules")
    public List<Schedule> getSchedules() {
        return repository.findAll();
    }

    @GetMapping("/schedules/{courseId}")
    public List<Schedule> getSchedulesByCourseId(@PathVariable String url) {
        return repository.findByUrl(url);
    }
}
