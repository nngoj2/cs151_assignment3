package photoAlbum;

import java.util.Collections;
import java.util.List;

public class SortByName implements SortingStrategy {
	 public List<Photo> sort(List<Photo> photos) {	 
		 Collections.sort(photos, (Photo a, Photo b)->{return a.getName().compareTo(b.getName());});
		 return photos;
	}
}
