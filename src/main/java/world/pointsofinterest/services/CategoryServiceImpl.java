package world.pointsofinterest.services;

import org.springframework.stereotype.Service;
import world.pointsofinterest.api.v1.mappers.CategoryMapper;
import world.pointsofinterest.api.v1.model.CategoryDTO;
import world.pointsofinterest.repositories.CategoryRepository;
import world.pointsofinterest.services.interfaces.CategoryService;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::categoryToCategoryDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        if(categoryDTO == null) { throw new InvalidParameterException("Category DTO is required"); }
        return categoryMapper.categoryToCategoryDTO(
                categoryRepository.save(categoryMapper.categoryDTOToCategory(categoryDTO)));
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        if(id == null) {throw new InvalidParameterException("ID of category to update is required");}
        if(categoryDTO == null || categoryDTO.getDescription() == null && categoryDTO.getName() == null){
            throw new InvalidParameterException("Either description or name must be passed to update");
        }

        return categoryRepository.findById(id).map(category -> {
            if(categoryDTO.getDescription() != null){
                category.setDescription(categoryDTO.getDescription());
            }
            if(categoryDTO.getName() != null){
                category.setName(categoryDTO.getName());
            }

            return categoryMapper.categoryToCategoryDTO(categoryRepository.save(category));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        categoryRepository.deleteById(id);

    }
}
