package timebridge.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import timebridge.model.Calendar;

/**
 * This interface provides methods to interact with the Calendar collection in the MongoDB database.
 *
 * @since 2024-12-19
 * @author Group 12
 */
public interface CalendarRepository extends MongoRepository<Calendar, String> {
    List<Calendar> findByName(String name);
}
