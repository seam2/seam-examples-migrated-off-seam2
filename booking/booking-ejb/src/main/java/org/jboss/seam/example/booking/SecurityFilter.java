package org.jboss.seam.example.booking;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            // check whether session variable is set
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession ses = req.getSession(false);
            // allow user to proccede if url is login.xhtml or user logged in or
            // user is accessing any page in //public folder
            String reqURI = req.getRequestURI();
            
            boolean isAuthenticated = false; 
            if (ses != null) {
                Object _o = ses.getAttribute("auth_user");
                if (_o != null) {
                    isAuthenticated = true;
                }
            }
            
            if (reqURI.indexOf("/resources/") > 0
                    || reqURI.indexOf("/javax.faces.resource/") > 0) 
                chain.doFilter(request, response);
            else if (!isAuthenticated && reqURI.indexOf("/index.jsf") >= 0) 
                chain.doFilter(request, response);
            else if (!isAuthenticated && reqURI.indexOf("/register.jsf") >= 0) 
                chain.doFilter(request, response);
            else if (isAuthenticated && reqURI.indexOf("/index.jsf") > 0)
                res.sendRedirect(req.getContextPath() + "/main.jsf"); 
            else if (!isAuthenticated)
                // user didn't log in but asking for a page that is not allowed
                // so take user to login page
                res.sendRedirect(req.getContextPath() + "/index.jsf");
            else 
                chain.doFilter(request, response);
                
        } catch (Exception t) {
            t.printStackTrace();
        }

    }

    @Override
    public void destroy() {

    }

}
