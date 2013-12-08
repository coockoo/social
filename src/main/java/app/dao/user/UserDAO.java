package app.dao.user;

import java.util.List;

public interface UserDAO {
	public User create(User user);
	public User findUserById(int id);
	public boolean update(User user);
	public boolean delete(User user);
    public List<User> getAllUsers();
}
