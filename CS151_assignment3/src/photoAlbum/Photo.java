/**
 * CIS151 Spring 2025 Assignment 3 Solution
 * @author Nam Tan Ngo
 * @version 1.0 03/28/2025
 */

package photoAlbum;

import java.io.File;
import java.util.Date;

/**
 * a class to hold the information of an image:
 * 		- name
 * 		- absolute path to the image
 * 		- date the image was added to the album
 * 		- size of the file hold the image
 */
public class Photo {
    private String name;
    private File filePath;
    private Date dateAdded;
    private long fileSize;

    /**
     * instructor to set up a class hold the information of an image
     * @param filePath - absolute path
     * @throws IllegalArgumentException if can't find the image 
     * with the input path
     */
    public Photo (String filePath) {
    	try {
    		this.filePath = new File(filePath);
    		if(this.filePath.exists()) {
    			this.fileSize = filePath.length();
    		}	
    	}catch(Exception e) {
    		throw new IllegalArgumentException("can't find the image with path");
    	}
    	if(filePath.lastIndexOf("\\") != -1) {
    		this.name = filePath.substring(
    				filePath.lastIndexOf("\\")+1, filePath.lastIndexOf("."));
    	}
    	else {
    		this.name = filePath.substring(0, filePath.lastIndexOf("."));
    	}	
    	this.dateAdded = new Date();
    }
 
    /**
     * get the name of the image
     * @return name - a string
     */
    public String getName() {return name;}
    
    /**
     * get the absolute path of the image
     * @return filePath - a File
     */
    public File getPath() {return filePath;}
    
    /**
     * get the date added of the image
     * @return dateAdded - a Date
     */
    public Date getDate() {return dateAdded;}
    
    /**
     * get the size of the file hold the image
     * @return fileSize - a long
     */
    public long getSize() {return fileSize;}
}
