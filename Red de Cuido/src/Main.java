import Controller.MainController;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        MainController maincontroller = MainController.getInstance();
        maincontroller.viewLoginInit();
        //maincontroller.test();
    }
}
