//$Id: BookingListAction.java 8748 2008-08-20 12:08:30Z pete.muir@jboss.org $
package org.jboss.seam.example.booking;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
@Scope(SESSION)
@Named("bookingList")
@Restrict("#{identity.loggedIn}")
@TransactionAttribute(REQUIRES_NEW)
public class BookingListAction implements BookingList, Serializable
{
   private static final long serialVersionUID = 1L;
   
   @PersistenceContext
   private EntityManager em;
   
   @Inject
   private User user;
   
   @DataModel
   private List<Booking> bookings;
   @DataModelSelection 
   private Booking booking;
   
   @Logger 
   private Log log;
   
   @Factory
   @Observer("bookingConfirmed")
   public void getBookings()
   {
      bookings = em.createQuery("select b from Booking b where b.user.username = :username order by b.checkinDate")
            .setParameter("username", user.getUsername())
            .getResultList();
   }
   
   public void cancel()
   {
      log.info("Cancel booking: #{bookingList.booking.id} for #{user.username}");
      Booking cancelled = em.find(Booking.class, booking.getId());
      if (cancelled!=null) em.remove( cancelled );
      getBookings();
      FacesMessages.instance().add("Booking cancelled for confirmation number #0", booking.getId());
   }
   
   public Booking getBooking()
   {
      return booking;
   }
   
   @Remove
   public void destroy() {}
}
