/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvargas.gatos_app;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author crist
 */
public class GatosService {

    public static void verGatos() throws IOException {
        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("https://api.thecatapi.com/v1/images/search")
//                .method("GET", null)
//                .build();
        Request request = new Request.Builder().url("https://api.thecatapi.com/v1/images/search").get().build();
        Response response = client.newCall(request).execute();

        String json = response.body().string();

        //Cortar los corchetes que vienen en la respuesta a la peticion
        json = json.substring(1, json.length());
        json = json.substring(0, json.length() - 1);

        //Crear un objeto a partir de la clase GSON
        Gson gson = new Gson();
        Gatos gatos = gson.fromJson(json, Gatos.class);
        System.out.println("gatos: " + gatos);
        BufferedImage image = null;
        try {
            URL url = new URL(gatos.getUrl());
//            Codigo inservible del profesor
//            System.out.println("url: " + url);
//            image = ImageIO.read(url);
//            ImageIcon fondoGato = new ImageIcon(image);

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.addRequestProperty("User-Agent", "");
            BufferedImage bufferedImage = ImageIO.read(http.getInputStream());
            ImageIcon fondoGato = new ImageIcon(bufferedImage);

            if (fondoGato.getIconWidth() > 800) {
                //Redimensional imagen
                Image fondo = fondoGato.getImage();
                Image modificada = fondo.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                fondoGato = new ImageIcon(modificada);
            }

            String menu = "Opciones: "
                    + "1. Ver otra imagen \n"
                    + "2. favorito \n"
                    + "3. volver \n";

            String[] botones = {"ver otra imagen", "favorito", "volver"};
            String idGato = gatos.getId();
            System.out.println("idGato: " + idGato);
            String opcion = (String) JOptionPane.showInputDialog(null, menu, idGato, JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);

            int seleccion = -1;
            //validamos que opcion selecciona el usuario
            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    seleccion = i;
                }
            }

            System.out.println("seleccion: " + seleccion);
            switch (seleccion) {
                case 0:
                    verGatos();
                    break;
                case 1:
                    favoritoGato(gatos);
                    break;
                default:
                    break;
            }

        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public static void favoritoGato(Gatos gato) {
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            //RequestBody body = RequestBody.create(mediaType, "{\r\n  \"image_id\": \"1n7\"\r\n}");
            RequestBody body = RequestBody.create(mediaType, "{\r\n  \"image_id\": \"" + gato.getId() + "\" \r\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("x-api-key", gato.getApikey())
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public static void verFavoritos(String apiKey) {
        System.out.println("verFavoritos: " + apiKey);
        try {
            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url("https://api.thecatapi.com/v1/favourites")
//                    .method("GET", null)
//                    .addHeader("x-api-key", apiKey)
//                    .build();
            Request request = new Request.Builder().url("https://api.thecatapi.com/v1/favourites").get().addHeader("x-api-key", apiKey).build();
            Response response = client.newCall(request).execute();

            // guardamos el string con la respuesta
            String json = response.body().string();
            
            //creamos el objeto gson
            Gson gson = new Gson();

            GatosFav[] gatosArray = gson.fromJson(json, GatosFav[].class);

            if (gatosArray.length > 0) {
                int min = 1;
                int max = gatosArray.length;
                int random = (int) (Math.random() * ((max - min)+1)) + min;
                int indice = random - 1;
                GatosFav gatoFav = gatosArray[indice];

                BufferedImage image = null;
                try {
                    URL url = new URL(gatoFav.image.getUrl());

                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.addRequestProperty("User-Agent", "");
                    BufferedImage bufferedImage = ImageIO.read(http.getInputStream());
                    ImageIcon fondoGato = new ImageIcon(bufferedImage);

                    if (fondoGato.getIconWidth() > 800) {
                        //Redimensional imagen
                        Image fondo = fondoGato.getImage();
                        Image modificada = fondo.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                        fondoGato = new ImageIcon(modificada);
                    }

                    String menu = "Opciones: "
                            + "1. Ver otra imagen \n"
                            + "2. Eliminar favorito \n"
                            + "3. Volver \n";

                    String[] botones = {"ver otra imagen", "eliminar favorito", "volver"};
                    String idGato = gatoFav.getId();
                    System.out.println("idGato: " + idGato);
                    String opcion = (String) JOptionPane.showInputDialog(null, menu, idGato, JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);

                    int seleccion = -1;
                    //validamos que opcion selecciona el usuario
                    for (int i = 0; i < botones.length; i++) {
                        if (opcion.equals(botones[i])) {
                            seleccion = i;
                        }
                    }

                    System.out.println("seleccion: " + seleccion);
                    switch (seleccion) {
                        case 0:
                            verFavoritos(apiKey);
                            break;
                        case 1:
                            borrarFavorito(gatoFav);
                            break;
                        default:
                            break;
                    }

                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public static void borrarFavorito(GatosFav gatoFav){
        try{
            
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/"+gatoFav.getId()+"")
                    .method("DELETE", body)
                    .addHeader("x-api-key", gatoFav.getApiKey())
                    .build();
            Response response = client.newCall(request).execute();
        } catch(IOException e){
            System.out.println(e);
        }
    }
}
