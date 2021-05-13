package hu.unideb.inf;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

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

import static java.lang.Integer.compare;
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
    private Label Forint;

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
    private TextField nevKER;

    @FXML
    private TextField roomKER;

    @FXML
    private TextField nevTextBox;

    @FXML
    private TextField szabaszamTextBox;

    @FXML
    private TextField erktavBox;

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

    @FXML
    void chooseRoom(ActionEvent event) {
        int roomnr = parseInt(dropRoom.getValue());
        String sql = "SELECT * FROM guest WHERE r_number = ?";
        String sql_room = "SELECT price as ar FROM rooms WHERE ID = ? ";
        String sql_sooner = "SELECT leaving FROM guest where r_number='"+roomnr+"'";
        String sql_date = "Select Datediff((SELECT leaving FROM guest WHERE r_number=? ),(SELECT arrival from guest WHERE r_number=?)) as napok";
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        LocalDate today = LocalDate.parse(formatter.format(date));
        try {
            PreparedStatement preparedStatement = connectionDB.prepareStatement(sql);
            PreparedStatement preparedStatement_room = connectionDB.prepareStatement(sql_room);
            PreparedStatement preparedStatement_date = connectionDB.prepareStatement(sql_date);
            PreparedStatement preparedStatement_sooner = connectionDB.prepareStatement(sql_sooner);
            preparedStatement.setInt(1, roomnr);
            preparedStatement_date.setInt(1, roomnr);
            preparedStatement_date.setInt(2, roomnr);
            preparedStatement_room.setInt(1, roomnr);
            ResultSet queryOutput = preparedStatement.executeQuery();
            ResultSet queryOutput_date = preparedStatement_date.executeQuery();
            ResultSet queryOutput_room = preparedStatement_room.executeQuery();
            ResultSet queryOutput_sooner = preparedStatement_sooner.executeQuery();

            List<Integer> napok = new ArrayList<>();
            while (queryOutput_date.next()){
                napok.add(queryOutput_date.getInt("napok"));
            }

            List<Integer> ar = new ArrayList<>();
            while (queryOutput_room.next()){
                ar.add(queryOutput_room.getInt("ar"));
            }
            List<Date> date_leaving = new ArrayList<>();
            while (queryOutput_sooner.next()){
                date_leaving.add(queryOutput_sooner.getDate("leaving"));
            }
            int ered = date.compareTo(date_leaving.get(0));
            if(ered<0){
                System.out.println("többet fizetett"+date_leaving.get(0)+"  "+today);
                String sql_date_jo = "Select Datediff(('"+today+"'),(SELECT arrival FROM guest WHERE r_number=? )) as napok";
                PreparedStatement preparedStatement_date_jo = connectionDB.prepareStatement(sql_date_jo);
                preparedStatement_date_jo.setInt(1, roomnr);
                ResultSet queryOutput_date_jo = preparedStatement_date_jo.executeQuery();
                List<Integer> napok_jo = new ArrayList<>();
                while (queryOutput_date_jo.next()){
                    napok_jo.add(queryOutput_date_jo.getInt("napok"));
                }
                napok.set(0,napok_jo.get(0));
            }

            int szamitott_ar = napok.get(0) * ar.get(0);

            if(!queryOutput.next()){
                infoBox("Hiba", null, "Hiba");
            }
            else {
                TextOsszes.setText("Név: " + queryOutput.getString(2) + "\n" + "Szobaszám: " + queryOutput.getString(6) + "\nItt töltött napok: " + napok.get(0) + "\nFizetendő összeg: " + szamitott_ar + "ft");
                Forint.setText(szamitott_ar + "ft");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    ObservableList<String> list= FXCollections.observableArrayList();
    ObservableList<String> list1= FXCollections.observableArrayList();
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
                //infoBox("Sikeres belépés",null,"Siker" );
                Login_tab.setDisable(true);
                Reservation_tab.setDisable(false);
                Checkout_tab.setDisable(false);
                Kereses_tab.setDisable(false);
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                DateErkez.setValue(LocalDate.parse(formatter.format(date)));
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
        reci_drop.setItems(recisek);
    }

    @FXML
    void refreshButton_fog(ActionEvent event) {
        list.clear();
        String sql = "SELECT id FROM rooms WHERE free=1";
        try {
            PreparedStatement preparedStatement = connectionDB.prepareStatement(sql);
            ResultSet queryOutput = preparedStatement.executeQuery();
            while(queryOutput.next()){
                list.add(queryOutput.getString("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dropRoom2.setItems(list);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        DateErkez.setValue(LocalDate.parse(formatter.format(date)));
    }

    @FXML
    void refreshButton_pay(ActionEvent event) {
        list1.clear();
        String sql = "SELECT id FROM rooms WHERE free=0";
        try {
            PreparedStatement preparedStatement = connectionDB.prepareStatement(sql);
            ResultSet queryOutput = preparedStatement.executeQuery();
            while(queryOutput.next()){
                list1.add(queryOutput.getString("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        dropRoom.setItems(list1);
    }


    @FXML
   public void foglalas(ActionEvent event) {
        String name = TextVeznev.getText() + " " + TextKernev.getText();
        int postalcode = parseInt(TextIranyito.getText());
        String address = TextVaros.getText() + " " + TextUtca.getText() + " " + TextHsz.getText();
        String email = TextEmail.getText();
        int roomnr = parseInt(dropRoom2.getValue());
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

            if(siker){
                infoBox("Hiba a feltöltésben", null, "Hiba");
            }
            else {
                infoBox("A foglalás sikerült!",null,"Siker" );
            }

            String sql_update = "UPDATE rooms SET free = ? WHERE id = ? ";
            PreparedStatement asd = connectionDB.prepareStatement(sql_update);
            asd.setInt(1, 0);
            asd.setInt(2, roomnr);
            asd.executeUpdate();
            //createSQLException
            //translateException
        }catch (Exception e) {
            e.printStackTrace();
        }
        TextVeznev.clear();
        TextKernev.clear();
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
    void fizetButton(ActionEvent event) {
        //Select Datediff((SELECT leaving FROM guest WHERE r_number=3 ),(SELECT arrival from guest WHERE r_number=3))
        String sql_update = "UPDATE guest SET r_number = 0 WHERE r_number = ?;";
        String sql = "UPDATE rooms SET free = ? WHERE id = ? ";
        int siker = 0;
        try {
            PreparedStatement asd = connectionDB.prepareStatement(sql);
            PreparedStatement update_guest = connectionDB.prepareStatement(sql_update);
            asd.setInt(1, 1);
            asd.setInt(2, parseInt(dropRoom.getValue()));
            update_guest.setInt(1, parseInt(dropRoom.getValue()));
            siker=asd.executeUpdate();
            siker=update_guest.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        if (siker==1){
            infoBox("Sikeres fizetés a szoba újra szabad",null,"Info");
        }
        else
            infoBox("Sikertelen fizetés",null,"Info");

        TextOsszes.clear();
        Forint.setText("");
    }

    @FXML
    void keresesButton(ActionEvent event) {
        //String sql = "SELECT name,r_number,arrival,leaving FROM guest WHERE r_number=? ";
        String sql="";
        int ez=0;
        String nev="";//= nevKER.getText();
        int szsz=99;// = parseInt(roomKER.getText());
        if(!roomKER.getText().isEmpty()){
            szsz = parseInt(roomKER.getText());
            sql = "SELECT name,r_number,arrival,leaving FROM guest WHERE r_number='"+szsz+"' ";
            ez=1;
            //szsz = parseInt(roomKER.getText());
            System.out.println("szobaszamos swl fut");
        }
        if(!nevKER.getText().isEmpty()){
            nev = nevKER.getText();
            sql = "SELECT name,r_number,arrival,leaving FROM guest WHERE name like '%"+nev+"%'";
            ez=2;
            System.out.println("neves swl fut");

        }

        try {
            PreparedStatement preparedStatement = connectionDB.prepareStatement(sql);
            System.out.println("nev: "+nev+"   Szoba:  "+szsz+"\n");
            ResultSet queryOutput = preparedStatement.executeQuery(sql);
            if(!queryOutput.next()){
                infoBox("Nincs ilyen vendeg", null, "INFO");
            }
            else {
                nevTextBox.setText(queryOutput.getString(1));
                szabaszamTextBox.setText(queryOutput.getString(2));
                erktavBox.setText(queryOutput.getString(3)+" - "+queryOutput.getString(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        nevKER.clear();
        roomKER.clear();
    }
    @FXML
    void vegosszegButton(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //bottomLabel.setText("DB connection is on");
    }    
}
