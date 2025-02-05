package lt.techin.demo.service;

import lt.techin.demo.model.Category;
import lt.techin.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category saveCategory(Category category) {
    return categoryRepository.save(category);
  }
}
