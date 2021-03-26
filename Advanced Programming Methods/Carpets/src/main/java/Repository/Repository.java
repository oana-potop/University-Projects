package Repository;

public interface Repository<ID, T> {
    void add(T elem);
    void delete(ID id);
    void update(T elem, ID id);
    T findByID(ID id);
    Iterable <T> findAll();
}
