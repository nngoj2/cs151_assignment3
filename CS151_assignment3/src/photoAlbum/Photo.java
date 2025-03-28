package photoAlbum;

import java.io.File;
import java.util.Date;

public class Photo {
    private String name;
    private File filePath;
    private Date dateAdded;
    private long fileSize;

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
 
    public String getName() {return name;}
    public File getPath() {return filePath;}
    public Date getDate() {return dateAdded;}
    public long getSize() {return fileSize;}
}
