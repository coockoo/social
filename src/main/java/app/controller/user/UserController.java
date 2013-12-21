package app.controller.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
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
@RequestMapping(value = "/api/user")
public class UserController {
	private ApplicationContext context = null;
	private UserDAO userDAO = null;
	private UserSession session = null;
	private User byJSON(String json) {
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(json);

		String dateStr = jo.get("birthdate").toString();
		String anoth = dateStr.substring(1, dateStr.length() - 1);

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
		String dateInString = anoth;
		Date date = null;
		try {
			date = sdf1.parse(dateInString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		User user = new User();
		user.setFirstName(jo.get("firstName").toString());
		user.setLastName(jo.get("lastName").toString());
		user.setNickName(jo.get("nickName").toString());
		user.setSex(jo.get("sex").toString());
		user.setRate(Integer.parseInt(jo.get("rate").toString()));
		user.setPassword(jo.get("password").toString());
		user.setBirthDate(date);
		user.setEmail(jo.get("email").toString());
		return user;
	}

	public UserController() {
		context = new ClassPathXmlApplicationContext("application-context.xml");
		userDAO = (UserDAO) context.getBean("userDAO");
		session = (UserSession) context.getBean("userSession");
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<String> allUsers(HttpServletRequest request) {
		List<User> users = userDAO.getAllUsers();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		System.out.println("Session " + request.getSession(false));
		
		return new ResponseEntity<String>(new Gson().toJson(users),
				responseHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/error")
	public @ResponseBody
	ResponseEntity<String> error(HttpServletRequest request) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		return new ResponseEntity<String>("error", responseHeaders, HttpStatus.OK);
	}

/*	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	ResponseEntity<String> showUser(@PathVariable(value = "id") int id, HttpServletRequest request, HttpSession session) {
		System.out.println("User by id: " + session);
		SecurityContext securityContext = SecurityContextHolder.getContext();
	    Authentication authentication = securityContext.getAuthentication();
	    if (authentication != null) {
	        Object principal = authentication.getPrincipal();
	        System.out.println("AuthUser: ");
	        System.out.print(principal instanceof UserDetails ? principal : null);
	    }
		System.out.println("In user/id");
		User user = userDAO.findUserById(id);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		return new ResponseEntity<String>(new Gson().toJson(user),
				responseHeaders, HttpStatus.OK);
	}*/

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	ResponseEntity<String> createUser(@RequestBody String json, HttpServletRequest request) {
		User user = byJSON(json);
		user = userDAO.create(user);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		//request.getSession().setAttribute("user", user);
		
		return new ResponseEntity<String>(new Gson().toJson(user),
				responseHeaders, HttpStatus.OK);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	ResponseEntity<String> updateUser(@PathVariable(value = "id") int id, @RequestBody String json) {
		System.out.println("We are in put");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		User user = byJSON(json);
		if (userDAO.update(id, user)) {
			return new ResponseEntity<String>(new Gson().toJson(userDAO.findUserById(id)), responseHeaders, HttpStatus.OK);
		} else {
			return null;
		}
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	ResponseEntity<String> deleteUser(@PathVariable(value = "id") int id) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		return new ResponseEntity<String>("{\"success\": " + userDAO.delete(userDAO.findUserById(id)) + "}", responseHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{userID}/enroll/{groupID}", method=RequestMethod.POST)
	ResponseEntity<String> enrollToGroup(@PathVariable(value = "userID") int userID, @PathVariable(value = "groupID") int groupID) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		return new ResponseEntity<String>("{\"success\": " + userDAO.enrollToGroup(userID, groupID) + "}", responseHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{userID}/groups", method=RequestMethod.GET)
	ResponseEntity<String> getGroups(@PathVariable(value = "userID") int userID) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		return new ResponseEntity<String>(new Gson().toJson(userDAO.getGroups(userDAO.findUserById(userID))), responseHeaders, HttpStatus.OK);
	}
}