package photoAlbum;

public interface AlbumIterator {
    boolean hasNext(); 		// to check if there is a next element
    boolean hasPrevious(); 	// to check if there is a previous element 
    Photo current(); 		// to get the photo at the current position
    Photo next(); 			// to advance the iterator to the next position
    Photo previous(); 		// to advance the iterator to the previous position
	boolean isEmpty(); 		// check if album hold any photo
}
