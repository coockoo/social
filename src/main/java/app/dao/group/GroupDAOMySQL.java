package app.dao.group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import app.dao.user.User;

public class GroupDAOMySQL implements GroupDAO {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<Group> selectGroupsOfUser(int ID)
			throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Group> getAllGroups() {
		Connection connection = null;
		PreparedStatement stmt1 = null;
		ResultSet rs1 = null;
		List<Group> groups = new ArrayList<Group>();
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt1 = connection.prepareStatement(
					"Select * from Groups;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs1 = stmt1.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs1.next()) {
				Group group = null;
				try {
					group = new Group(rs1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				groups.add(group);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return groups;
	}

	public Group findGroupById(int id) {
		// TODO Auto-generated method stub
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Group group = new Group();
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        List<User> users = new ArrayList<User>();
        List<Group> groups = new ArrayList<Group>();
        try {
            stmt1 = connection.prepareStatement("Select * from Users where userID in (select userID from Userstogroups where groupID = ?);");
			stmt1.setInt(1, id);
			rs1 = stmt1.executeQuery();
			    while (rs1.next()) {
			        User user = new User(rs1);
			        users.add(user);
			    } 

            stmt2 = connection.prepareStatement("Select * from Groups where groupID = ?;");
			stmt2.setInt(1, id);
			rs2 = stmt2.executeQuery();
			    while (rs2.next()) {
			        group = new Group(rs2, users);
			        groups.add(group);
			    }
        } catch (SQLException ex) {
            Logger.getLogger(GroupDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return groups.get(0);
	}

	public boolean isGroupOfUser(int userID, int groupID) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Group> selectGroupsOfUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
