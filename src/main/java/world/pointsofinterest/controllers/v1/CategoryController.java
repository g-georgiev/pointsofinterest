package world.pointsofinterest.controllers.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import world.pointsofinterest.api.v1.model.CategoryDTO;
import world.pointsofinterest.api.v1.model.POIResponseDTO;
import world.pointsofinterest.services.POIServiceImpl;
import world.pointsofinterest.services.interfaces.CategoryService;
import world.pointsofinterest.services.interfaces.POIService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {
    public static final String BASE_URL = "/v1/categories";
    public static final String POI_PATH = "/poi";

    private final CategoryService categoryService;
    private final POIService poiService;

    public CategoryController(CategoryService categoryService, POIServiceImpl poiService) {
        this.categoryService = categoryService;
        this.poiService = poiService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all categories")
    public List<CategoryDTO> getListOfCategories(){
        return categoryService.findAll();
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a category by ID")
    public CategoryDTO getCategoryById(
            @Parameter(description = "The id of the category to fetch", required = true)
            @PathVariable Long id){
        return categoryService.findById(id);
    }

    @GetMapping({"/{id}" + POI_PATH})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all the points of interest of a given category")
    public List<POIResponseDTO> getPOIByCategoryId(
            @Parameter(description = "The id of the category of the points of interest to fetch", required = true)
            @PathVariable Long id){
        return poiService.findAllByCategory(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new category")
    public CategoryDTO createNewCategory(
            @Parameter(description = "Data for the new category", required = true)
            @Valid
            @RequestBody CategoryDTO categoryDTO){
        return categoryService.save(categoryDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an exiting category")
    public CategoryDTO updateCategory(
            @Parameter(description = "The id of the category to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "New data for the category to update", required = true)
            @Valid
            @RequestBody CategoryDTO categoryDTO){
        return categoryService.update(id, categoryDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a category")
    public void deleteCategory(
            @Parameter(description = "The id of the category to delete", required = true)
            @PathVariable Long id){
        categoryService.deleteById(id);
    }
}
