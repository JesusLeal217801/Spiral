/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.itson.spiral.entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.itson.spiral.persistencia.Conexion;

/**
 * Contiene los atributos de la clase Clima y metodos utilizados para el CRUD
 * basico y otras operaciones.
 * @author Jesús Leal, Axel Valdez, Gabriel Leyva
 */
public class Clima {

    private int id;
    private String municipio;
    private Date fecha;
    private String clima;
    private double gradosCelsius;
    private String vientoDireccion;

    /**
     * Obtiene los registros de la tabla Clima existentes en la base de datos.
     * 
     * @return Lista de Clima.
     */
    public static List<Clima> obtener() {
        List<Clima> clima = new ArrayList<>();
        try {
            Connection conexion = Conexion.obtener();
            Statement statement = conexion.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT id, municipio, fecha, clima, gradosCelsius, vientoDireccion FROM clima");

            while (resultSet.next()) {
                Clima c = new Clima();
                c.setId(resultSet.getInt(1));
                c.setMunicipio(resultSet.getString(2));
                c.setFecha(resultSet.getDate(3));   
                c.setClima(resultSet.getString(4));
                c.setGradosCelsius(resultSet.getDouble(5));
                c.setVientoDireccion(resultSet.getString(6));

                clima.add(c);
            }
        } catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return clima;
    }

    /**
     * Guarda los datos obtenidos en la tabla Clima.
     * @param municipio Municipio donde se registraron los datos
     * @param fecha Fecha en la que se registraron dichos datos
     * @param clima El clima que habia en el momento.
     * @param grados Los grados celsius.
     * @param viento La dirección del viento.
     * @return Retorna un valor que especifica si se ha hecho un cambio.
     */
    public static boolean guardar(String municipio, Date fecha, String clima, Double grados, String viento) {
        boolean resultado = false;
        
        java.sql.Date fechaDos = new java.sql.Date(fecha.getTime());
        
        try {
            Connection conexion = Conexion.obtener();
            String consulta = "INSERT INTO clima (municipio, fecha, clima, gradosCelsius, vientoDireccion) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, municipio);
            statement.setDate(2, fechaDos);
            statement.setString(3, clima);
            statement.setDouble(4, grados);
            statement.setString(5, viento);
            
            statement.execute();
            
            resultado = statement.getUpdateCount() == 1;
     
            conexion.close();
            
        } catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return resultado;
    }
    
    /**
     * Edita los datos seleccionados de un reglon de la tabla.
     * @param id Id del reglon.
     * @param municipio Municipio del reglon seleccionado.
     * @param fecha Fecha del reglon seleccionado.
     * @param clima Clima del reglon seleccionado.
     * @param grados Grados celsius del reglon seleccionado.
     * @param viento Dirección del viento del reglon seleccionado.
     * @return Retorna un valor que especifica si se ha hecho un cambio.
     */
    public static boolean editar(int id, String municipio, Date fecha, String clima, Double grados, String viento) {
        boolean resultado = false;
        
        java.sql.Date fechaDos = new java.sql.Date(fecha.getTime());
        
        try{
            Connection conexion = Conexion.obtener();
            String consulta = "UPDATE clima SET municipio = ?, fecha = ?, clima = ?, gradosCelsius = ?, vientoDireccion = ? WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, municipio);
            statement.setDate(2, fechaDos);
            statement.setString(3, clima);
            statement.setDouble(4, grados);
            statement.setString(5, viento);
            statement.setInt(6, id);
            
            statement.execute();
            
            resultado = statement.getUpdateCount() == 1;
            
            conexion.close();
        } catch (Exception ex){
            System.out.println("Ocurrio un error: " + ex.getMessage());
        }
        
        return resultado;
    }
    
    /**
     * Elimina un reglon seleccionado de la tabla.
     * @param id Id del reglon seleccionado.
     * @return Retorna un valor que especifica si se ha hecho un cambio.
     */
    public static boolean eliminar(int id) {
        boolean resultado = false;
        
        try{
            Connection conexion = Conexion.obtener();
            String consulta = "DELETE from clima WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, id);
            
            statement.execute();
            resultado = statement.getUpdateCount() == 1;
            
            conexion.close();
        } catch (Exception ex){
            System.out.println("Ocurrio un error: " + ex.getMessage());
        }
        
        return resultado;
    }
    
    /**
     * Este metodo obtiene la información de un reglon a partir de su ID
     * @param id El id del objeto guardado en el reglon.
     * @return Retonra la información obtenida del reglon.
     */
    public static Clima obtenerPorId(int id) {

        Clima c = new Clima();

        try {
            Connection conexion = Conexion.obtener();
            String consulta = "SELECT municipio, fecha, clima, gradosCelsius, vientoDireccion FROM clima WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, id);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                c.setId(resultSet.getInt(1));
                c.setMunicipio(resultSet.getString(2));
                c.setFecha(resultSet.getDate(3));
                c.setClima(resultSet.getString(4));
                c.setGradosCelsius(resultSet.getDouble(5));
                c.setVientoDireccion(resultSet.getString(6));
            }
            
        } catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }

        return c;
    }

    /**
     * Obtiene el id
     * @return El id
     */
    public int getId() {
        return id;
    }

    /**
     * Le da un valor a id
     * @param id ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el municipio
     * @return El municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * Le da un valor a municipio
     * @param municipio El municipio
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * Obtiene el valor de fecha
     * @return La fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Le da un valor a la fecha
     * @param fecha La fehca
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el valor del clima
     * @return El clima
     */
    public String getClima() {
        return clima;
    }

    /**
     * Le da un valor a clima
     * @param clima El clima
     */
    public void setClima(String clima) {
        this.clima = clima;
    }

    /**
     * Obtiene el valor de los grados
     * @return Los grados
     */
    public double getGradosCelsius() {
        return gradosCelsius;
    }

    /**
     * Le da un valor a los grados
     * @param gradosCelsius Los grados
     */
    public void setGradosCelsius(double gradosCelsius) {
        this.gradosCelsius = gradosCelsius;
    }

    /**
     * Obtiene el valor del viento
     * @return El viento
     */
    public String getVientoDireccion() {
        return vientoDireccion;
    }

    /**
     * Le da un valor a viento
     * @param vientoDireccion El viento
     */
    public void setVientoDireccion(String vientoDireccion) {
        this.vientoDireccion = vientoDireccion;
    }

}
