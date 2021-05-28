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
public class Gatos {
    
    String id;
    String url;
    String apikey = "b2735349-3e28-4721-a294-aa6f10abee26";
    //String apikey = "74668d02-a299-40ae-bb05-130d2e65c227"; //-- Profe
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    @Override
    public String toString() {
        return "Gatos{" + "id=" + id + ", url=" + url + ", apikey=" + apikey + '}';
    }
    
    
    
}
