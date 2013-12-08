package app.controller.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import app.dao.user.User;
import app.dao.user.UserDAO;

@Controller
@RequestMapping(value = "/api/user")
public class UserController {
	private ApplicationContext context = null;
	private UserDAO userDAO = null;

	public UserController() {
		context = new ClassPathXmlApplicationContext("application-context.xml");
		userDAO = (UserDAO) context.getBean("userDAO");
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<String> allUsers() {
		List<User> users = userDAO.getAllUsers();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		return new ResponseEntity<String>(new Gson().toJson(users),
				responseHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<String> showUser(@PathVariable(value = "id") int id) {
		User user = userDAO.findUserById(id);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		return new ResponseEntity<String>(new Gson().toJson(user),
				responseHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	ResponseEntity<String> createUser(@RequestBody String json) {
		System.out.println(json);
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(json);

		String dateStr = jo.get("birthdate").toString();
		String anoth = dateStr.substring(1, dateStr.length() - 1);
		// System.out.println("birthdate: " + dateStr.substring(1,
		// dateStr.length()-1));

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-M-dd");
		String dateInString = anoth;// "31-08-1982";
		Date date = null;
		try {
			date = sdf1.parse(dateInString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(date);

		User user = new User();
		user.setFirstName(jo.get("firstName").toString());
		user.setLastName(jo.get("lastName").toString());
		user.setNickName(jo.get("nickName").toString());
		user.setSex(jo.get("sex").toString());
		user.setRate(Integer.parseInt(jo.get("rate").toString()));
		user.setPassword(jo.get("password").toString());
		user.setBirthDate(date);
		user.setEmail(jo.get("email").toString());
		// User user = new Gson().fromJson(json, User.class);
		user = userDAO.create(user);
		System.out.println(user);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		return new ResponseEntity<String>(new Gson().toJson(user),
				responseHeaders, HttpStatus.OK);
	}
}
