package firma;

import java.io.Serializable;
import java.util.Objects;

public class Produs  implements Entity<Integer>, Serializable {

    private String denumire;
    private Float pret;
    private Integer stoc;
    private int id;
    public Produs() {

    }
    public Produs(String denumire, Float pret, Integer stoc) {
        this.denumire = denumire;
        this.pret = pret;
        this.stoc = stoc;

    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Float getPret() {
        return pret;
    }

    public void setPret(Float pret) {
        this.pret = pret;
    }

    public Integer getStoc() {
        return stoc;
    }

    public void setStoc(Integer stoc) {
        this.stoc = stoc;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produs produs = (Produs) o;
        return id == produs.id &&
                Objects.equals(denumire, produs.denumire) &&
                Objects.equals(pret, produs.pret) &&
                Objects.equals(stoc, produs.stoc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(denumire, pret, stoc, id);
    }

    @Override
    public String toString() {
        return "Produs{" +
                "denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", stoc=" + stoc +
                ", id=" + id +
                '}';
    }
}
