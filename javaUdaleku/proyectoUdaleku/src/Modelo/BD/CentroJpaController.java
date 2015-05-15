/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.BD;

import Excepciones.exceptions.NonexistentEntityException;
import Excepciones.exceptions.PreexistingEntityException;
import Modelo.UML.Centro;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.UML.Provincia;
import Modelo.UML.Modelo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author 1gprog07
 */
public class CentroJpaController implements Serializable {

    public CentroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Centro centro) throws PreexistingEntityException, Exception {
        if (centro.getModeloCollection() == null) {
            centro.setModeloCollection(new ArrayList<Modelo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Provincia idprovincia = centro.getIdprovincia();
            if (idprovincia != null) {
                idprovincia = em.getReference(idprovincia.getClass(), idprovincia.getIdprovincia());
                centro.setIdprovincia(idprovincia);
            }
            Collection<Modelo> attachedModeloCollection = new ArrayList<Modelo>();
            for (Modelo modeloCollectionModeloToAttach : centro.getModeloCollection()) {
                modeloCollectionModeloToAttach = em.getReference(modeloCollectionModeloToAttach.getClass(), modeloCollectionModeloToAttach.getIdmodelo());
                attachedModeloCollection.add(modeloCollectionModeloToAttach);
            }
            centro.setModeloCollection(attachedModeloCollection);
            em.persist(centro);
            if (idprovincia != null) {
                idprovincia.getCentroCollection().add(centro);
                idprovincia = em.merge(idprovincia);
            }
            for (Modelo modeloCollectionModelo : centro.getModeloCollection()) {
                modeloCollectionModelo.getCentroCollection().add(centro);
                modeloCollectionModelo = em.merge(modeloCollectionModelo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCentro(centro.getIdcentro()) != null) {
                throw new PreexistingEntityException("Centro " + centro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Centro centro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Centro persistentCentro = em.find(Centro.class, centro.getIdcentro());
            Provincia idprovinciaOld = persistentCentro.getIdprovincia();
            Provincia idprovinciaNew = centro.getIdprovincia();
            Collection<Modelo> modeloCollectionOld = persistentCentro.getModeloCollection();
            Collection<Modelo> modeloCollectionNew = centro.getModeloCollection();
            if (idprovinciaNew != null) {
                idprovinciaNew = em.getReference(idprovinciaNew.getClass(), idprovinciaNew.getIdprovincia());
                centro.setIdprovincia(idprovinciaNew);
            }
            Collection<Modelo> attachedModeloCollectionNew = new ArrayList<Modelo>();
            for (Modelo modeloCollectionNewModeloToAttach : modeloCollectionNew) {
                modeloCollectionNewModeloToAttach = em.getReference(modeloCollectionNewModeloToAttach.getClass(), modeloCollectionNewModeloToAttach.getIdmodelo());
                attachedModeloCollectionNew.add(modeloCollectionNewModeloToAttach);
            }
            modeloCollectionNew = attachedModeloCollectionNew;
            centro.setModeloCollection(modeloCollectionNew);
            centro = em.merge(centro);
            if (idprovinciaOld != null && !idprovinciaOld.equals(idprovinciaNew)) {
                idprovinciaOld.getCentroCollection().remove(centro);
                idprovinciaOld = em.merge(idprovinciaOld);
            }
            if (idprovinciaNew != null && !idprovinciaNew.equals(idprovinciaOld)) {
                idprovinciaNew.getCentroCollection().add(centro);
                idprovinciaNew = em.merge(idprovinciaNew);
            }
            for (Modelo modeloCollectionOldModelo : modeloCollectionOld) {
                if (!modeloCollectionNew.contains(modeloCollectionOldModelo)) {
                    modeloCollectionOldModelo.getCentroCollection().remove(centro);
                    modeloCollectionOldModelo = em.merge(modeloCollectionOldModelo);
                }
            }
            for (Modelo modeloCollectionNewModelo : modeloCollectionNew) {
                if (!modeloCollectionOld.contains(modeloCollectionNewModelo)) {
                    modeloCollectionNewModelo.getCentroCollection().add(centro);
                    modeloCollectionNewModelo = em.merge(modeloCollectionNewModelo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = centro.getIdcentro();
                if (findCentro(id) == null) {
                    throw new NonexistentEntityException("The centro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Centro centro;
            try {
                centro = em.getReference(Centro.class, id);
                centro.getIdcentro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The centro with id " + id + " no longer exists.", enfe);
            }
            Provincia idprovincia = centro.getIdprovincia();
            if (idprovincia != null) {
                idprovincia.getCentroCollection().remove(centro);
                idprovincia = em.merge(idprovincia);
            }
            Collection<Modelo> modeloCollection = centro.getModeloCollection();
            for (Modelo modeloCollectionModelo : modeloCollection) {
                modeloCollectionModelo.getCentroCollection().remove(centro);
                modeloCollectionModelo = em.merge(modeloCollectionModelo);
            }
            em.remove(centro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Centro> findCentroEntities() {
        return findCentroEntities(true, -1, -1);
    }

    public List<Centro> findCentroEntities(int maxResults, int firstResult) {
        return findCentroEntities(false, maxResults, firstResult);
    }

    private List<Centro> findCentroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Centro.class));
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

    public Centro findCentro(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Centro.class, id);
        } finally {
            em.close();
        }
    }

    public int getCentroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Centro> rt = cq.from(Centro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
