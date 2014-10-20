//$Id: HotelSearchingAction.java 8998 2008-09-16 03:08:11Z shane.bryzak@jboss.com $
package org.jboss.seam.example.booking;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
@Named("hotelSearch")
@SessionScoped
//@Restrict("#{identity.loggedIn}")
public class HotelSearchingAction implements HotelSearching
{
    @PersistenceContext
    private EntityManager em;
    
    private String searchString;
    private int pageSize = 10;
    private int page;
    private boolean nextPageAvailable;
   
    //@DataModel no more needed, just use JSF2
    private List<Hotel> hotels;
   
    public void find() 
    {
        page = 0;
        queryHotels();
    }

    public void nextPage() 
    {
        page++;
        queryHotels();
    }
    
    private void queryHotels() {
        List<Hotel> results = em.createQuery("select h from Hotel h where lower(h.name) like #{pattern} or lower(h.city) like #{pattern} or lower(h.zip) like #{pattern} or lower(h.address) like #{pattern}")
                                .setMaxResults(pageSize+1)
                                .setFirstResult(page * pageSize)
                                .getResultList();
        
        nextPageAvailable = results.size() > pageSize;
        if (nextPageAvailable) 
        {
            hotels = new ArrayList<Hotel>(results.subList(0,pageSize));
        } else {
            hotels = results;
        }
    }

    public boolean isNextPageAvailable()
    {
        return nextPageAvailable;
    }
   
   public int getPageSize() {
      return pageSize;
   }
   
   public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
   }
   
   @Produces
   @RequestScoped
   @Named("pattern")
   public String getSearchPattern()
   {
      return searchString==null ? 
            "%" : '%' + searchString.toLowerCase().replace('*', '%') + '%';
   }
   
   public String getSearchString()
   {
      return searchString;
   }
   
   public void setSearchString(String searchString)
   {
      this.searchString = searchString;
   }
   
   @Remove
   public void destroy() {}
}
