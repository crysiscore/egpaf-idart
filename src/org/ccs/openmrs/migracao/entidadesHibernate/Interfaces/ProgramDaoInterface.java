/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidadesHibernate.Interfaces;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ColacoVM
 */
public interface ProgramDaoInterface <T, Id extends Serializable> {
    public void persist(T var1);

    public void update(T var1);

    public T findById(Id var1);

    public void delete(T var1);

    public List<T> findAll();

    public void deleteAll();
}
