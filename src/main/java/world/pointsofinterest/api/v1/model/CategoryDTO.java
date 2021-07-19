package world.pointsofinterest.api.v1.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CategoryDTO {
    @Positive(message = "ID must be positive")
    private Long id;

    @NotNull(message = "Name is a required property")
    @NotEmpty(message = "Name cannot be an empty string")
    private String name;

    @NotNull(message = "Description is a required property")
    @NotEmpty(message = "Description cannot be an empty string")
    private String description;

    public CategoryDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
