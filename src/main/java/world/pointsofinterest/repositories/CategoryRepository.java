package world.pointsofinterest.repositories;

import org.springframework.data.repository.CrudRepository;
import world.pointsofinterest.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
