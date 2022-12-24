package com.delivery_service.service.food;

import com.delivery_service.dto.request.FoodRequest;
import com.delivery_service.dto.responce.FoodResponse;
import com.delivery_service.postgres.entity.Category;
import com.delivery_service.postgres.repository.FoodRepository;
import com.delivery_service.service.cafe.CafeService;
import com.delivery_service.service.category.CategoryService;
import com.delivery_service.util.mapper.FoodMapper;
import com.delivery_service.postgres.entity.Food;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.mapstruct.factory.Mappers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService{
    private final FoodRepository foodRepository;
    private final CafeService cafeService;
    private final CategoryService categoryService;

    private final FoodMapper mapper = Mappers.getMapper(FoodMapper.class);

    private Food toFood(FoodRequest foodRequest) throws ChangeSetPersister.NotFoundException {
        return Food.builder()
                .category(categoryService.fetchCategory(foodRequest.getCategoryId()))
                .cafe(cafeService.fetchCafe(foodRequest.getCafeId()))
                .name(foodRequest.getName())
                .price(foodRequest.getPrice())
                .productLink(foodRequest.getProductLink())
                .imageLink(foodRequest.getImageLink())
                .isActive(foodRequest.getIsActive())
                .build();
    }
    @Override
    public void save(FoodRequest foodRequest) throws ChangeSetPersister.NotFoundException {
        Food food = toFood(foodRequest);
        foodRepository.save(food);
    }

    @Override
    public List<Food> fetchFood() {
        return foodRepository.findAll();
    }

    @Override
    public Food fetchById(long id) throws ChangeSetPersister.NotFoundException {
        return Optional.ofNullable(foodRepository.findById(id)).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @Override
    public List<FoodResponse> findFoodByCafeAndCategory(long cafeId, long categoryId) throws ChangeSetPersister.NotFoundException {
        val cafe = cafeService.fetchCafe(cafeId);
        val category = categoryService.fetchCategory(categoryId);
        return foodRepository.findByCafeAndCategoryAndIsActive(cafe, category, true).stream().map(mapper::foodToResponse).toList();
    }

    @Override
    @Cacheable("categoryByCafe")
    public Map<Category, List<Food>> findCategoryByCafe(long cafeId) throws ChangeSetPersister.NotFoundException {
        val cafe = cafeService.fetchCafe(cafeId);
        return foodRepository.findByCafe(cafe).stream().collect(Collectors.groupingBy(Food::getCategory));
    }

    @Override
    public void update(FoodRequest foodRequest) throws ChangeSetPersister.NotFoundException {
        List<Food> foods = fetchFood();
        Food updateFood = toFood(foodRequest);
        for (Food f : foods) {
            if (Objects.equals(f.getProductLink(), foodRequest.getProductLink()) && Boolean.TRUE.equals(f.getIsActive())) {
                return;
            } else if (Objects.equals(f.getProductLink(), foodRequest.getProductLink()) && Boolean.FALSE.equals(f.getIsActive())) {
                f.setName(updateFood.getName());
                f.setPrice(updateFood.getPrice());
                f.setImageLink(updateFood.getImageLink());
                f.setProductLink(updateFood.getProductLink());
                f.setCafe(updateFood.getCafe());
                f.setCategory(updateFood.getCategory());
                f.setIsActive(true);

                foodRepository.save(f);
                return;
            }
        }
        foodRepository.save(updateFood);
    }

    @Override
    public void updateAll() {
        List<Food> foods = fetchFood();
        for (Food f : foods) {
            f.setIsActive(false);
            foodRepository.save(f);
        }
    }

    @Override
    public List<FoodResponse> searchFood(long cafeId, String nameText) throws ChangeSetPersister.NotFoundException {
        val cafe = cafeService.fetchCafe(cafeId);
        return foodRepository.findByCafeAndIsActiveAndNameLikeIgnoreCase(cafe,true, "%"+nameText+"%")
                .stream()
                .map(mapper::foodToResponse)
                .toList();
    }

    @Override
    public boolean exist(long id) {
        return foodRepository.existsById(id);
    }
}
