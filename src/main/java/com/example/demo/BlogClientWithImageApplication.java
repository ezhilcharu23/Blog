package com.example.demo;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.example.demo.dao.BlogRepository;
import com.example.demo.dao.CategoryRepository;
import com.example.demo.dao.CommentRepository;
import com.example.demo.dao.LoginRepository;
import com.example.demo.dao.UserRepository;

@SpringBootApplication
@EnableEurekaClient 
public class BlogClientWithImageApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(BlogClientWithImageApplication.class, args);
	}
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	CategoryRepository catRepo;
	
	@Autowired
	BlogRepository blogRepo;
	
	@Autowired
	CommentRepository comRepo;
	
	@Autowired
	LoginRepository loginRepo;

	@Override
	public void run(String... args) throws Exception {
//		User u=new User();
//		u.setName("Alex");
//		u.setPassword("123");
//		u.setUserId("abc");
//		u.setEmail("abc@gmail.com");
//		u.setRole("user");		
//		
//		userRepo.save(u);
//		
//		Login l=new Login();
//		l.setUserId("abc");
//		l.setPassword("123");
//		loginRepo.save(l);
//		
//		User ua=new User();
//		ua.setName("Ashwin");
//		ua.setPassword("123");
//		ua.setUserId("ashwin");
//		ua.setEmail("ashwin@gmail.com");
//		ua.setRole("admin");
//		
//		
//		
//		Login la=new Login();
//		la.setUserId("ashwin");
//		la.setPassword("123");
//		loginRepo.save(la);
//		
//		userRepo.save(ua);
//		
//		User u1=new User();
//		u1.setName("Max");
//		u1.setPassword("123");
//		u1.setUserId("max");
//		u1.setEmail("max@gmail.com");
//		u1.setRole("user");
//		
//		userRepo.save(u1);
//		
//		Login l1=new Login();
//		l1.setUserId("max");
//		l1.setPassword("123");
//		loginRepo.save(l1);
//		
//		u.setFollowing(Arrays.asList(u1));
//		
//		
//		Category c=new Category();
//		c.setCName("Education");
//		catRepo.save(c);
//		
//		Blog b=new Blog();
//		b.setCategory(c);
//		b.setCreatedBy(u);
//		b.setDate(new Date());
//		
//		b.setTitle("Maths");
//		b.setContent("Maths is a subject which is not only applicable in our academics but also in real life. "
//				+ "Making kids love this subject is a challenge, especially for parents. "
//				+ "It takes a lot of brainpower to master Maths and this can be tough for kids. "
//				+ "Some students may find Maths hard to learn. ");
//		b.setStatus("pending");
//		blogRepo.save(b);
//		
//		Comment comment=new Comment();
//		comment.setBlog(b);
//		comment.setMessage("Cool");
//		comment.setNoOfLikes(2);
//		comment.setUser(u1);
//		
//		comRepo.save(comment);
//	
//		b.setComments(Arrays.asList(comment));
//		
//	
//		blogRepo.save(b);
//		
//		Comment c1=new Comment();
//		c1.setMessage("Great work");
//		c1.setUser(u);
//		c1.setNoOfLikes(10);
//		comRepo.save(c1);
//		
//		Blog b1=new Blog();
//		b1.setDate(new Date());
//		b1.setCategory(c);
//		b1.setComments(Arrays.asList(c1));
//		b1.setStatus("pending");
//		b1.setCreatedBy(u1);
//		b1.setTitle("Choosing Your Path to Education Leadership");
//		b1.setContent("The need for effective teacher leaders is ongoing and urgent in K-12 schools. "
//				+ "Each teacher lends their expertise and talent to the overall welfare of the school community."
//				+ " Gathering this expertise and putting it to good use doesn’t happen magically. Great teacher leaders, supported by experienced mentors, help pave the way for effective supervision.\r\n"
//				+ "\r\n"
//				+ "Your classroom duties are essential to student achievement, "
//				+ "but now you’re wondering how you can contribute to the whole school community."
//				+ " Do you find yourself helping colleagues find resources for a lesson? "
//				+ "Have you helped someone build curriculum plans or learn how to navigate new tech software?"
//				+ " Do you have a knack for supporting your peers? If so, you’re a natural leader. "
//				+ "It’s time to take some steps to expand your footprint in the school system."
//				+ "Assess Your Character Traits\r\n"
//				+ "Your path to leadership might take a formal turn in becoming an administrative leader like an instructional coach, "
//				+ "department chair, principal, or superintendent or you might take a more informal, organic route like addressing a problem or proposing a new program. "
//				+ "Your leadership skills will determine how you make a difference.\r\n"
//				+ "\r\n"
//				+ "Here are a few characteristics and skills needed to promote change successfully.");
//		
//		blogRepo.save(b1);
//		
//		
//		Category cat1=new Category();
//		cat1.setCName("Motivation");
//		catRepo.save(cat1);
//		
//		Blog b2=new Blog();
//		b2.setCategory(cat1);
//		b2.setTitle("5 Strategies to Increase Student Motivation");
//		b2.setContent("As we wrap up another school year, teachers often note a marked decline in student motivation as the weather warms and the promise of summer fun clouds the mind. It is common to see an uptick in behavior challenges and distractibility.\r\n"
//				+ "\r\n"
//				+ "Whether you are seeking ideas for this year or next, I’m happy to pass along my favorite resources for the recurring dilemma: How do I motivate my students to want to excel?\r\n"
//				+ "\r\n"
//				+ "What factors drive intrinsic motivation?\r\n"
//				+ "Three factors proven to increase intrinsic motivation include autonomy, belonging, and competence, otherwise known as the ABCs.\r\n"
//				+ "\r\n"
//				+ "When we can examine motivation within ourselves, our students, or our colleagues through the ABC lens,"
//				+ " we can more easily identify opportunities for increasing engagement.");
//		b2.setCreatedBy(u1);
//		b2.setDate(new Date());
//		b2.setNoOfLikes(20);
//		b2.setStatus("pending");
//		blogRepo.save(b2);
		
		
		
	}

}
