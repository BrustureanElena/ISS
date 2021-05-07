package server;

import firma.AgentVanzari;
import firma.Produs;
import persistance.AgentRepository;
import persistance.ProdusRepository;
import services.IObserver;
import services.IServices;
import services.MyException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceImplementation implements IServices {

    private AgentRepository agentRepository;
    private ProdusRepository produsRepository;
    private Map<Integer,IObserver> agentiLogati;

    public ServiceImplementation(AgentRepository agentRepository,ProdusRepository produsRepository) {
        this.agentRepository = agentRepository;
        this.produsRepository=produsRepository;
        agentiLogati=new ConcurrentHashMap<>();
    }

    @Override
    public void login(AgentVanzari agentVanzari, IObserver obs) throws MyException {
        AgentVanzari agentVanzari1 = agentRepository.findAgentByUsernameParola(agentVanzari.getUsername(),
                agentVanzari.getParola());

        if(agentVanzari1!= null) {
            if(agentiLogati.get(agentVanzari1.getId()) != null){
                throw new MyException("Agentul deja s-a logat!");
            }
            agentiLogati.put(agentVanzari1.getId(),obs);

        }else{
            throw new MyException("Autentificare esuata!");
        }
    }


    @Override
    public synchronized void logout(AgentVanzari agentVanzari, IObserver obs) throws MyException {
        agentiLogati.remove(agentVanzari);
    }

    @Override
    public synchronized List<Produs> getToateProduseleVandute() {
        return produsRepository.getToateProduseleVandute();
    }
}
