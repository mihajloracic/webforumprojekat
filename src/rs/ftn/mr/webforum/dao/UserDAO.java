package rs.ftn.mr.webforum.dao;

import java.util.List;

import rs.ftn.mr.webforum.entities.User;

public interface UserDAO {

	/**
	 * Gets a specific <code>User</code>.
	 * 
	 * @param userId The User ID to search
	 * @return <code>User</code>object containing all the information
	 * @see #selectAll
	 */
	public User selectById(int userId) ;
	
	/**
	 * Gets a specific <code>User</code>.
	 * 
	 * @param username The User name to search
	 * @return <code>User</code> object containing all the information
	 * or <code>null</code> if no data was found. 
	 * @see #selectAll
	 */
	public User selectByName(String userName) ;
	/**
	 * Gets all users
	 * 
	 * @return <code>ArrayList</code> with the users. Each entry is an <code>User</code> object
	 */
	public List selectAll() ;
	/**
	 * Deletes an user.
	 * 
	 * @param userId The user ID to delete
	 * @see #undelete(int)
	 */
	public void delete(int userId) ;

	
	/**
	 * Updates a user.
	 * 
	 * @param user Reference to a <code>User</code> object to update
	 */
	public void update(User user) ;
	
	/**
	 * Adds a new User.
	 * After successfuly persisting the data, this method
	 * <b>shoud</b> call <code>user.setId(theNewId);</code>, as well
	 * return the new user id. 
	 * @param user Reference to a valid and configured <code>User</code> object
	 * @return The new user id
	 */
	public int addNew(User user);

	

}
