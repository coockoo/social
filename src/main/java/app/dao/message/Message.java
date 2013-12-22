package app.dao.message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import app.dao.group.Group;
import app.dao.user.User;

@Entity
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messagesID = null;
	
    private transient User user = null;
	
    private transient Group group = null;
	
    private String messageText = null;
    private Date messageDate = null;
    private Integer likes = 0;
    private Integer dislikes = 0;
    
    /*public Message() {
        
    }
    
    public Message(Integer messageID, User user, Group group, String messageText, Date messageDate) {
        this.messageID = messageID;
        this.messageText = messageText;
        this.messageDate = messageDate;
        this.user = user;
        this.group = group;
    }*/
    
    public Integer getMessageID() {
        return messagesID;
    }
    
    public void setMessageID(Integer messageID) {
        this.messagesID = messageID;
    }
    
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "userID")
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "groupID")
    public Group getGroup() {
        return group;
    }
    
    public void setGroup(Group group) {
        this.group = group;
    }
    
    public String getMessageText() {
        return messageText;
    }
    
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    
    public Date getMessageDate() {
        return messageDate;
    }
    
    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }
    
    public Integer getMessageLikes() {
        return likes;
    }
    
    public void setMessageLikes(Integer likes) {
        this.likes = likes;
    }
    
    public Integer getMessageDislikes() {
        return dislikes;
    }
    
    public void setMessageDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }
}
