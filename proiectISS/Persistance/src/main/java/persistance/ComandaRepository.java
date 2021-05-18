package persistance;

import firma.AgentVanzari;
import firma.Comanda;

import java.util.List;

public interface ComandaRepository extends RepositoryCRUD<Integer, Comanda> {

    List<Comanda> getComenziRealizateDeAgent(int idAgent);

    void updateComanda(Comanda comandaNoua);
}
