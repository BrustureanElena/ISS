package persistance.jdbc;

import firma.Comanda;
import firma.Produs;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistance.ComandaRepository;

import java.util.List;

public class ComandaHibernateRepository implements ComandaRepository {

    static SessionFactory sessionFactory;

    public ComandaHibernateRepository() {
        sessionFactory = HibernateUtility.getSessionFactory();
    }


    @Override
    public void add(Comanda elem) throws Exception {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                session.save(elem);
                System.out.println("ComandaHibernateRepo save" + elem);
                tx.commit();
            }catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }

    }



    @Override
    public void deleteVoid(Integer id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Comanda cSTERS = session.createQuery("from Comanda where id = ?", Comanda.class)
                        .setParameter(0, id)
                        .setMaxResults(1)
                        .uniqueResult();

                session.delete(cSTERS);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }

    }


    @Override
    public void updateC(Produs elem) {

    }
/*
    @Override
    public void updateComanda(Comanda elem) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Comanda cUpdated = session.createQuery("from Comanda where id = ?", Comanda.class)
                        .setParameter(0, elem.getId())
                        .setMaxResults(1)
                        .uniqueResult();


                cUpdated.setStatus(elem.getStatus());
                cUpdated.setNumeClient(elem.getNumeClient());
                cUpdated.setIdAgent(elem.getIdAgent());
                cUpdated.setDataPunereComanda(elem.getDataPunereComanda());
                cUpdated.setCantitateProdus(elem.getCantitateProdus());




                session.update(cUpdated);

                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }
*/
    @Override
    public Comanda findById(Integer id) {
        Comanda result = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                result = session.createQuery("from Comanda  where id = ? ", Comanda .class)
                        .setParameter(0,id).uniqueResult();

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
    public List<Comanda> findAll() {
        List<Comanda> result = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                result = session.createQuery("from Comanda",Comanda.class).list();

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
    public List<Comanda> getComenziRealizateDeAgent(int idAgent) {
        List<Comanda> result = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                result = session.createQuery("from Comanda where idAgent = ?", Comanda.class)
                        .setParameter(0,idAgent).list();

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
