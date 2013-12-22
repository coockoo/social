package app.controller.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import app.UserSession;
import app.dao.user.User;
import app.dao.user.UserDAO;


@Controller
@RequestMapping(value = "/api")
public class AuthenticationController {
	
	@Autowired
	@Qualifier("authenticationManager")
	protected AuthenticationManager authenticationManager;
	
	/*@Autowired
	protected HttpServletRequest request;*/
	
	private ApplicationContext context = null;
	private UserDAO userDAO = null;
	
	public AuthenticationController() {
		context = new ClassPathXmlApplicationContext("application-context.xml");
		userDAO = (UserDAO) context.getBean("userDAO");
	}
	
/*	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	ResponseEntity<String> showUser(@PathVariable(value = "id") int id) {
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
	    Authentication authentication = securityContext.getAuthentication();
	    if (authentication != null) {
	        Object principal = authentication.getPrincipal();
	        System.out.println("AuthUser: ");
	        System.out.println(principal instanceof UserDetails ? principal : null);
	    }
		User user = userDAO.findUserById(id);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		return new ResponseEntity<String>(new Gson().toJson(user),
				responseHeaders, HttpStatus.OK);
	}*/
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody User login(@RequestBody User user_) {
		/*HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");*/
		Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
				user_.getNickName(), user_.getPassword());
		System.out.println(user_);
		/*JsonObject credentials = new JsonParser().parse(json).getAsJsonObject();
		Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
				credentials.get("nickName").getAsString(), credentials.get("password").getAsString());
*/
		try {
			Authentication authentication = authenticationManager
					.authenticate(authenticationToken);
			SecurityContextHolder.getContext()
					.setAuthentication(authentication);
			System.out.println("authenticated");
			
			User user = userDAO.getUserByCredentials(user_.getNickName(), 
					user_.getPassword());
			System.out.println("user: " + user);
			return user;
			/*if (user_ != null) {
				return new ResponseEntity<String>(new Gson().toJson(user_, user_.getClass()), responseHeaders,
						HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("{user : \"fail\"}", responseHeaders, 
											HttpStatus.OK);
			}*/
		} catch (AuthenticationException ex) {
			System.out.println("not authenticated");
			return null;//new ResponseEntity<String>("{user : \"failed\"}", responseHeaders, 
					//HttpStatus.OK);
		}
	}
	
/*		@RequestMapping(value = "/login", method = RequestMethod.POST)
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
	}*/
	
	

}
