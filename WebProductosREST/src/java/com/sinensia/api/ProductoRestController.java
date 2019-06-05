package com.sinensia.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import static javafx.scene.input.KeyCode.T;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

// Decoradores en forma de anotación
@WebServlet(asyncSupported = true, urlPatterns = "/api/productos")
public class ProductoRestController extends HttpServlet {

    private ServicioProductoSinglenton servProd;

    @Override
    public void init() {
        servProd = ServicioProductoSinglenton.getInstancia();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter escritorRespuesta = response.getWriter();

        response.setContentType("application/json;charset=UTF-8");

        BufferedReader bufRead = request.getReader();

        StringBuilder textoJson = new StringBuilder();

        for (String lineaJson = bufRead.readLine(); lineaJson != null; lineaJson = bufRead.readLine()) {

            textoJson.append(lineaJson);
        }

        bufRead.close();

        escritorRespuesta.println(textoJson.toString().toUpperCase());

        Gson gson = new Gson();

        Producto producto = gson.fromJson(textoJson.toString(), Producto.class);

        System.out.println("   " + producto.getNombre());

        ServicioProductoSinglenton sps = ServicioProductoSinglenton.getInstancia();

        sps.modificar(producto);

        String jsonRespuesta = gson.toJson(producto);

        escritorRespuesta.println(jsonRespuesta);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BufferedReader bufRead = request.getReader();

        StringBuilder rJson = new StringBuilder();

        for (String linea = bufRead.readLine(); linea != null; linea = bufRead.readLine()) {

            rJson.append(linea);
        }

        bufRead.close();
        
        Gson gson = new Gson();

        Producto nuevoP = gson.fromJson(rJson.toString(), Producto.class);

        servProd.insertar(nuevoP);

        PrintWriter escritorRespuesta = response.getWriter();

        response.setContentType("application/json;charset=UTF-8");

        String JsonResp = gson.toJson(nuevoP);

        escritorRespuesta.println(JsonResp);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Cogemos el String de Salida
        PrintWriter escritorRespuesta = response.getWriter();

        response.setContentType("application/json;charset=UTF-8");

        //Creamos un producto 
        Producto prod = new Producto();

        prod.setNombre("coche");
        prod.setPrecio("10000");
        //Insertamos el producto con un array de Productos
        servProd.insertar(prod); 
        //Obtenemos el array de objetos Producto y creamos un array de Json
        //que comprueba que en ServicioProductosSingleton tiene el metodo obtener todos
        Producto[] lista = servProd.obtenerTodos();

        JsonArray listaJson = new JsonArray();
        //Vamos iterando el array de productos y en cada producto lo parseamos a Json y lo añadimos al array de Json
        for (Producto p : lista) {

            Gson gson = new Gson();
            String json = gson.toJson(p);
            listaJson.add(json);

        }
        //Cuando termina el bucle enviamos el json por el stream de respuesta
        escritorRespuesta.println(listaJson);
    }
}
