package photoAlbum;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

public class PhotoAlbumView {
	private JLabel label;
	private JPanel leftPanel;
	private JPanel centerPanel;
	private JPanel menu;
	private JFrame frame;
	

	
	public PhotoAlbumView() {
		/////////////////////////////////NORTH////////////////////////////////////
		label = new JLabel("Photo Album Manager");
		///////////////////////////////////LEFT//////////////////////////////////
		
		leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(300, 200));
		leftPanel.setBackground(Color.BLUE);

		
		///////////////////////////////////CENTER//////////////////////////////////
		centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(200, 200));
		centerPanel.setBackground(Color.ORANGE);

		///////////////////////////////////SOUTH////////////////////////////////////
		JButton addB = new JButton("Add Photo");
		JButton delB = new JButton("Delete Photo");
		JButton nextB = new JButton("Next");
		JButton preB = new JButton("Previous");
		JButton sortNameB = new JButton("Sort By Name");
		JButton sortDateB = new JButton("Sort By Date");
		JButton sortSizeB = new JButton("Sort By Size");
		
		menu = new JPanel();
		menu.setPreferredSize(new Dimension(200, 50));
		menu.setBackground(Color.GREEN);

		menu.add(addB);
		menu.add(delB);
		menu.add(nextB);
		menu.add(preB);
		menu.add(sortNameB);
		menu.add(sortDateB);
		menu.add(sortSizeB);	
		/////////////////////////////////////////////////////////////////////////////
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setSize(800, 800);
		frame.add(label, BorderLayout.NORTH);
		frame.add(leftPanel, BorderLayout.WEST);
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(menu, BorderLayout.SOUTH);
		frame.setTitle("Photo Album Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.pack();
		frame.setVisible(true);		
	}
	
	public void stateChanged(ChangeEvent event) {
		PhotoAlbumModel source = (PhotoAlbumModel) event.getSource();
		AlbumIterator iterator = source.iterator();
		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		while(iterator.hasNext()) {
			try {
				Photo photo = new Photo(iterator.next());
				
				BufferedImage image = ImageIO.read(photo.getPath());
				if(image.getHeight()>image.getWidth()) {
					image.getScaledInstance((image.getWidth()*40)/image.getHeight(), 40, Image.SCALE_SMOOTH);
				}
				else {
					image.getScaledInstance(50, (image.getHeight()*50)/image.getWidth(), Image.SCALE_SMOOTH);
				}
				ImageIcon icon = new ImageIcon(image);				
				JLabel imageLabel = new JLabel(icon);
				JPanel panel = new JPanel();
				panel.add(imageLabel);
				panel.add(new JLabel(photo.getName()));
				leftPanel.add(panel);		
			}
			catch (IOException e) {e.printStackTrace();}
			catch(Exception e) {throw new IllegalArgumentException("some Error in stateChanged");}
		}
		frame.add(leftPanel, BorderLayout.WEST);
	}
	
}
