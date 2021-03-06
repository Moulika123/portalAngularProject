package com.niit.portalmiddleware.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.niit.portalbackend.Blog;
import com.niit.portalbackend.BlogComment;
import com.niit.portalbackend.BlogLike;
import com.niit.portalbackend.dao.BlogCommentDao;
import com.niit.portalbackend.dao.BlogDao;
import com.niit.portalbackend.dao.BlogLikeDao;

@RestController
public class BlogController {
	
	@Autowired
	BlogDao blogDao;
	
	@Autowired
	BlogCommentDao blogCommentDao;
	
	@Autowired
	BlogLikeDao blogLikeDao;
	
	
	@PostMapping("/addblog")
	public ResponseEntity<?> addblog(@RequestBody Blog blog)
	{
		try {
			
			blog.setBlogDate(new Date());
			
			blogDao.addBlog(blog);
		}catch(Exception e)
		{
			return new ResponseEntity<String>("Error in adding  blog", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	
	@GetMapping("/blogslist")
	public ResponseEntity<?> blogslist(){
		try {
			System.out.println("entered into middleware");
			List<Blog> blogs = blogDao.getAllBlogs();
			
			return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
		}catch(Exception e)
		{
			return new ResponseEntity<String>("error in getting blogs", HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/blogDescription")
	public ResponseEntity<?> blogDescription(@RequestParam("blogId") int blogId){
		try {
			Blog blog = blogDao.getblogById(blogId);
			
			return new ResponseEntity<Blog>(blog,HttpStatus.OK);
		}catch(Exception e)
		{
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
		}
	
		
	}
	
	@PostMapping("/approval")
	public ResponseEntity<?> approval(@RequestBody Blog blog)
	{
		Blog existingBlog = blogDao.getblogById(blog.getBlogId());
		System.out.println(blog.isApproved());
		try {

			existingBlog.setApproved(blog.isApproved());
			existingBlog.setRemarks(blog.getRemarks());
			blogDao.updateBlog(existingBlog);
			
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}catch(Exception e)
		{
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/addBlogComment")
	public ResponseEntity<?> addBlogComment(@RequestBody BlogComment blogComment)
	{
		System.out.println(blogComment.getBlog());
		blogComment.setCommentedDate(new Date());
		try {
			blogCommentDao.addBlogComment(blogComment);
			List<BlogComment> blogComments=blogDao.getblogById(blogComment.getBlog().getBlogId()).getBlogComments();
			return new ResponseEntity<List<BlogComment>>(blogComments, HttpStatus.OK);	
		}catch(Exception e)
		{
			return new ResponseEntity<Boolean>(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/blogComments")
	public ResponseEntity<?> blogComments(@RequestParam("blogId") int id)
	{
		System.out.println("entered to blogComments");
		try {
			List<BlogComment> blogComments = blogCommentDao.allBlogComments(id);
			System.out.println(blogComments);
			return new ResponseEntity<List<BlogComment>>(blogComments, HttpStatus.OK);
		}catch(Exception e)
		{
			return new ResponseEntity<Boolean>(false,HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/likeBlog")
	public ResponseEntity<?> likeBlog(@RequestBody BlogLike blogLike)
	{
		try {
			Blog blog = blogDao.getblogById(blogLike.getBlogId());
			blog.setLikes(blog.getLikes()+1);
			blogDao.updateBlog(blog);
			blogLike.setBlogId(blogLike.getBlogId());
			blogLike.setUsername(blogLike.getUsername());
			blogLikeDao.addBlogLike(blogLike);
			List<BlogLike> blogLikes = blogLikeDao.blogLikes(blogLike.getBlogId());
			return new ResponseEntity<List<BlogLike>>(blogLikes, HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<Boolean>(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/likeStatus")
	public ResponseEntity<?> likeStatus(@RequestParam("blogId") int id)
	{
		try {
			BlogLike blogLike = blogLikeDao.getBlogLikeById(id);
				return new ResponseEntity<BlogLike>(blogLike, HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<Boolean>(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/unlikeBlog")
	public ResponseEntity<?> unlikeBlog(@RequestBody BlogLike blogLike)
	{
		try {
			Blog blog = blogDao.getblogById(blogLike.getBlogId());
			blog.setLikes(blog.getLikes()-1);
			blogDao.updateBlog(blog);
			BlogLike blogLiked = blogLikeDao.getBlogLike(blogLike.getBlogId(), blogLike.getUsername());
			if(blogLike!=null)
			{
				blogLikeDao.deleteBlogLike(blogLiked);
			}
			
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<Boolean>(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
