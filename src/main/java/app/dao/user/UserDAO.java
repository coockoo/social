package app.dao.user;

import java.util.List;

import app.dao.group.Group;

public interface UserDAO {
	public User create(User user);
	public User findUserById(int id);
	public boolean update(int id, User user);
	public boolean delete(User user);
    public List<User> getAllUsers();
    public boolean enrollToGroup (int userID, int groupID);
    public List<Group> getGroups (User user);
}
