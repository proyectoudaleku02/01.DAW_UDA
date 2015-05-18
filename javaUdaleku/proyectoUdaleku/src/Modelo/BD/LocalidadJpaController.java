/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.BD;

import Modelo.BD.exceptions.NonexistentEntityException;
import Modelo.BD.exceptions.PreexistingEntityException;
import Modelo.UML.Localidad;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.UML.Municipio;
import Modelo.UML.Via;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author 1glm02
 */
public class LocalidadJpaController implements Serializable {

    public LocalidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Localidad localidad) throws PreexistingEntityException, Exception {
        if (localidad.getViaCollection() == null) {
            localidad.setViaCollection(new ArrayList<Via>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipio idmunicipio = localidad.getIdmunicipio();
            if (idmunicipio != null) {
                idmunicipio = em.getReference(idmunicipio.getClass(), idmunicipio.getIdmunicipio());
                localidad.setIdmunicipio(idmunicipio);
            }
            Collection<Via> attachedViaCollection = new ArrayList<Via>();
            for (Via viaCollectionViaToAttach : localidad.getViaCollection()) {
                viaCollectionViaToAttach = em.getReference(viaCollectionViaToAttach.getClass(), viaCollectionViaToAttach.getIdvia());
                attachedViaCollection.add(viaCollectionViaToAttach);
            }
            localidad.setViaCollection(attachedViaCollection);
            em.persist(localidad);
            if (idmunicipio != null) {
                idmunicipio.getLocalidadCollection().add(localidad);
                idmunicipio = em.merge(idmunicipio);
            }
            for (Via viaCollectionVia : localidad.getViaCollection()) {
                Localidad oldIdlocalidadOfViaCollectionVia = viaCollectionVia.getIdlocalidad();
                viaCollectionVia.setIdlocalidad(localidad);
                viaCollectionVia = em.merge(viaCollectionVia);
                if (oldIdlocalidadOfViaCollectionVia != null) {
                    oldIdlocalidadOfViaCollectionVia.getViaCollection().remove(viaCollectionVia);
                    oldIdlocalidadOfViaCollectionVia = em.merge(oldIdlocalidadOfViaCollectionVia);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLocalidad(localidad.getIdlocalidad()) != null) {
                throw new PreexistingEntityException("Localidad " + localidad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Localidad localidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Localidad persistentLocalidad = em.find(Localidad.class, localidad.getIdlocalidad());
            Municipio idmunicipioOld = persistentLocalidad.getIdmunicipio();
            Municipio idmunicipioNew = localidad.getIdmunicipio();
            Collection<Via> viaCollectionOld = persistentLocalidad.getViaCollection();
            Collection<Via> viaCollectionNew = localidad.getViaCollection();
            if (idmunicipioNew != null) {
                idmunicipioNew = em.getReference(idmunicipioNew.getClass(), idmunicipioNew.getIdmunicipio());
                localidad.setIdmunicipio(idmunicipioNew);
            }
            Collection<Via> attachedViaCollectionNew = new ArrayList<Via>();
            for (Via viaCollectionNewViaToAttach : viaCollectionNew) {
                viaCollectionNewViaToAttach = em.getReference(viaCollectionNewViaToAttach.getClass(), viaCollectionNewViaToAttach.getIdvia());
                attachedViaCollectionNew.add(viaCollectionNewViaToAttach);
            }
            viaCollectionNew = attachedViaCollectionNew;
            localidad.setViaCollection(viaCollectionNew);
            localidad = em.merge(localidad);
            if (idmunicipioOld != null && !idmunicipioOld.equals(idmunicipioNew)) {
                idmunicipioOld.getLocalidadCollection().remove(localidad);
                idmunicipioOld = em.merge(idmunicipioOld);
            }
            if (idmunicipioNew != null && !idmunicipioNew.equals(idmunicipioOld)) {
                idmunicipioNew.getLocalidadCollection().add(localidad);
                idmunicipioNew = em.merge(idmunicipioNew);
            }
            for (Via viaCollectionOldVia : viaCollectionOld) {
                if (!viaCollectionNew.contains(viaCollectionOldVia)) {
                    viaCollectionOldVia.setIdlocalidad(null);
                    viaCollectionOldVia = em.merge(viaCollectionOldVia);
                }
            }
            for (Via viaCollectionNewVia : viaCollectionNew) {
                if (!viaCollectionOld.contains(viaCollectionNewVia)) {
                    Localidad oldIdlocalidadOfViaCollectionNewVia = viaCollectionNewVia.getIdlocalidad();
                    viaCollectionNewVia.setIdlocalidad(localidad);
                    viaCollectionNewVia = em.merge(viaCollectionNewVia);
                    if (oldIdlocalidadOfViaCollectionNewVia != null && !oldIdlocalidadOfViaCollectionNewVia.equals(localidad)) {
                        oldIdlocalidadOfViaCollectionNewVia.getViaCollection().remove(viaCollectionNewVia);
                        oldIdlocalidadOfViaCollectionNewVia = em.merge(oldIdlocalidadOfViaCollectionNewVia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = localidad.getIdlocalidad();
                if (findLocalidad(id) == null) {
                    throw new NonexistentEntityException("The localidad with id " + id + " no longer exists.");
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
            Localidad localidad;
            try {
                localidad = em.getReference(Localidad.class, id);
                localidad.getIdlocalidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The localidad with id " + id + " no longer exists.", enfe);
            }
            Municipio idmunicipio = localidad.getIdmunicipio();
            if (idmunicipio != null) {
                idmunicipio.getLocalidadCollection().remove(localidad);
                idmunicipio = em.merge(idmunicipio);
            }
            Collection<Via> viaCollection = localidad.getViaCollection();
            for (Via viaCollectionVia : viaCollection) {
                viaCollectionVia.setIdlocalidad(null);
                viaCollectionVia = em.merge(viaCollectionVia);
            }
            em.remove(localidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Localidad> findLocalidadEntities() {
        return findLocalidadEntities(true, -1, -1);
    }

    public List<Localidad> findLocalidadEntities(int maxResults, int firstResult) {
        return findLocalidadEntities(false, maxResults, firstResult);
    }

    private List<Localidad> findLocalidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Localidad.class));
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

    public Localidad findLocalidad(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Localidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getLocalidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Localidad> rt = cq.from(Localidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
