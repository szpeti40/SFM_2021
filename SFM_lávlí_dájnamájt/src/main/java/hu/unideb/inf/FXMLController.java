package hu.unideb.inf;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ComboBox<String> dropRoom;

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
    private Tab Kereses_tab;


    @FXML
    private ImageView door;

    @FXML
    private ImageView csengo;

    @FXML
    private ComboBox<String> dropRoom2;


    @FXML
    private Button ButtonValaszt;

    @FXML
    private TextField TextVegossz;

    @FXML
    private Button ButtonVegossz;

    @FXML
    private ComboBox<String> reci_drop;

    @FXML
    private ImageView door2;

    ObservableList<String> list= FXCollections.observableArrayList();
    ObservableList<String> recisek= FXCollections.observableArrayList("admin","csvirag","selek");


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
        reci_drop.setItems(recisek);
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
                Kereses_tab.setDisable(false);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        sql = "SELECT id FROM rooms WHERE free=1";
        try {
            PreparedStatement preparedStatement = connectionDB.prepareStatement(sql);
            ResultSet queryOutput = preparedStatement.executeQuery();
            System.out.println("ittvagyok");
            while(queryOutput.next()){
                list.add(queryOutput.getString("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dropRoom.setItems(list);
        dropRoom2.setItems(list);

        /*oolean res = login(textAzonosito.getText(),textPassword.getText(),connectionDB);
        if (res){
            testLabel.setText("Sikeres login");
        }
        else{
            testLabel.setText("Rossz felhasznalo név vagy jelszó");
        }*/
    }

    @FXML
    void dropChanged(ActionEvent event) {

    }


    @FXML
   public void foglalas(ActionEvent event) {
        String name = TextVeznev.getText() + " " + TextKernev.getText();
        int postalcode = parseInt(TextIranyito.getText());
        String address = TextVaros.getText() + " " + TextUtca.getText() + " " + TextHsz.getText();
        String email = TextEmail.getText();
        int roomnr = parseInt(dropRoom2.getValue());//parseInt(TextSzoba.getText());
        String mettol = DateErkez.getValue().toString();
        String meddig = DateTavoz.getValue().toString();
        String recis = reci_drop.getValue();

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
        TextVeznev.clear();
        TextIranyito.clear();
        TextVaros.clear();
        TextUtca.clear();
        TextHsz.clear();
        TextEmail.clear();
    }
    @FXML
    void ButtonKeres_handle(ActionEvent event) {

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
    @FXML
    void chooseRoom(ActionEvent event) {

    }

    @FXML
    void fizetButton(ActionEvent event) {

    }

    @FXML
    void keresesButton(ActionEvent event) {

    }
    @FXML
    void vegosszegButton(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //bottomLabel.setText("DB connection is on");
    }    
}
