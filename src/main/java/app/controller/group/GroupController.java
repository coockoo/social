package app.controller.group;

import java.sql.SQLException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import app.dao.group.Group;
import app.dao.group.GroupDAO;
import app.dao.user.User;
import app.dao.user.UserDAO;


@Controller
@RequestMapping(value = "/api/group")
public class GroupController {
	
	private ApplicationContext context = null;
	private GroupDAO groupDAO = null;

	public GroupController() {
		context = new ClassPathXmlApplicationContext("application-context.xml");
		groupDAO = (GroupDAO) context.getBean("groupDAO");
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<String> allGroups() {
		List<Group> groups = null;
		groups = groupDAO.getAllGroups();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		return new ResponseEntity<String>(new Gson().toJson(groups),
				responseHeaders, HttpStatus.OK);
	}	

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<String> showGroup(@PathVariable(value = "id") int id) {
		Group group = groupDAO.findGroupById(id);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json");
		return new ResponseEntity<String>(new Gson().toJson(group),
				responseHeaders, HttpStatus.OK);
	}
	
	


}
