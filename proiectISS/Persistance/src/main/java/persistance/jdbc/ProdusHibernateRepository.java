package persistance.jdbc;

import firma.Produs;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistance.ProdusRepository;

import java.util.List;

public class ProdusHibernateRepository implements ProdusRepository {
    static SessionFactory sessionFactory;

    public ProdusHibernateRepository(){
        sessionFactory = HibernateUtility.getSessionFactory();
        System.out.println("ProdusHibernateRepo" + sessionFactory);
    }

    @Override
    public List<Produs> getToateProduseleVandute() {
        List<Produs> result = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                result = session.createQuery("from Produs", Produs.class).list();

                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if(transaction !=null)
                    transaction.rollback();
            }
        }
        return result;
    }
}
