package app.controller.group;

import java.sql.SQLException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import app.dao.group.Group;
import app.dao.group.GroupDAO;
import app.dao.message.Message;
import app.dao.message.MessageDAO;
import app.dao.user.User;
import app.dao.user.UserDAO;


@Controller
@RequestMapping(value = "/api/group")
public class GroupController {
	
	private ApplicationContext context = null;
	private GroupDAO groupDAO = null;
	private MessageDAO messageDAO = null;

	public GroupController() {
		context = new ClassPathXmlApplicationContext("application-context.xml");
		groupDAO = (GroupDAO) context.getBean("groupDAO");
		messageDAO = (MessageDAO) context.getBean("messageDAO");
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody
	List<Group> allGroups() {
		List<Group> groups = null;
		groups = groupDAO.getAllGroups();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		return groups;
		//return new ResponseEntity<String>(new Gson().toJson(groups),
				//responseHeaders, HttpStatus.OK);
	}	

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Group showGroup(@PathVariable(value = "id") int id) {
		Group group = groupDAO.findGroupById(id);
		return group;
		//return new ResponseEntity<String>(new Gson().toJson(group),
				//responseHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{groupID}/newmessage/{userID}", method = RequestMethod.POST)
	public @ResponseBody
	void addmessage( @RequestBody Message messageText 
			, @PathVariable(value = "groupID") int groupID
			, @PathVariable(value = "userID") int userID// ) {
			) {
		System.out.println("Hello from message!");
		System.out.println(messageText);
		try {
			messageDAO.addMessage(userID, groupID, "HELLO FROM HIBERNATE");
			//return new ResponseEntity<String>("{\"success\": " + added + "}", responseHeaders, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			//return new ResponseEntity<String>("{\"success\": " + added + "}", responseHeaders, HttpStatus.OK);
		}
	}
	
	@RequestMapping( value = "/{groupID}/messages", method = RequestMethod.GET) 
	public @ResponseBody
	List<Message> allMessages(@PathVariable(value = "groupID") int groupID) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		List<Message> messages = messageDAO.getAllMessagesOfGroup(groupID);
		return messages;
		//return new ResponseEntity<String>(new Gson().toJson(messages),
			//	responseHeaders, HttpStatus.OK);
	}
	
	
	@RequestMapping( value = "/{groupID}/users", method = RequestMethod.GET) 
	public @ResponseBody
	List<User> usersOfTheGroup(@PathVariable(value = "groupID") int groupID) {
		System.out.println("In users groups");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		List<User> users = groupDAO.getUsersOfGroup(groupID);
		return users;
		//return new ResponseEntity<String>(new Gson().toJson(users),
				//responseHeaders, HttpStatus.OK);
	}

}
