package world.pointsofinterest.controllers.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import world.pointsofinterest.api.v1.model.ImageDTO;
import world.pointsofinterest.services.interfaces.ImageService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(ImageController.BASE_URL)
public class ImageController {
    public static final String BASE_URL = "/api/v1/images";

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all images")
    public List<ImageDTO> getListOfImages(){
        return imageService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Add an image")
    public ImageDTO createNewImage(
            @Parameter(description = "The id of the point of interest", required = true)
            @PathVariable Long id,
            @Parameter(description = "The image to be added", required = true)
            @Valid
            @RequestBody ImageDTO imageDTO) {
        return imageService.save(imageDTO);
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get image by ID")
    public ImageDTO getImageById(
            @Parameter(description = "The id of the image to fetch", required = true)
            @PathVariable Long id) {
        return imageService.findById(id);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an exiting category")
    public ImageDTO updateCategory(
            @Parameter(description = "The id of the category to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "New data for the category to update", required = true)
            @Valid
            @RequestBody ImageDTO imageDTO){
        return imageService.update(id, imageDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete an image")
    public void deleteImage(
            @Parameter(description = "The id of the image to delete", required = true)
            @PathVariable Long id){
            imageService.deleteById(id);
    }
}
