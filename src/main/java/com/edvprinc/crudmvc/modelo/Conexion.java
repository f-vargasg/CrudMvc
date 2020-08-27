/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edvprinc.crudmvc.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author garfi
 */
public class Conexion {
    private Connection conn = null;
    private boolean openConn = false;
    private int numOpenConns = 0;
    private int cantInvGetConn = 0;

    /**
     * Get the value of cantInvGetConn
     *
     * @return the value of cantInvGetConn
     */
    public int getCantInvGetConn() {
        return cantInvGetConn;
    }


    /**
     * Get the value of numOpenConns
     *
     * @return the value of numOpenConns
     */
    public int getNumOpenConns() {
        return numOpenConns;
    }


    public Connection getConn() {
        ++this.cantInvGetConn;
        return conn;
    }
    
    private  Conexion() {
        try {
           Class.forName("org.mariadb.jdbc.Driver");
           // conn =  DriverManager.getConnection("jdbc:mariadb://10.25.1.80:3306/dbTest1", "fvargas", "valerie5250");
           conn =  DriverManager.getConnection("jdbc:mariadb://10.25.1.86:3306/TESTDB", "admin", "valerie5250");
           this.openConn = true;
           ++this.numOpenConns;
        } catch (ClassNotFoundException | SQLException e) {
        }

    }

    public boolean isOpenConn() {
        return openConn;
    }
    
    private static class ConexionClassHolder
    {
       static final Conexion SINGLE_INSTANCE = new Conexion();
    }
    
    public static Conexion getInstance()
    {
        return ConexionClassHolder.SINGLE_INSTANCE;
    }
    
    /*
    private Connection getConexion() {
            
        try {
           Class.forName("org.mariadb.jdbc.Driver");
           conn =  DriverManager.getConnection("jdbc:mariadb://10.25.1.80:3306/dbTest1", "fvargas", "valerie5250");
        } catch (ClassNotFoundException | SQLException e) {
        }
        
        return conn;
    }
    */
}
