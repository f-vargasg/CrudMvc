/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edvprinc.crudmvc.app;

import com.edvprinc.crudmvc.controlador.ControladorCRUD;
import com.edvprinc.crudmvc.modelo.ClienteDAO;
import com.edvprinc.crudmvc.vista.JFCrud;

/**
 *
 * @author garfi
 */
public class CrudMvc {

    public static void main(String[] args) {
        JFCrud vistaC = new JFCrud();
        ClienteDAO modeloC = new ClienteDAO();
        ControladorCRUD controlaC = new ControladorCRUD(vistaC, modeloC);
        
        vistaC.setVisible(true);
        vistaC.setLocationRelativeTo(null);

    }
}
