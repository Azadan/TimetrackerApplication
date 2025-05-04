package TimetrackerApplication.example.TimetrackerApplication.Repository;

import TimetrackerApplication.example.TimetrackerApplication.Model.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {

}
