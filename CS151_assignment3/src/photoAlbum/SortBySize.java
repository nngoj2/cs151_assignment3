package photoAlbum;

import java.util.Collections;
import java.util.List;

public class SortBySize implements SortingStrategy {
	 public List<Photo> sort(List<Photo> photos) {	 
		 Collections.sort(photos, (Photo a, Photo b)->{return Long.compare(a.getSize(), b.getSize());});
		 return photos;
	}
}
