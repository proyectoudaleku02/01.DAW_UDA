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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sergio
 */
@Entity
@Table(name = "LOCALIDADES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Localidades.findAll", query = "SELECT l FROM Localidades l"),
    @NamedQuery(name = "Localidades.findByIdlocalidad", query = "SELECT l FROM Localidades l WHERE l.idlocalidad = :idlocalidad"),
    @NamedQuery(name = "Localidades.findByNombreloc", query = "SELECT l FROM Localidades l WHERE l.nombreloc = :nombreloc")})
public class Localidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDLOCALIDAD")
    private Long idlocalidad;
    @Basic(optional = false)
    @Column(name = "NOMBRELOC")
    private String nombreloc;
    @JoinColumn(name = "IDMUNICIPIO", referencedColumnName = "IDMUNICIPIO")
    @ManyToOne
    private Municipio idmunicipio;
    @OneToMany(mappedBy = "idlocalidad")
    private Collection<Via> viasCollection;

    public Localidad() {
    }

    public Localidad(Long idlocalidad) {
        this.idlocalidad = idlocalidad;
    }

    public Localidad(Long idlocalidad, String nombreloc) {
        this.idlocalidad = idlocalidad;
        this.nombreloc = nombreloc;
    }

    public Long getIdlocalidad() {
        return idlocalidad;
    }

    public void setIdlocalidad(Long idlocalidad) {
        this.idlocalidad = idlocalidad;
    }

    public String getNombreloc() {
        return nombreloc;
    }

    public void setNombreloc(String nombreloc) {
        this.nombreloc = nombreloc;
    }

    public Municipio getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(Municipio idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    @XmlTransient
    public Collection<Via> getViasCollection() {
        return viasCollection;
    }

    public void setViasCollection(Collection<Via> viasCollection) {
        this.viasCollection = viasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlocalidad != null ? idlocalidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Localidad)) {
            return false;
        }
        Localidad other = (Localidad) object;
        if ((this.idlocalidad == null && other.idlocalidad != null) || (this.idlocalidad != null && !this.idlocalidad.equals(other.idlocalidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.UML.Localidades[ idlocalidad=" + idlocalidad + " ]";
    }
    
}
