package TimetrackerApplication.example.TimetrackerApplication.Repository;

import TimetrackerApplication.example.TimetrackerApplication.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryNameAndUserId(String categoryName, Long userId);
}
