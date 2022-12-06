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
 *
 * @author jesus
 */
public class Clima {

    private int id;
    private String municipio;
    private Date fecha;
    private String clima;
    private double gradosCelsius;
    private String vientoDireccion;

    /**
     * Obtiene los registros de computadora existentes en la base de datos.
     *
     * @return Lista de computadoras.
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
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     *
     * @param municipio
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     *
     * @return
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     *
     * @param fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     *
     * @return
     */
    public String getClima() {
        return clima;
    }

    /**
     *
     * @param clima
     */
    public void setClima(String clima) {
        this.clima = clima;
    }

    /**
     *
     * @return
     */
    public double getGradosCelsius() {
        return gradosCelsius;
    }

    /**
     *
     * @param gradosCelsius
     */
    public void setGradosCelsius(double gradosCelsius) {
        this.gradosCelsius = gradosCelsius;
    }

    /**
     *
     * @return
     */
    public String getVientoDireccion() {
        return vientoDireccion;
    }

    /**
     *
     * @param vientoDireccion
     */
    public void setVientoDireccion(String vientoDireccion) {
        this.vientoDireccion = vientoDireccion;
    }

}
