package persistance.jdbc;

import firma.AgentVanzari;
import firma.Comanda;
import firma.Produs;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistance.AgentRepository;

import java.util.List;

public class AgentHibernateRepository implements AgentRepository {
    static SessionFactory sessionFactory;

    public AgentHibernateRepository(){
        sessionFactory = HibernateUtility.getSessionFactory();
        System.out.println("AbonatHibernateRepo" + sessionFactory);
    }


    @Override
    public AgentVanzari findAgentByUsernameParola(String username, String parola) {
        AgentVanzari result = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                result = session.createQuery("from AgentVanzari where username = ? and parola = ?", AgentVanzari.class)
                        .setParameter(0,username).setParameter(1,parola).uniqueResult();

                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if(transaction !=null)
                    transaction.rollback();
            }
        }
        return result;
    }
    @Override
    public void add(AgentVanzari elem) throws Exception {

    }


    @Override
    public void deleteVoid(Integer id) {

    }


    @Override
    public void updateC(Produs elem) {

    }



    @Override
    public AgentVanzari findById(Integer integer) {
        return null;
    }

    @Override
    public List<AgentVanzari> findAll() {
        return null;
    }
}
