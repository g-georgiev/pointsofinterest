package world.pointsofinterest.services;

import org.springframework.stereotype.Service;
import world.pointsofinterest.model.POI;
import world.pointsofinterest.repositories.*;

import java.util.HashSet;
import java.util.Set;

@Service
public class POIServiceImpl implements POIService{

    private final POIRepository poiRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final VideoRepository videoRepository;
    private final ProfileRepository profileRepository;

    public POIServiceImpl(POIRepository poiRepository, CategoryRepository categoryRepository,
                          ImageRepository imageRepository, VideoRepository videoRepository,
                          ProfileRepository profileRepository) {
        this.poiRepository = poiRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.videoRepository = videoRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public Set<POI> findAll() {
        Set<POI> POIs = new HashSet<>();
        poiRepository.findAll().forEach(POIs::add);
        return POIs;
    }

    @Override
    public POI findById(Long aLong) {
        return poiRepository.findById(aLong).orElse(null);
    }

    @Override
    public POI save(POI object) {
        return poiRepository.save(object);
    }

    @Override
    public void delete(POI object) {
        poiRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        poiRepository.deleteById(aLong);
    }
}
