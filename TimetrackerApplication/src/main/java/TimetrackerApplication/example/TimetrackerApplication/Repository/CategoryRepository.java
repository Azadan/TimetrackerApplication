package TimetrackerApplication.example.TimetrackerApplication.Repository;

import TimetrackerApplication.example.TimetrackerApplication.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryNameAndUser_UserId(String categoryName, Long userId);
}
