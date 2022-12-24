package com.delivery_service.service.category;

import com.delivery_service.postgres.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import com.delivery_service.dto.request.CategoryRequest;
import com.delivery_service.postgres.entity.Category;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    private Category toCategory(CategoryRequest categoryRequest) {
        return Category.builder()
                .id(categoryRequest.getId())
                .name(categoryRequest.getName())
                .build();
    }

    @Override
    public void saveCategory(CategoryRequest categoryRequest) {
        var category = toCategory(categoryRequest);
        categoryRepository.save(category);
    }

    @Override
    public List<Category> fetchCategoryList() {
        return categoryRepository.findAll();
    }

    @Override
    public Category fetchCategory(long id) throws ChangeSetPersister.NotFoundException {
        return Optional.ofNullable(categoryRepository.findById(id)).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @Override
    public Optional<Category> fetchCategory(String name) throws ChangeSetPersister.NotFoundException {
        return Optional.ofNullable(categoryRepository.findByName(name)).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @Override
    public boolean exist(long id) {
        return categoryRepository.existsById(id);
    }


}
