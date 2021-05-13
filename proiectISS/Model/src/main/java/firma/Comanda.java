package firma;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Comanda implements Entity<Integer>, Serializable {

    private int id;
    private String numeClient;
    private Date dataPunereComanda;
    private String status;
    private int idProdus;
    private int idAgent;
    private int cantitateProdus;

    public Comanda(){}

    public Comanda(String numeClient, Date dataPunereComanda, String status, int idProdus, int cantitateProdus,int idAbonat) {
        this.numeClient = numeClient;
        this.dataPunereComanda = dataPunereComanda;
        this.status = status;
        this.idProdus = idProdus;
        this.cantitateProdus = cantitateProdus;
        this.idAgent=idAbonat;
    }

    public int getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(int idAgent) {
        this.idAgent = idAgent;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public Date getDataPunereComanda() {
        return dataPunereComanda;
    }

    public void setDataPunereComanda(Date dataPunereComanda) {
        this.dataPunereComanda = dataPunereComanda;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public int getCantitateProdus() {
        return cantitateProdus;
    }

    public void setCantitateProdus(int cantitateProdus) {
        this.cantitateProdus = cantitateProdus;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        this.id=integer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comanda comanda = (Comanda) o;
        return id == comanda.id &&
                idProdus == comanda.idProdus &&
                idAgent== comanda.idAgent &&
                cantitateProdus == comanda.cantitateProdus &&
                Objects.equals(numeClient, comanda.numeClient) &&
                Objects.equals(dataPunereComanda, comanda.dataPunereComanda) &&
                Objects.equals(status, comanda.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeClient, dataPunereComanda, status, idProdus, idAgent, cantitateProdus);
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "id=" + id +
                ", numeClient='" + numeClient + '\'' +
                ", dataPunereComanda=" + dataPunereComanda +
                ", status='" + status + '\'' +
                ", idProdus=" + idProdus +
                ", idAbonat=" + idAgent +
                ", cantitateProdus=" + cantitateProdus +
                '}';
    }
}
