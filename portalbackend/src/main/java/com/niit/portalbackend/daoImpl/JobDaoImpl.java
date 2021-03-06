package com.niit.portalbackend.daoImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.portalbackend.Job;
import com.niit.portalbackend.dao.JobDao;

@Transactional
@Repository("jobDao")
public class JobDaoImpl implements JobDao{
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean addJob(Job job) {
		try {
			sessionFactory.getCurrentSession().save(job);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	@Override
	public boolean deleteJob(Job job) {
		try {
			sessionFactory.getCurrentSession().delete(job);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	@Override
	public boolean updateJob(Job job) {
		try {
			sessionFactory.getCurrentSession().update(job);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	@Override
	public Job getJobById(int id) {
		try {
			return sessionFactory.getCurrentSession().get(Job.class, id);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@Override
	public List<Job> allJobs() {
		try {
			return sessionFactory.getCurrentSession().createQuery("from Job", Job.class).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	
}
