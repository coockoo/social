package app.dao.group;


import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.dao.user.User;

public class Group implements Serializable {
	private static final long serialVersionUID = 2L;
	//TODO category of the group
    private Integer groupID = null;
    private String name = null;
    private String information = null;
    private List<User> usersList = null;
    //private List<Message> messagesList = null;
    
    public Group() {
        
    }
    
    public Group(Integer groupID, String name, String information) {
        this.groupID = groupID;
        this.name = name;
        this.information = information;
        this.usersList = new ArrayList<User>();
        //this.messagesList = new ArrayList<Message>();
    }
    
    public Group(ResultSet groupResultSet) throws SQLException {
        setID(groupResultSet.getInt(1));
        setName(groupResultSet.getString(2));
        setInformation(groupResultSet.getString(3));
        //setUserList(usersList);
    }
    public Group(ResultSet groupResultSet, List<User> usersList) throws SQLException {
        setID(groupResultSet.getInt(1));
        setName(groupResultSet.getString(2));
        setInformation(groupResultSet.getString(3));
        setUserList(usersList);
    }
    
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
    
    public List<User> getUsersList() {
        return usersList;
    }
    
    public void setUserList(List<User> usersList) {
        this.usersList = usersList;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
