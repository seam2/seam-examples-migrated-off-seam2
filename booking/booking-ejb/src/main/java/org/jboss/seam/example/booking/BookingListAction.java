//$Id: BookingListAction.java 8748 2008-08-20 12:08:30Z pete.muir@jboss.org $
package org.jboss.seam.example.booking;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
@SessionScoped
@Named("bookingList")
//@Restrict("#{identity.loggedIn}")
@TransactionAttribute(REQUIRES_NEW)
public class BookingListAction implements BookingList, Serializable
{
   private static final long serialVersionUID = 1L;
   
   @PersistenceContext
   private EntityManager em;
   
   @Inject
   private User user;
   
   private ListDataModel<Booking> bookings;
   private Logger log = Logger.getLogger(BookingListAction.class.getCanonicalName());
   
   @Produces
   public void getBookings()
   {
      bookings = ((ListDataModel<Booking>) em.createQuery("select b from Booking b where b.user.username = :username order by b.checkinDate")
            .setParameter("username", user.getUsername())
            .getResultList());
   }
   
   public void cancel()
   {
      log.info("Cancel booking: #{bookingList.booking.id} for #{user.username}");
      Booking cancelled = em.find(Booking.class, getBooking().getId());
      if (cancelled!=null) em.remove( cancelled );
      getBookings();
      
      FacesMessage facesMessage = new FacesMessage("Booking cancelled for confirmation number "+ getBooking().getId());
	  FacesContext.getCurrentInstance().addMessage(null,  facesMessage);
   }
   
   public Booking getBooking()
   {
      return bookings.getRowData();
   }
   
   @Remove
   public void destroy() {}

}
