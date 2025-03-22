package photoAlbum;

import java.io.File;
import java.util.Date;

public class Photo {
    private String name;
    private File filePath;
    private Date dateAdded;
    private long fileSize;

    // Constructor, getters, and setters
    public Photo (String filePath) {
    	try {
    		this.filePath = new File(filePath);
    		if(this.filePath.exists()) {
    			this.fileSize = filePath.length();
    		}	
    	}catch(Exception e) {
    		throw new IllegalArgumentException("can't find the image with path");
    	}
    	this.name = filePath.substring(filePath.lastIndexOf("\\")+1, filePath.lastIndexOf("."));
    	this.dateAdded = new Date();
    }
    
    public Photo(Photo p) {
    	name = p.getName();
    	filePath = p.getPath();
    	dateAdded = p.getDate();
    	fileSize = p.getSize();
    }
    
    public String getName() {return name;}
    public File getPath() {return filePath;}
    public Date getDate() {return dateAdded;}
    public long getSize() {return fileSize;}
}
