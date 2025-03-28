package photoAlbum;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SortBySize implements SortingStrategy {
	 public List<Photo> sort(List<Photo> photos) {	 
		 List<Photo> clone = new LinkedList<Photo>();
		 clone.addAll(photos);
		 Collections.sort(clone, 
				 (Photo a, Photo b)->{
					 return Long.compare(a.getSize(), b.getSize());});
		 return clone;
	}
}
