package services;

import firma.Comanda;
import firma.Produs;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {
    void comandaAdded(Comanda comanda) throws MyException, RemoteException;



    void comandaUpdated()throws RemoteException;

    void comandaDeleted(Comanda comanda)throws RemoteException;
}
