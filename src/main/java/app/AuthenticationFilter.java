package app;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.dao.user.User;

public class AuthenticationFilter implements Filter{
	private ApplicationContext context = null;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("In filter");
		//HttpServletRequest req = (HttpServletRequest) request;
		//HttpSession session = req.getSession(false);
		UserSession session = (UserSession) context.getBean("userSession");
		System.out.println("" + session.getProp());
		if (session.getUser() == null) {
			System.out.println("SessionVal1 + " + session);
			request.getRequestDispatcher("/api/user/error").forward(request, response);
		} else {
			User currentUser = session.getUser();
			System.out.println("SessionVal2 + " + session);
			System.out.println("login: " + currentUser.getNickName() + " : " + currentUser.getPassword());
			//System.out.println("user: " + currentUser.getEmail());
			chain.doFilter(request, response);
		}	
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		context = new ClassPathXmlApplicationContext("application-context.xml");
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
