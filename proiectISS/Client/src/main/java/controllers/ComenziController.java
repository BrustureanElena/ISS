package controllers;

import firma.AgentVanzari;
import firma.Comanda;
import firma.Produs;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import services.IObserver;
import services.IServices;
import services.MyException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class ComenziController  extends UnicastRemoteObject implements Serializable,IObserver {
    private IServices service;
    private AgentVanzari agentConectat;
    private ObservableList<Comanda> modelComenzi = FXCollections.observableArrayList();

    @FXML
    TableView<Comanda> idTabelComenzi;
    public ComenziController() throws RemoteException {
    }
    public void initialize() {
        idTabelComenzi.setItems(modelComenzi);

    }
    public void initModel()  {

        modelComenzi.setAll(service.getComenziRealizateDeAgent(agentConectat.getId()));

    }

    public void setContext(IServices service) throws RemoteException{
        this.service = service;

    }
    public void setAgentConectat(AgentVanzari agentConectat) throws MyException {
        this.agentConectat=service.agentConectat(agentConectat.getUsername(), agentConectat.getParola());
        initModel();
    }




    public void anuleazaComanda(ActionEvent actionEvent) {

        Comanda comandaSelected=idTabelComenzi.getSelectionModel().getSelectedItem();
        if(comandaSelected!=null)
        {
            try{
                service.stergeComanda(comandaSelected.getId());
                MessageBox.showMessage(null, Alert.AlertType.INFORMATION,"Yeey!", "Comanda anulata cu succes!");
                initModel();


            }catch (Exception e) {
                MessageBox.showErrorMessage(null, e.getMessage());
            }
        }else {
            MessageBox.showErrorMessage(null, "NIMIC SELECTAT");}

    }


    @Override
    public void comandaAdded(Comanda comanda) throws MyException, RemoteException {

    }



    @Override
    public void comandaUpdated() throws RemoteException {
        Platform.runLater(()->{
            modelComenzi.setAll(service.getComenziRealizateDeAgent(agentConectat.getId()));
            idTabelComenzi.refresh();
        });
    }

    @Override
    public void comandaDeleted(Comanda comanda) {

    }
}
