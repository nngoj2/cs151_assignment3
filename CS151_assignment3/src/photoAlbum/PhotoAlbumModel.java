/**
 * CIS151 Spring 2025 Assignment 3 Solution
 * @author Nam Tan Ngo
 * @version 1.0 03/28/2025
 */

package photoAlbum;

import java.util.LinkedList;
import java.util.List;

import javax.swing.event.ChangeEvent;

/**
 * a class that hold a list of Photo and a list of viewer
 * the data in this class are not sorted, only insert in order
 */
public class PhotoAlbumModel {
	private List<Photo> photos;
	private List<PhotoAlbumView> viewers;
	
	/**
	 * instructor which initial the object with 3 viewers
	 */
	public PhotoAlbumModel() {
		photos = new LinkedList<Photo>();
		viewers = new  LinkedList<PhotoAlbumView>();
		viewers.add(new PhotoAlbumView(photos, new SortByDate()));
		viewers.add(new PhotoAlbumView(photos, new SortByName()));
		viewers.add(new PhotoAlbumView(photos, new SortBySize()));
	}
	
	/**
	 * mutator
	 * add Photo to the list and notify all viewers
	 * @param photo - a Photo will be added
	 */
	public void addPhoto(Photo photo) {
		photos.add(photo);
		ChangeEvent event = new ChangeEvent(this);
		for(PhotoAlbumView viewer: viewers) {
			viewer.addState(event);
		}
	}
	
	/**
	 * mutator
	 * delete a photo in the list by name and notify all viewers
	 * @param photo - a Photo will be deleted
	 */
	public void deletePhoto(Photo photo) {
		photos.remove(photo);
		for(PhotoAlbumView viewer: viewers) {
			viewer.deleteState(photo);
		}
	}
	
	/**
	 * search for a Photo in the list with the input name
	 * @param name - a string
	 * @return the first inserted photo with the same name
	 * @throws IllegalArgumentException if can't find the Photo with the same name
	 */
	public Photo searchPhoto(String name) {
		for(Photo photo:photos) {
			if(photo.getName().equals(name)) {
				return photo;
			}
		}
		throw new IllegalArgumentException("searchPhoto error: can't find the image with name: " + name);
	}
	
	/**
	 * add viewer to the model
	 * @param viewer - a PhotoAlbumView
	 */
	public void addViewer(PhotoAlbumView viewer) {viewers.add(viewer);}
	
	/**
	 * get the viewer with index i in list of viewer
	 * @param i - an integer represent a viewer in list of viewers
	 * @return - viewer, an PhotoAlbumView
	 * @throws IllegalArgumentException if viewers list doesn't contain viewer
	 * with that index
	 */
	public PhotoAlbumView getViewer(int i) {
		if(viewers.size()>i) {return viewers.get(i);}
		throw new IllegalArgumentException("getViewer error: model doesn't contain this viewer index");
	}
	
	/**
	 * accessor
	 * return list of Photo of the model
	 * @return list of Photo
	 */
	public List<Photo> getPhotos() {return photos;}
	
}
