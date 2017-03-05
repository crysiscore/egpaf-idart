/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.entidadesHibernate.servicos;

import java.util.List;
import org.ccs.openmrs.migracao.entidades.Users;
import org.ccs.openmrs.migracao.entidadesHibernate.dao.UsersDao;
import org.hibernate.Session;

public class UsersService {
    private static UsersDao usersDao;

    public UsersService() {
        usersDao = new UsersDao();
    }

    public void persist(Users entity) {
        usersDao.openCurrentSessionwithTransaction();
        usersDao.persist(entity);
        usersDao.closeCurrentSessionwithTransaction();
    }

    public void update(Users entity) {
        usersDao.openCurrentSessionwithTransaction();
        usersDao.update(entity);
        usersDao.closeCurrentSessionwithTransaction();
    }

    public Users findById(String id) {
        usersDao.openCurrentSession();
        Users obs = usersDao.findById(id);
        usersDao.closeCurrentSession();
        return obs;
    }

    public void delete(String id) {
        usersDao.openCurrentSessionwithTransaction();
        Users obs = usersDao.findById(id);
        usersDao.delete(obs);
        usersDao.closeCurrentSessionwithTransaction();
    }

    public List<Users> findAll() {
        usersDao.openCurrentSession();
        List<Users> obss = usersDao.findAll();
        usersDao.closeCurrentSession();
        return obss;
    }

    public void deleteAll() {
        usersDao.openCurrentSessionwithTransaction();
        usersDao.deleteAll();
        usersDao.closeCurrentSessionwithTransaction();
    }

    public UsersDao usersDao() {
        return usersDao;
    }
}

