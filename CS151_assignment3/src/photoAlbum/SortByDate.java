/**
 * CIS151 Spring 2025 Assignment 3 Solution
 * @author Nam Tan Ngo
 * @version 1.0 03/28/2025
 */

package photoAlbum;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * a class to sort list of Photo by date added
 */
public class SortByDate implements SortingStrategy {
	
	/**
	 * sort method to sort Photo by date added from newest to oldest
	 * @param photos
	 * @return a sorted list of Photo
	 */
	 public List<Photo> sort(List<Photo> photos) {	 
		 List<Photo> clone = new LinkedList<Photo>();
		 clone.addAll(photos);
		 Collections.sort(clone, 
				 (Photo a, Photo b)->{
					 return a.getDate().compareTo(b.getDate());});
		 return clone.reversed();
	}
}
