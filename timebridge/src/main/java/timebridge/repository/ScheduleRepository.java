package timebridge.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import timebridge.model.Schedule;

public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    List<Schedule> findByUrl(String url);
}
