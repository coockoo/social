package app.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.dao.user.User;
import app.dao.user.UserDAO;


@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private ApplicationContext context = null;
	private UserDAO userDAO = null;
	
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		context = new ClassPathXmlApplicationContext("application-context.xml");
		userDAO = (UserDAO) context.getBean("userDAO");
		
		System.out.println("In service");
		User user = null;
		user = userDAO.getUserByLogin(username);
		if (user == null) {
			throw new UsernameNotFoundException("User with username " + username + " was not found");
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new org.springframework.security.core.userdetails.User(user.getNickName(), 
																   	  user.getPassword(),
																   	  grantedAuthorities);
	}

}