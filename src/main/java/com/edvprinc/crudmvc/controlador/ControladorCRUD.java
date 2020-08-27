/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edvprinc.crudmvc.controlador;

import com.edvprinc.crudmvc.modelo.ClienteDAO;
import com.edvprinc.crudmvc.vista.JFCrud;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author garfi
 */
public class ControladorCRUD implements ActionListener, KeyListener {

    private JFCrud vistaCRUD = new JFCrud();
    private ClienteDAO modeloCRUD = new ClienteDAO();
    private DefaultTableModel modelo2;

    public ControladorCRUD(JFCrud vistaCRUD, ClienteDAO modeloCRUD) {
        this.modeloCRUD = modeloCRUD;
        this.vistaCRUD = vistaCRUD;
        this.vistaCRUD.jBtnRegistrar.addActionListener(this);
        this.vistaCRUD.jBtnListar.addActionListener(this);
        this.vistaCRUD.jBtnEditar.addActionListener(this);
        this.vistaCRUD.jBtnGEdit.addActionListener(this);
        this.vistaCRUD.jBtnCancel.addActionListener(this);
        this.vistaCRUD.jBtnBorrar.addActionListener(this);
        this.vistaCRUD.jTxtCodClienteN.addKeyListener(this);
        this.vistaCRUD.jTxtBusqueda.addKeyListener(this);
        inicializarCrud();
    }

    private void CrearModelo() {
        try {
            modelo2 = (new DefaultTableModel(null,
                    new String[]{"Cod.Cliente", "Nombre", "Fec.Nacimiento"}) {
                Class[] types = new Class[]{
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class
                };
                boolean[] canEdit = new boolean[]{
                    false, false, false
                };

                @Override
                public Class getColumnClass(int columnIndex
                ) {
                    return types[columnIndex];
                }

                @Override
                public boolean isCellEditable(int rowIndex, int colIndex
                ) {
                    return canEdit[colIndex];
                }
            });
            this.vistaCRUD.jTDatos.setModel(modelo2);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString() + "error2");
        }
    }

    public void inicializarCrud() {
        CrearModelo();
    }

    public void cleanTabla(JTable pTablaD) {
        // DefaultTableModel dm = (DefaultTableModel) getModel();
        DefaultTableModel dm = (DefaultTableModel) (pTablaD.getModel());
        int rowCount = dm.getRowCount();
//Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
        }
    }

    public void LlenarTabla(JTable pTabla) throws Exception {
        // DefaultTableModel modeloT = new DefaultTableModel();

        // tablaD.setModel(modeloT);
        // tablaD.setModel(modelo2);
        /*
        modeloT.addColumn("cod_cliente_n");
        modeloT.addColumn("nom_cliente");
        modeloT.addColumn("fec_nacimiento");
         */
        cleanTabla(pTabla);
        Object[] columna = new Object[3];

        int numRegistros = modeloCRUD.listPersona().size();

        for (int i = 0; i < numRegistros; i++) {

            columna[0] = modeloCRUD.listPersona().get(i).getCodClienteN();
            columna[1] = modeloCRUD.listPersona().get(i).getNomCliente();
            columna[2] = modeloCRUD.listPersona().get(i).getFec_nacimiento();

            modelo2.addRow(columna);

        }

    }

    // <editor-fold defaultstate="collapsed" desc="Aplicar Registrar">
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaCRUD.jBtnRegistrar) {
            try {
                int codClienteN = Integer.parseInt(vistaCRUD.jTxtCodClienteN.getText());
                String nomCliente = vistaCRUD.jTxtNomCliente.getText();
                java.util.Date fecNacimiento = vistaCRUD.jDtChFecNacim.getDate();

                Date sqlDate = new java.sql.Date(fecNacimiento.getTime());
                // LocalDate date = fecNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                String rptRegistro = modeloCRUD.insertCliente(codClienteN, nomCliente, sqlDate);
                if (rptRegistro != null) {
                    JOptionPane.showMessageDialog(null, rptRegistro);
                } else {
                    JOptionPane.showMessageDialog(null, "Registro Erroneo");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.toString() + " error");
            }

        }
// </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Aplicar Listar">
        if (e.getSource() == vistaCRUD.jBtnListar) {
            try {
                LlenarTabla(vistaCRUD.jTDatos);
                JOptionPane.showMessageDialog(null, "Lista de Registros");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.toString() + " error");
            }
        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Aplicar Editar">
        if (e.getSource() == vistaCRUD.jBtnEditar) {
            vistaCRUD.jBtnCancel.setEnabled(true);
            vistaCRUD.jBtnGEdit.setEnabled(true);
            int filaEditar = vistaCRUD.jTDatos.getSelectedRow();
            int numFS = vistaCRUD.jTDatos.getSelectedRowCount();

            if (filaEditar >= 0 && numFS == 1) {
                vistaCRUD.jTxtCodClienteN.setText(String.valueOf(vistaCRUD.jTDatos.getValueAt(filaEditar, 0)));
                vistaCRUD.jTxtNomCliente.setText(String.valueOf(vistaCRUD.jTDatos.getValueAt(filaEditar, 1)));
                if (vistaCRUD.jTDatos.getValueAt(filaEditar, 2) != null) {
                    Date sqlDate = (Date) (vistaCRUD.jTDatos.getValueAt(filaEditar, 2));
                    java.util.Date utDate = new java.util.Date(sqlDate.getTime());
                    vistaCRUD.jDtChFecNacim.setDate(utDate);
                }

                vistaCRUD.jTxtCodClienteN.setEditable(false);
                vistaCRUD.jBtnRegistrar.setEnabled(false);
                vistaCRUD.jBtnEditar.setEnabled(false);
                vistaCRUD.jBtnBorrar.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila o al menos una");
            }
        }
// </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Postear Data (jBtnGEdit)">
        if (e.getSource() == vistaCRUD.jBtnGEdit) {
            try {
                int codClienteN = Integer.parseInt(vistaCRUD.jTxtCodClienteN.getText());
                String nomCliente = vistaCRUD.jTxtNomCliente.getText();
                java.util.Date fecNacimiento = vistaCRUD.jDtChFecNacim.getDate();

                Date sqlDate = new java.sql.Date(fecNacimiento.getTime());
                // LocalDate date = fecNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                int rptaEdit = modeloCRUD.editarCliente(codClienteN, nomCliente, sqlDate);
                if (rptaEdit > 0) {
                    JOptionPane.showMessageDialog(null, "Edicion Exitosa!!!");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo realizar la edición");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.toString() + " error");
            } finally {
                limpiarElementos();
                vistaCRUD.jBtnRegistrar.setEnabled(true);
                vistaCRUD.jBtnEditar.setEnabled(true);
                vistaCRUD.jBtnBorrar.setEnabled(true);
                vistaCRUD.jBtnGEdit.setEnabled(false);
            }
        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Cancelar edicion">
        if (e.getSource() == vistaCRUD.jBtnCancel) {
            limpiarElementos();
            vistaCRUD.jBtnRegistrar.setEnabled(true);
            vistaCRUD.jBtnEditar.setEnabled(true);
            vistaCRUD.jBtnBorrar.setEnabled(true);
            vistaCRUD.jBtnGEdit.setEnabled(false);
            vistaCRUD.jBtnCancel.setEnabled(false);
        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Aplicar Borrar">
        if (e.getSource() == vistaCRUD.jBtnBorrar) {
            int filaInicio = vistaCRUD.jTDatos.getSelectedRow();
            int numFS = vistaCRUD.jTDatos.getSelectedRowCount();

            ArrayList<Integer> listCtaClte = new ArrayList<>();
            int codClienteN = -1;

            if (filaInicio > 0) {

                for (int i = 0; i < numFS; i++) {
                    codClienteN = (int) (vistaCRUD.jTDatos.getValueAt(i + filaInicio, 0));
                    listCtaClte.add(codClienteN);
                }

                for (int i = 0; i < numFS; i++) {
                    codClienteN = listCtaClte.get(i);
                    int rptaUsuario = JOptionPane.showConfirmDialog(null,
                            "Quiere eliminar el registro con " + String.valueOf(codClienteN)
                            + "?");
                    if (rptaUsuario == 0) {
                        modeloCRUD.eliminarPersona(codClienteN);
                    }
                }
                try {
                    LlenarTabla(vistaCRUD.jTDatos);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorCRUD.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccion al menos una fila para eliminar");
            }

        }
        // </editor-fold>

    }

    private void limpiarElementos() {
        vistaCRUD.jTxtCodClienteN.setText("");
        vistaCRUD.jTxtCodClienteN.setEditable(true);
        vistaCRUD.jTxtNomCliente.setText("");
        vistaCRUD.jTxtCodClienteN.setEditable(true);
        vistaCRUD.jDtChFecNacim.setDate(null);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == vistaCRUD.jTxtCodClienteN) {
            char c = e.getKeyChar();
            if (c <'0' || c > '9') {
                e.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vistaCRUD.jTxtBusqueda) {
            String nomCliente = vistaCRUD.jTxtBusqueda.getText();
            cleanTabla(vistaCRUD.jTDatos);
            Object[] columna = new Object[3];

            int numRegistros = modeloCRUD.buscarClienteXNombre(nomCliente).size();

            for (int i = 0; i < numRegistros; i++) {

                columna[0] = modeloCRUD.buscarClienteXNombre(nomCliente).get(i).getCodClienteN();
                columna[1] = modeloCRUD.buscarClienteXNombre(nomCliente).get(i).getNomCliente();
                columna[2] = modeloCRUD.buscarClienteXNombre(nomCliente).get(i).getFec_nacimiento();

                modelo2.addRow(columna);

            }
        }
    }

}
