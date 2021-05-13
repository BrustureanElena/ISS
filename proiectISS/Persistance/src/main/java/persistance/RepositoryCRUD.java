package persistance;

import firma.Comanda;
import firma.Produs;

import java.util.List;


public interface RepositoryCRUD<ID, T> {
    void add(T elem) throws Exception;
   // Comanda delete(ID id);
    void deleteVoid(Integer id);
    //Comanda update(T elem);
    void updateC(Produs elem);
   // void updateComanda(Comanda elem);
    T findById(ID id);
    List<T> findAll();
}
