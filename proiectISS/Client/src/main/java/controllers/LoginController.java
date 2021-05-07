package controllers;

import firma.AgentVanzari;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.IServices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginController extends UnicastRemoteObject {

    private AgentController agentController;
    private Parent parentAgent;
    private IServices service;

    @FXML
    TextField idUsername;
    @FXML
    PasswordField idParola;
    @FXML
    Button idLogin;


    public LoginController() throws RemoteException {
    }
    @FXML
    public void initialize() {

    }
    public void setContext(IServices service) throws RemoteException {
        this.service = service;
    }

    public void setControllerAgent(AgentController agentcontroller1){
        this.agentController =  agentcontroller1;
    }
    public void setParents(Parent parentAgent){


        this.parentAgent = parentAgent;
    }
    public void handleLogin(ActionEvent actionEvent) {
        String username = idUsername.getText();
        String parola = idParola.getText();


            try{
                AgentVanzari agentVanzari=new AgentVanzari("",username,parola);
                service.login(agentVanzari, agentController);

                Stage stage = new Stage();
                stage.setScene(new Scene(parentAgent));


                stage.setOnCloseRequest(event -> {
                    agentController.logout();
                    System.exit(0);
                });
               agentController.setAgentConectat(agentVanzari);

                stage.show();

                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

            } catch (Exception e) {
                MessageBox.showErrorMessage(null,e.getMessage());
            }



    }
}
