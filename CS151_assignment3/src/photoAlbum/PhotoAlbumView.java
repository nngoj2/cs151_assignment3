/**
 * CIS151 Spring 2025 Assignment 3 Solution
 * @author Nam Tan Ngo
 * @version 1.0 03/28/2025
 */

package photoAlbum;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;

/**
 * a class of viewer which show the data in model class that organized by
 * a sort class 
 */
public class PhotoAlbumView {
	private JPanel middlePanel;
	private JPanel detailPanel;
	private List<Photo> photos;
	private SortingStrategy sortClass;
	private AlbumIterator iterator;
	
	/**
	 * instructor which initial an viewer
	 * @param photos - a list of Photo
	 * @param sortClass - a class that hold method to sort Photo list
	 */
	public PhotoAlbumView(List<Photo> photos, SortingStrategy sortClass) {
		this.sortClass = sortClass;
		this.photos = sortClass.sort(photos);
		
		// set the left panel
		detailPanel = new JPanel();
		detailPanel.setPreferredSize(new Dimension(200, 500));
		detailPanel.setBackground(new Color(210,210,210));
		detailPanel.setLayout(new FlowLayout(FlowLayout.LEFT));	
		Border border = BorderFactory.createMatteBorder(4, 0, 4, 0, new Color(71,145,255));
		detailPanel.setBorder(border);
		
		// set the center panel
		middlePanel = new JPanel();
		middlePanel.setPreferredSize(new Dimension(700, 500));
		middlePanel.setLayout(new BorderLayout());	
		Border border2 = BorderFactory.createMatteBorder(4, 4, 4, 0, new Color(71,145,255));
		middlePanel.setBorder(border2);

		this.reset();
}
	
	/**
	 * a method return an iterator class to access the list of photo
	 * @return a class implement AlbumIterator
	 */
	public AlbumIterator iterator() {
		return new AlbumIterator() {
			private int current = 0;
		    public boolean hasNext() {return current < (photos.size() - 1);} 	
		    public boolean hasPrevious() {return current > 0;}
		    public boolean isEmpty() {return photos.isEmpty();}
		    public Photo current(){return photos.get(current);}
		    public Photo next() {return photos.get(++current);}
		    public Photo previous(){return photos.get(--current);}
		};
	}
	
	/**
	 * a reset class that: 
	 * 		- re-sort the album with sort class, 
	 * 		- update left/center panels, 
	 * 		- reset the iterator
	 */
	public void reset() {
		this.photos = sortClass.sort(photos);
		this.iterator = iterator();
		current();
		updateDetailPanel(200,500);
	}
	
	/**
	 * a method to update the viewer after model add new Photo
	 * @param event - a ChangeEvent
	 */
	public void addState(ChangeEvent event) {		
		PhotoAlbumModel source = (PhotoAlbumModel) event.getSource();
		this.photos = sortClass.sort(source.getPhotos());
		this.iterator = iterator();
		current();
		updateDetailPanel(200,500);
	}
	
	/**
	 * a method to update the viewer after model delete a Photo
	 * @param photo - the Photo that was deleted
	 */
	public void deleteState(Photo photo) {		
		Photo temp = iterator.current();
		photos.remove(photo);	
		this.iterator = iterator();	
		if(temp != photo) {
			while(iterator.current() != temp) {
				iterator.next();
			}
		}
		current();
		updateDetailPanel(200,500);
	}
	
	/**
	 * set the sort class of the viewer
	 * @param sortClass - a SortingStrategy
	 */
	public void setSort(SortingStrategy sortClass) {this.sortClass = sortClass;}
	
	/**
	 * get the detail panel (left panel in the frame)
	 * @return detailPanel - a JPanel
	 */
	public JPanel getDetailPanel() {return detailPanel;}
	
	/**
	 * get the middle panel (center panel in the frame)
	 * @return middlePanel - a JPanel
	 */
	public JPanel getMiddlePanel() {return middlePanel;}
	
	/**
	 * update the middle panel with new Photo
	 * @param boundX - an integer hold the width of the panel
	 * @param boundY - an integer hold the height of the panel
	 * @param photo - the photo will be displayed in the middle panel
	 */
	private void updateMainPanel(int boundX, int boundY,Photo photo) {
		try {
			JLabel label = this.photoToJPanel(boundX - 50, boundY - 50, photo);
			label.setPreferredSize(new Dimension(boundX, boundY));
			middlePanel.add(label, BorderLayout.CENTER);
		}
		catch(Exception e) {throw e;}
	}
	
	/**
	 * friendly version use of updateMainPanel which update middle panel
	 * to the next Photo in the list
	 */
	public void next() {
		middlePanel.removeAll();
		if(iterator != null && iterator.hasNext()) {
			try {updateMainPanel(700, 500, iterator.next());}
			catch(Exception e) {throw e;}
		}
		else {
			current();
		}
		middlePanel.repaint();
	}
	
	/**
	 * friendly version use of updateMainPanel which update middle panel
	 * to the previous Photo in the list
	 */
	public void previous() {
		middlePanel.removeAll();
		if(iterator != null && iterator.hasPrevious()) {
			try {updateMainPanel(700, 500, iterator.previous());}
			catch(Exception e) {throw e;}
		}
		else {
			current();
		}
		middlePanel.repaint();
	}
	
	/**
	 * friendly version use of updateMainPanel which update middle panel
	 * to the current Photo in the list
	 */
	public void current() {
		middlePanel.removeAll();
		if(!iterator.isEmpty()) {
			try {updateMainPanel(700, 500, iterator.current());}
			catch(Exception e) {throw e;}
		}
		middlePanel.repaint();			
	}
	
	/**
	 * update the detail panel with list of Photo inside Viewer
	 * work with makeIconPanel method
	 * @param boundX - an integer hold the width of the panel
	 * @param boundY - an integer hold the height of the panel
	 * @throws IllegalArgumentException for any error
	 */
	public void updateDetailPanel(int boundX, int boundY) {
		AlbumIterator tempIterator = iterator();
		detailPanel.removeAll();
		try {	
			if(!tempIterator.isEmpty()) {
				detailPanel.add(makeIconPanel(
						boundX/2 - 10, 
						boundX/2 - 10, 
						tempIterator.current()) );
			}
			while(tempIterator.hasNext()){
				detailPanel.add(makeIconPanel(
						boundX/2 - 10, 
						boundX/2 - 10, 
						tempIterator.next()) );		
			};
		}	
		catch(Exception e) {
			throw new IllegalArgumentException("some Error in updateDetailPanel:", e);
			}
		detailPanel.repaint();
	}
	
	/**
	 * convert an Photo data type to a JPanel with image and name
	 * works with photoToJPanel function
	 * @param boundX - an integer, which hold the JPanel width
	 * @param boundY - an integer, which hold the JPanel height
	 * @param photo -  a Photo, hold information of the image
	 * @return a JPanel which hold a list of image icon
	 * @throws IllegalArgumentException for any error
	 */
	private JPanel makeIconPanel(int boundX, int boundY, Photo photo) {
		try {	
					Border border = BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0,0,0));
					Border borderName = BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0,0,0));

					//image
					JLabel image = this.photoToJPanel(boundX -10, boundY -10, photo);
					image.setLayout(new FlowLayout(FlowLayout.CENTER));
					
					// name
					JLabel name = new JLabel(photo.getName());		
					JPanel namePanel = new JPanel();
					namePanel.add(name);
					namePanel.setBorder(borderName);
					
					JPanel panel = new JPanel();
					panel.setLayout(new BorderLayout());
					panel.setPreferredSize(new Dimension(boundX, boundY));
					panel.add(image, BorderLayout.CENTER);
					panel.add(namePanel, BorderLayout.SOUTH);
					panel.setBorder(border);
					return panel;	
		}
		catch(Exception e) {
			throw new IllegalArgumentException("some Error in makeIconPanel: ", e);
			}
	}
	
	/**
	 * convert a Photo to a JLabel
	 * @param boundX - an integer, which hold the JLabel width
	 * @param boundY - an integer, which hold the JLabel height
	 * @param photo -  a Photo, hold information of the image
	 * @return a JLabel, which hold the image
	 * @throws IOException if can't read the image
	 * @throws IllegalArgumentException for any error
	 */
	private JLabel photoToJPanel(int boundX, int boundY, Photo photo) {
		try {
			BufferedImage image = ImageIO.read(photo.getPath());
			Image scaledImage;
			if(image.getHeight()>image.getWidth()) {
				scaledImage = image.getScaledInstance(-1, boundY, Image.SCALE_SMOOTH);} 
			else { 
				scaledImage =image.getScaledInstance(boundX, -1, Image.SCALE_SMOOTH);}
				 
			ImageIcon icon = new ImageIcon(scaledImage);	
			return new JLabel(icon);		
		}
		catch (IOException e) {e.printStackTrace();}
		catch(Exception e) {throw new IllegalArgumentException("photoToJPanel error: can't read photo");}
		throw new IllegalArgumentException("photoToJPanel error: can't return JPanel");
	}
}