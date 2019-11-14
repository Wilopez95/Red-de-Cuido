package Vista;

import Acceso.MapeoEnfermedades;
import Controller.MainController;
import FactoryUsuario.Cliente;
import FactoryUsuario.Empleado;
import FactoryUsuario.Horario;
import Objetos.Contrato;
import Objetos.Sesion;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Home_Cliente {
    private JPanel Main;
    private JTabbedPane tabs_Empleado;
    private JPanel Reportes;
    private JScrollPane Reportes_interno;
    private JTable Reportes_tabla;
    private JTextField field_buscar;
    private JPanel Calificar_panel;
    private JScrollPane Reportes_interno2;
    private JTable Calificaciones_tabla;
    private JPanel Validaciones;
    private JTable Clientes_tabla;
    private JTextField fieldc_buscar;
    private JTable Enfermedades_tabla;
    private JPanel Empleados;
    private JScrollPane Empleados_interno;
    private JTable Empleados_tabla;
    private JTextField fielde_buscar;
    private JScrollPane Empleados_interno2;
    private JTable Horarios_tabla;
    private JPanel Filtros_panel;
    private JComboBox Combo_Centro;
    private JComboBox Combo_Servicio;
    private JPanel Contrato;
    private JTextField fechai_field;
    private JTextField fechaf_field;
    private JButton contratarButton;
    private JPanel Contrato_interno;
    private JTextField horas_field;
    private JButton calificarButton;
    private TableRowSorter sorter;
    private TableRowSorter sorterC;
    private TableRowSorter sorterE;
    MainController maincontroller;
    JFrame frame;

    public Home_Cliente() throws SQLException {
        String tab_style="",Style="<html>" +
                "<div align='center' style='padding:20px; width:150px; color:Black;'><h3>" +
                "~" +
                "</h3></div></html>";
        maincontroller = MainController.getInstance();

        //System.out.println(UIManager.getDefaults());

        tab_style = Style.replace("~","Historial");
        tabs_Empleado.addTab(tab_style, Reportes);
        tab_style = Style.replace("~","Contratar");
        tabs_Empleado.addTab(tab_style, Contrato);
        tab_style = Style.replace("~","Cuidadores");
        tabs_Empleado.addTab(tab_style, Empleados);

        tabs_Empleado.setEnabledAt(1,false);
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

        calificarButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int index = Reportes_tabla.getSelectedRow();
                int ID = (int) Reportes_tabla.getValueAt(index, 0);
                int Calificacion_e = Integer.parseInt(Calificaciones_tabla.getValueAt(1, 0).toString());
                String Comentario_e = Calificaciones_tabla.getValueAt(2,0).toString();
                try {
                    maincontroller.CalificarCliente(Calificacion_e,Comentario_e,ID);
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
                            Object[][] datosL= new Object[][]{
                                    {"Cliente"},
                                    {i.getCalificacion_Cliente()},
                                    {i.getComentario_Cliente()}};

                            dtmCal.setDataVector(datosL, columnasCal);
                            Calificaciones_tabla.setRowHeight(2,100);
                            Calificaciones_tabla.setRowHeight(5,100);
                        }
                    }
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

        //CONTRATO++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        contratarButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int index = Empleados_tabla.getSelectedRow();
                int ID = (int) Empleados_tabla.getValueAt(index, 0);
                Cliente cliente = (Cliente) maincontroller.getSesion().getUsuario();
                String correo = cliente.getCorreo();
                String servicio = Combo_Servicio.getSelectedItem().toString();
                String fecha_inicial = fechai_field.getText();
                String fecha_final = fechaf_field.getText();
                int horas = Integer.parseInt(horas_field.getText());
                try {
                    maincontroller.RegistrarContrato(ID,correo,servicio,fecha_inicial,fecha_final,horas);
                    JOptionPane.showMessageDialog(frame, "La contratacion se realizo con exito");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                System.out.println(correo);
            }
        });

        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        //EMPLEADOS++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        for (String s: maincontroller.ListaServicios) {
            Combo_Servicio.addItem(s);
        }
        for (String c: maincontroller.ListaCentros) {
            Combo_Centro.addItem(c);
        }
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

        Combo_Centro.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                fielde_buscar.setText(Combo_Centro.getSelectedItem().toString());

            }
        });

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

                    tabs_Empleado.setEnabledAt(1,true);
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
