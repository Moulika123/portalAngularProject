package com.niit.portalbackend.daoImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.portalbackend.BlogComment;
import com.niit.portalbackend.dao.BlogCommentDao;

@Transactional
@Repository("blogCommentDao")
public class BlogCommentDaoImpl implements BlogCommentDao{
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean addBlogComment(BlogComment blogComment) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(blogComment);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteBlogComment(BlogComment blogComment) {
		try {
			sessionFactory.getCurrentSession().delete(blogComment);
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}

	@Override
	public boolean updateBlogComment(BlogComment blogComment) {
		try {
			sessionFactory.getCurrentSession().update(blogComment);
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}

	@Override
	public BlogComment getBlogComment(int commentId) {
		try {
			return sessionFactory.getCurrentSession().get(BlogComment.class, commentId);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@Override
	public List<BlogComment> allBlogComments(int blogId) {
		try {
			return sessionFactory.getCurrentSession().createQuery("from BlogComment where blog_blogId="+blogId, BlogComment.class ).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}

}
