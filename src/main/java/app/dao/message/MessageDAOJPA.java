package app.dao.message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.dao.group.Group;
import app.dao.user.User;

@Repository
public class MessageDAOJPA implements MessageDAO {
	
	@PersistenceContext
	private EntityManager em;

	public Message selectMessageByID(int ID) {
		return em.find(Message.class, ID);
	}

	@Transactional
	public boolean addMessage(int userID, int groupID, String text)
			throws SQLException {
		Query query = em.createNativeQuery("INSERT INTO Messages (userID, groupID, messagetext) values (?, ?, ?)");
		query.setParameter(1, userID);
		query.setParameter(2, groupID);
		query.setParameter(3, text);
		query.executeUpdate();
		return false;
	}

	public List<Message> getAllMessagesOfGroup(int id) {
		Query query = em.createNativeQuery(
                "Select * from Messages where groupID = ?", Message.class);
		query.setParameter(1, id);
		List<Message> messages = new ArrayList<Message>();
		messages.addAll((List<Message>)query.getResultList());
		return messages;
	}

}
