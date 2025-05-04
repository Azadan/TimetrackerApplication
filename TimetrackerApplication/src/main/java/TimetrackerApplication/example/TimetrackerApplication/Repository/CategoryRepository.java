package TimetrackerApplication.example.TimetrackerApplication.Repository;

import TimetrackerApplication.example.TimetrackerApplication.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryNameAndUser_UserId(String categoryName, Long userId);

    List<Category> findByUser_UserId(Long userId);
}
