package services;

import firma.AgentVanzari;
import firma.Produs;

import java.util.List;

public interface IServices {
    public void login(AgentVanzari agentVanzari, IObserver obs) throws MyException ;
    public void logout(AgentVanzari agentVanzari, IObserver obs) throws MyException ;
    public List<Produs> getToateProduseleVandute();
}
