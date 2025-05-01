package TimetrackerApplication.example.TimetrackerApplication.Service;

import TimetrackerApplication.example.TimetrackerApplication.Exceptions.CategoryAlreadyExistException;
import TimetrackerApplication.example.TimetrackerApplication.Model.Category;
import TimetrackerApplication.example.TimetrackerApplication.Model.User;
import TimetrackerApplication.example.TimetrackerApplication.Repository.CategoryRepository;
import TimetrackerApplication.example.TimetrackerApplication.Request.CreateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public Category createCategory(CreateCategoryRequest req) {
        return Optional.of(req).filter(category -> !categoryRepository.existsByCategoryNameAndUserId(req.getName(), req.getUserId()))
                .map(request -> {
                    Category category = new Category();
                    category.setCategoryName(request.getName());
                    category.setDescription(request.getDescription());
                    User user = userService.getUserById(request.getUserId());
                    category.setUser(user);
                    return categoryRepository.save(category);
                }).orElseThrow(() -> new CategoryAlreadyExistException("Category already exists"));
    }
}
