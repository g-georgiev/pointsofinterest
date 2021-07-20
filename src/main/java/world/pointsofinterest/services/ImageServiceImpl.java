package world.pointsofinterest.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import world.pointsofinterest.api.v1.mappers.ImageMapper;
import world.pointsofinterest.api.v1.model.ImageDTO;
import world.pointsofinterest.model.POI;
import world.pointsofinterest.model.Profile;
import world.pointsofinterest.repositories.ImageRepository;
import world.pointsofinterest.repositories.POIRepository;
import world.pointsofinterest.repositories.ProfileRepository;
import world.pointsofinterest.services.interfaces.ImageService;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ProfileRepository profileRepository;
    private final POIRepository poiRepository;

    private final ImageMapper imageMapper;

    public ImageServiceImpl(ImageRepository imageRepository,
                            ProfileRepository profileRepository,
                            POIRepository poiRepository,
                            ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.profileRepository = profileRepository;
        this.poiRepository = poiRepository;
        this.imageMapper = imageMapper;
    }

    @Override
    public ImageDTO findById(Long id) {
        return imageRepository.findById(id)
                .map(imageMapper::imageToImageDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<ImageDTO> findAll() {
        return imageRepository.findAll().stream()
                .map(imageMapper::imageToImageDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageDTO> findAllByPOI(Long id) {
        POI poi = poiRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return poi.getImageSet().stream()
                .map(imageMapper::imageToImageDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageDTO> findAllByProfile(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return profile.getImageSet().stream()
                .map(imageMapper::imageToImageDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ImageDTO save(ImageDTO imageDTO) {
        if(imageDTO == null) { throw new InvalidParameterException("Image DTO must be not null"); }


        POI poi = null;
        Profile profile = null;
        if(imageDTO.getPoiId() != null) {
            poi = poiRepository.findById(imageDTO.getPoiId()).orElseThrow(ResourceNotFoundException::new);
        }
        if(imageDTO.getProfileId() != null) {
            profile = profileRepository.findById(imageDTO.getProfileId()).orElseThrow(ResourceNotFoundException::new);
        }

        return imageMapper.imageToImageDTO(imageRepository.save(
                imageMapper.imageDTOToImage(imageDTO, poi, profile)));
    }

    @Override
    public ImageDTO update(Long id, ImageDTO imageDTO) {
        if(id == null) {throw new InvalidParameterException("ID of image to update is required");}
        if(imageDTO == null || imageDTO.getDescription() == null && imageDTO.getRating() == null &&
        imageDTO.getProfileId() == null && imageDTO.getPoiId() == null){
            throw new InvalidParameterException("Either description, rating POI id or profile id must be passed to update");
        }

        return imageRepository.findById(id).map(image -> {
            if(imageDTO.getRating() != null){
                image.addRating(imageDTO.getRating());
            }

            if(imageDTO.getDescription() != null){
                image.setDescription(imageDTO.getDescription());
            }

            if(imageDTO.getProfileId() != null){
                image.setProfile(profileRepository.findById(imageDTO.getProfileId())
                        .orElseThrow(ResourceNotFoundException::new));
            }

            if(imageDTO.getPoiId() != null){
                image.setPoi(poiRepository.findById(imageDTO.getPoiId())
                        .orElseThrow(ResourceNotFoundException::new));
            }

            return imageMapper.imageToImageDTO(imageRepository.save(image));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        imageRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        imageRepository.deleteById(id);
    }
}
