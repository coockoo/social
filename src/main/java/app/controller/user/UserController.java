package app.controller.user;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.dao.user.User;
import app.dao.user.UserDAO;


@Controller
@RequestMapping(value="/api/user")
public class UserController {
	private ApplicationContext context = null;
	private UserDAO userDAO = null;

	public UserController () {
		context = new ClassPathXmlApplicationContext("application-context.xml");
		userDAO = (UserDAO) context.getBean("userDAO");
	}

	@RequestMapping(value="/all", method=RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<String> allUsers () {
		List<User> users = userDAO.getAllUsers();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		return new ResponseEntity<String>(new Gson().toJson(users),responseHeaders, HttpStatus.OK);
	}
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<String> showUser(@PathVariable(value="id") int id) {
		User user = userDAO.findUserById(id);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		return new ResponseEntity<String>(new Gson().toJson(user),responseHeaders, HttpStatus.OK);
	}
	@RequestMapping(value="/create", method=RequestMethod.POST)
	ResponseEntity<String> createUser(@RequestBody String json) {
		System.out.println(json);
		//Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
		User user = new Gson().fromJson(json, User.class);
		user = userDAO.create(user);
		System.out.println(user);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		return new ResponseEntity<String>(new Gson().toJson(user),responseHeaders, HttpStatus.OK);
	}

}
