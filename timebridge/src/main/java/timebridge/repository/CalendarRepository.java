package timebridge.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import timebridge.model.Calendar;

public interface CalendarRepository extends MongoRepository<Calendar, String> {
    List<Calendar> findByName(String name);
}
