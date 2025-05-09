package TimetrackerApplication.example.TimetrackerApplication.Controller;

import TimetrackerApplication.example.TimetrackerApplication.DTO.CategoryDto;
import TimetrackerApplication.example.TimetrackerApplication.Exceptions.CategoryAlreadyExistException;
import TimetrackerApplication.example.TimetrackerApplication.Exceptions.CategoryNotFoundException;
import TimetrackerApplication.example.TimetrackerApplication.Exceptions.UserNotFoundException;
import TimetrackerApplication.example.TimetrackerApplication.Model.Category;
import TimetrackerApplication.example.TimetrackerApplication.Repository.CategoryRepository;
import TimetrackerApplication.example.TimetrackerApplication.Request.CreateCategoryRequest;
import TimetrackerApplication.example.TimetrackerApplication.Request.UpdateCategoryRequest;
import TimetrackerApplication.example.TimetrackerApplication.Response.ApiResponse;
import TimetrackerApplication.example.TimetrackerApplication.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;


    @GetMapping("/find/{userId}")
    public ResponseEntity<ApiResponse> getCategoryByUserId(@PathVariable Long userId) {
        try {
            List <Category> categories = categoryService.getCategoryByUserId(userId);
            List<CategoryDto> categoryDto = categoryService.listConvertToDto(categories);
            return ResponseEntity.ok(new ApiResponse("Category found", true, categoryDto));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Category with this userId not found", false, null));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("User with this userId not found", false, null));
        }
    }
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CreateCategoryRequest req) {
        try {
            Category category = categoryService.createCategory(req);
            CategoryDto categoryDto = categoryService.convertToDto(category);
            return ResponseEntity.ok(new ApiResponse("Category created successfully", true, categoryDto));
        } catch (CategoryAlreadyExistException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Category already exist", false, null));
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody UpdateCategoryRequest req, @PathVariable long id) {
        try {
            Category category = categoryService.updateCategoryRequest(id, req);
            CategoryDto categoryDto = categoryService.convertToDto(category);
            return ResponseEntity.ok(new ApiResponse("Category updated successfully", true, categoryDto));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Category with this id not found", false, null));
        }
    }
}
