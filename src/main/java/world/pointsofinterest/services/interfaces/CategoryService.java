package world.pointsofinterest.services.interfaces;

import org.springframework.transaction.annotation.Transactional;
import world.pointsofinterest.api.v1.model.CategoryDTO;

import java.util.List;

@Transactional
public interface CategoryService extends CommonService<CategoryDTO, CategoryDTO, Long> {
    List<CategoryDTO> findAll();
}
