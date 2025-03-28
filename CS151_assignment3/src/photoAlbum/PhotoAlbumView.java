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

public class PhotoAlbumView {
	private JPanel middlePanel;
	private JPanel detailPanel;
	private List<Photo> photos;
	private SortingStrategy sortClass;
	private AlbumIterator iterator;
	
	public PhotoAlbumView(List<Photo> photos, SortingStrategy sortClass) {
		this.sortClass = sortClass;
		this.photos = sortClass.sort(photos);
		
		detailPanel = new JPanel();
		detailPanel.setPreferredSize(new Dimension(200, 500));
		detailPanel.setBackground(new Color(210,210,210));
		detailPanel.setLayout(new FlowLayout(FlowLayout.LEFT));	
		Border border = BorderFactory.createMatteBorder(4, 0, 4, 0, new Color(71,145,255));
		detailPanel.setBorder(border);
		
		middlePanel = new JPanel();
		middlePanel.setPreferredSize(new Dimension(700, 500));
		middlePanel.setLayout(new BorderLayout());	
		Border border2 = BorderFactory.createMatteBorder(4, 4, 4, 0, new Color(71,145,255));
		middlePanel.setBorder(border2);

		this.reset();
}
	
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
	
	public void reset() {
		this.photos = sortClass.sort(photos);
		this.iterator = iterator();
		current();
		updateDetailPanel(200,500);
	}
	
	public void addState(ChangeEvent event) {		
		PhotoAlbumModel source = (PhotoAlbumModel) event.getSource();
		this.photos = sortClass.sort(source.getPhotos());
		this.iterator = iterator();
		current();
		updateDetailPanel(200,500);
	}
	
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
	
	public void setSort(SortingStrategy sortClass) {this.sortClass = sortClass;}
	public JPanel getDetailPanel() {return detailPanel;}
	public JPanel getMiddlePanel() {return middlePanel;}
	
	private void updateMainPanel(int boundX, int boundY,Photo photo) {
		JLabel label = this.photoToJPanel(photo, boundX - 50, boundY - 50);
		label.setPreferredSize(new Dimension(boundX, boundY));
		middlePanel.add(label, BorderLayout.CENTER);
	}
	
	public void next() {
		middlePanel.removeAll();
		if(iterator != null && iterator.hasNext()) {
			updateMainPanel(700, 500, iterator.next());
		}
		else {
			current();
		}
		middlePanel.repaint();
	}
	
	public void previous() {
		middlePanel.removeAll();
		if(iterator != null && iterator.hasPrevious()) {
			updateMainPanel(700, 500, iterator.previous());
		}
		else {
			current();
		}
		middlePanel.repaint();
	}
	
	public void current() {
		middlePanel.removeAll();
		if(!iterator.isEmpty()) {
			updateMainPanel(700, 500, iterator.current());
		}
		middlePanel.repaint();			
	}
	
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
			throw new IllegalArgumentException("some Error in updateDetailPanel");
			}
		detailPanel.repaint();
	}
	
	private JPanel makeIconPanel(int boundX, int boundY, Photo photo) {
		try {	
					Border border = BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0,0,0));
					Border borderName = BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0,0,0));

					//image
					JLabel image = this.photoToJPanel(photo, boundX -10, boundY -10);
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
			throw new IllegalArgumentException("some Error in updateDetailPanel");
			}
	}
	
	private JLabel photoToJPanel(Photo photo, int boundX, int boundY) {
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