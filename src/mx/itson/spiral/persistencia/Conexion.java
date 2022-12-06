/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.itson.spiral.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author jesus
 */
public class Conexion {
    
     public static Connection obtener(){
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/spiraldb?user=root&password=soyElAdministrador");
        } catch (Exception ex){
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return conexion;
    }
    
}
