package hu.unideb.inf;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import static java.lang.Integer.parseInt;

public class FXMLController extends DatabaseConnection implements Initializable  {


    Connection connectionDB = connectToDB();
    Stage dialogStage = new Stage();

    @FXML
    private Tab Login_tab;

    @FXML
    private TextField textAzonosito;

    @FXML
    private PasswordField textPassword;

    @FXML
    private Button ButtonBejelent;

    @FXML
    private Tab ker;

    @FXML
    private DatePicker DateErkezes;

    @FXML
    private DatePicker DateTavozas;

    @FXML
    private CheckBox CheckErkely;

    @FXML
    private Button ButtonKeres;

    @FXML
    private TableColumn<?, ?> TableNev;

    @FXML
    private TableColumn<?, ?> TableErkez;

    @FXML
    private TableColumn<?, ?> TableTavoz;

    @FXML
    private TableColumn<?, ?> TableSzobasz;

    @FXML
    private TableColumn<?, ?> TableVendegsz;

    @FXML
    private TableColumn<?, ?> TableFizet;

    @FXML
    private TextField TextSzobatip;

    @FXML
    private Tab fog;

    @FXML
    private TextField TextVeznev;

    @FXML
    private TextField TextKernev;

    @FXML
    private TextField TextIranyito;

    @FXML
    private TextField TextVaros;

    @FXML
    private TextField TextUtca;

    @FXML
    private TextField TextHsz;

    @FXML
    private TextField TextEmail;

    @FXML
    private DatePicker DateErkez;

    @FXML
    private DatePicker DateTavoz;

    @FXML
    private TextField TextSzoba;

    @FXML
    private TextField TextReci;

    @FXML
    private Button ButtonVegleg;

    @FXML
    private Tab fiz;

    @FXML
    private TextArea TextOsszes;

    @FXML
    private TextField TextSzobasz;


    @FXML
    private Label testLabel;

    @FXML
    private Tab Reservation_tab;
    @FXML
    private Tab Search_tab;

    @FXML
    private DatePicker DateArrive;

    @FXML
    private DatePicker DateLeave;

    @FXML
    private Spinner<?> SpinNight;

    @FXML
    private Spinner<?> SpinRoomType;

    @FXML
    private Spinner<?> SpinVendeg;

    @FXML
    private Spinner<?> SpinChild;

    @FXML
    private TextField SpinRoomNum;

    @FXML
    private CheckBox CheckPotagy;

    @FXML
    private CheckBox CheckReggeli;

    @FXML
    private CheckBox CheckHalf;

    @FXML
    private CheckBox CheckFull;

    @FXML
    private CheckBox CheckClean;

    @FXML
    private Button foglalas_button;

    @FXML
    private Tab Checkout_tab;

    @FXML
    private ImageView door;

    @FXML
    private ImageView csengo;

    public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    //public String user = textAzonosito.getText().toString();

    @FXML
    public void handleButtonLogin(ActionEvent event) {

        String user = textAzonosito.getText();
        String password = textPassword.getText();
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
                Login_tab.setDisable(true);
                Reservation_tab.setDisable(false);
                Checkout_tab.setDisable(false);
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

    @FXML
    void foglalas(ActionEvent event) {
        String name = TextVeznev.getText() + " " + TextKernev.getText();
        int postalcode = parseInt(TextIranyito.getText());
        String address = TextVaros.getText() + " " + TextUtca.getText() + " " + TextHsz.getText();
        String email = TextEmail.getText();
        int roomnr = parseInt(TextSzoba.getText());
        String mettol = DateErkez.getValue().toString();
        String meddig = DateTavoz.getValue().toString();
        String recis = TextReci.getText();

        String sql = "INSERT INTO guest (name, postalcode, address, email, r_number, recis, arrival, leaving) " +
                "VALUES(?,?,?,?,?,?,?,?)";
        boolean siker = false;
        try {
            PreparedStatement preparedStatement = connectionDB.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, postalcode);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, email);
            preparedStatement.setInt(5, roomnr);
            preparedStatement.setString(6, recis);
            preparedStatement.setString(7, mettol);
            preparedStatement.setString(8, meddig);
            siker = preparedStatement.execute();
            //ResultSet sikeres = preparedStatement.executeQuery(sql);
            if(siker){
                infoBox("Hiba a feltöltésben", null, "Hiba");
            }
            else {
                infoBox("A feltöltés sikerült!",null,"Siker" );
            }
            //createSQLException
            //translateException


        }catch (Exception e) {
            e.printStackTrace();
        }
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
