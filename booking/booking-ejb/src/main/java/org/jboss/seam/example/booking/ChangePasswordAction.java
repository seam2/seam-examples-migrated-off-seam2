//$Id: ChangePasswordAction.java 5579 2007-06-27 00:06:49Z gavin $
package org.jboss.seam.example.booking;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
@Named("changePassword")
//@Restrict("#{identity.loggedIn}")
public class ChangePasswordAction implements ChangePassword
{
    @Produces
    @SessionScoped
    private static User user;
   
   @PersistenceContext
   private EntityManager em;
   
   private String verify;
   
   private boolean changed;
     
   public void changePassword()
   {
      if ( user.getPassword().equals(verify) )
      {
         user = em.merge(user);
         //FacesMessages  add("Password updated");
         changed = true;
      }
      else 
      {
         //facesMessages.addToControl("verify", "Re-enter new password");
         revertUser();
         verify=null;
      }
   }
   
   public boolean isChanged()
   {
      return changed;
   }
   
   private void revertUser()
   {
      user = em.find(User.class, user.getUsername());
   }
   public String getVerify()
   {
      return verify;
   }
   public void setVerify(String verify)
   {
      this.verify = verify;
   }
   
   @Remove
   public void destroy() {}
}
