package world.pointsofinterest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import world.pointsofinterest.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByIdIn(Long[] ids);
}
