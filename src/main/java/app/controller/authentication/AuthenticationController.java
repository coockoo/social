package app.controller.authentication;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import app.UserSession;
import app.dao.user.User;
import app.dao.user.UserDAO;


@Controller
@RequestMapping(value = "/api")
public class AuthenticationController {
	
	private ApplicationContext context = null;
	private UserDAO userDAO = null;
	private UserSession session = null;
	
	public AuthenticationController() {
		context = new ClassPathXmlApplicationContext("application-context.xml");
		userDAO = (UserDAO) context.getBean("userDAO");
		session = (UserSession) context.getBean("userSession");
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<User> login(@RequestBody String json, HttpServletRequest request) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		System.out.println(json);
		JsonObject credentials = new JsonParser().parse(json).getAsJsonObject();
		User userToLogin = userDAO.getUserByCredentials(credentials.get("login").getAsString(), 
				credentials.get("password").getAsString());
		System.out.println(session);
		session.setUser(userToLogin);
		session.setProp(10);
		//request.getSession(true).setAttribute("user", userToLogin);
		//System.out.println(request.getSession(false));
		if (userToLogin == null) {
			return new ResponseEntity<User>(userToLogin, responseHeaders, HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(userToLogin, responseHeaders, HttpStatus.OK);
		}
	}
	
	

}
