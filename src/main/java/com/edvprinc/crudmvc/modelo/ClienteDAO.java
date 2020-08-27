/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edvprinc.crudmvc.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author garfi
 */
public class ClienteDAO {

    Conexion conexion;

    public ClienteDAO() {
        // this.conexion = new Conexion();
        this.conexion = Conexion.getInstance();
    }

    public String insertCliente(int pCodCliente_n,
            String pNomCliente,
            Date pFecNacimiento) {
        String rptRegistro = null;
        try {
            Connection accesoDB = conexion.getConn();
            CallableStatement cs = accesoDB.prepareCall("call sp_InsertCliente(?,?,?)");
            cs.setInt(1, pCodCliente_n);
            cs.setString(2, pNomCliente);
            cs.setDate(3, pFecNacimiento);

            int numFAfectas = cs.executeUpdate();

            if (numFAfectas > 0) {
                rptRegistro = "registro exitoso";
            }
        } catch (SQLException e) {
        }
        return rptRegistro;

    }
    
    public int editarCliente (int pCodClienteN,
            String pNomCliente, 
            Date pFecNacimiento)
    {
        int numFA = 0;
        try {
            Connection acceDB = conexion.getConn();
            CallableStatement cs = acceDB.prepareCall("call sp_editCliente(?,?,?)");
            cs.setInt(1, pCodClienteN);
            cs.setString(2, pNomCliente);
            cs.setDate(3, pFecNacimiento);

            numFA = cs.executeUpdate();
            
            
        } catch (Exception e) {
        }
        return numFA;
    } 
    
     public int eliminarPersona (int pCodCliente_n)
    {
        int numFA = 0;
        try {
            Connection acceDB = conexion.getConn();
            CallableStatement cs = acceDB.prepareCall("call sp_deleteCliente(?)");
            cs.setInt(1, pCodCliente_n);

             numFA = cs.executeUpdate();
            
            
        } catch (Exception e) {
        }
        return numFA;
    } 

    public ArrayList<Cliente> listPersona() throws Exception {
        ArrayList listaCliente = new ArrayList();
        Cliente cliente;

        try {
            Connection acceDB = conexion.getConn();
            PreparedStatement ps = acceDB.prepareStatement("select * from Cliente");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setCodClienteN(rs.getInt(1));
                cliente.setNomCliente(rs.getString(2));
                Date date = rs.getDate(3);
                cliente.setFec_nacimiento(date);
                listaCliente.add(cliente);
            }
        } catch (Exception e) {
            throw e;
        }

        return listaCliente;
    }

    public ArrayList<Cliente> buscarClienteXNombre(String pNomCliente) {
        ArrayList<Cliente> listCliente = new ArrayList<>();
        Cliente cliente ;
        try {
            Connection accDB = conexion.getConn();
            CallableStatement cs = accDB.prepareCall("call sp_buscaCxNombre(?)");
            cs.setString(1, pNomCliente);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {                
                cliente = new Cliente();
                cliente.setCodClienteN(rs.getInt(1));
                cliente.setNomCliente(rs.getString(2));
                cliente.setFec_nacimiento(rs.getDate(3));
                listCliente.add(cliente);
            }
        } catch (Exception e) {
        }
        return  listCliente;
    }

}
