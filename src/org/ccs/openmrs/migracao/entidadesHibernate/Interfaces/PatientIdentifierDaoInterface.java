/*
 * Decompiled with CFR 0_114.
 */
package org.ccs.openmrs.migracao.entidadesHibernate.Interfaces;

import java.io.Serializable;
import java.util.List;

public interface PatientIdentifierDaoInterface<T, Id extends Serializable> {
    public void persist(T var1);

    public void update(T var1);

    public T findById(Id var1);
    
    public List<T> findByAllIdentifierLike(Id var1);

    public T findByPatientId(Id var1);

     public  List<T> findByNidAndNameAndSurname(Id var1,Id var2,Id var3);  
     
    public  List<T> findAllByNidLikeAndNameLikeAndSurnameLike(Id var1,Id var2,Id var3);         
            
    public void delete(T var1);

    public List<T> findAll();

    public void deleteAll();
}

