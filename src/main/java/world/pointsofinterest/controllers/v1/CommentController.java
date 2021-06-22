package world.pointsofinterest.controllers.v1;

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
    public CommentDTO updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO){
        return commentService.update(id, commentDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long id){
        commentService.deleteById(id);
    }
}
