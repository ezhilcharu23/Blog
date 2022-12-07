package com.example.demo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.Blog;
import com.example.demo.Category;
import com.example.demo.Comment;
import com.example.demo.FileDB;
import com.example.demo.Login;
import com.example.demo.User;
import com.example.demo.Exceptions.ApplicationException;

@Repository
public class AppDao {
	//commit

	@Autowired
	BlogRepository blogRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	CommentRepository comRepo;

	@Autowired
	CategoryRepository catRepo;

	@Autowired
	FileDBRepository fileDbRepo;

	@Autowired
	LoginRepository loginRepo;
	
	
	//1.Method to view pending request
	public List<Blog> viewRequest(){
		
			List<Blog> blogs = blogRepo.findBystatus("pending");
			return blogs;		
	}
	
	//2. Method to mark blogs as verified or rejected
	public Blog verify(int blogId, String status) {
		Blog blog = blogRepo.findById(blogId).get();
		blog.setStatus(status);
		blogRepo.save(blog);
		return blog;
	}
	
	//3. Delete user 
	public User deleteUser(String userId) {
		

			User user = userRepo.findById(userId).get();

			// set the User obj of Blog as null before deleting the user
			List<Blog> writtenBlog = blogRepo.findBycreatedBy(user);
			for (Blog blog : writtenBlog) {
				blog.setCreatedBy(null);
			}
			System.out.println("delete called");
			System.out.println("blog user nulled");

			// set the user object of comment as null before deleting the user
			List<Comment> comments = comRepo.findByuser(user);
			for (Comment comment : comments) {
				comment.setUser(null);
			}
			System.out.println("Comment nulled");
			List<User> users = userRepo.findAll();
			for (User us : users) {
				
					List<User> following = us.getFollowing();
					List<User> updatedList = new ArrayList<User>();
					for (User followed : following) {
						if (!followed.getUserId().equals(user.getUserId())) {
							updatedList.add(followed);
						}
					}
					System.out.println(updatedList);
					us.setFollowing(updatedList);
					userRepo.save(us);
				
			}
			System.out.println("user nulled");

			userRepo.delete(user);
			return user;	

	}
	
	//4. Fetch all the users from the repo
	public List<User> getAllUsers(){
		return userRepo.findByRole("user");
	}
	
	//5. Register user to the database
	public User registerUser(User user) {
		
		try {
			User u=userRepo.findById(user.getUserId()).get();
		}
		catch(Exception e) {
			FileDB file = fileDbRepo.findById(user.getProfilePic().getId()).get();
			user.setProfilePic(file);
			
			//addimg user to user table
			User regUser = userRepo.save(user);
			
			Login login=new Login();
			login.setUserId(user.getUserId());
			login.setPassword(user.getPassword());
			
			//adding user details to login table			
			loginRepo.save(login);
			
			return regUser;	
		}
		
		throw new ApplicationException("Resource already found");
	}
	
	//6. Display all verified, rejected as well as pending blogs
	public List<Blog> getAllBlogs(){
		return blogRepo.findAll();
	}
	
	//7. Fetch a blog based on id
	public Blog getBlog(int id) {
		return blogRepo.findById(id).get();
	}
	
	//8. Fetch only verified blogs
	public List<Blog> getVerifiedBlogs(){
		
			List<Blog> blogs = blogRepo.findAll();

			List<Blog> verifiedBlogs = new ArrayList<>();
			for (Blog blog : blogs) {
				if (blog.getStatus().equals("verified")) {
					verifiedBlogs.add(blog);
				}
			}
			return verifiedBlogs;		
	}
	
	//9.Add a blog to the database
	public Blog addBlog(Blog blog) {
		
			Blog newBlog = new Blog();
			Category category = catRepo.findById(blog.getCategory().getCId()).get();
			newBlog.setCategory(category);
			newBlog.setContent(blog.getContent());
			newBlog.setStatus(blog.getStatus());
			newBlog.setDate(new Date());
			newBlog.setFile(blog.getFile());
			newBlog.setTitle(blog.getTitle());
			
			User user = userRepo.findById(blog.getCreatedBy().getUserId()).get();
			
			newBlog.setCreatedBy(user);
			
			blogRepo.save(newBlog);
			
			List<Blog> userBlog=user.getBlogs();
			userBlog.add(newBlog);
			user.setBlogs(userBlog);
			
			userRepo.save(user);			

			return newBlog;		
		}
	
	//10.Update password of the user
	
	public User changePassword(Login login) {
		
			User user = userRepo.findById(login.getUserId()).get();
			Login newLogin = loginRepo.findById(login.getUserId()).get();
			
			user.setPassword(login.getPassword());
			newLogin.setPassword(login.getPassword());
			
			userRepo.save(user);
			loginRepo.save(newLogin);
			
			return user;
		
	}
	
	//11.Fetch all categories
	public List<Category> getCategories(){
		return catRepo.findAll();
	}
	
	//12. Get blogs based on category name
	public List<Blog> getBlogsByCategoryName(String categoryName){
		List<Blog> blogs = blogRepo.findAll();
		List<Blog> filteredBlogs = new ArrayList<Blog>();		

			for (Blog b : blogs) {
				if (b.getCategory().getCName().equals(categoryName)) {
					filteredBlogs.add(b);
				}
			}
			
			return filteredBlogs;
		
	}
	
	//13. Only verified blogs based on category
	public List<Blog> getVerifiedBlogsByCategoryName(String categoryName){
		List<Blog> blogs = blogRepo.findAll();
		List<Blog> filteredBlogs = new ArrayList<Blog>();		

			for (Blog b : blogs) {
				if (b.getCategory().getCName().equals(categoryName) && b.getStatus().equals("verified")) {
					filteredBlogs.add(b);
				}
			}
			
			return filteredBlogs;
		
	}
	
	//14. Delete blog
	public Blog deleteBlog( int blogId) {
		
		Blog blog=blogRepo.findById(blogId).get();
		
		List<Blog> updatedBlogList=new ArrayList<Blog>();		
		User createdBy=blog.getCreatedBy();		
		List<Blog> blogs=createdBy.getBlogs();
		
		for(Blog b:blogs) {
			if(!b.equals(blog)) {
				updatedBlogList.add(b);
			}
			
		}
		
		
		
		createdBy.setBlogs(updatedBlogList);
		userRepo.save(createdBy);
		
		List<User> users=userRepo.findAll();
		for(User us:users) {
			List<Blog> updatedSavedBlogs=new ArrayList<Blog>();
			List<Blog> savedBlogs=us.getSavedBlogs();
			for(Blog b:savedBlogs) {
				if(b.getId()!=(blogId)) {
					updatedSavedBlogs.add(b);
				}
			}
			us.setSavedBlogs(updatedSavedBlogs);
			userRepo.save(us);
		}
		
		List<Comment> comments=blog.getComments();
		for(Comment c:comments) {
			if(c.getBlog().getId()==blogId) {
				comRepo.delete(c);
			}
		}
		
		blogRepo.delete(blog);
		
		return blog;
		
	}
	
	//15. Get comments for a given blog id
	public List<Comment> findComments(int blogId){
		Blog blog=blogRepo.findById(blogId).get();
		List<Comment> comments=comRepo.findByblog(blog);
		return comments;
	}
	
	//16. Adding comment
	public Comment addComment(Comment comment) {
		Comment newComment=new Comment();
		
		newComment.setBlog(blogRepo.findById(comment.getBlog().getId()).get());
		newComment.setDate(new Date());
		newComment.setMessage(comment.getMessage());
		newComment.setNoOfLikes(0);
		newComment.setUser(userRepo.findById(comment.getUser().getUserId()).get());
		
		comRepo.save(newComment);
		
		return newComment;
	}
	
	//17.Find user by id
	public User findUser(String userId) {
		return userRepo.findById(userId).get();
	}
	
	//18. Sort blogs based on Date
	public List<Blog> getBlogByDate(){
		return blogRepo.findByOrderByDateDesc();
	}
	
	//19. add or delete like based on path variables
	public Blog changeLike(int blogId,boolean value) {
		Blog blog=blogRepo.findById(blogId).get();
		
		int n=blog.getNoOfLikes();
		
		System.out.println(n);
		if(value) {
			n+=1;
			blog.setNoOfLikes(n);
			
		}
		
		else {
			n-=1;
			blog.setNoOfLikes(n);
		}
		
		blogRepo.save(blog);
		System.out.println(blog.getNoOfLikes());
		return blog;
	}
	
	//20. Fetch verified sorted blogs
	public List<Blog> getVerifiedBlogsByDate(){
		List<Blog> sorted=blogRepo.findByOrderByDateDesc();
		
		List<Blog> newList = new ArrayList<>();
		for (Blog blog : sorted) {
			if (blog.getStatus().equals("verified")) {
				newList.add(blog);
			}
		}
		
		return newList;
	}
	
	
	//21.Add new category
	public Category addCategory(Category c) {
		Category category=new Category();
		category.setCName(c.getCName());
		return catRepo.save(category);
	}









	
	
	
	
}
