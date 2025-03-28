package photoAlbum;

import java.util.LinkedList;
import java.util.List;

import javax.swing.event.ChangeEvent;

public class PhotoAlbumModel {
	private List<Photo> photos;
	private List<PhotoAlbumView> viewers;
	
	public PhotoAlbumModel() {
		photos = new LinkedList<Photo>();
		viewers = new  LinkedList<PhotoAlbumView>();
		viewers.add(new PhotoAlbumView(photos, new SortByDate()));
		viewers.add(new PhotoAlbumView(photos, new SortByName()));
		viewers.add(new PhotoAlbumView(photos, new SortBySize()));
	}
	
	public void addPhoto(Photo photo) {
		photos.add(photo);
		ChangeEvent event = new ChangeEvent(this);
		for(PhotoAlbumView viewer: viewers) {
			viewer.addState(event);
		}
	}
	
	public void deletePhoto(Photo photo) {
		photos.remove(photo);
		for(PhotoAlbumView viewer: viewers) {
			viewer.deleteState(photo);
		}
	}
	
	public Photo searchPhoto(String name) {
		for(Photo photo:photos) {
			if(photo.getName().equals(name)) {
				return photo;
			}
		}
		throw new IllegalArgumentException("can't find the image with name: " + name);
	}
	
	public void addViewer(PhotoAlbumView viewer) {viewers.add(viewer);}
	
	public PhotoAlbumView getViewer(int i) {
		if(viewers.size()>i) {return viewers.get(i);}
		throw new IllegalArgumentException("model doesn't contain this viewer index");
	}
	public List<Photo> getPhotos() {return photos;}
	
}
