package org.jboss.seam.example.booking;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
@Named("authenticator")
public class AuthenticatorAction 
    implements Authenticator
{
    @PersistenceContext 
    private EntityManager em;

    @Inject   
    private User user;
   
	public boolean authenticate() {
		List results = em
				.createQuery(
						"select u from User u where u.username=#{identity.username} and u.password=#{identity.password}")
				.getResultList();

		if (results.size() == 0) {
			return false;
		} else {
			user = (User) results.get(0);
			return true;
		}
	}

}
