package app.dao.message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import app.dao.group.Group;


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

	public List<Message> getAllMessagesOfGroup(Group group) {
		// TODO Auto-generated method stub
		
		return null;
	}

	public Message selectMessageByID(int ID) {
		// TODO Auto-generated method stub
		
		return null;
	}		
	}