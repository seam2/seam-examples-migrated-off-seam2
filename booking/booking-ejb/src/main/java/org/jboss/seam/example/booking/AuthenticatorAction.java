package org.jboss.seam.example.booking;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.picketlink.annotations.PicketLink;
import org.picketlink.authentication.BaseAuthenticator;
import org.picketlink.credential.DefaultLoginCredentials;


@Stateless
@Named("authenticator")
@PicketLink
public class AuthenticatorAction 
    extends BaseAuthenticator 
{
	
    
    @PersistenceContext 
    //@Inject
    private transient EntityManager em;
    
    public static final String SESSION_USER_PROPERTY = "auth_user";

    @Produces
    @SessionScoped
    @Named("loggedUser")
    private static User user;

    @Inject
    private transient DefaultLoginCredentials loginCredentials;
    
    @Override
	public void authenticate() {
		Query query = em
				.createQuery("select u from User u where u.username=:user and u.password=:passwd");
		query.setParameter("user", loginCredentials.getUserId());
		query.setParameter("passwd", loginCredentials.getPassword());
		List results = query.getResultList();

		if (results.size() == 0) {
			setStatus(AuthenticationStatus.FAILURE);
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									"Authentication Failure - The username or password you provided were invalid."));
		} else {
			setStatus(AuthenticationStatus.SUCCESS);
			HttpSession sess = (HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(false);
			user = (User) results.get(0);
			sess.setAttribute(SESSION_USER_PROPERTY, user);
			setAccount(new org.picketlink.idm.model.basic.User(
					user.getUsername()));
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Welcome, " + user.getUsername()));
		}
	}



}
