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

public class UserDAOMySQL implements UserDAO {

	private DataSource dataSource;
	public void setDataSource (DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public User create(User user) {
		Connection connection = null;
		PreparedStatement stmt = null;
		java.sql.Date userBirthdate = new Date(user.getBirthDate().getYear(), user.getBirthDate().getMonth(), user.getBirthDate().getDay());
		String dateString = "2001/03/09";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
		java.util.Date convertedDate = null;
		try {
			convertedDate = dateFormat.parse(dateString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		//java.sql.Date userBirthdate = new Date(convertedDate.getYear(), convertedDate.getMonth(), convertedDate.getDay());
		try {
			connection = dataSource.getConnection();
			stmt = connection.prepareStatement("INSERT INTO Users "
									+ "(firstname, lastname, nickname, sex, birthdate, email, password) "
									+ "VALUES (?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getNickName());
			stmt.setString(4, user.getSex());
			stmt.setDate(5, userBirthdate);
			stmt.setString(6, user.getEmail());
			stmt.setString(7, user.getPassword());
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

	public boolean update(User user) {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();
			stmt = connection.prepareStatement("UPDATE Users SET firstname=?, lastname=?, nickname=?, sex=?, birthdate=?, email=?, password=? WHERE userID=?");
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getNickName());
			stmt.setString(4, user.getSex());
			stmt.setDate(5, (java.sql.Date) user.getBirthDate());
			stmt.setString(6, user.getEmail());
			stmt.setString(7, user.getPassword());
			stmt.setInt(8, user.getUserID());
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
		try {
			connection = dataSource.getConnection();
			stmt = connection.prepareStatement("DELETE FROM Users WHERE userID=?");
			stmt.setInt(1, user.getUserID());
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
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
}
