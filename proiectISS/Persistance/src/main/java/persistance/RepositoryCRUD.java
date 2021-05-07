package persistance;

import java.util.List;


public interface RepositoryCRUD<ID, T> {
    void add(T elem) throws Exception;
    void delete(ID id);
    void update(T elem);
    T findById(ID id);
    List<T> findAll();
}
