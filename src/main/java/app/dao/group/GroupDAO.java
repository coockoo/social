package app.dao.group;

import java.sql.SQLException;
import java.util.List;

public interface GroupDAO {
    public Group selectGroupByID(int ID);
    public List<Group> selectGroupsOfUser(int ID) throws ClassNotFoundException, SQLException;
	public List<Group> allGroupsList() throws ClassNotFoundException, SQLException;
	public List<Group> groupById(int id) throws ClassNotFoundException, SQLException;
	public boolean isGroupOfUser(int userID, int groupID)
			throws ClassNotFoundException, SQLException;
}