/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edvprinc.crudmvc.modelo;

import java.sql.Date;

/**
 *
 * @author garfi
 */
public class Cliente {

    private int codClienteN;

    /**
     * Get the value of CodClienteN
     *
     * @return the value of CodClienteN
     */
    public int getCodClienteN() {
        return codClienteN;
    }

    /**
     * Set the value of CodClienteN
     *
     * @param CodClienteN new value of CodClienteN
     */
    public void setCodClienteN(int CodClienteN) {
        this.codClienteN = CodClienteN;
    }

    private String nomCliente;

    /**
     * Get the value of nomCliente
     *
     * @return the value of nomCliente
     */
    public String getNomCliente() {
        return nomCliente;
    }

    /**
     * Set the value of nomCliente
     *
     * @param nomCliente new value of nomCliente
     */
    public void setNomCliente(String nomCliente) {
        this.nomCliente = nomCliente;
    }

    public Cliente() {
        this.codClienteN = -1;
        this.nomCliente = "";
    }

    private Date fec_nacimiento;

    /**
     * Get the value of fec_nacimiento
     *
     * @return the value of fec_nacimiento
     */
    public Date getFec_nacimiento() {
        return fec_nacimiento;
    }

    /**
     * Set the value of fec_nacimiento
     *
     * @param fec_nacimiento new value of fec_nacimiento
     */
    public void setFec_nacimiento(Date fec_nacimiento) {
        this.fec_nacimiento = fec_nacimiento;
    }

}
