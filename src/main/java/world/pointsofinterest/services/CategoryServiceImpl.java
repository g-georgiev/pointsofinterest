package world.pointsofinterest.services;

import org.springframework.stereotype.Service;
import world.pointsofinterest.api.v1.mappers.CategoryMapper;
import world.pointsofinterest.api.v1.mappers.POIMapper;
import world.pointsofinterest.api.v1.model.CategoryDTO;
import world.pointsofinterest.api.v1.model.POIResponseDTO;
import world.pointsofinterest.controllers.v1.CategoryController;
import world.pointsofinterest.model.Category;
import world.pointsofinterest.repositories.CategoryRepository;
import world.pointsofinterest.services.interfaces.CategoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, POIMapper poiMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> {
                    CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
                    categoryDTO.setLinks(initLinks(category));
                    return categoryDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findById(Long aLong) {
        return categoryRepository.findById(aLong)
                .map(category -> {
                    CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
                    categoryDTO.setLinks(initLinks(category));
                    return categoryDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = categoryRepository.save(categoryMapper.categoryDTOToCategory(categoryDTO));
        CategoryDTO newCategoryDTO = categoryMapper.categoryToCategoryDTO(category);
        newCategoryDTO.setLinks(initLinks(category));
        return newCategoryDTO;
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        return categoryRepository.findById(id).map(category -> {

            if(categoryDTO.getDescription() != null){
                category.setDescription(categoryDTO.getDescription());
            }

            if(categoryDTO.getName() != null){
                category.setName(categoryDTO.getName());
            }

            CategoryDTO returnDto = categoryMapper.categoryToCategoryDTO(categoryRepository.save(category));
            returnDto.setLinks(initLinks(category));

            return returnDto;

        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        categoryRepository.deleteById(id);

    }

    private Map<String, String> initLinks(Category category) {
        Map<String, String> links = new HashMap<>();
        links.put("showPOI", CategoryController.BASE_URL + "/" + category.getId() + CategoryController.POI_PATH);
        return links;
    }
}
