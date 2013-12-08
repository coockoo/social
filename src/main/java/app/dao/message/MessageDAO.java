package app.dao.message;

import java.sql.SQLException;
import java.util.List;

import app.dao.group.Group;


public interface MessageDAO {
	
    public Message selectMessageByID(int ID);
    public boolean addMessage(int userID, int groupID, String text)throws SQLException;
    public List<Message> getAllMessagesOfGroup (int id);
    
}
