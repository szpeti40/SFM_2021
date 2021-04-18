package hu.unideb.inf;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FXMLController extends DatabaseConnection implements Initializable  {


    Connection connectionDB = connectToDB();
    Stage dialogStage = new Stage();
    @FXML
    private TextField textAzonosito;

    @FXML
    private PasswordField textPassword;
    @FXML
    private Label testLabel;
    @FXML
    private Button ButtonLogin;

    public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    @FXML
    public void handleButtonLogin(ActionEvent event) {

        String user = textAzonosito.getText().toString();
        String password = textPassword.getText().toString();
        String sql = "SELECT * FROM receptionist WHERE username = ? and password = ?";
        try {
            PreparedStatement preparedStatement = connectionDB.prepareStatement(sql);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            ResultSet queryOutput = preparedStatement.executeQuery();

            if(!queryOutput.next()){
                testLabel.setText("Rossz felhasznalo név vagy jelszó");
                infoBox("Rossz jelszó vagy felhasználónév", null, "Hiba");
            }
            else {
                testLabel.setText("Sikeres login");
                infoBox("Sikeres belépés",null,"Siker" );


            }

        }catch (Exception e){
            e.printStackTrace();
        }


        /*oolean res = login(textAzonosito.getText(),textPassword.getText(),connectionDB);
        if (res){
            testLabel.setText("Sikeres login");
        }
        else{
            testLabel.setText("Rossz felhasznalo név vagy jelszó");
        }*/
    }

    /*void handleButtonAction(ActionEvent event) {

        String connectQuery = "SELECT username FROM `receptionist`";
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while(queryOutput.next()){
                shownUserNameLabel.setText(queryOutput.getString("username"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //bottomLabel.setText("DB connection is on");
    }    
}
