package persistance.jdbc;

import firma.Comanda;
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

    @Override
    public void add(Object elem) throws Exception {

    }


    @Override
    public void deleteVoid(Integer id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Produs produs = session.createQuery("from Produs where id = ?", Produs.class)
                        .setParameter(0, id)
                        .setMaxResults(1)
                        .uniqueResult();
                System.out.println("Se va sterge " + produs);
                session.delete(produs);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void updateC(Produs elem) {

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Produs pUpdated = session.createQuery("from Produs where id = ?", Produs.class)
                        .setParameter(0, elem.getId())
                        .setMaxResults(1)
                        .uniqueResult();

                System.out.println("Inainte de modificare " + pUpdated);


                pUpdated.setStoc(elem.getStoc());
                pUpdated.setDenumire(elem.getDenumire());
                pUpdated.setPret(elem.getPret());


                session.update(pUpdated);

                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }

        }
    }



    @Override
    public Object findById(Object o) {
        return null;
    }

    @Override
    public List findAll() {
        return null;
    }
}
