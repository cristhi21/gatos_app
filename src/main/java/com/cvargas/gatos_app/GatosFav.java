/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvargas.gatos_app;

/**
 *
 * @author crist
 */
public class GatosFav {
    String id;
    String imageId;
    String apiKey = "b2735349-3e28-4721-a294-aa6f10abee26";
    Imagex image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Imagex getImag() {
        return image;
    }

    public void setImage(Imagex image) {
        this.image = image;
    }
    
}
