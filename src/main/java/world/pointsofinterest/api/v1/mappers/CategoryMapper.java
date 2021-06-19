package world.pointsofinterest.api.v1.mappers;

import org.springframework.stereotype.Component;
import world.pointsofinterest.api.v1.model.CategoryDTO;
import world.pointsofinterest.model.Category;

@Component
public class CategoryMapper {
    public CategoryDTO categoryToCategoryDTO(Category category){
        return new CategoryDTO(category.getId(), category.getName(), category.getDescription());
    }

    public Category categoryDTOToCategory(CategoryDTO categoryDTO){
        return new Category(null, categoryDTO.getName(), categoryDTO.getDescription());
    }
}
