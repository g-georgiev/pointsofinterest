package world.pointsofinterest.controllers.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.services.interfaces.CommentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(CommentController.BASE_URL)
public class CommentController {
    public static final String BASE_URL = "/v1/comments";

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all comments")
    public List<CommentDTO> getListOfComments(){
        return commentService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Post a comment for a user userProfile or a point of interest")
    public CommentDTO createNewComment(
            @Parameter(description = "The id of the user userProfile", required = true)
            @PathVariable Long id,
            @Parameter(description = "The data of the comment to be posted", required = true)
            @Valid
            @RequestBody CommentDTO commentDTO){
        return commentService.save(commentDTO);
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get comment by ID")
    public CommentDTO getCommentById(
            @Parameter(description = "The id of the comment to fetch", required = true)
            @PathVariable Long id) {
        return commentService.findById(id);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a comment")
    public CommentDTO updateComment(
            @Parameter(description = "The id of the comment to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "New data for the comment ot update", required = true)
            @Valid
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
