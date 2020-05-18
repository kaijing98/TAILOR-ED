/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Admin;
import entity.Comment;
import entity.Event;
import entity.EventOrder;
import entity.Offences;
import entity.Seller;
import entity.Post;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.EventExistsException;
import util.exception.EventNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.OffenceExistException;
import util.exception.OffenceNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UserNotFoundException;
import util.exception.UserUsernameExistException;

/**
 *
 * @author decimatum
 */
@Stateless
public class AdminSessionBean implements AdminSessionBeanLocal {

    @EJB(name = "SellerSessionBeanLocal")
    private SellerSessionBeanLocal sellerSessionBeanLocal;

//    @EJB(name = "EmailSessionBeanLocal")
//    private EmailSessionBeanLocal emailSessionBeanLocal;

    @PersistenceContext(unitName = "tailoredJsf-ejbPU")
    private EntityManager em;
    
    private ValidatorFactory validatorFactory;
    private Validator validator;
    
    public AdminSessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewAdmin(Admin newAdminEntity) throws UserUsernameExistException, UnknownPersistenceException, InputDataValidationException
    {
        try
        {
            Set<ConstraintViolation<Admin>>constraintViolations = validator.validate(newAdminEntity);
        
            if(constraintViolations.isEmpty())
            {
                em.persist(newAdminEntity);
                em.flush();

                return newAdminEntity.getUserId();
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }            
        }
        catch(PersistenceException ex)
        {
            if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
            {
                if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                {
                    throw new UserUsernameExistException();
                }
                else
                {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
            else
            {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }
    
    
    //create new event practice code
    @Override
    public Long createNewEvent (Event newEventEntity,Long adminId) throws UnknownPersistenceException, InputDataValidationException,EventExistsException, UserNotFoundException {
        
        
        try{
            Admin admin = retrieveAdminByUserId(adminId);
            Set<ConstraintViolation<Event>>constraintViolations = validator.validate(newEventEntity);
        
            if(constraintViolations.isEmpty())
            {
                
                em.persist(newEventEntity);
                admin.getEvents().add(newEventEntity);
                newEventEntity.setAdmin(admin);
                em.flush();
                
                

                return newEventEntity.getEventId();
            }
            else
            { 
                throw new InputDataValidationException(prepareEventInputDataValidationErrorsMessage(constraintViolations));
            }            
        }
        catch(PersistenceException ex)
        {
            if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
            {
                if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                {
                    throw new EventExistsException();
                }
                else
                {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
            else
            {
                throw new UnknownPersistenceException(ex.getMessage());
            }
            
        } catch (UserNotFoundException ex) {
            throw new UserNotFoundException("Unable to find the user as the user does not exist");
        } 
    
    }
    
    //create an offence
    //note the type of offences is initialized by admin only
    //at point of initialization, no relationship to customer/seller is set
    @Override
    public Long createNewOffence (Offences newOffenceEntity) throws UnknownPersistenceException, InputDataValidationException, OffenceExistException
    {
        try
        {
            Set<ConstraintViolation<Offences>>constraintViolations = validator.validate(newOffenceEntity);
        
            if(constraintViolations.isEmpty())
            {
                em.persist(newOffenceEntity);
                em.flush();

                return newOffenceEntity.getOffencesId();
            }
            else
            {
                throw new InputDataValidationException(prepareOffenceInputDataValidationErrorsMessage(constraintViolations));
            }            
        }
        catch(PersistenceException ex)
        {
            if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
            {
                if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                {
                    throw new OffenceExistException();
                }
                else
                {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
            else
            {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        } 
    }
    
    @Override
    public Offences retrieveOffenceById(Long offenceId) throws OffenceNotFoundException
    {
        Offences offence = em.find(Offences.class, offenceId);
        
        if(offence != null)
        {
            return offence;
        }
        else
        {
            throw new OffenceNotFoundException("OffenceId " + offenceId + " does not exist!");
        }
    }
    @Override
    public Event retrieveEventById(Long eventId) throws EventNotFoundException{
        Event event = em.find(Event.class, eventId);
        
        if(event != null)
        {
            return event;
        }
        else
        {
            throw new EventNotFoundException("EventId " + eventId + " does not exist!");
        }
        
    }
    @Override
    public List<Event> retrieveAllEvents() {
        Query query = em.createQuery("SELECT e FROM Event e WHERE e.isDeleted = FALSE");
        return query.getResultList();
    }
    
    @Override
    public void updateEvent(Event event) throws InputDataValidationException, EventNotFoundException {
        Set<ConstraintViolation<Event>> constraintViolations = validator.validate(event);

        if (constraintViolations.isEmpty()) {
            if (event.getEventId() != null) {
                Event eventToUpdate = retrieveEventById(event.getEventId());
                eventToUpdate.setName(event.getName());
                eventToUpdate.setDescription(event.getDescription());
                eventToUpdate.setVenue(event.getVenue());
                eventToUpdate.setTime(event.getTime());
                eventToUpdate.setPrice(event.getPrice());
                eventToUpdate.setImage(event.getImage());
                eventToUpdate.setEventTypeEnum(event.getEventTypeEnum());
                
               
            } else {
                throw new EventNotFoundException("Event ID not provided for event to be updated");
            }
        } else {
            throw new InputDataValidationException(prepareEventInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public void deleteEvent(Long eventId) throws EventNotFoundException
    {
        try {
            Event eventToRemove = retrieveEventById(eventId);
            List<EventOrder> ordersAssociated  = eventToRemove.getEventOrders();
            
            if(ordersAssociated.isEmpty()){
                em.remove(eventToRemove);
            } else {
                eventToRemove.setIsDeleted(true);
            }   
        } catch (EventNotFoundException ex) {
            throw new EventNotFoundException("Event ID " + eventId + " does not exist!");
        }       
    }
    @Override
    public List<Offences> retrieveAllOffences()
    {
        Query query = em.createQuery("SELECT o FROM Offences o");
        
        return query.getResultList();
    }
    
    @Override
    public List<Admin> retrieveAllAdmins()
    {
        Query query = em.createQuery("SELECT a FROM Admin a WHERE a.isDeleted = FALSE");
        
        return query.getResultList();
    }
    
    //note this is the same as retrieving a user from the user table
    //this returns the admin even if it is deleted
    @Override
    public Admin retrieveAdminByUserId(Long userId) throws UserNotFoundException
    {
        Admin admin = em.find(Admin.class, userId);
        
        if(admin != null)
        {
            return admin;
        }
        else
        {
            throw new UserNotFoundException("UserId " + userId + " does not exist!");
        }
    }
    
    @Override
    public Admin retrieveAdminByUsername(String username) throws UserNotFoundException
    {
        Query query = em.createQuery("SELECT a FROM Admin a WHERE a.username = :inUsername AND a.isDeleted = FALSE");
        query.setParameter("inUsername", username);
        
        try
        {
            return (Admin)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new UserNotFoundException("Admin Username " + username + " does not exist!");
        }
    }
    
    //note we should not allow update of username
    @Override
    public void updateAdmin(Admin admin) throws InputDataValidationException, UserNotFoundException
    {
        //setting the same username and password so 
        //it would not throw a constraint error when client data does not contain username and password
        Admin currentAdmin = retrieveAdminByUserId(admin.getUserId());
        admin.setUsername(currentAdmin.getUsername());
        admin.setPassword(currentAdmin.getPassword());
        
        Set<ConstraintViolation<Admin>>constraintViolations = validator.validate(admin);
        
        if(constraintViolations.isEmpty())
        {
            if(admin.getUserId()!= null)
            {
                Admin adminToUpdate = retrieveAdminByUserId(admin.getUserId());
                adminToUpdate.setEmail(admin.getEmail());
                adminToUpdate.setFirstName(admin.getFirstName());
                adminToUpdate.setLastName(admin.getLastName());
                // Username and password are deliberately NOT updated to demonstrate that client is not allowed to update account credential through this business method
            }
            else
            {
                throw new UserNotFoundException("User ID not provided for admin to be updated");
            }
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    
    
    @Override
    public void deleteAdmin(Long userId) throws UserNotFoundException
    {
        try {
            Admin adminToRemove = retrieveAdminByUserId(userId);
            List<Post> postsAssociated = adminToRemove.getPosts();
            List<Comment> commentsAssociated = adminToRemove.getComments();
            if(postsAssociated.isEmpty() && commentsAssociated.isEmpty()){
                em.remove(adminToRemove);
            } else {
                adminToRemove.setIsDeleted(true);
            }   
        } catch (UserNotFoundException ex) {
            throw new UserNotFoundException("Admin ID " + userId + " does not exist!");
        }       
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Admin>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
    
    private String prepareOffenceInputDataValidationErrorsMessage(Set<ConstraintViolation<Offences>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
    
     private String prepareEventInputDataValidationErrorsMessage(Set<ConstraintViolation<Event>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
    
    @Override
    public void approveSeller(Long sellerId) throws UserNotFoundException
    {
        try {
            Seller sellerToApprove = sellerSessionBeanLocal.retrieveSellerById(sellerId);
            sellerToApprove.setIsVerified(true);
            em.flush();
        }
        catch (UserNotFoundException ex){
            throw new UserNotFoundException("Seller ID: " + sellerId + "not found");
        }
    }

    public void banSeller(){
    }   
}
