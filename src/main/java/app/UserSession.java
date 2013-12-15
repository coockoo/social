package app;

import app.dao.user.User;

public class UserSession {
	private User user = null;
	private int testProp = 0;
	
	public UserSession () {
		System.out.println("User session created");
	}
	
	public void setUser (User user) {
		System.out.println("Saving user: " + user);
		this.user = user;
	}
	
	public void setProp(int prop) {
		this.testProp = prop;
	}
	
	public int getProp() {
		return this.testProp;
	}
	
	public User getUser () {
		System.out.println("Getting user: " + user);
		return this.user;
	}

}
