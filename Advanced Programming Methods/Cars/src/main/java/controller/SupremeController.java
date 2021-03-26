package controller;

import domain.Car;
import domain.Reservation;
import javafx.beans.property.SimpleStringProperty;
import repository.FileRepoCars;
import repository.FileRepoReservations;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import domain.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class SupremeController {
    private FileRepoCars carRepo;
    private FileRepoReservations reservationRepo;
    private int currentCarID;
    private int currentReservationID;

    @FXML
    private TextField id;
    @FXML
    private TextField manufacturer;
    @FXML
    private TextField model;
    @FXML
    private TextField maxspeed;
    @FXML
    private TextField price;
    @FXML
    private TextField year;
    //@FXML
    //private CheckBox stillManufactured;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button updateButton;
    @FXML
    private TableView CarTable = new TableView();
    @FXML
    private TableColumn<Car,String> ccolid = new TableColumn<>("abc");
    @FXML
    private TableColumn<Car,String> ccolman = new TableColumn<>("abc");
    @FXML
    private TableColumn<Car,String> ccolmod = new TableColumn<>("abc");
    @FXML
    private TableColumn<Car,String> ccolmaxspd = new TableColumn<>("abc");
    @FXML
    private TableColumn<Car,String> ccolye = new TableColumn<>("abc");
    @FXML
    private TableColumn<Car,String> ccolpr = new TableColumn<>("abc");

    @FXML
    private TextField rid;
    @FXML
    private TextField rcarid;
    @FXML
    private TextField nameOwner;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField initDate;
    @FXML
    private TextField finDate;
    @FXML
    private TableView ReservationTable = new TableView();
    @FXML
    private TableColumn<Reservation,String> rcolid = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> rcolcarid = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> rcolcarman = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> rcolcarmod = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> rcolcarmaxspd = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> rcolcarye = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> rcolcarpr = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> rcolnameowner = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> rcolphnr = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> rcolinitDate = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> rcolfinDate = new TableColumn<>("abc");
   // @FXML
   // private TableColumn<Car,String> imCol = new TableColumn<>("abc");

    @FXML
    private Button addRButton;
    @FXML
    private Button removeRButton;
    @FXML
    private Button updateRButton;

    @FXML
    private Button rep1button;
    @FXML
    private Button rep2button;
    @FXML
    private Button rep3button;

    @FXML
    private TextArea ReportsText;

    @FXML
    private TextField repcarid;
    @FXML
    private TextField repindate;
    @FXML
    private TextField repfindate;





    public SupremeController(){

    }

    public void initialize(){
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("ceva.properties"));
            String carFile = properties.getProperty("carFile");
            System.out.println(carFile);
            if (carFile == null) {
                carFile = "carfile.txt";
                System.err.println("Requests file not found. Using default" + carFile);
            }
            String reservationFile = properties.getProperty("reservationFile");
            if (reservationFile == null) {
                reservationFile = "reservationsfile.txt";
                System.err.println("RepairedForms file not found. Using default" + reservationFile);
            }



            FileRepoCars file1 = new FileRepoCars(carFile);
            FileRepoReservations file2 = new FileRepoReservations(reservationFile);
            this.carRepo = file1;
            this.reservationRepo = file2;
        }catch (IOException ex){System.err.println("Error reading the configuration file"+ex);}

        createTable();
        createReservationTable();

        this.currentCarID = calculateCarID();
        this.currentReservationID = calculateReservationID();

        List<Car> al = new ArrayList<Car>((Collection<? extends Car>) this.carRepo.findAll());
        for(int i = 0; i < al.size(); i++){
            addToTable(al.get(i));
        }

        List<Reservation> al1 = new ArrayList<Reservation>((Collection<? extends Reservation>) this.reservationRepo.findAll());
        for(int i = 0; i < al1.size(); i++)
            addToReservationTable(al1.get(i));
    }

    private void createTable(){
        CarTable.setEditable(true);
        ccolid.setCellValueFactory(new PropertyValueFactory<>("ID"));
        ccolman.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        ccolmod.setCellValueFactory(new PropertyValueFactory<>("model"));
        ccolmaxspd.setCellValueFactory(new PropertyValueFactory<>("maxspeed"));
        ccolye.setCellValueFactory(new PropertyValueFactory<>("year"));
        ccolpr.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void createReservationTable(){
        ReservationTable.setEditable(true);
        rcolid.setCellValueFactory(new PropertyValueFactory<>("ID"));
        rcolcarid.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCar().getID().toString()));
        rcolcarman.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCar().getManufacturer()));
        rcolcarmod.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCar().getModel()));
        rcolcarmaxspd.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCar().getMaxspeed())));
        rcolcarpr.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getCar().getPrice())));
        rcolcarye.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCar().getYear())));
        //rcolphnr.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().getCar().getIsManufactured())));
        rcolnameowner.setCellValueFactory(new PropertyValueFactory<>("nameOwner"));
        rcolphnr.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        rcolinitDate.setCellValueFactory(new PropertyValueFactory<>("initDate"));
        rcolfinDate.setCellValueFactory(new PropertyValueFactory<>("finDate"));
        //res_rprCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        //res_clCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void addToTable(Car car){
        CarTable.getItems().add(car);
    }

    public void addToReservationTable(Reservation res){
        ReservationTable.getItems().add(res);
    }


    public void removeFromTable(int id){
        int i = 0;
        for(Object obj : CarTable.getItems()){
            Car car = (Car) obj;
            if(car.getID() == id)
                break;
            i++;
        }

        CarTable.getItems().remove(i);
    }

    public void removeFromReservationTable(int id){
        int i = 0;
        for(Object obj : ReservationTable.getItems()){
            Reservation res = (Reservation) obj;
            if(res.getID() == id)
                break;
            i++;
        }

        ReservationTable.getItems().remove(i);
    }

    public void updateInTable(Car ncar, int id){
        int i = 0;
        for(Object obj : CarTable.getItems()){
            Car car = (Car) obj;
            if(car.getID() == id)
                break;
            i++;
        }

        CarTable.getItems().set(i,ncar);
    }

    public void updateInReservationTable(Reservation nres, int id){
        int i = 0;
        for(Object obj : ReservationTable.getItems()){
            Reservation res = (Reservation) obj;
            if(res.getID() == id)
                break;
            i++;
        }

        ReservationTable.getItems().set(i,nres);
    }

    @FXML
    public void addCarHandler(ActionEvent actionEvent){

        try {
            String man = manufacturer.getText();
            String mod = model.getText();
            int mS = Integer.parseInt(maxspeed.getText());
            int mY = Integer.parseInt(year.getText());
            double pr = Double.parseDouble(price.getText());
            //Boolean sM = stillManufactured.selectedProperty().get();
            System.out.println("here");
            Car car = this.addCar(man,mod,mS,mY,pr);
            this.addToTable(car);


        }catch (Exception exception){
            System.out.println("nu mai" + exception);
        }
    }

    @FXML
    public void addReservationHandler(ActionEvent actionEvent){

        try {
            String no = nameOwner.getText();
            String pn = phoneNumber.getText();
            String ind = initDate.getText();
            String fd = finDate.getText();
            int cid = Integer.parseInt(rcarid.getText());
            //int pr = Integer.parseInt(res_price.getText());

            Car car = findCarByID(cid);
            if(car == null)
                throw new NullPointerException("Car does not exist");

            System.out.println("here");

            Reservation res = this.addReservation(no,pn,ind,fd,car);
            this.addToReservationTable(res);


        }catch (Exception exception){
            System.out.println("nu mai" + exception);
        }
    }

    @FXML
    public void removeCarHandler(ActionEvent actionEvent){

        try {
            int ident = Integer.parseInt(id.getText());
            this.removeCar(ident);
            this.removeFromTable(ident);
        }catch (Exception exception){
            System.out.println("nu mai" + exception);
        }
    }

    @FXML
    public void removeReservationHandler(ActionEvent actionEvent){

        try {
            int ident = Integer.parseInt(rid.getText());
            this.removeReservation(ident);
            this.removeFromReservationTable(ident);
        }catch (Exception exception){
            System.out.println("nu mai" + exception);
        }
    }

    @FXML
    public void updateCarHandler(ActionEvent actionEvent){
        try {
            String man = manufacturer.getText();
            String mod = model.getText();
            int mS = Integer.parseInt(maxspeed.getText());
            int mY = Integer.parseInt(year.getText());
            double pr = Double.parseDouble(price.getText());
            //Boolean sM = stillManufactured.selectedProperty().get();
            int ident = Integer.parseInt(id.getText());
            Car car = this.updateCar(man,mod,mS,mY,pr,ident);
            car.setID(ident);
            this.updateInTable(car,ident);


        }catch (Exception exception){
            System.out.println("nu mai" + exception);
        }
    }

    @FXML
    public void updateReservationHandler(ActionEvent actionEvent){
        try {
            String no = nameOwner.getText();
            String pn = phoneNumber.getText();
            String ind = initDate.getText();
            String fd = finDate.getText();
            int cid = Integer.parseInt(rcarid.getText());
            //int pr = Integer.parseInt(res_price.getText());
            int ident = Integer.parseInt(rid.getText());
            Car car = findCarByID(cid);
            if(car == null)
                throw new NullPointerException("Car does not exist");

            System.out.println("here");

            Reservation res = this.updateReservation(no,pn,ind,fd,car,ident);
            res.setID(ident);
            this.updateInReservationTable(res,ident);


        }catch (Exception exception){
            System.out.println("nu mai" + exception);
        }
    }

    public Car addCar(String manufacturer, String model, int maxSpeed, int year, double price){
        try{
            this.currentCarID += 1;
            System.out.println(this.currentCarID);
            Car car = new Car(currentCarID,manufacturer,model,maxSpeed,year,price);
            carRepo.add(car);
            return car;
        }catch(Exception exception){
            this.currentCarID -= 1;
            System.err.println("Exception occurred:" + exception);
        }
        return null;
    }

    public Reservation addReservation(String owner, String phoneNumber, String initDate, String finDate, Car car){
        try{
            this.currentReservationID += 1;
            System.out.println(this.currentReservationID);
            Reservation res = new Reservation(currentReservationID,car, owner, phoneNumber, initDate, finDate);
            reservationRepo.add(res);
            return res;
        }catch(Exception exception){
            this.currentReservationID -= 1;
            System.err.println("Exception occurred:" + exception);
        }
        return null;
    }


    public void removeCar(int id){
        try{
            carRepo.delete(id);
        }catch(Exception exception){System.err.println("Exception occurred:" + exception);}
    }

    public void removeReservation(int id){
        try{
            reservationRepo.delete(id);
        }catch(Exception exception){System.err.println("Exception occurred:" + exception);}
    }

    public Car updateCar(String manufacturer, String model, int maxSpeed, int year, double price, int id){
        try{
            Car car = new Car(currentCarID,manufacturer,model,maxSpeed,year, price);
            carRepo.update(car,id);
            return car;
        }catch(Exception exception){System.err.println("Exception occurred:" + exception);}
        return null;
    }

    public Reservation updateReservation(String owner, String phoneNumber, String initDate, String finDate, Car car, int id){
        try{
            Reservation res = new Reservation(currentReservationID, car, owner, phoneNumber, initDate, finDate);
            reservationRepo.update(res,id);
            return res;
        }catch(Exception exception){System.err.println("Exception occurred:" + exception);}
        return null;
    }

    private Car findCarByID(int cid){
        for(Car cr: carRepo.findAll()) {
            if (cr.getID() == cid)
                return cr;
        }
        return null;
    }

    private int calculateCarID() {
        int cid=0;
        try (BufferedReader buffer = new BufferedReader(new FileReader(this.carRepo.filename))) {
            String line = null;
            while ((line = buffer.readLine()) != null) {
                String[] el = line.split(";");
                cid = Integer.parseInt(el[0]);
            }
        } catch (Exception e) { System.out.println("Error:" + e); }
        return cid;
    }

    private int calculateReservationID(){
        int rid=0;
        try (BufferedReader buffer = new BufferedReader(new FileReader(this.reservationRepo.filename))) {
            String line = null;
            while ((line = buffer.readLine()) != null) {
                String[] el = line.split(";");
                rid = Integer.parseInt(el[0]);
            }
        } catch (Exception e) { System.out.println("Error:" + e); }
        return rid;
    }

    @FXML
    private void getOwnersbyCar(ActionEvent actionEvent){
        int id = Integer.parseInt(repcarid.getText());
        Car car = findCarByID(id);
        Set <String> owners = this.reservationRepo.owners_car(car);
        String ownersbengos = String.join("\n",owners);
        ReportsText.setText(ownersbengos);
    }

    @FXML
    private void getOwnersbyInitDate(ActionEvent actionEvent){
        String date = repindate.getText();
        Set <String> owners = this.reservationRepo.owners_rentDate(date);
        String ownersbengos = String.join("\n",owners);
        ReportsText.setText(ownersbengos);
    }

    @FXML
    private void getNumbersbyFinDate(ActionEvent actionEvent){
        String date = repfindate.getText();
        Set <String> numbers = this.reservationRepo.numbers_finDate(date);
        System.out.println(numbers);
        String numbersbengos = String.join("\n",numbers);
        ReportsText.setText(numbersbengos);
    }

}
