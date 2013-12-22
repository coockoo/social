package app.dao.group;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import app.dao.message.Message;
import app.dao.user.User;

@Entity
@Table(name = "Groups")
public class Group implements Serializable {
	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer groupID = null;
	private String name = null;
	private String information = null;
	private transient Set<User> usersList = null;
	private transient Set<Message> messagesList = null;

	/*
	 * public Group() {
	 * 
	 * }
	 * 
	 * public Group(Integer groupID, String name, String information) {
	 * this.groupID = groupID; this.name = name; this.information = information;
	 * this.usersList = new ArrayList<User>(); //this.messagesList = new
	 * ArrayList<Message>(); }
	 * 
	 * public Group(ResultSet groupResultSet) throws SQLException {
	 * setID(groupResultSet.getInt(1)); setName(groupResultSet.getString(2));
	 * setInformation(groupResultSet.getString(3)); //setUserList(usersList); }
	 * public Group(ResultSet groupResultSet, List<User> usersList) throws
	 * SQLException { setID(groupResultSet.getInt(1));
	 * setName(groupResultSet.getString(2));
	 * setInformation(groupResultSet.getString(3)); setUserList(usersList); }
	 */

	public Integer getID() {
		return groupID;
	}

	public void setID(Integer groupID) {
		this.groupID = groupID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "Userstogroups", joinColumns = { @JoinColumn(name = "groupID") }, inverseJoinColumns = { @JoinColumn(name = "userID") })
	public Set<User> getUsersList() {
		return usersList;
	}

	public void setUserList(Set<User> usersList) {
		this.usersList = usersList;
	}

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<Message> getMessagesList() {
		return this.messagesList;
	}

	public void setMessagesList(Set<Message> messagesList) {
		this.messagesList = messagesList;
	}

	@Override
	public String toString() {
		return name;
	}

}
