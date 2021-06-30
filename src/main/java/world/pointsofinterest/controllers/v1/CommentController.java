package world.pointsofinterest.controllers.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.services.interfaces.CommentService;

@RestController
@RequestMapping(CommentController.BASE_URL)
public class CommentController {
    public static final String BASE_URL = "/api/v1/comments";

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a comment")
    public CommentDTO updateComment(
            @Parameter(description = "The id of the comment to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "New data for the comment ot update", required = true)
            @RequestBody CommentDTO commentDTO){
        return commentService.update(id, commentDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a comment")
    public void deleteComment(
            @Parameter(description = "The id of the comment to delete", required = true)
            @PathVariable Long id){
        commentService.deleteById(id);
    }
}
