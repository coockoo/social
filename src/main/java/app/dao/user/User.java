package app.dao.user;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import app.dao.group.Group;
import app.dao.message.Message;

@Entity
@Table(name = "Users")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userID = null;
    private String firstName = null;
    private String lastName = null;
    private String nickName = null;
    private String sex = null;
    
    @Temporal(value = TemporalType.DATE)
    private Date birthdate = null;
    
    private String email= null;
    private Integer rate = 0;
    private String password = null;
    private transient Set<Group> groups = null;
    private transient Set<Message> messages = null;
    //TODO list of Users-friends
    
    /*public User() {
    }
    
    public User(int userID, String firstName, String lastName, String sex, Date birthdate, String email, String nickName,
    		String password) {
    	System.out.println("Im here!");
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.sex = sex;
        this.birthdate = birthdate;
        this.email = email;   
        this.password = password;
    }
    
    public User(ResultSet userResultSet) throws SQLException {
        setUserID(userResultSet.getInt(1));
        setFirstName(userResultSet.getString(2));
        setLastName(userResultSet.getString(3));
        setNickName(userResultSet.getString(4));
        setSex(userResultSet.getString(5));
        setBirthDate(userResultSet.getDate(6));
        setEmail(userResultSet.getString(7)); 
        setRate(userResultSet.getInt(8));
        setPassword(userResultSet.getString(9));
    }*/
    
    public Integer getUserID() {
        return userID;
    }
    
    public void setUserID(Integer userID) {
        this.userID = userID;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getNickName() {
        return nickName;
    }
    
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    
    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public Date getBirthDate(){
        return birthdate;
    }
    
    public void setBirthDate(Date birthDate) {
        this.birthdate = birthDate;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
        
    public Integer getRate() {
        return rate;
    }
    
    public void setRate(Integer rate) {
        this.rate = rate;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    @ManyToMany(mappedBy = "usersList", fetch = FetchType.EAGER)
    //@LazyCollection(LazyCollectionOption.FALSE)
    public Set<Group> getGroups() {
    	return this.groups;
    }
    
    public void setGroups(Set<Group> groups) {
    	this.groups = groups;
    }
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Message> getMessages() {
    	return this.messages;
    }
    
    public void setMessages(Set<Message> messages) {
    	this.messages = messages;
    }
    
    @Override
    public String toString() {
        return this.getFirstName() + " : " + this.getLastName() + " : " + this.getUserID();
    }

}