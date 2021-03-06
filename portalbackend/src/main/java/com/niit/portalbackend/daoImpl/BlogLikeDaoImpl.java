package com.niit.portalbackend.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.portalbackend.BlogLike;
import com.niit.portalbackend.dao.BlogLikeDao;

@Transactional
@Repository("blogLikeDao")
public class BlogLikeDaoImpl implements BlogLikeDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public boolean addBlogLike(BlogLike blogLike) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(blogLike);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteBlogLike(BlogLike blogLike) {
		try {
			sessionFactory.getCurrentSession().delete(blogLike);
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}

	@Override
	public BlogLike getBlogLikeById(int id) {
		try {
			return sessionFactory.getCurrentSession().get(BlogLike.class, id);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@Override
	public List<BlogLike> blogLikes(int id) {
		try {
			return sessionFactory.getCurrentSession().createQuery("from BlogLike where id="+id, BlogLike.class ).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public BlogLike getBlogLike(int id, String username) {
		try
		{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from BlogLike where blogId =? and username = ?");
			query.setParameter(0, id);
			query.setParameter(1, username);
			BlogLike blogLike = (BlogLike)query.getSingleResult();
			return blogLike;
		}
		catch(Exception e) {
			return null;
		}
		
	}
	
	

}
