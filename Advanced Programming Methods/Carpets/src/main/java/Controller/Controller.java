package Controller;

import Domain.Carpet;
import Domain.Order;
import javafx.beans.property.SimpleStringProperty;
import Repository.CarpetFileRepository;
import Repository.OrderFileRepository;

import java.io.*;
import java.util.*;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class Controller {
    private CarpetFileRepository carpetRepo;
    private OrderFileRepository orderRepo;
    private int currentCarpetID;
    private int currentOrderID;

//---------------------------------------------------------------FXML----DECLARATIONS-------------------------------------------------------------------------

    @FXML
    private TextField carpet_id_field;
    @FXML
    private TextField carpet_name_field;
    @FXML
    private TextField carpet_dimension_field;
    @FXML
    private TextField carpet_color_field;
    @FXML
    private TextField carpet_price_field;
    @FXML
    private Button carpet_add_button;
    @FXML
    private Button carpet_remove_button;
    @FXML
    private Button carpet_update_button;
    @FXML
    private TableView carpet_tableView = new TableView();
    @FXML
    private TableColumn<Carpet,String> carpet_id_col = new TableColumn<>("abc");
    @FXML
    private TableColumn<Carpet,String> carpet_name_col = new TableColumn<>("abc");
    @FXML
    private TableColumn<Carpet,String> carpet_dimension_col = new TableColumn<>("abc");
    @FXML
    private TableColumn<Carpet,String> carpet_color_col = new TableColumn<>("abc");
    @FXML
    private TableColumn<Carpet,String> carpet_price_col = new TableColumn<>("abc");





    @FXML
    private TextField order_id_field;
    @FXML
    private TextField order_namePers_field;
    @FXML
    private TextField order_address_field;
    @FXML
    private TextField order_date_field;
    @FXML
    private TextField order_carpet_id_field;
    @FXML
    private Button order_add_button;
    @FXML
    private Button order_remove_button;
    @FXML
    private Button order_update_button;
    @FXML
    private TableView order_tableView = new TableView();
    @FXML
    private TableColumn<Order,String> order_id_col = new TableColumn<>("abc");
    @FXML
    private TableColumn<Order,String> order_carpet_id_col = new TableColumn<>("abc");
    @FXML
    private TableColumn<Order,String> order_name_col = new TableColumn<>("abc");
    @FXML
    private TableColumn<Order,String> order_dimension_col = new TableColumn<>("abc");
    @FXML
    private TableColumn<Order,String> order_color_col = new TableColumn<>("abc");
    @FXML
    private TableColumn<Order,String> order_price_col = new TableColumn<>("abc");
    @FXML
    private TableColumn<Order,String> order_namePerson_col = new TableColumn<>("abc");
    @FXML
    private TableColumn<Order,String> order_address_col = new TableColumn<>("abc");
    @FXML
    private TableColumn<Order,String> order_date_col = new TableColumn<>("abc");


    @FXML
    private TextArea reports_textArea;
    @FXML
    private TextField reports_textField1;
    @FXML
    private TextField reports_textField2;
    @FXML
    private TextField reports_textField3;
    @FXML
    private Button reports_button_1;
    @FXML
    private Button reports_button_2;
    @FXML
    private Button reports_button_3;


//--------------------------------------------------------------------------------------------------------------------------------------------------------------------


    public Controller(){

    }

    public void initialize(){
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("props.properties"));       // <-- move to main
            String baseFile = properties.getProperty("baseFile");
            System.out.println(baseFile);
            if (baseFile == null) {
                baseFile = "baseFile.txt";
                System.err.println("BaseObj file not found. Using default" + baseFile);
            }
            String compFile = properties.getProperty("compFile");
            if (compFile == null) {
                compFile = "compFile.txt";
                System.err.println("CompObj file not found. Using default" + compFile);
            }



            CarpetFileRepository file1 = new CarpetFileRepository(baseFile);
            OrderFileRepository file2 = new OrderFileRepository(compFile);
            this.carpetRepo = file1;
            this.orderRepo = file2;
        }catch (IOException ex){System.err.println("Error reading the configuration file"+ex);}

        createCarpetTable();
        createOrderTable();
        this.currentCarpetID = calculateCarpetID();
        this.currentOrderID = calculateOrderID();

        List<Carpet> al = new ArrayList<Carpet>((Collection<? extends Carpet>) this.carpetRepo.findAll());
        for(int i = 0; i < al.size(); i++)
            addToCarpetTable(al.get(i));

        List<Order> al1 = new ArrayList<Order>((Collection<? extends Order>) this.orderRepo.findAll());
        for(int i = 0; i < al1.size(); i++)
            addToOrderTable(al1.get(i));
    }


    private void createCarpetTable(){
        carpet_tableView.setEditable(true);
        carpet_id_col.setCellValueFactory(new PropertyValueFactory<>("ID"));
        carpet_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        carpet_dimension_col.setCellValueFactory(new PropertyValueFactory<>("dimension"));
        carpet_color_col.setCellValueFactory(new PropertyValueFactory<>("color"));
        carpet_price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        //base_bool_prop_col.setCellValueFactory(new PropertyValueFactory<>("bool_prop"));
    }

    private void createOrderTable(){
        order_tableView.setEditable(true);
        order_id_col.setCellValueFactory(new PropertyValueFactory<>("ID"));
        order_carpet_id_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCarpet().getID().toString()));
        order_name_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCarpet().getName()));
        order_dimension_col.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCarpet().getDimension())));
        order_color_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCarpet().getColor()));
        order_price_col.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCarpet().getPrice())));
        //comp_bool_prop_col.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().getBaseObj().getBool_prop())));
        order_namePerson_col.setCellValueFactory(new PropertyValueFactory<>("namePers"));
        order_address_col.setCellValueFactory(new PropertyValueFactory<>("address"));
        order_date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        //comp_string_prop_c2_col.setCellValueFactory(new PropertyValueFactory<>("string_prop_c2"));
        //comp_bool_prop_c_col.setCellValueFactory(new PropertyValueFactory<>("bool_prop_c"));
    }


    public void addToCarpetTable(Carpet o){
        carpet_tableView.getItems().add(o);
    }

    public void addToOrderTable(Order co){
        order_tableView.getItems().add(co);
    }


    public void removeFromCarpetTable(int id){
        int i = 0;
        for(Object obj : carpet_tableView.getItems()){
            Carpet o = (Carpet) obj;
            if(o.getID() == id)
                break;
            i++;
        }

        carpet_tableView.getItems().remove(i);
    }

    public void removeFromOrderTable(int id){
        int i = 0;
        for(Object obj : order_tableView.getItems()){
            Order co = (Order) obj;
            if(co.getID() == id)
                break;
            i++;
        }

        order_tableView.getItems().remove(i);
    }


    public void updateInCarpetTable(Carpet newObj, int id){
        int i = 0;
        for(Object obj : carpet_tableView.getItems()){
            Carpet o = (Carpet) obj;
            if(o.getID() == id)
                break;
            i++;
        }

        carpet_tableView.getItems().set(i,newObj);
    }

    public void updateInOrderTable(Order newCO, int id){
        int i = 0;
        for(Object obj : order_tableView.getItems()){
            Order co = (Order) obj;
            if(co.getID() == id)
                break;
            i++;
        }

        order_tableView.getItems().set(i,newCO);
    }

    @FXML
    public void addCarpetHandler(ActionEvent actionEvent){    //////////////////////////////////////////////////////
        try {
            String name = carpet_name_field.getText();
            int dimension = Integer.parseInt(carpet_dimension_field.getText());
            String color = carpet_color_field.getText();
            int price = Integer.parseInt(carpet_price_field.getText());
            Carpet o = this.addCarpet(name,dimension, color,price);
            this.addToCarpetTable(o);


        }catch (Exception exception){
            System.out.println("No bueno!" + exception);
        }
    }

    @FXML
    public void addOrderHandler(ActionEvent actionEvent){
        try {
//            int int_prop_c1 = Integer.parseInt(comp_int_prop_c1_field.getText());
//            int int_prop_c2 = Integer.parseInt(comp_int_prop_c2_field.getText());
            String namePers = order_namePers_field.getText();
            String address = order_address_field.getText();
            String date = order_date_field.getText();
            int cid = Integer.parseInt(order_carpet_id_field.getText());

            Carpet o = findCarpetByID(cid);
            if(o == null)
                throw new NullPointerException("Carpet does not exist");


            Order co = this.addOrder(namePers, address, date, o);
            this.addToOrderTable(co);


        }catch (Exception exception){
            System.out.println("nono" + exception);
        }
    }

    @FXML
    public void removeCarpetHandler(ActionEvent actionEvent){

        try {
            int ident = Integer.parseInt(carpet_id_field.getText());
            this.removeCarpet(ident);
            this.removeFromCarpetTable(ident);
        }catch (Exception exception){
            System.out.println("nono" + exception);
        }
    }

    @FXML
    public void removeOrderHandler(ActionEvent actionEvent){

        try {
            int ident = Integer.parseInt(order_id_field.getText());
            this.removeOrder(ident);
            this.removeFromOrderTable(ident);
        }catch (Exception exception){
            System.out.println("No bueno!" + exception);
        }
    }

    @FXML
    public void updateCarpetHandler(ActionEvent actionEvent){
        try {
            //int int_prop1 = Integer.parseInt(base_int_prop1_field.getText());
            //int int_prop2 = Integer.parseInt(base_int_prop2_field.getText());
//            String name = carpet_name_field.getText();
//            String string_prop2 = base_string_prop2_field.getText();
//            boolean bool_prop = base_bool_prop_checkBox.selectedProperty().get();
//            int ident = Integer.parseInt(base_id_field.getText());
            String name = carpet_name_field.getText();
            int dimension = Integer.parseInt(carpet_dimension_field.getText());
            String color = carpet_color_field.getText();
            int price = Integer.parseInt(carpet_price_field.getText());
            int ident = Integer.parseInt(carpet_id_field.getText());
            Carpet o = this.updateCarpet(name, dimension, color, price,ident);
            o.setID(ident);
            this.updateInCarpetTable(o,ident);


        }catch (Exception exception){
            System.out.println("No bueno!" + exception);
        }
    }

    @FXML
    public void updateOrderHandler(ActionEvent actionEvent){
        try {
//            int int_prop_c1 = Integer.parseInt(comp_int_prop_c1_field.getText());
//            int int_prop_c2 = Integer.parseInt(comp_int_prop_c2_field.getText());
//            String string_prop_c1 = comp_string_prop_c1_field.getText();
//            String string_prop_c2 = comp_string_prop_c2_field.getText();
//            int bid = Integer.parseInt(comp_base_id_field.getText());
//            boolean bool_prop_c = comp_bool_prop_c_checkBox.selectedProperty().get();
            String namePers = order_namePers_field.getText();
            String address = order_address_field.getText();
            String date = order_date_field.getText();
            int cid = Integer.parseInt(order_carpet_id_field.getText());
            int ident = Integer.parseInt(order_id_field.getText());

            Carpet o = findCarpetByID(cid);

            if(o == null)
                throw new NullPointerException("Carpet does not exist");


            Order co = this.updateOrder(namePers,address,date, o, ident);
            co.setID(ident);
            this.updateInOrderTable(co,ident);


        }catch (Exception exception){
            System.out.println("No bueno!" + exception);
        }
    }



    public Carpet addCarpet(String name, int dimension, String color, int price){
        try{
            this.currentCarpetID += 1;
            Carpet o = new Carpet(currentCarpetID,name,dimension, color,price);
            carpetRepo.add(o);
            return o;
        }catch(Exception exception){
            this.currentCarpetID -= 1;
            System.err.println("Exception occurred:" + exception);
        }
        return null;
    }

    public Order addOrder(String namePers, String address, String date, Carpet o){
        try{
            this.currentOrderID += 1;
            Order co = new Order(currentOrderID, namePers, address, date, o);
            orderRepo.add(co);
            return co;
        }catch(Exception exception){
            this.currentOrderID -= 1;
            System.err.println("Exception occurred:" + exception);
        }
        return null;
    }


    public void removeCarpet(int id){
        try{
            carpetRepo.delete(id);
        }catch(Exception exception){System.err.println("Exception occurred:" + exception);}
    }

    public void removeOrder(int id){
        try{
            orderRepo.delete(id);
        }catch(Exception exception){System.err.println("Exception occurred:" + exception);}
    }


    public Carpet updateCarpet(String name, int dimension, String color, int price, int id){
        try{
            Carpet o = new Carpet(currentCarpetID,name,dimension,color,price);
            carpetRepo.update(o,id);
            return o;
        }catch(Exception exception){System.err.println("Exception occurred:" + exception);}
        return null;
    }

    public Order updateOrder(String namePers, String address, String date, Carpet o, int id){
        try{
            Order co = new Order(currentOrderID, namePers, address, date, o);
            orderRepo.update(co,id);
            return co;
        }catch(Exception exception){System.err.println("Exception occurred:" + exception);}
        return null;
    }

    private int calculateCarpetID() {
        int bid =0;
        try (BufferedReader buffer = new BufferedReader(new FileReader(this.carpetRepo.filename))) {
            String line = null;
            while ((line = buffer.readLine()) != null) {
                String[] el = line.split(" - ");
                bid = Integer.parseInt(el[0]);
            }
        } catch (Exception e) { System.out.println("Error:" + e); }
        return bid;
    }

    private int calculateOrderID(){
        int cid =0;
        try (BufferedReader buffer = new BufferedReader(new FileReader(this.orderRepo.filename))) {
            String line = null;
            while ((line = buffer.readLine()) != null) {
                String[] el = line.split("; ");
                cid = Integer.parseInt(el[0]);
            }
        } catch (Exception e) { System.out.println("Error:" + e); }
        return cid;
    }


    private Carpet findCarpetByID(int cid){
        for(Carpet o: carpetRepo.findAll()) {
            if (o.getID() == cid)
                return o;
        }
        return null;
    }

    public void report1Handler(ActionEvent actionEvent){
        String date = reports_textField1.getText();
        Set<String> carpets = this.orderRepo.CarpetsByDate(date);
        String carpetsBengos = String.join("\n",carpets);
        reports_textArea.setText(carpetsBengos);
    }


//
//    public void button1Handler(ActionEvent actionEvent){
//        int id = Integer.parseInt(reports_textField1.getText());
//        Car car = findCarByID(id);
//        Set <String> s = this.reservationRepo.peopleByCar(car);
//        String ss = String.join("\n",s);
//        reports_textArea.setText(ss);
//    }
//
//    public void button2Handler(ActionEvent actionEvent){
//        String pers = reports_textField2.getText();
//        Set <String> s = this.reservationRepo.carsByPerson(pers);
//        String ss = String.join("\n",s);
//        reports_textArea.setText(ss);
//    }
//
//    public void button3Handler(ActionEvent actionEvent){
//        String date = reports_textField3.getText();
//        Set <String> s = this.reservationRepo.peopleByDate(date);
//        String ss = String.join("\n",s);
//        reports_textArea.setText(ss);
//    }
//
//




}

