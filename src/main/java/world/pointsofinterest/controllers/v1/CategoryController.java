package world.pointsofinterest.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import world.pointsofinterest.api.v1.model.CategoryDTO;
import world.pointsofinterest.api.v1.model.POIResponseDTO;
import world.pointsofinterest.services.POIServiceImpl;
import world.pointsofinterest.services.interfaces.CategoryService;
import world.pointsofinterest.services.interfaces.POIService;

import java.util.List;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {
    public static final String BASE_URL = "/api/v1/categories";
    public static final String POI_PATH = "/poi";

    private final CategoryService categoryService;
    private final POIService poiService;

    public CategoryController(CategoryService categoryService, POIServiceImpl poiService) {
        this.categoryService = categoryService;
        this.poiService = poiService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDTO> getListOfCategories(){
        return categoryService.findAll();
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryById(@PathVariable Long id){
        return categoryService.findById(id);
    }

    @GetMapping({"/{id}" + POI_PATH})
    @ResponseStatus(HttpStatus.OK)
    public List<POIResponseDTO> getPOIByCategoryId(@PathVariable Long id){
        return poiService.findAllByCategory(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO createNewCategory(@RequestBody CategoryDTO categoryDTO){
        return categoryService.save(categoryDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        return categoryService.update(id, categoryDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteById(id);
    }
}
