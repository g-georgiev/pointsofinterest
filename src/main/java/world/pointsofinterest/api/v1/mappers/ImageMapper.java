package world.pointsofinterest.api.v1.mappers;

import org.springframework.stereotype.Component;
import world.pointsofinterest.api.v1.model.ImageDTO;
import world.pointsofinterest.model.Image;
import world.pointsofinterest.model.POI;
import world.pointsofinterest.model.UserProfile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Component
public class ImageMapper {

    public ImageDTO imageToImageDTO(Image image){
        Long POIId = null;
        Long profileId = null;
        if(image.getPoi() != null) { POIId = image.getPoi().getId(); }
        if(image.getProfile() != null) { profileId = image.getProfile().getId(); }
        byte[] imageBin = null;
        if(image.getBin() != null && image.getBin().length > 0){
            imageBin = decompressImage(image.getBin());
        }

        return new ImageDTO(image.getId(), imageBin, image.getUrl(), image.getDescription(), image.getRating(), POIId, profileId);
    }

    public Image imageDTOToImage(ImageDTO imageDTO, POI poi, UserProfile userProfile){
        return new Image(imageDTO.getId(), imageDTO.getDescription(), imageDTO.getRating(), imageDTO.getUrl(),
                compressImage(imageDTO.getData()), poi, userProfile, null);
    }

    private Byte[] compressImage(byte[] data) {
        //Deflate the image bytes
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            //TODO:Handle that shit
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        byte[] bytes = outputStream.toByteArray();

        //Box the image bytes
        Byte[] boxedBytes = new Byte[bytes.length];
        for(int i = 0; i < bytes.length; i++){
            boxedBytes[i] = bytes[i];
        }
        return boxedBytes;
    }

    private byte[] decompressImage(Byte[] boxedData) {

        //Unbox the image bytes
        byte[] unboxedData = new byte[boxedData.length];
        for(int i = 0; i < boxedData.length; i++){
            unboxedData[i] = boxedData[i];
        }

        //Inflate the image bytes
        Inflater inflater = new Inflater();
        inflater.setInput(unboxedData);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(unboxedData.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException ioe) {
            //TODO:Handle that shit
        }
        return outputStream.toByteArray();
    }
}
