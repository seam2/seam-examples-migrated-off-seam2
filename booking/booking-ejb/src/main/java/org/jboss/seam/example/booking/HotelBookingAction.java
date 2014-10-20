//$Id: HotelBookingAction.java 5579 2007-06-27 00:06:49Z gavin $
package org.jboss.seam.example.booking;

import static javax.persistence.PersistenceContextType.EXTENDED;

import java.util.Calendar;
import java.util.logging.Logger;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.event.Event;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
@Named("hotelBooking")
//@Restrict("#{identity.loggedIn}")
public class HotelBookingAction implements HotelBooking
{
   
   @PersistenceContext(type=EXTENDED)
   private EntityManager em;
   
   @Inject 
   private User user;
   
   @Inject//(required=false) @Out
   private Hotel hotel;
   
   @Inject 
   //@Out(required=false)
   private Booking booking;
     
   //@Inject
   //private FacesMessages facesMessages;
      
   //@In
   //private Events events;
   @Inject
   private Event<BookingEvent> bookingevent;
   
   @Inject
   Conversation conversation;
   
   //@Logger 
   private static final Logger LOG = Logger.getLogger(HotelBookingAction.class.getName());
   
   private boolean bookingValid;
   
   //@Begin <-- do a programatic call?
   public void selectHotel(Hotel selectedHotel)
   {
	// Start a Conversation  
	  if (!conversation.isTransient()) {  
	     LOG.info("Existing conversation found:" + this.conversation.getId() + " Ending it...");  
	     conversation.end();  
	  }  
	  conversation.begin();  	  
      hotel = em.merge(selectedHotel);
   }
   
   public void bookHotel()
   {      
      booking = new Booking(hotel, user);
      Calendar calendar = Calendar.getInstance();
      booking.setCheckinDate( calendar.getTime() );
      calendar.add(Calendar.DAY_OF_MONTH, 1);
      booking.setCheckoutDate( calendar.getTime() );
   }
   
   public void setBookingDetails()
   {
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DAY_OF_MONTH, -1);
      if ( booking.getCheckinDate().before( calendar.getTime() ) )
      {
         //facesMessages.addToControl("checkinDate", "Check in date must be a future date");
    	 //TODO: ^^ do rather Bean Validation ?!
         bookingValid=false;
      }
      else if ( !booking.getCheckinDate().before( booking.getCheckoutDate() ) )
      {
         //facesMessages.addToControl("checkoutDate", "Check out date must be later than check in date");
    	//TODO: ^^ do rather Bean Validation ?!
         bookingValid=false;
      }
      else
      {
         bookingValid=true;
      }
   }
   
   public boolean isBookingValid()
   {
      return bookingValid;
   }
   
   //@End --> programatic call
   public void confirm()
   {
      em.persist(booking);
      //facesMessages.add("Thank you, #{user.name}, your confimation number for #{hotel.name} is #{booking.id}");
      //TODO: ^^ do rather Bean Validation ?!
      LOG.info("New booking: #{booking.id} for #{user.username}");
      //events.raiseTransactionSuccessEvent("bookingConfirmed");
      bookingevent.fire(new BookingEvent());
      conversation.end();
   }
   
   //@End --> programmatic call to end conversation
   public void cancel() 
   {
	   conversation.end();
   }
   
   @Remove
   public void destroy() {}
}