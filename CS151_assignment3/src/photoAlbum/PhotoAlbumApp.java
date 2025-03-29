/**
 * CIS151 Spring 2025 Assignment 3 Solution
 * @author Nam Tan Ngo
 * @version 1.0 03/28/2025
 */

package photoAlbum;

/**
 * a class to hold the main method
 * the start of the program
 */
public class PhotoAlbumApp {
	
	/**
	 * start of the program
	 * @param args - a string array
	 */
	public static void main(String[] args) {
	     try {PhotoAlbumController controller = new PhotoAlbumController();}
	     catch(Exception e) {System.out.println(e);}
	}
}