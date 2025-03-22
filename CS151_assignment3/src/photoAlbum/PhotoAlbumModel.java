package photoAlbum;

import java.util.LinkedList;
import java.util.TreeSet;

import javax.swing.event.ChangeEvent;

public class PhotoAlbumModel {
	private LinkedList<Photo> photos;
	private TreeSet<PhotoAlbumView> viewers;
	
	public PhotoAlbumModel() {
		photos = new LinkedList<Photo>();
		viewers = new  TreeSet<PhotoAlbumView>();
	}
	
	public void addPhoto(Photo photo) {
		photos.add(photo);
		ChangeEvent event = new ChangeEvent(this);
		for(PhotoAlbumView viewer: viewers) {
			viewer.stateChanged(event);
		}
	}
	
	public AlbumIterator iterator() {
		return new AlbumIterator() {
			private int current = 0;
		    public boolean hasNext() {return current < photos.size();} 	// to check if there is a next element
		    public boolean hasPrevious() {return current > 0;} 			// to check if there is a previous element 
		    public Photo current(){return photos.get(current);} 		// to get the photo at the current position
		    public Photo next() {return photos.get(current++);} 		// to advance the iterator to the next position
		    public Photo previous(){return photos.get(current--);} 		// to advance the iterator to the previous position
		};
	}
	
	public void addViewer(PhotoAlbumView viewer) {
		viewers.add(viewer);
	}
	
	
}
