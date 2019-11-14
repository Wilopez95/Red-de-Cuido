package Vista;

import Controller.MainController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginView {
    private JTextField mailField;
    private JButton iniciarSesiónButton;
    private JPanel jpanel;
    private JPasswordField passwordField;
    private JButton IniciarSesionbtn;
    private JLabel Errorlabel;
    private JButton registrarseButton;
    MainController maincontroller;
    JFrame frame;

    public LoginView() throws SQLException {
        maincontroller = MainController.getInstance();
        frame = new JFrame("Red de Cuido");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.add(jpanel);
        frame.setExtendedState(frame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        IniciarSesionbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Verificar que formato tenga @ si no da error en la evaluacion.
                if(mailField.getText().isEmpty()){
                    Errorlabel.setForeground(new Color(187,28,27));
                    Errorlabel.setText("Debe ingresar algun Correo");
                }else if(passwordField.getText().isEmpty()){
                    Errorlabel.setForeground(new Color(187,28,27));
                    Errorlabel.setText("Debe ingresar la contraseña");
                }else if(checkvalid(mailField.getText())) {
                     try {
                         Errorlabel.setText("");
                        maincontroller.Login(mailField.getText(),passwordField.getText());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }else {
                    Errorlabel.setForeground(new Color(187,28,27));
                    Errorlabel.setText("Debe ingresar un correo valido");

                }
            }
        });
        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    maincontroller.viewRegister();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean checkvalid(String mail){
        boolean flag = false;
        char[] strToArr = mail.toCharArray();

        for (char output : strToArr){
            if(output == '@'){
                System.out.println(output);
                flag = true;
            }
        }
        return flag;

    }

    public void showError(){
        JOptionPane optionPane = new JOptionPane("No se ha podido iniciar sesion por\n" +
                "favor verifique sus credenciales\n" +
                " e intente nuevamente", JOptionPane.ERROR_MESSAGE);
        JDialog dialog = optionPane.createDialog("Error");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    public void showAccNotification(){
        JOptionPane.showMessageDialog(frame,
                "Tu cuenta se registro, sera evaluada y nos\n"+
                        "y nos pondremos en contacto una vez aprobada");
    }

    public void CloseWindows(){
        frame.dispose();
    }
}
