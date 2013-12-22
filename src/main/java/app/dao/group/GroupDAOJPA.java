package app.dao.group;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import app.dao.user.User;

@Repository
public class GroupDAOJPA implements GroupDAO {
	
	@PersistenceContext
	private EntityManager em;

	public List<Group> selectGroupsOfUser(User user) {
		System.out.println("get groups " + user.getGroups());
		TypedQuery<Group> query = em.createQuery(
                "select group from Group group where group.groupID = (select user.userID from User user where user.userID = :id)", Group.class);
		query.setParameter("id", user.getUserID());
		List<Group> groups = new ArrayList<Group>();
		groups.addAll(query.getResultList());
		return groups;
	}

	//checked
	public List<Group> getAllGroups() {
		TypedQuery<Group> query = em.createQuery(
                "select group from Group group order by group.groupID", Group.class);
        return query.getResultList();
	}

	//cheched
	public Group findGroupById(int id) {
		return em.find(Group.class, id);
	}

	
	public boolean isGroupOfUser(int userID, int groupID) {
		// TODO Auto-generated method stub
		return false;
	}

	//checked
	public List<User> getUsersOfGroup(int groupID) {
		Query query = em.createNativeQuery(
                "Select * from Users where userID IN (Select userID from Userstogroups where groupID = ?)", User.class);
		query.setParameter(1, groupID);
		List<User> users = new ArrayList<User>();
		System.out.println(query.getFirstResult());
		users.addAll((List<User>)query.getResultList());
		return users;
	}

}
