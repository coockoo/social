package app.dao.message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import app.dao.group.Group;
import app.dao.user.User;


public class Message {
    private Integer messageID = null;
    private User user = null;
    private Group group = null;
    private String messageText = null;
    private Date messageDate = null;
    private Integer likes = 0;
    private Integer dislikes = 0;
    
    public Message() {
        
    }
    
    public Message(Integer messageID, User user, Group group, String messageText, Date messageDate) {
        this.messageID = messageID;
        this.messageText = messageText;
        this.messageDate = messageDate;
        this.user = user;
        this.group = group;
    }
    
    public Integer getMessageID() {
        return messageID;
    }
    
    public void setMessageID(Integer messageID) {
        this.messageID = messageID;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
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
