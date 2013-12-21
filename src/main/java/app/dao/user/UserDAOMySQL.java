package app.dao.user;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import app.dao.group.Group;

import com.google.gson.Gson;

public class UserDAOMySQL implements UserDAO {

	private DataSource dataSource;
	public void setDataSource (DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public User create(User user) {
		Connection connection = null;
		PreparedStatement stmt = null;
		java.sql.Date userBirthdate = new Date(user.getBirthDate().getYear(), user.getBirthDate().getMonth(), user.getBirthDate().getDay());
		try {
			connection = dataSource.getConnection();
			stmt = connection.prepareStatement("INSERT INTO Users "
									+ "(firstname, lastname, nickname, sex, birthdate, email, password, rate) "
									+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getNickName());
			stmt.setString(4, user.getSex());
			stmt.setDate(5, userBirthdate);
			stmt.setString(6, user.getEmail());
			stmt.setString(7, user.getPassword());
			stmt.setInt(8, user.getRate());
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}


	public User findUserById(int id) {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			connection = dataSource.getConnection();
			stmt = connection.prepareStatement("SELECT * FROM Users WHERE userID=?");
			stmt.setLong(1, id);
			resultSet = stmt.executeQuery();
			while(resultSet.next()) {
				user = new User(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return user;	
	}
	public boolean update(int id, User user) {
		System.out.println(new Gson().toJson(user));
		Connection connection = null;
		PreparedStatement stmt = null;
		java.sql.Date userBirthdate = new Date(user.getBirthDate().getYear(), user.getBirthDate().getMonth(), user.getBirthDate().getDay());
		try {
			connection = dataSource.getConnection();
			stmt = connection.prepareStatement("UPDATE Users SET "
					+ "firstname = COALESCE(?, firstname), "
					+ "lastname = COALESCE(?, lastname), "
					+ "nickname = COALESCE(?, nickname), "
					+ "sex = COALESCE(?, sex), "
					+ "birthdate = COALESCE(?, birthdate), "
					+ "email = COALESCE(?, email), "
					+ "password = COALESCE(?, password) "
					+ "WHERE userID=?");
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getNickName());
			stmt.setString(4, user.getSex());
			stmt.setDate(5, userBirthdate);
			stmt.setString(6, user.getEmail());
			stmt.setString(7, user.getPassword());
			stmt.setInt(8, id);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delete(User user) {
		Connection connection = null;
		PreparedStatement stmt = null;
		if (user == null) {
			return false;
		}
		try {
			connection = dataSource.getConnection();
			stmt = connection.prepareStatement("DELETE FROM Users WHERE userID=?");
			stmt.setInt(1, user.getUserID());
			stmt.execute();
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<User> getAllUsers() {
		Connection connection = null;
		Statement stmt = null;
		List<User> users = new ArrayList<User>();
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			resultSet = stmt.executeQuery("SELECT * FROM Users");
			while (resultSet.next()) {
				User user = new User(resultSet);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return users;
	}

	public boolean enrollToGroup(int userID, int groupID) {
		Connection connection = null;
		PreparedStatement statement = null;
		if (findUserById(userID) == null) {
			return false;
		}
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement("INSERT INTO Userstogroups (userID, groupID) values (?,?);");
			statement.setInt(1, userID);
			statement.setInt(2, groupID);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public List<Group> getGroups(User user) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet;
		List<Group> groups = new ArrayList<Group>();
		if (user == null) {
			return null;
		}
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement("SELECT * FROM Userstogroups WHERE userID=?");
			statement.setInt(1, user.getUserID());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Group group = new Group(resultSet);
				groups.add(group);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return groups;
	}

	public User getUserByCredentials(String login, String password) {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			connection = dataSource.getConnection();
			stmt = connection.prepareStatement("SELECT * FROM Users WHERE nickName=? AND password=?");
			stmt.setString(1, login);
			stmt.setString(2, password);
			resultSet = stmt.executeQuery();
			while(resultSet.next()) {
				user = new User(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return user;	
	}

	public User getUserByLogin(String login) {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			connection = dataSource.getConnection();
			stmt = connection.prepareStatement("SELECT * FROM Users WHERE nickName=?");
			stmt.setString(1, login);
			resultSet = stmt.executeQuery();
			while(resultSet.next()) {
				user = new User(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}
}
