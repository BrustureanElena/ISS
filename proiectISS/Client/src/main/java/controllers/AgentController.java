package controllers;

import firma.AgentVanzari;
import firma.Comanda;
import firma.Produs;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.IObserver;
import services.IServices;
import services.MyException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class AgentController  extends UnicastRemoteObject implements IObserver, Serializable {
    private ComenziController comenziController;
    private IServices service;
    private Parent parentComenzi;
    private AgentVanzari agentConectat;
    private ObservableList<Produs> modelProduse = FXCollections.observableArrayList();
    private ObservableList<Comanda> modelComenzi = FXCollections.observableArrayList();
    @FXML
    TableView<Comanda> tabelComenzi;

    @FXML
    TableView<Produs> idTabelProduse;
    @FXML
    TextField textFieldCantitate;
    @FXML
    TextField textFieldClient;

    @FXML
    TextField idTextFieldCantitate;
    @FXML
    TextField idTextFieldStatus;

    @FXML
    TextField idTextFieldProdus;


    public AgentController() throws RemoteException {
    }

    public void initialize() {
        idTabelProduse.setItems(modelProduse);
        tabelComenzi.setItems(modelComenzi);

    }

    public void initModel() {

        modelProduse.setAll(service.getToateProduseleVandute());
        modelComenzi.setAll(service.getComenziRealizateDeAgent(agentConectat.getId()));

    }

    public void setContext(IServices service) throws RemoteException {
        this.service = service;
        // initModel();
    }

    public void setAgentConectat(AgentVanzari agentConectat) throws MyException {
        this.agentConectat = service.agentConectat(agentConectat.getUsername(), agentConectat.getParola());
        initModel();
    }


    public void logout() {
        try {
            service.logout(agentConectat, this);
            System.exit(0);
        } catch (MyException e) {
            System.out.println("Logout error " + e);
        }
    }

    public void plaseazaComanda(ActionEvent actionEvent) {
        Produs produsSelectat = idTabelProduse.getSelectionModel().getSelectedItem();
        String numeClient = textFieldClient.getText();
        int cantitate = Integer.parseInt(textFieldCantitate.getText());
        if (produsSelectat != null) {
            try {
                service.addComanda(numeClient, new Date(), "nelivrata", produsSelectat.getId(), cantitate, agentConectat.getId());
                MessageBox.showMessage(null, Alert.AlertType.INFORMATION, "Yeey!", "Comanda plsata cu succes!");
                textFieldCantitate.setText("");
                textFieldClient.setText("");


            } catch (Exception e) {
                MessageBox.showErrorMessage(null, e.getMessage());
            }
        } else {
            MessageBox.showErrorMessage(null, "NIMIC SELECTAT");
        }

    }

    @Override
    public void comandaAdded(Comanda comanda) throws MyException, RemoteException {
        Platform.runLater(() -> {
            Produs produs = modelProduse.stream()
                    .filter(x -> x.getId().equals(comanda.getIdProdus()))
                    .findFirst()
                    .get();
            int index = modelProduse.indexOf(produs);

            int newStoc = comanda.getCantitateProdus();
            int fin = produs.getStoc() - newStoc;

            try {
                service.modificaProdus(produs.getId(), fin, produs.getDenumire(), produs.getPret());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(fin);

            modelProduse.set(index, produs);

            modelProduse.setAll(service.getToateProduseleVandute());
            modelComenzi.setAll(service.getComenziRealizateDeAgent(agentConectat.getId()));
            tabelComenzi.refresh();
            idTabelProduse.refresh();

        });
    }


    @Override
    public void comandaUpdated() {
        Platform.runLater(() -> {
            modelProduse.setAll(service.getToateProduseleVandute());
            idTabelProduse.refresh();
            modelComenzi.setAll(service.getComenziRealizateDeAgent(agentConectat.getId()));
            tabelComenzi.refresh();
        });

    }

    @Override
    public void comandaDeleted(Comanda comanda) {
        Platform.runLater(() -> {
            Produs produs = modelProduse.stream()
                    .filter(x -> x.getId().equals(comanda.getIdProdus()))
                    .findFirst()
                    .get();
            int index = modelProduse.indexOf(produs);


            int fin = produs.getStoc() + comanda.getCantitateProdus();
            System.out.println(produs.getStoc());
            System.out.println(comanda.getCantitateProdus());
            try {
                service.modificaProdus(produs.getId(), fin, produs.getDenumire(), produs.getPret());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(fin);

            modelProduse.set(index, produs);

            modelProduse.setAll(service.getToateProduseleVandute());
            modelComenzi.setAll(service.getComenziRealizateDeAgent(agentConectat.getId()));
            tabelComenzi.refresh();
            idTabelProduse.refresh();

        });

    }

    public void setComenziController(ComenziController comenziController) {
        this.comenziController = comenziController;
    }

    public void setParents(Parent parentAgent) {


        this.parentComenzi = parentAgent;
    }

    public void goToComenzi(ActionEvent actionEvent) {

        try {

            Stage stage = new Stage();
            stage.setScene(new Scene(parentComenzi));


            //   stage.setTitle("Bibliotecar: " + bibliotecar.getUsername());
            comenziController.setAgentConectat(agentConectat);
            stage.show();

            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

        } catch (Exception e) {
            MessageBox.showErrorMessage(null, e.getMessage());
        }
    }

    public void anuleazaComanda(ActionEvent actionEvent) {


        Comanda comandaSelected = tabelComenzi.getSelectionModel().getSelectedItem();
        if (comandaSelected != null) {
            try {
                service.stergeComanda(comandaSelected.getId());
                MessageBox.showMessage(null, Alert.AlertType.INFORMATION, "Yeey!", "Comanda anulata cu succes!");
                initModel();


            } catch (Exception e) {
                MessageBox.showErrorMessage(null, e.getMessage());
            }
        } else {
            MessageBox.showErrorMessage(null, "NIMIC SELECTAT");
        }
    }

    public void modificaComanda(ActionEvent actionEvent) {
        Comanda comandaSelected = tabelComenzi.getSelectionModel().getSelectedItem();
        int cantitate = Integer.parseInt(idTextFieldCantitate.getText());
        String cantitateS = idTextFieldCantitate.getText();
        String status = idTextFieldStatus.getText();
        String idProdusS = idTextFieldProdus.getText();
        int idProdus = Integer.parseInt(idTextFieldProdus.getText());
        if (comandaSelected != null) {
            try {
               // if(!status.equals("") && idProdusS.equals(" ") && cantitateS.equals(" "))
              //  service.modificaComanda(comandaSelected.getId(),status,comandaSelected.getIdProdus(),comandaSelected.getCantitateProdus());

             //   if(!status.equals(" ") && !idProdusS.equals(" ") && !cantitateS.equals(" "))

                service.modificaComanda(comandaSelected.getId(),status,idProdus,cantitate);

                //ala fostu din comanda
                Produs produs = modelProduse.stream()
                        .filter(x -> x.getId().equals(comandaSelected.getIdProdus()))
                        .findFirst()
                        .get();
                int stoca=produs.getStoc()+comandaSelected.getCantitateProdus();
                produs.setStoc(stoca);
                service.modificaProdus(produs.getId(), stoca, produs.getDenumire(), produs.getPret());


                Produs produs2 = modelProduse.stream()
                        .filter(x -> x.getId().equals(idProdus))
                        .findFirst()
                        .get();
                int stoc2=produs2.getStoc()-cantitate;
                produs2.setStoc(stoc2);
                service.modificaProdus(produs2.getId(), stoc2, produs2.getDenumire(), produs2.getPret());


                MessageBox.showMessage(null, Alert.AlertType.INFORMATION, "Yeey!", "Comanda modificata cu succes!");
                initModel();


            } catch (Exception e) {
                MessageBox.showErrorMessage(null, e.getMessage());
            }
        } else {
            MessageBox.showErrorMessage(null, "NIMIC SELECTAT");
        }

    }
}
