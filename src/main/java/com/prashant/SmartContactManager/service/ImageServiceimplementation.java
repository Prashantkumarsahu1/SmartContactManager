package com.prashant.SmartContactManager.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.prashant.SmartContactManager.helper.AppConstants;

@Service
public class ImageServiceimplementation implements ImageService {

    private Cloudinary cloudinary;

    //constructor
    public ImageServiceimplementation(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile contactImage, String filename) {
        //code which will upload the image on the server and return url

        

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id", filename));
            return this.getURLFromPublicId(filename);
        } catch (IOException e) {            
            e.printStackTrace();
            return null;
        } //get the image in byte array
        
    }

    @Override
    public String getURLFromPublicId(String publicId) {
        //code which will return the URL of the image
        return cloudinary.url()
        .transformation(
            new Transformation<>()
            .width(AppConstants.CONTACT_IMAGE_WIDTH)
            .height(AppConstants.CONTACT_IMAGE_HEIGHT)
            .crop(AppConstants.CONTACT_IMAGE_CROP)
        )
        .generate(publicId);
        
    }

}
