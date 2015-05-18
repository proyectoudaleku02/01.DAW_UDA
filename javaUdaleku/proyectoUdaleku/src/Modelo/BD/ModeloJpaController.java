/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.BD;

import Modelo.BD.exceptions.NonexistentEntityException;
import Modelo.BD.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.UML.Centro;
import Modelo.UML.Modelo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author 1glm02
 */
public class ModeloJpaController implements Serializable {

    public ModeloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Modelo modelo) throws PreexistingEntityException, Exception {
        if (modelo.getCentroCollection() == null) {
            modelo.setCentroCollection(new ArrayList<Centro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Centro> attachedCentroCollection = new ArrayList<Centro>();
            for (Centro centroCollectionCentroToAttach : modelo.getCentroCollection()) {
                centroCollectionCentroToAttach = em.getReference(centroCollectionCentroToAttach.getClass(), centroCollectionCentroToAttach.getIdcentro());
                attachedCentroCollection.add(centroCollectionCentroToAttach);
            }
            modelo.setCentroCollection(attachedCentroCollection);
            em.persist(modelo);
            for (Centro centroCollectionCentro : modelo.getCentroCollection()) {
                centroCollectionCentro.getModeloCollection().add(modelo);
                centroCollectionCentro = em.merge(centroCollectionCentro);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findModelo(modelo.getIdmodelo()) != null) {
                throw new PreexistingEntityException("Modelo " + modelo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Modelo modelo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modelo persistentModelo = em.find(Modelo.class, modelo.getIdmodelo());
            Collection<Centro> centroCollectionOld = persistentModelo.getCentroCollection();
            Collection<Centro> centroCollectionNew = modelo.getCentroCollection();
            Collection<Centro> attachedCentroCollectionNew = new ArrayList<Centro>();
            for (Centro centroCollectionNewCentroToAttach : centroCollectionNew) {
                centroCollectionNewCentroToAttach = em.getReference(centroCollectionNewCentroToAttach.getClass(), centroCollectionNewCentroToAttach.getIdcentro());
                attachedCentroCollectionNew.add(centroCollectionNewCentroToAttach);
            }
            centroCollectionNew = attachedCentroCollectionNew;
            modelo.setCentroCollection(centroCollectionNew);
            modelo = em.merge(modelo);
            for (Centro centroCollectionOldCentro : centroCollectionOld) {
                if (!centroCollectionNew.contains(centroCollectionOldCentro)) {
                    centroCollectionOldCentro.getModeloCollection().remove(modelo);
                    centroCollectionOldCentro = em.merge(centroCollectionOldCentro);
                }
            }
            for (Centro centroCollectionNewCentro : centroCollectionNew) {
                if (!centroCollectionOld.contains(centroCollectionNewCentro)) {
                    centroCollectionNewCentro.getModeloCollection().add(modelo);
                    centroCollectionNewCentro = em.merge(centroCollectionNewCentro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = modelo.getIdmodelo();
                if (findModelo(id) == null) {
                    throw new NonexistentEntityException("The modelo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modelo modelo;
            try {
                modelo = em.getReference(Modelo.class, id);
                modelo.getIdmodelo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modelo with id " + id + " no longer exists.", enfe);
            }
            Collection<Centro> centroCollection = modelo.getCentroCollection();
            for (Centro centroCollectionCentro : centroCollection) {
                centroCollectionCentro.getModeloCollection().remove(modelo);
                centroCollectionCentro = em.merge(centroCollectionCentro);
            }
            em.remove(modelo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Modelo> findModeloEntities() {
        return findModeloEntities(true, -1, -1);
    }

    public List<Modelo> findModeloEntities(int maxResults, int firstResult) {
        return findModeloEntities(false, maxResults, firstResult);
    }

    private List<Modelo> findModeloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Modelo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Modelo findModelo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Modelo.class, id);
        } finally {
            em.close();
        }
    }

    public int getModeloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Modelo> rt = cq.from(Modelo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
