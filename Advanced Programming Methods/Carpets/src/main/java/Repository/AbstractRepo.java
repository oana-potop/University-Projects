package Repository;

import Domain.Identifiable;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRepo<ID, T extends Identifiable<ID>> implements Repository<ID,T>{
    protected Map<ID,T> map;

    public AbstractRepo(){
        map = new HashMap<>();
    }

    public void add(T el){
        if(map.containsKey(el.getID()))
            throw new RuntimeException("Element is already in the repository");
        map.put(el.getID(),el);
    }
    public void delete(ID id){
        if(map.containsKey(id))
            map.remove(id);
        else
            throw new RuntimeException("The element you are trying to delete is not in the repository");
    }
    public void update(T el, ID id){
        if(map.containsKey(id))
            map.put(id,el);
        else
            throw new RuntimeException("The element you are trying to update is not in the repository");
    }
    public T findByID(ID id){
        if (map.containsKey(id))
            return map.get(id);
        else
            return null;
    }

    public Iterable<T> findAll(){
        return map.values();
    }


}
