package Vista;

import Acceso.MapeoEnfermedades;
import Controller.MainController;
import FactoryUsuario.Cliente;
import FactoryUsuario.Empleado;
import FactoryUsuario.Horario;
import Objetos.Contrato;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Home_Employed {
    private JPanel Main;
    private JTabbedPane tabs_Empleado;
    private JPanel Reportes;
    private JPanel Validaciones;
    private JPanel Empleados;
    private TableRowSorter sorter;
    private TableRowSorter sorterC;
    private TableRowSorter sorterE;
    private JTable Reportes_tabla;
    private JTextField field_buscar;
    private JScrollPane Reportes_interno;
    private JTable Calificaciones_tabla;
    private JScrollPane Validaciones_interno;
    private JTable Clientes_tabla;
    private JTextField fieldc_buscar;
    private JTable Enfermedades_tabla;
    private JScrollPane Empleados_interno;
    private JTextField fielde_buscar;
    private JTable Empleados_tabla;
    private JScrollPane Empleados_interno2;
    private JTable Horarios_tabla;
    private JButton habilitarButton;
    private JButton calificarButton;
    private JScrollPane Reportes_interno2;
    private JPanel Calificar_panel;
    private JScrollPane Validaciones_interno2;
    private JPanel Enfermedades_panel;
    MainController maincontroller;
    JFrame frame;

    public Home_Employed(int tipo) throws SQLException {

        String tab_style="",Style="<html>" +
                "<div align='center' style='padding:20px; width:150px; color:Black;'><h3>" +
                "~" +
                "</h3></div></html>";
        maincontroller = MainController.getInstance();

        //System.out.println(UIManager.getDefaults());

        tab_style = Style.replace("~","Contratos");
        tabs_Empleado.addTab(tab_style, Reportes);
        tab_style = Style.replace("~","Clientes");
        tabs_Empleado.addTab(tab_style, Validaciones);
        tab_style = Style.replace("~","Empleados");
        tabs_Empleado.addTab(tab_style, Empleados);

        //REPORTES+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        Object[][] datos={};
        String[] columnas={"Número", "Servicio", "Cliente","Empleado", "Inicio", "Fin", "Precio"},
        columnasCal={"Calificación"};
        DefaultTableModel dtm = new DefaultTableModel(datos, columnas);
        DefaultTableModel dtmCal = new DefaultTableModel(datos, columnasCal);
        sorter = new TableRowSorter<>(dtm);

        Reportes_tabla.setModel(dtm);
        Reportes_tabla.setRowSorter(sorter);
        Reportes_tabla.setDefaultEditor(Object.class, null);

        Calificaciones_tabla.setModel(dtmCal);
        if(tipo==0){ Calificaciones_tabla.setDefaultEditor(Object.class, null); }

        calificarButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int index = Reportes_tabla.getSelectedRow();
                int ID = (int) Reportes_tabla.getValueAt(index, 0);
                int Calificacion_e = Integer.parseInt(Calificaciones_tabla.getValueAt(1, 0).toString());
                String Comentario_e = Calificaciones_tabla.getValueAt(2,0).toString();
                try {

                    maincontroller.CalificarEmpleado(Calificacion_e,Comentario_e,ID);
                    JOptionPane.showMessageDialog(frame, "Se registro la evaluación");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        TextPrompt placeholder = new TextPrompt("Buscar", field_buscar);

        Object[] fila={};
        for (Contrato i: maincontroller.Contratos) {
            fila= new Object[]{i.getID(), i.getServicio(), i.getCliente(), i.getEmpleado(), i.getFecha_inicio(),
                    i.getFecha_final(), "₡"+i.getPrecio()};
            dtm.addRow(fila);
        }

        Reportes_tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                int index = Reportes_tabla.getSelectedRow();
                if (index > -1) {
                    // print first column value from selected row
                    int ID = (int) Reportes_tabla.getValueAt(index, 0);
                    for(Contrato i:maincontroller.Contratos){
                        if (i.getID()==ID){
                            Object[][] datosL= new Object[][]{{"Empleado"},
                                    {i.getCalificacion_Empleado()},
                                    {i.getComentario_Empleado()}};

                            dtmCal.setDataVector(datosL, columnasCal);
                            Calificaciones_tabla.setRowHeight(2,100);
                            Calificaciones_tabla.setRowHeight(5,100);
                        }
                    }

                    if(tipo==1){ calificarButton.setEnabled(true); }
                }
            }
        });

        field_buscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(field_buscar.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                search(field_buscar.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                search(field_buscar.getText());
            }
            public void search(String str) {
                if (str.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(str));
                }
            }
        });

        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        //VALIDACIONES++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        String[] columnasV={"Nombre", "Correo", "Calificación"},
                columnasEnf={"Enfermedad", "Tratamiento"};
        DefaultTableModel dtmV = new DefaultTableModel(datos, columnasV);
        DefaultTableModel dtmEnf = new DefaultTableModel(datos, columnasEnf);
        sorterC = new TableRowSorter<>(dtmV);

        Clientes_tabla.setModel(dtmV);
        Clientes_tabla.setRowSorter(sorterC);
        Clientes_tabla.setDefaultEditor(Object.class, null);

        Enfermedades_tabla.setModel(dtmEnf);
        Enfermedades_tabla.setDefaultEditor(Object.class, null);

        habilitarButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int index = Clientes_tabla.getSelectedRow();
                String correo = (String) Clientes_tabla.getValueAt(index, 1);

                try {
                    maincontroller.EnableClient(correo);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                dtmV.removeRow(index);
            }
        });

        TextPrompt placeholderV = new TextPrompt("Buscar", fieldc_buscar);

        for (Cliente i: maincontroller.ListaClientes) {
            fila= new Object[]{i.getNombre(), i.getCorreo(), i.getCalificacion()};
            dtmV.addRow(fila);
        }

        Clientes_tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                int index = Clientes_tabla.getSelectedRow();
                if (index > -1) {
                    // print first column value from selected row
                    String correo = (String) Clientes_tabla.getValueAt(index, 1);
                    for(Cliente i:maincontroller.ListaClientes) {
                        if (i.getCorreo().compareTo(correo) == 0) {
                            dtmEnf.setDataVector(new Object[][]{}, columnasEnf);
                            for (MapeoEnfermedades j : i.getListaEnfermedades()) {
                                Object[] datosE = new Object[]{j.getEnfermedad(), j.getTratamiento()};

                                dtmEnf.addRow(datosE);
                            }

                            Enfermedades_tabla.setRowHeight(100);
                        }
                    }

                    if(tipo==1){ habilitarButton.setEnabled(true); }
                }
            }
        });

        fieldc_buscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(fieldc_buscar.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                search(fieldc_buscar.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                search(fieldc_buscar.getText());
            }
            public void search(String str) {
                if (str.length() == 0) {
                    sorterC.setRowFilter(null);
                } else {
                    sorterC.setRowFilter(RowFilter.regexFilter(str));
                }
            }
        });

        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        //EMPLEADOS++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        if(tipo==0) {
            String[] columnasE = {"ID", "Nombre", "Tipo", "Puesto", "Centro", "Calificación"},
                    columnasHor = {"Día", "Inicio", "Fin"};
            DefaultTableModel dtmE = new DefaultTableModel(datos, columnasE);
            DefaultTableModel dtmHor = new DefaultTableModel(datos, columnasHor);
            sorterE = new TableRowSorter<>(dtmE);

            Empleados_tabla.setModel(dtmE);
            Empleados_tabla.setRowSorter(sorterE);
            Empleados_tabla.setDefaultEditor(Object.class, null);

            Horarios_tabla.setModel(dtmHor);
            Horarios_tabla.setDefaultEditor(Object.class, null);

            TextPrompt placeholderE = new TextPrompt("Buscar", fielde_buscar);

            for (Empleado i : maincontroller.ListaEmpleados) {
                fila = new Object[]{i.getID(), i.getNombre(), i.getTipo(), i.getPuesto(), i.getCentro(), i.getCalificacion()};
                dtmE.addRow(fila);
            }

            Empleados_tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent event) {
                    int index = Empleados_tabla.getSelectedRow();
                    if (index > -1) {
                        // print first column value from selected row
                        int ID = (int) Empleados_tabla.getValueAt(index, 0);
                        for (Empleado i : maincontroller.ListaEmpleados) {
                            if (i.getID() == ID) {
                                dtmHor.setDataVector(new Object[][]{}, columnasHor);
                                for (Horario j : i.getListaHorarios()) {
                                    Object[] datosHor = new Object[]{j.getDia(), j.getHoraI(), j.getHoraF()};

                                    dtmHor.addRow(datosHor);
                                }
                            }
                        }
                    }
                }
            });

            fielde_buscar.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    search(fielde_buscar.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    search(fielde_buscar.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    search(fielde_buscar.getText());
                }

                public void search(String str) {
                    if (str.length() == 0) {
                        sorterE.setRowFilter(null);
                    } else {
                        sorterE.setRowFilter(RowFilter.regexFilter(str));
                    }
                }
            });
        }else{
            tabs_Empleado.removeTabAt(2);
        }
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        frame = new JFrame("Red de Cuido");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.add(Main);
        frame.setExtendedState(frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
}

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
}
