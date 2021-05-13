package services;

import firma.AgentVanzari;
import firma.Comanda;
import firma.Produs;

import java.util.Date;
import java.util.List;

public interface IServices {
    public void login(AgentVanzari agentVanzari, IObserver obs) throws MyException ;
    public void logout(AgentVanzari agentVanzari, IObserver obs) throws MyException ;
    public List<Produs> getToateProduseleVandute();
    public AgentVanzari agentConectat(String username, String parola);

    public List<Comanda>  getComenziRealizateDeAgent(int idAbonat);
    public Comanda deleteComanda( int idProdus) throws Exception;
    public void modificaProdus(Integer id,int stoc,String denumire,Float pret) throws Exception;
    public void addComanda(String numeClient, Date dataPunereComanda, String status, int idProdus, int cantitateProdus, int idAbonat) throws Exception;
    public void stergeComanda (Integer id);
    Comanda findComanda(Comanda byId);
}
