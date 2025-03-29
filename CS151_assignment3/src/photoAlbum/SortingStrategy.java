/**
 * CIS151 Spring 2025 Assignment 3 Solution
 * @author Nam Tan Ngo
 * @version 1.0 03/28/2025
 */

package photoAlbum;

import java.util.List;

/**
 * an interface which hold method to sort list of Photo data type
 */
public interface SortingStrategy {
	/**
	 * sort method to sort Photo
	 * @param photos
	 * @return a sorted list of Photo
	 */
	 List<Photo> sort(List<Photo> photos);
}
