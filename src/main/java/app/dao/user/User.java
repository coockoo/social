package app.dao.user;


import java.util.Date;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer userID = null;
    private String firstName = null;
    private String lastName = null;
    private String nickName = null;
    private String sex = null;
    private Date birthdate = null;
    private String email= null;
    private Integer rate = 0;
    private String password = null;
    //TODO list of Users-friends
    
    public User() {
    }
    
    public User(int userID, String firstName, String lastName, String sex, Date birthdate, String email, String nickName,
    		String password) {
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
        setPassword(userResultSet.getString(8));
    }
    
    public int getUserID() {
        return userID;
    }
    
    public void setUserID(int userID) {
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
    
        
    public int getRate() {
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
    
    @Override
    public String toString() {
        return firstName;
    }

}