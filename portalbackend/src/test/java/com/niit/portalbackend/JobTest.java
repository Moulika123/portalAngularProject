package com.niit.portalbackend;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.portalbackend.dao.JobDao;



public class JobTest {
	AnnotationConfigApplicationContext context;
	Job job;
	JobDao jobDao;
	
	@Before
	public void init()
	{
		
		context =  new AnnotationConfigApplicationContext();;
		context.scan("com.niit");
		context.refresh();
		jobDao = (JobDao) context.getBean("jobDao");
		job = new Job();
	}
	
	@Test
	public void addTest()
	{
		job.setJobTitle("Java Developer");
		job.setJobDescription("Core Java and Advanced Java, HTML,CSS knowledge is required");
		job.setCompanyName("Infosys");
		job.setSalary(20000);
		job.setExperience("Fresher/Experienced");
		job.setLocation("Hyderabad");
		job.setPostedOn(new Date());
		job.setPostedBy("chandu");
		
		Assert.assertEquals("Added", true, jobDao.addJob(job));
	}

}
