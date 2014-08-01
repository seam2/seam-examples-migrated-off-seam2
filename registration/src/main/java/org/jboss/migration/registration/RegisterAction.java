package org.jboss.migration.registration;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named("register")
@Stateless
public class RegisterAction {

	private final User newUser = new User();

	@PersistenceContext
	private EntityManager em;
	
	private Logger log = Logger.getLogger(RegisterAction.class.getCanonicalName());

	private UIInput usernameInput;

	public UIInput getUsernameInput() {
		return usernameInput;
	}

	public void setUsernameInput(UIInput usernameInput) {
		this.usernameInput = usernameInput;
	}

	@Produces
	@Named
	public User getNewUser() {
		return newUser;
	}

	public String register() {
		User existingUser = em.find(User.class, newUser.getUsername());

		if (existingUser == null) {
			em.persist(newUser);
			log.info("Registered new user " + newUser.getUsername());
			return "/registered.xhtml";
		} else {
			String message = "User " + newUser.getUsername() + " already exists";
			FacesMessage facesMessage = new FacesMessage(message);
			FacesContext.getCurrentInstance().addMessage(null,  facesMessage);
			log.warning(message);
			return null;
		}
	}

}
