/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.UML;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sergio
 */
@Entity
@Table(name = "CENTROS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Centros.findAll", query = "SELECT c FROM Centros c"),
    @NamedQuery(name = "Centros.findByIdcentro", query = "SELECT c FROM Centros c WHERE c.idcentro = :idcentro"),
    @NamedQuery(name = "Centros.findByNombrecent", query = "SELECT c FROM Centros c WHERE c.nombrecent = :nombrecent")})
public class Centro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCENTRO")
    private Long idcentro;
    @Basic(optional = false)
    @Column(name = "NOMBRECENT")
    private String nombrecent;
    @JoinTable(name = "CENTROS_HAS_MODELOS", joinColumns = {
        @JoinColumn(name = "IDCENTRO", referencedColumnName = "IDCENTRO")}, inverseJoinColumns = {
        @JoinColumn(name = "IDMODELO", referencedColumnName = "IDMODELO")})
    @ManyToMany
    private Collection<Modelo> modelosCollection;
    @JoinColumn(name = "IDPROVINCIA", referencedColumnName = "IDPROVINCIA")
    @ManyToOne
    private Provincia idprovincia;

    public Centro() {
    }

    public Centro(Long idcentro) {
        this.idcentro = idcentro;
    }

    public Centro(Long idcentro, String nombrecent) {
        this.idcentro = idcentro;
        this.nombrecent = nombrecent;
    }

    public Long getIdcentro() {
        return idcentro;
    }

    public void setIdcentro(Long idcentro) {
        this.idcentro = idcentro;
    }

    public String getNombrecent() {
        return nombrecent;
    }

    public void setNombrecent(String nombrecent) {
        this.nombrecent = nombrecent;
    }

    @XmlTransient
    public Collection<Modelo> getModelosCollection() {
        return modelosCollection;
    }

    public void setModelosCollection(Collection<Modelo> modelosCollection) {
        this.modelosCollection = modelosCollection;
    }

    public Provincia getIdprovincia() {
        return idprovincia;
    }

    public void setIdprovincia(Provincia idprovincia) {
        this.idprovincia = idprovincia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcentro != null ? idcentro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Centro)) {
            return false;
        }
        Centro other = (Centro) object;
        if ((this.idcentro == null && other.idcentro != null) || (this.idcentro != null && !this.idcentro.equals(other.idcentro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.UML.Centros[ idcentro=" + idcentro + " ]";
    }
    
}
