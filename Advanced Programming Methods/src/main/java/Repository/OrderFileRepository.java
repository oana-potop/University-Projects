package Repository;

import Domain.Carpet;
import Domain.Order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


public class OrderFileRepository extends AbstractRepo<Integer, Order> implements OrderRepo<Integer,Order> {
    public String filename;

    public OrderFileRepository(String filename){
        this.filename = filename;
        readFromFile();
    }

    private void readFromFile(){
        try (BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
            String line = null;
            while((line = buffer.readLine()) != null)
            {
                String[] el = line.split("; ");
                if (el.length != 5)
                {
                    System.err.println("Line is not valid!" + line);
                    continue;
                }

                try{
                    String[] objEl = el[4].split(" - ");
                    if (objEl.length != 5) {
                        System.err.println("Line is not valid!" + line);
                        System.err.println(objEl.length);
                        continue;
                    }

                    Carpet o = new Carpet();
                    try {
                        int id = Integer.parseInt(objEl[0]);
                        int dimension = Integer.parseInt(objEl[2]);
                        int price = Integer.parseInt(objEl[4]);
                        String name = objEl[1];
                        String color = objEl[3];
                        //boolean bool_prop = Boolean.parseBoolean(objEl[5]);
                        o = new Carpet(id, name, dimension, color, price);
                    }catch(Exception e){System.err.println("Error in data parsing:" + e); }


                    int id_o = Integer.parseInt(el[0]);
                    //int int_prop_c1 = Integer.parseInt(el[1]);
                    //int int_prop_c2 = Integer.parseInt(el[2]);
                    String namePers = el[1];
                    String address = el[2];
                    String date = el[3];
                    //boolean bool_prop_c = Boolean.parseBoolean(el[5]);


                    Order order = new Order(id_o, namePers, address, date, o);
                    super.add(order);
                }catch(NumberFormatException exception){
                    System.err.println("Invalid id format!" + el[0]);
                }
            }
        }catch (IOException exception){
            throw new RepositoryException("File reading error!" + exception);
        }
    }

    private void writeToFile(){
        try ( PrintWriter p = new PrintWriter(filename)){
            for (Order co: findAll()){
                String s = co.getID() + "; " + co.getNamePers() + "; " + co.getAddress() + "; " + co.getDate() + "; " + co.getCarpet();
                p.println(s);
            }
        }
        catch (IOException exception){throw new RepositoryException("Writing error" + exception);}
    }

    @Override
    public void add(Order co){
        try{
            super.add(co);
            writeToFile();
        }
        catch(RuntimeException e)
        { throw new RepositoryException (e);}
    }

    @Override
    public void delete(Integer id)
    {
        try{
            super.delete(id);
            writeToFile();
        }catch(RuntimeException e){throw new RepositoryException(e);}
    }

    @Override
    public void update(Order co, Integer id)
    {
        try{
            super.update(co, id);
            writeToFile();
        }catch(RuntimeException ex){throw new RepositoryException(ex);}
    }


//    @Override
//    //The name of the people who reserved a certain car
//    public Set<String> peopleByCar(Car car){
//        Collection<Reservation> reservations = (Collection<Reservation>) findAll();
//        Set<String> people = reservations.stream().filter(reservation -> reservation.getCar().equals(car)).map(reservation -> reservation.getName()).collect(Collectors.toSet());
//        return people;
//    }
//
//    @Override
//    //All the cars rented by a certain person
//    public Set<String> carsByPerson(String name){
//        Collection<Reservation> reservations = (Collection<Reservation>) findAll();
//        Set<String> cars = reservations.stream().filter(reservation -> reservation.getName().equals(name)).map(reservation -> reservation.getCar().toString()).collect(Collectors.toSet());
//        return cars;
//    }
//
//    @Override
//    //The names of the people renting at a certain date
//    public Set<String> peopleByDate(String startDate){
//        Collection<Reservation> reservations = (Collection<Reservation>) findAll();
//        Set<String> people = reservations.stream().filter(reservation -> reservation.getStartDate().equals(startDate)).map(reservation -> reservation.getName()).collect(Collectors.toSet());
//        return people;
//    }
//
//    @Override
//    //The cars that have been rented at a price higher than a certain number
//    public Set<Car> carsByPrice(int price){         //  <-- make Set<String>  and do .toString() below
//        Collection<Reservation> reservations = (Collection<Reservation>) findAll();
//        Set<Car> cars = reservations.stream().filter(reservation -> reservation.getPrice() > price).map(reservation -> reservation.getCar()).collect(Collectors.toSet());
//        return cars;
//    }
//
//    @Override
//    //The people that have rented a certain car at a price lower than a given number
//    public Set<String> peopleByCarByPrice(Car car, int price){
//        Collection<Reservation> reservations = (Collection<Reservation>) findAll();
//        Set<String> people = reservations.stream().filter(reservation -> reservation.getCar().equals(car)).filter(reservation -> reservation.getPrice() < price).map(reservation -> reservation.getName()).collect(Collectors.toSet());
//        return people;
//    }

    @Override
    public Set<String> CarpetsByDate(String date){
        Collection<Order> orders = (Collection<Order>) findAll();
        Set<String> carpets = orders.stream().filter(order -> order.getDate().equals(date)).map(order -> order.getCarpet().toString()).collect(Collectors.toSet());
        return carpets;
    }

}
