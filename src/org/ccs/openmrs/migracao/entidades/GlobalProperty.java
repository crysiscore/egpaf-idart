/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author colaco
 */
@Entity
@Table(name = "global_property", catalog = "openmrs", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GlobalProperty.findAll", query = "SELECT g FROM GlobalProperty g")
    , @NamedQuery(name = "GlobalProperty.findByProperty", query = "SELECT g FROM GlobalProperty g WHERE g.property = :property")
    , @NamedQuery(name = "GlobalProperty.findByUuid", query = "SELECT g FROM GlobalProperty g WHERE g.uuid = :uuid")
    , @NamedQuery(name = "GlobalProperty.findByDatatype", query = "SELECT g FROM GlobalProperty g WHERE g.datatype = :datatype")
    , @NamedQuery(name = "GlobalProperty.findByPreferredHandler", query = "SELECT g FROM GlobalProperty g WHERE g.preferredHandler = :preferredHandler")})
public class GlobalProperty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private String property;
    @Lob
    @Column(name = "property_value")
    private String propertyValue;
    @Lob
    private String description;
    @Basic(optional = false)
    private String uuid;
    private String datatype;
    @Lob
    @Column(name = "datatype_config")
    private String datatypeConfig;
    @Column(name = "preferred_handler")
    private String preferredHandler;
    @Lob
    @Column(name = "handler_config")
    private String handlerConfig;

    public GlobalProperty() {
    }

    public GlobalProperty(String property) {
        this.property = property;
    }

    public GlobalProperty(String property, String uuid) {
        this.property = property;
        this.uuid = uuid;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getDatatypeConfig() {
        return datatypeConfig;
    }

    public void setDatatypeConfig(String datatypeConfig) {
        this.datatypeConfig = datatypeConfig;
    }

    public String getPreferredHandler() {
        return preferredHandler;
    }

    public void setPreferredHandler(String preferredHandler) {
        this.preferredHandler = preferredHandler;
    }

    public String getHandlerConfig() {
        return handlerConfig;
    }

    public void setHandlerConfig(String handlerConfig) {
        this.handlerConfig = handlerConfig;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (property != null ? property.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GlobalProperty)) {
            return false;
        }
        GlobalProperty other = (GlobalProperty) object;
        if ((this.property == null && other.property != null) || (this.property != null && !this.property.equals(other.property))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ccs.openmrs.migracao.entidades.GlobalProperty[ property=" + property + " ]";
    }
    
}
