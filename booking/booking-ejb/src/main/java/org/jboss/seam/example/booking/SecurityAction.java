package org.jboss.seam.example.booking;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.picketlink.Identity.AuthenticationResult;
import org.picketlink.internal.DefaultIdentity;

@Named
public class SecurityAction {

    @Inject
    DefaultIdentity identity;

    public String login() {
        AuthenticationResult authResult = identity.login();
        if (authResult == AuthenticationResult.SUCCESS)
            return "main";
        else
            return "#";
    }
    
    public String logout() {
        identity.logout();
        HttpSession sess = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        sess.removeAttribute(AuthenticatorAction.SESSION_USER_PROPERTY);
        sess.invalidate();
        return "index";
    }
    

}
