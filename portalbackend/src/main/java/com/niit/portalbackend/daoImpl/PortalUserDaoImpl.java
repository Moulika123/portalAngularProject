package com.niit.portalbackend.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.portalbackend.portal_user;
import com.niit.portalbackend.dao.PortalUserDao;

@Repository("portalUserDao")
@Transactional
public class PortalUserDaoImpl implements PortalUserDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean addPortalUser(portal_user user) {

		try {
			sessionFactory.getCurrentSession().persist(user);
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	@Override
	public boolean deletePortalUser(portal_user user) {

		try {
			sessionFactory.getCurrentSession().delete(user);
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	@Override
	public boolean updatePortalUser(portal_user user) {

		try {
			sessionFactory.getCurrentSession().update(user);
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	@Override
	public portal_user getUserByUsername(String username) {
		try {
			return sessionFactory.getCurrentSession().get(portal_user.class, username);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public List<portal_user> getAllUsers() {

		try {
			return sessionFactory.getCurrentSession().createQuery("from portal_user", portal_user.class)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public portal_user getCredentials(portal_user user) {

		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from portal_user where username =? and password = ?");
			query.setParameter(0, user.getUsername());
			query.setParameter(1, user.getPassword());
			portal_user validUser = (portal_user) query.getSingleResult();
			return validUser;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
