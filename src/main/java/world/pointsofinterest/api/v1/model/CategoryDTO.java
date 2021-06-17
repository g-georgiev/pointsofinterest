package world.pointsofinterest.api.v1.model;

public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String categoryURL;

    public CategoryDTO(Long id, String name, String description, String categoryURL) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryURL = categoryURL;
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

    public String getCategoryURL() {
        return categoryURL;
    }

    public void setCategoryURL(String categoryURL) {
        this.categoryURL = categoryURL;
    }
}
