package app.dao.group;

import java.sql.SQLException;
import java.util.List;

import app.dao.user.User;

public interface GroupDAO {
    public List<Group> selectGroupsOfUser(User user);
	public List<Group> getAllGroups();
	public Group findGroupById(int id);
	public boolean isGroupOfUser(int userID, int groupID);
	public List<User> getUsersOfGroup(int groupID);
}