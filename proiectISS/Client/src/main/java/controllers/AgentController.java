package controllers;

import firma.AgentVanzari;
import firma.Produs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import services.IObserver;
import services.IServices;
import services.MyException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AgentController  extends UnicastRemoteObject implements IObserver, Serializable {

    private IServices service;
    private AgentVanzari agentConectat;
    private ObservableList<Produs> modelProduse = FXCollections.observableArrayList();

    @FXML
    TableView<Produs> idTabelProduse;

    public AgentController() throws RemoteException {
    }
    public void initialize() {
        idTabelProduse.setItems(modelProduse);

    }
    public void initModel()  {

        modelProduse.setAll(service.getToateProduseleVandute());

    }
    public void setContext(IServices service) throws RemoteException{
        this.service = service;
        initModel();
    }
    public void setAgentConectat(AgentVanzari agentConectat) throws MyException {
        this.agentConectat=agentConectat;
        initModel();
    }
    public void logout(){
        try {
            service.logout(agentConectat,this);
            System.exit(0);
        } catch (MyException e) {
            System.out.println("Logout error " + e);
        }
    }
}
