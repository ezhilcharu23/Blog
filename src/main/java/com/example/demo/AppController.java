package com.example.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Exceptions.ApplicationException;
import com.example.demo.Exceptions.BlogNotCreatedException;
import com.example.demo.Exceptions.BlogNotFoundException;
import com.example.demo.Exceptions.CategoryNotFoundException;
import com.example.demo.Exceptions.Error;
import com.example.demo.Exceptions.IdNotFoundException;
import com.example.demo.Exceptions.UserNotRegisteredException;
import com.example.demo.Exceptions.VerifiedBlogsNotExist;
import com.example.demo.dao.AppDao;
import com.example.demo.dao.BlogRepository;
import com.example.demo.dao.CategoryRepository;
import com.example.demo.dao.CommentRepository;
import com.example.demo.dao.FileDBRepository;
import com.example.demo.dao.LoginRepository;
import com.example.demo.dao.UserRepository;

@RestController
@CrossOrigin(origins = { "http://localhost:3000/","*"})
public class AppController {
	
	//autowired required repositories

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

	@Autowired
	AppDao appDao;

	// 1. Method to view all the pending blogs
	@GetMapping("viewRequest")
	public ResponseEntity<List<Blog>> getRequest() {

		try {
			List<Blog> b = appDao.viewRequest();
			return new ResponseEntity<List<Blog>>(b, HttpStatus.OK);

		} catch (Exception e) {
//			return new ResponseEntity("No data found on status pending ", HttpStatus.NOT_FOUND);
			throw new ApplicationException("No data found on status pending ");
			
		}
	}

	// 2. Method to verify the blog as verified or rejected
	@PutMapping("verifyBlog/{blogId}/{status}")
	public ResponseEntity<Blog> verifyBlog(@PathVariable int blogId, @PathVariable String status) {

		Blog blog = appDao.verify(blogId, status);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	// 3. Deleting the user using user Id
	@DeleteMapping("deleteUser/{userid}")
	public ResponseEntity<User> deleteUserDetails(@PathVariable String userid) {

		try {

			User user = appDao.deleteUser(userid);
			return new ResponseEntity<User>(user, HttpStatus.OK);

		} catch (Exception e) {

//			return new ResponseEntity<User>(new User(), HttpStatus.NOT_FOUND);
			throw new IdNotFoundException("User cannot be deleted");
		}

	}

	// 4. Get all the users from the database
	@GetMapping("viewUserDetails")
	public ResponseEntity<List<User>> viewUserDetails() {

		try {
			List<User> users = appDao.getAllUsers();
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);

		} catch (Exception e) {
//			return new ResponseEntity("No user Found", HttpStatus.NOT_FOUND);
			throw new UserNotRegisteredException("UserDetails not found");
		}
	}

	// 5. Register user
	@PostMapping("reguser")
	public ResponseEntity regUser(@RequestBody User user) {

		try {
			User registeredUser = appDao.registerUser(user);
			return new ResponseEntity(registeredUser, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(new Error(HttpStatus.NOT_ACCEPTABLE.toString(),e.getMessage()),HttpStatus.NOT_ACCEPTABLE );
//			throw new ApplicationException("Resource already found");
		}
	}

	// 6. Display all blogs (verified and rejected)
	@GetMapping("displayAllBlogs")
	public ResponseEntity<List<Blog>> displayBlogs() {

		try {

			List<Blog> allBlogs = appDao.getAllBlogs();
			return new ResponseEntity(allBlogs, HttpStatus.OK);

		} catch (Exception e) {

//			return new ResponseEntity("No blog is found", HttpStatus.NOT_FOUND);

			throw new BlogNotFoundException("Blog not found");
		}
	}

	// 7. Fetch a blog based on the blog id
	@GetMapping("blog/{blogId}")
	public ResponseEntity viewBlog(@PathVariable int blogId) {
		try {
			Blog blog = appDao.getBlog(blogId);
			return new ResponseEntity(blog, HttpStatus.OK);
		} catch (Exception e) {
//			return new ResponseEntity(new Blog(), HttpStatus.NOT_FOUND);
			throw new BlogNotFoundException("Blog resource not found");
		}

	}

	// 8.Fetch only the verified blogs
	@GetMapping("displayverified")
	public ResponseEntity<List<Blog>> displayVerified() {
		try {
			List<Blog> verifiedBlogs = appDao.getVerifiedBlogs();
			return new ResponseEntity<>(verifiedBlogs, HttpStatus.OK);

		} catch (Exception e) {
//			return new ResponseEntity("No verified blog is found", HttpStatus.NOT_FOUND);
			throw new VerifiedBlogsNotExist("No verified blog is found");
		}

	}

	// 9. Method to add blog to the database
	@PostMapping("createBlog")
	public ResponseEntity createBlog(@RequestBody Blog blog) {
		try {
			Blog newBlog = appDao.addBlog(blog);
			return new ResponseEntity(newBlog, HttpStatus.CREATED);

		} catch (Exception e) {
//			return new ResponseEntity(new Blog(), HttpStatus.BAD_REQUEST);
			throw new BlogNotCreatedException("Blog Resource already found");
		}

	}

//	@PostMapping("reguser")
//	public ResponseEntity registerUser(@RequestBody User user) {
//		try {
//			FileDB fb = fileDbRepo.findById(user.getProfilePic().getId()).get();
//			user.setProfilePic(fb);
//			User u = userRepo.save(user);
//			return new ResponseEntity<>(u, HttpStatus.CREATED);
//		} catch (Exception e) {
//			return new ResponseEntity(new User(), HttpStatus.BAD_REQUEST);
//		}
//	}

	// 10. Update the password of user
	@PutMapping("updatePassword")
	public ResponseEntity updateUser(@RequestBody Login login) {
		try {
			User user = appDao.changePassword(login);
			return new ResponseEntity(user, HttpStatus.OK);
		} catch (Exception e) {
//			return new ResponseEntity("Not able to update", HttpStatus.BAD_REQUEST);
			throw new IdNotFoundException("Not able to update");
		}

	}

	// 11. Fetch all the categories
	@GetMapping("showAllCategories")
	public List<Category> showCat() {
		try {
			return appDao.getCategories();
			}catch(Exception e) {
				throw new BlogNotFoundException("Blog resource not found");
			}
	}

	// 12. Get blogs based on selected category
	@GetMapping("category/{categoryName}")
	public ResponseEntity getCategory(@PathVariable String categoryName) {

		List<Blog> filteredBlogs = blogRepo.findAll();
		List<Blog> res = new ArrayList<Blog>();

		try {

			filteredBlogs = appDao.getBlogsByCategoryName(categoryName);

			return new ResponseEntity(filteredBlogs, HttpStatus.FOUND);
		} catch (Exception e) {
//			return new ResponseEntity(filteredBlogs, HttpStatus.NOT_FOUND);
			throw new ApplicationException("Resource not found");
		}
	}

	// 13. Only verified blogs based on category
	@GetMapping("categoryVerifiedOnly/{cName}")
	public ResponseEntity getCategoryverified(@PathVariable String cName) {

		List<Blog> filteredBlogs = blogRepo.findAll();
		List<Blog> res = new ArrayList<Blog>();

		try {

			filteredBlogs = appDao.getVerifiedBlogsByCategoryName(cName);

			return new ResponseEntity(filteredBlogs, HttpStatus.FOUND);
		} catch (Exception e) {
//			return new ResponseEntity(filteredBlogs, HttpStatus.NOT_FOUND);
			throw new CategoryNotFoundException(" Verified category not found ");
		}
	}

	

	// 15. Fetch comments from db for a given blog id
	@GetMapping("comments/{blogId}")
	public ResponseEntity getComments(@PathVariable int blogId) {
		try {
			List<Comment> comments = appDao.findComments(blogId);
			return new ResponseEntity(comments, HttpStatus.FOUND);
		} catch (Exception e) {
//			return new ResponseEntity("Blog id not found", HttpStatus.NOT_FOUND);
			throw new BlogNotFoundException("Comments Not found");
		}
	}

	// 16. Create a new comment
	@PostMapping("addComment")
	public ResponseEntity addComment(@RequestBody Comment comment) {

		try {
			Comment newComment = appDao.addComment(comment);
			return new ResponseEntity(newComment, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(new Comment(), HttpStatus.BAD_REQUEST);
		}
	}
	
	//17. Find user by userid
	@GetMapping("findUser/{userId}")
	public ResponseEntity getUserbyId(@PathVariable String userId) {
		try {
		User user=appDao.findUser(userId);
		return new ResponseEntity(user,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("User not found",HttpStatus.NOT_FOUND);
		}
	}
			
	
	//18. Sort the blogs based on date
	@GetMapping("displayRecentBlog")
	public ResponseEntity<List<Blog>> displayRecentBlogs() {
		try {
			
			List<Blog> sorted=appDao.getBlogByDate();
			return new ResponseEntity<List<Blog>>(sorted, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity("No Recent Blog is found", HttpStatus.NOT_FOUND);
		}
	}
	
	//19. Get blog using blog id
	@GetMapping("downloadBlog/{bid}")
	public ResponseEntity<Blog> downloadBlogs(@PathVariable int bid) {
		try {
			Blog b = appDao.getBlog(bid);			
			return new ResponseEntity<Blog>(b, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity("No blog is found", HttpStatus.NOT_FOUND);
		}
	}
	
	//20. Get blogs written by a user
	@GetMapping("blogs/{author}")
	public ResponseEntity viewBlog(@PathVariable String author) {
		
		return new ResponseEntity(blogRepo.getByCreaterId(author),HttpStatus.OK);
		
	}
	
	//21. Method to modify like
	@GetMapping("like/{blogId}/{value}")
	public ResponseEntity<Blog> likes(@PathVariable int blogId, @PathVariable boolean value) {
		try {
			Blog blog=appDao.changeLike(blogId, value);
			return new ResponseEntity<Blog>(blog,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Blog>(new Blog(),HttpStatus.BAD_REQUEST);
		}
	}
	
	//22. Fetch verified sorted blogs based on date
	@GetMapping("displayRecentVerified")
	public ResponseEntity<List<Blog>> displayVerifiedRecent(){
		
		try {
			List<Blog> sorted=appDao.getVerifiedBlogsByDate();
			return new ResponseEntity<List<Blog>>(sorted,HttpStatus.OK);
			
		}catch (Exception e) {
			
			return new ResponseEntity("No Verified Recent Blogs Found ", HttpStatus.NOT_FOUND);
		}
		
	}
	
	//23. Add a new category
	@PostMapping("addCategory")
	public ResponseEntity<Category> addCategory(@RequestBody Category cat){
		
		try {
			
			Category c=appDao.addCategory(cat);
			return new ResponseEntity(c,HttpStatus.OK);
			
		}catch (Exception e) {
			
			return new ResponseEntity(e.getMessage(),HttpStatus.FORBIDDEN);
		}
		
	}
		
	
	
	//24. Delete blog	
	@DeleteMapping("deleteBlog/{blogId}")
	public ResponseEntity deleteBlog(@PathVariable int blogId) {
		try {
			Blog b=appDao.deleteBlog(blogId);
			return new ResponseEntity(b,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_IMPLEMENTED);
		}
	}
	
	
}
