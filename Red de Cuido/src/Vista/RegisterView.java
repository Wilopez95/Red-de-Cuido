package Vista;

import Acceso.MapeoEnfermedades;
import Acceso.Perfil;
import Controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterView {
    private JPanel jpanel;
    private JCheckBox padeceDeAlgunaEnfermedadCheckBox;
    private JButton registrarButton;
    private JTextField NameField;
    private JTextField NameField2;
    private JTextField NameField3;
    private JTextField mailField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JTextField justificationField;
    private JLabel nameError1;
    private JLabel nameError2;
    private JLabel nameError3;
    private JLabel mailError;
    private JLabel passError1;
    private JLabel passError2;
    private JLabel justificationError;
    private Perfil perfil;
    MainController maincontroller;
    JFrame frame;

    public RegisterView() throws SQLException {
        maincontroller = MainController.getInstance();
        perfil = new Perfil();
        frame = new JFrame("Red de Cuido");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.add(jpanel);
        frame.setExtendedState(frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        hideErrorsMesages();



        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                hideErrorsMesages();
                if(checkfields() && checkPasswords()){
                    if(padeceDeAlgunaEnfermedadCheckBox.isSelected()){
                        System.out.println("Ingresar enfermedaes");
                        frame.setEnabled(false);
                        JFrame frame1 = new JFrame("Enfermades");
                        frame1.setLayout(new GridBagLayout());
                        GridBagConstraints c = new GridBagConstraints();
                        JPanel titulo = new JPanel();
                        titulo.setLayout(new FlowLayout());
                        c.fill = GridBagConstraints.HORIZONTAL;
                        c.weightx = 0.5;
                        c.ipadx = 600;
                        c.ipady = 50;
                        c.gridx = 0;
                        c.gridy = 0;
                        JLabel titulotxt = new JLabel("Ingrese una enfermedad perro");
                        titulo.add(titulotxt);
                        frame1.add(titulo, c);


                        JPanel body = new JPanel();
                        body.setLayout(new FlowLayout());
                        c.fill = GridBagConstraints.HORIZONTAL;
                        c.weightx = 0.5;
                        c.ipadx = 600;
                        c.ipady = 30;
                        c.gridx = 0;
                        c.gridy = 1;
                        JLabel enfermedadtxt = new JLabel("Enfermedad :");
                        JTextField enfermedadimp = new JTextField("", 30);
                        body.add(enfermedadtxt);
                        body.add(enfermedadimp);
                        frame1.add(body, c);


                        JPanel body1 = new JPanel();
                        body1.setLayout(new FlowLayout());
                        c.fill = GridBagConstraints.HORIZONTAL;
                        c.weightx = 0.5;
                        c.ipadx = 600;
                        c.ipady = 30;
                        c.gridx = 0;
                        c.gridy = 2;
                        JLabel tratamientotxt = new JLabel("Tratamiento :");
                        JTextField tratamientoimp = new JTextField("", 30);
                        body1.add(tratamientotxt);
                        body1.add(tratamientoimp);
                        frame1.add(body1, c);


                        JPanel body2 = new JPanel();
                        body2.setLayout(new FlowLayout());
                        c.fill = GridBagConstraints.HORIZONTAL;
                        c.weightx = 0.5;
                        c.ipadx = 600;
                        c.ipady = 20;
                        c.gridx = 0;
                        c.gridy = 3;
                        JLabel info = new JLabel();
                        body2.add(info);
                        frame1.add(body2, c);


                        JPanel footer = new JPanel();
                        footer.setLayout(new FlowLayout());
                        footer.setBackground(Color.GRAY);
                        c.fill = GridBagConstraints.HORIZONTAL;
                        c.weightx = 0.5;
                        c.ipadx = 600;
                        c.ipady = 50;
                        c.gridx = 0;
                        c.gridy = 4;
                        JButton agregar = new JButton("Agregar");
                        JButton finalizar = new JButton("Finalizar");
                        footer.add(agregar);
                        footer.add(finalizar);
                        frame1.add(footer, c);


                        agregar.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                if (enfermedadimp.getText().isEmpty() || tratamientoimp.getText().isEmpty()) {
                                    info.setForeground(Color.red);
                                    info.setText("Debe ingresar datos validos");
                                } else {
                                    MapeoEnfermedades mapeoEnfermedades = new MapeoEnfermedades(enfermedadimp.getText(),tratamientoimp.getText());
                                    perfil.addEnfermedad(mapeoEnfermedades);
                                    info.setText(" ENFERMEDAD REGISTRADA");
                                    info.setForeground(Color.GRAY);
                                    enfermedadimp.setText("");
                                    tratamientoimp.setText("");
                                }
                            }
                        });

                        finalizar.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                try {
                                    if(RegisterPerfil()){
                                        frame.dispose();
                                        frame1.dispose();
                                        maincontroller.viewLoginInit();
                                        maincontroller.showNotificatioAcc();
                                    }else {
                                        JOptionPane optionPane = new JOptionPane("No se pudo registrar, verifique sus datos \n" +
                                                "e intente de nuevo", JOptionPane.ERROR_MESSAGE);
                                        JDialog dialog = optionPane.createDialog("Error faltal");
                                        dialog.setAlwaysOnTop(true);
                                        dialog.setVisible(true);
                                        frame.setEnabled(true);
                                        frame1.dispose();
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        frame1.setResizable(false);
                        frame1.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                        frame1.setSize(600, 300);
                        frame1.setVisible(true);

                    }else {
                        System.out.println("No enfermedaes");
                        int n = JOptionPane.showConfirmDialog(
                                frame,
                                "¿Esta seguro que no desea registrar enfermades?",
                                "Confirmacion",
                                JOptionPane.YES_NO_OPTION);
                        if (n==0){
                            System.out.println("registrar al cliente");
                            try {
                                if(RegisterPerfil()){
                                    frame.dispose();
                                    maincontroller.viewLoginInit();
                                    maincontroller.showNotificatioAcc();
                                }else {
                                    JOptionPane optionPane = new JOptionPane("No se pudo registrar, verifique sus datos \n" +
                                            "e intente de nuevo", JOptionPane.ERROR_MESSAGE);
                                    JDialog dialog = optionPane.createDialog("Error faltal");
                                    dialog.setAlwaysOnTop(true);
                                    dialog.setVisible(true);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }else {
                            System.out.println("No hace nada");
                        }

                    }

                }

            }
        });

    }

    private boolean RegisterPerfil() throws SQLException {
        perfil.setNombre(NameField.getText()+" "+NameField2.getText()+" "+NameField3.getText());
        perfil.setCorreo(mailField.getText());
        perfil.setContraseña(passwordField1.getText());
        perfil.setJustificacion(justificationField.getText());

        return maincontroller.registrarCliente(perfil);
    }

    private void hideErrorsMesages(){
        nameError1.setVisible(false);
        nameError2.setVisible(false);
        nameError3.setVisible(false);
        mailError.setVisible(false);
        passError1.setVisible(false);
        passError2.setVisible(false);
        justificationError.setVisible(false);
    }


    private boolean checkPasswords(){
        if (passwordField1.getText().equals(passwordField2.getText())){
            return true;
        }else {
            passError2.setVisible(true);
            passError2.setText("Las contraseñas no coinciden");
            passError2.setForeground(new Color(187,28,27));
            return false;
        }
    }

    private boolean checkfields(){
        if(NameField.getText().isEmpty()){
            nameError1.setForeground(new Color(187,28,27));
            nameError1.setVisible(true);
            return false;
        }else if(NameField2.getText().isEmpty()){
            nameError2.setForeground(new Color(187,28,27));
            nameError2.setVisible(true);
            return false;
        }else if(NameField3.getText().isEmpty()){
            nameError3.setForeground(new Color(187,28,27));
            nameError3.setVisible(true);
            return false;
        }else if(mailField.getText().isEmpty()){
            mailError.setForeground(new Color(187,28,27));
            mailError.setVisible(true);
            return false;
        }else if (passwordField1.getText().isEmpty()){
            passError1.setForeground(new Color(187,28,27));
            passError1.setVisible(true);
            return false;
        }else if (passwordField2.getText().isEmpty()){
            passError2.setForeground(new Color(187,28,27));
            passError2.setVisible(true);
            return false;
        }else if (justificationError.getText().isEmpty()){
            justificationError.setForeground(new Color(187,28,27));
            justificationError.setVisible(true);
        }

        return true;
    }

    public void CloseWindows(){
        frame.dispose();
    }
}
