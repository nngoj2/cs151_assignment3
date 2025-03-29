/**
 * CIS151 Spring 2025 Assignment 3 Solution
 * @author Nam Tan Ngo
 * @version 1.0 03/28/2025
 */

package photoAlbum;

/**
 * an interface hold methods to access list of Photo
 */
public interface AlbumIterator {
	
	/**
	 * to check if there is a next element
	 * @return true if there is, false otherwise
	 */
    boolean hasNext();
    
	/**
	 * to check if there is a previous element
	 * @return true if there is, false otherwise
	 */
    boolean hasPrevious();
    
    /**
	 * to check if album hold any photo
	 * @return true if there is, false otherwise
	 */
	boolean isEmpty();
	
    /**
	 * to get the photo at the current position
	 * @return Photo at index current
	 */
    Photo current();
    
    /**
	 * to get the photo at the next position
	 * also set current to next index
	 * @return Photo at index current + 1
	 */
    Photo next();
    
    /**
	 * to get the photo at the previous position
	 * also set current to previous index
	 * @return Photo at index current - 1
	 */
    Photo previous();

}
