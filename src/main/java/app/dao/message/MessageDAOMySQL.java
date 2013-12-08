package app.dao.message;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.dao.group.Group;
import app.dao.group.GroupDAO;
import app.dao.user.User;
import app.dao.user.UserDAO;


public class MessageDAOMySQL implements MessageDAO {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean addMessage(int userID, int groupID, String text) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement stmt = null;
			try {
				connection = dataSource.getConnection();
				stmt = connection.prepareStatement("INSERT INTO Messages "
						+ "(userID, groupID, messagetext) "
						+ "VALUES (?, ?, ?)");
				stmt.setInt(1, userID);
				stmt.setInt(2, groupID);
				stmt.setString(3, text);
				stmt.execute();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			
		}

	public Message selectMessageByID(int ID) {
		// TODO Auto-generated method stub
		
		return null;
	}

	public List<Message> getAllMessagesOfGroup(int id) {
		// TODO Auto-generated method stub
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		GroupDAO groupDAO = (GroupDAO) context.getBean("groupDAO");
		UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		User messageUser = new User();
		List<Message> messages = new ArrayList<Message> ();
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		ResultSet rs1 = null;
		User currentUser = null;
		Group currentGroup = null;
		
        try {
			stmt1 = connection.prepareStatement("Select * from Messages where groupID = ?;");
			stmt1.setInt(1, id);
			rs1 = stmt1.executeQuery();
		    while (rs1.next()) {
/*		    	Date sqlDate = null;
		    	if (rs1.getDate(5) != null) {
			    	java.util.Date curDate = rs1.getDate(5);
			    	sqlDate = new Date(curDate.getYear(), curDate.getMonth(), curDate.getDay());
		    	}*/

		    	currentGroup = groupDAO.findGroupById(id);
		    	currentUser = userDAO.findUserById(rs1.getInt(2));
		        Message message = new Message();
		        message.setGroup(currentGroup);
		        message.setMessageID(rs1.getInt(1));
		        message.setUser(currentUser);
		        message.setGroup(currentGroup);
		        message.setMessageText(rs1.getString(4));
		        //message.setMessageDate(sqlDate);
		        message.setMessageLikes(rs1.getInt(6));
		        message.setMessageDislikes(rs1.getInt(7));
		        messages.add(message);
		    } 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return messages;
	}		
	}