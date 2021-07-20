package world.pointsofinterest.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import world.pointsofinterest.api.v1.mappers.CategoryMapper;
import world.pointsofinterest.api.v1.model.CategoryDTO;
import world.pointsofinterest.model.Category;
import world.pointsofinterest.repositories.CategoryRepository;
import world.pointsofinterest.services.interfaces.CategoryService;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    //Testing data
    private Category testCategory1;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository, new CategoryMapper());

        //Set up category
        testCategory1 = new Category(1L, "Sites", "Sites");
    }

    @Test
    void findAll() {
        when(categoryRepository.findAll()).thenReturn(List.of(testCategory1, testCategory1));

        List<CategoryDTO> categoryDTOS = categoryService.findAll();

        assertNotNull(categoryDTOS);
        assertEquals(2, categoryDTOS.size());
    }

    @Test
    void findById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory1));

        CategoryDTO categoryDTO = categoryService.findById(1L);

        assertNotNull(categoryDTO);
        assertEquals(1, categoryDTO.getId());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.findById(2L));
    }

    @Test
    void save() {
        when(categoryRepository.save(any(Category.class))).thenAnswer(ans -> ans.getArguments()[0]);

        CategoryDTO categoryDTO = new CategoryDTO(null, testCategory1.getName(), testCategory1.getDescription());
        CategoryDTO savedCategory = categoryService.save(categoryDTO);

        assertNotNull(savedCategory);
        verify(categoryRepository).save(any(Category.class));
        assertEquals(categoryDTO.getName(), savedCategory.getName());
        assertEquals(categoryDTO.getDescription(), savedCategory.getDescription());

        assertThrows(InvalidParameterException.class, () -> categoryService.save(null));
    }

    @Test
    void update() {
        when(categoryRepository.save(any(Category.class))).thenAnswer(ans -> ans.getArguments()[0]);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory1));

        CategoryDTO categoryDTO = new CategoryDTO(null, testCategory1.getName(), testCategory1.getDescription());
        CategoryDTO savedCategory = categoryService.update(1L, categoryDTO);

        assertNotNull(savedCategory);
        verify(categoryRepository).save(any(Category.class));
        assertEquals(categoryDTO.getName(), savedCategory.getName());
        assertEquals(categoryDTO.getDescription(), savedCategory.getDescription());

        assertThrows(InvalidParameterException.class, () -> categoryService.update(null, categoryDTO));
        assertThrows(InvalidParameterException.class, () -> categoryService.update(1L,
                new CategoryDTO( null, null, null )));
    }

    @Test
    void deleteById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory1));
        categoryService.deleteById(1L);

        verify(categoryRepository).deleteById(1L);
    }
}