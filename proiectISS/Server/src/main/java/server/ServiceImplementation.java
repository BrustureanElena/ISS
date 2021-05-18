package server;

import firma.AgentVanzari;
import firma.Comanda;
import firma.Produs;
import persistance.AgentRepository;
import persistance.ComandaRepository;
import persistance.ProdusRepository;
import services.IObserver;
import services.IServices;
import services.MyException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceImplementation implements IServices {

    private AgentRepository agentRepository;
    private ProdusRepository produsRepository;
    private ComandaRepository comandaRepository;
    private Map<Integer,IObserver> agentiLogati;

    public ServiceImplementation(AgentRepository agentRepository,ProdusRepository produsRepository,ComandaRepository comandaRepository) {
        this.agentRepository = agentRepository;
        this.produsRepository=produsRepository;
        this.comandaRepository=comandaRepository;
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

    @Override
    public AgentVanzari agentConectat(String username, String parola) {
        return agentRepository.findAgentByUsernameParola(username,parola);
    }
    @Override
    public synchronized List<Comanda> getComenziRealizateDeAgent(int idAgent) {

        return comandaRepository.getComenziRealizateDeAgent(idAgent);
    }


    @Override
    public void addComanda(String numeClient, Date dataPunereComanda, String status, int idProdus, int cantitateProdus, int idAbonat) throws Exception {


        Comanda comanda=new Comanda(numeClient,dataPunereComanda,status,idProdus,cantitateProdus,idAbonat);
        comandaRepository.add(comanda);
        notifyComandaAdded(comanda);
    }

    @Override
    public void stergeComanda(Integer id) {
        notifyComandaDeleted(comandaRepository.findById(id));
        comandaRepository.deleteVoid(id);

       // notifyComandaUpdated();
    }

    @Override
    public void modificaComanda(int idComanda, String status, int idProdus, int cantitateProdus) throws Exception {

        Comanda comandaNoua=comandaRepository.findById(idComanda);
        comandaNoua.setStatus(status);
        comandaNoua.setCantitateProdus(cantitateProdus);
        comandaNoua.setIdProdus(idProdus);
        comandaRepository.updateComanda(comandaNoua);


        notifyComandaUpdated();
    }

    private void notifyComandaDeleted(Comanda comanda) {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(var o: agentiLogati.entrySet()) {
            executor.execute(() -> {
                try {
                    o.getValue().comandaDeleted(comanda);
                } catch ( RemoteException e) {
                    System.err.println("Error notifying bibliotecar adaugare carte " + e);
                }
            });
        }

        executor.shutdown();
    }



    @Override
    public void modificaProdus(Integer id, int stoc, String denumire, Float pret) throws Exception {
        Produs produs=new Produs(denumire,pret,stoc);
        produs.setId(id);
        produsRepository.updateC(produs);

    }



    private final int defaultThreadsNo=5;
    private void notifyComandaAdded(Comanda comanda) {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(var o: agentiLogati.entrySet()) {
            executor.execute(() -> {
                try {
                    o.getValue().comandaAdded(comanda);
                } catch (RemoteException | MyException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
    }

    private void notifyComandaUpdated() {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(var o: agentiLogati.entrySet()) {
            executor.execute(() -> {
                try {
                    o.getValue().comandaUpdated();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }


        executor.shutdown();
    }
}
