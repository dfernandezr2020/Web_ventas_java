/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sinensia.api;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ServicioProductoSinglenton {
    
    private ArrayList<Producto> listaProductos;
    
    public void insertar(Producto p){
        listaProductos.add(p);
    }
    
    public Producto modificar(Producto p){
        p.setNombre(p.getNombre() + " - Modificado");
        p.setPrecio(p.getPrecio() + " - Modificado");
        return p;
    }
    
    public Producto[] obtenerTodos(){
        return (Producto[]) listaProductos.toArray();
    }
    
    private static ServicioProductoSinglenton instancia;
    //Nadie puede hacer new excepto dentro de esta clase y puede ser protected
    private ServicioProductoSinglenton(){
        this.listaProductos = new ArrayList<>();
    }
    //La primera vez que se llama al método, se crea la instancia a partir de ese momento hasta que la aplicación
    //termine, la instancia seguirá "viva" y es devuelta por el método, venga de donde venga la llamada
    public static ServicioProductoSinglenton getInstancia(){
        if(instancia == null)
            instancia = new ServicioProductoSinglenton();
        return instancia;
    }
    
}
