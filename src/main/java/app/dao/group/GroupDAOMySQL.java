package app.dao.group;

import java.sql.SQLException;
import java.util.List;

public class GroupDAOMySQL implements GroupDAO {

	public Group selectGroupByID(int ID) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Group> selectGroupsOfUser(int ID)
			throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Group> allGroupsList() throws ClassNotFoundException,
			SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Group> groupById(int id) throws ClassNotFoundException,
			SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isGroupOfUser(int userID, int groupID)
			throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
