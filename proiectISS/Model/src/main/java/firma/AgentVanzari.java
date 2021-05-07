package firma;

import java.io.Serializable;
import java.util.Objects;

public class AgentVanzari  implements Entity<Integer>, Serializable {

    //am scos prenumele
    private String nume;
    private String username;
    private String parola;
    private int id;
    public AgentVanzari() {

    }
    public AgentVanzari(String nume, String username, String parola) {
        this.nume = nume;
        this.username = username;
        this.parola = parola;
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgentVanzari that = (AgentVanzari) o;
        return id == that.id &&
                Objects.equals(nume, that.nume) &&
                Objects.equals(username, that.username) &&
                Objects.equals(parola, that.parola);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, username, parola, id);
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
    public String toString() {
        return "AgentVanzari{" +
                "nume='" + nume + '\'' +
                ", username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                ", id=" + id +
                '}';
    }
}
