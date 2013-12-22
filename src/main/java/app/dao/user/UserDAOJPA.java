package app.dao.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.dao.group.Group;

@Repository
public class UserDAOJPA implements UserDAO{

	@PersistenceContext
	private EntityManager em;
	
	//checked
	@Transactional
	public User create(User user) {
		em.persist(user);
		return user;
	}

	//cheched
	@Transactional
	public User findUserById(int id) {
		return em.find(User.class, id);
	}

	@Transactional
	public boolean update(int id, User user) {
		// TODO Auto-generated method stub
		return false;
	}

	//checked
	@Transactional
	public boolean delete(User user) {
		// TODO Auto-generated method stub
		em.remove(em.contains(user) ? user : em.merge(user));
		return false;
	}

	//cheched
	@Transactional
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		TypedQuery<User> query = em.createQuery(
                "select user from User user order by user.userID", User.class);
        return query.getResultList();
	}

	//checked
	@Transactional
	public boolean enrollToGroup(int userID, int groupID) {
		Query query = em.createNativeQuery("INSERT INTO Userstogroups (userID, groupID) values (?, ?)");
		query.setParameter(1, userID);
		query.setParameter(2, groupID);
		query.executeUpdate();
		return false;
	}

	//cheched
	public List<Group> getGroups(User user) {
		System.out.println("get groups " + user.getGroups());
		TypedQuery<Group> query = em.createQuery(
                "select group from Group group where group.groupID = (select user.userID from User user where user.userID = :id)", Group.class);
		query.setParameter("id", user.getUserID());
		List<Group> groups = new ArrayList<Group>();
		groups.addAll(query.getResultList());
		return groups;
	}

	//cheched
	public User getUserByCredentials(String login, String password) {
		// TODO Auto-generated method stub
		TypedQuery<User> query = em.createQuery(
                "select user from User user where user.nickName = :nName and user.password = :uPassword", User.class);
		query.setParameter("nName", login);
		query.setParameter("uPassword", password);
		User resUser = query.getResultList().get(0);
		System.out.println(resUser);
        return resUser;
	}

	//cheched
	public User getUserByLogin(String login) {
		TypedQuery<User> query = em.createQuery(
                "select user from User user where user.nickName = :nName", User.class);
		query.setParameter("nName", login);
		User resUser = query.getResultList().get(0);
		System.out.println(resUser);
        return resUser;
	}

}
