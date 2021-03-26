package Repository;

import Domain.Carpet;
import Domain.Identifiable;

import java.util.Set;

public interface OrderRepo<ID, T extends Identifiable<ID>> extends Repository<ID, T>{

    public Set<String> CarpetsByDate(String date);
//    public Set<String> peopleByCar(Car car);
//    public Set carsByPerson(String name);
//    public Set peopleByDate(String startDate);
//    public Set carsByPrice(int price);
//    public Set peopleByCarByPrice(Car car, int price);

}
