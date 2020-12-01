package pt.ua.Blossom.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pt.ua.Blossom.model.User;
import pt.ua.Blossom.repository.UserRepository;

@Controller
public class UserController {
	
	private UserRepository repository;

	public UserController(UserRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping("/profile")
	public String getProfile(Model model) {
		User user = new User();
		user.setFname("Jose");
		user.setLname("Oliveira");
		user.setMail("jlo@ua.pt");
		user.setPhoneNumber("+351123456789");
		user.setPassword("password");
		user.setDateSubscription(new Date(System.currentTimeMillis()));
		model.addAttribute(user);
		return "profile/myprofile";
	}
	
	@GetMapping("/profiles")
	public String getUsers(Model model) {
		model.addAttribute(this.repository.findAll());
		return "profiles/users";
	}
	
	
	
	

}
