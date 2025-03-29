/**
 * CIS151 Spring 2025 Assignment 3 Solution
 * @author Nam Tan Ngo
 * @version 1.0 03/28/2025
 */

package photoAlbum;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * a class to received user input and notify the model about any change
 */
public class PhotoAlbumController {
	private JLabel northLabel;
	private JPanel westPanel;
	private JPanel centerPanel;
	private JFrame frame;
	private PhotoAlbumModel model;
	private PhotoAlbumView viewer;
	
	/**
	 * the instructor which create new model and the user interface
	 */
	public PhotoAlbumController() {
		this.model = new PhotoAlbumModel();
		this.viewer = model.getViewer(0);
		
		northLabel = new JLabel("Photo Album Manager");
		northLabel.setHorizontalAlignment(SwingConstants.CENTER);	
		
		westPanel =viewer.getDetailPanel();
		
		centerPanel = viewer.getMiddlePanel();

		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setSize(900, 600);
		frame.add(northLabel, BorderLayout.NORTH);
		frame.add(westPanel, BorderLayout.WEST);
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(initSouthPanel(), BorderLayout.SOUTH);
		frame.setTitle("Photo Album Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
	}

	/**
	 * the method to create the south panel which will be placed 
	 * at the bottom of the frame. not included in the instructor to
	 * increase readability
	 * @return JPanel
	 */
	private JPanel initSouthPanel() {
		JButton addB = new JButton("Add Photo");
		JButton delB = new JButton("Delete Photo");
		JButton nextB = new JButton("Next");
		JButton preB = new JButton("Previous");
		JButton sortNameB = new JButton("Sort By Name");
		JButton sortDateB = new JButton("Sort By Date");
		JButton sortSizeB = new JButton("Sort By Size");	
		JPanel container = new JPanel();

		addB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
		        JFileChooser fileChooser = new JFileChooser(new File("."));
		        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); 
		        int result = fileChooser.showOpenDialog(null); 
		        if (result == JFileChooser.APPROVE_OPTION) {
		            File selectedFile = fileChooser.getSelectedFile();
		            String filePath = selectedFile.getAbsolutePath();
		            model.addPhoto(new Photo(filePath));
		        } else {
		            System.out.println("No file selected.");
		        }
		        frame.setVisible(true);	
			}
		});
		
		delB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
		        String name = JOptionPane.showInputDialog(
		        		delB, 
		        		"Enter the photo's name you want to delete: ", 
		        		"Input", 
		        		JOptionPane.QUESTION_MESSAGE);
				try {
			        if (name != null) {model.deletePhoto(model.searchPhoto(name));}
				}catch(Exception e) {throw e;}	
				frame.setVisible(true);	
			}	
		});
		
		nextB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				viewer.next();
				frame.setVisible(true);	
			}
		});
		
		preB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				viewer.previous();
				frame.setVisible(true);	
			}
		});
		
		sortDateB.addActionListener(initSortB(0));
		sortNameB.addActionListener(initSortB(1));
		sortSizeB.addActionListener(initSortB(2));
		
		
		container.setBackground(new Color(170,170,170));
		container.add(addB);
		container.add(delB);
		container.add(nextB);
		container.add(preB);
		container.add(sortNameB);
		container.add(sortDateB);
		container.add(sortSizeB);

		JPanel menu = new JPanel();
		menu.setPreferredSize(new Dimension(200, 60));
		menu.setBackground(new Color(170,170,170));
		menu.setLayout(new GridBagLayout());

		menu.add(container);
		return menu;
	}
	
	/**
	 * add ActionListener to "sort JButton" in initSouthPanel
	 * separate to increase readability
	 * @param i - an integer represent viewer in model
	 * 		0: viewer with sort-by-date sort class
	 * 		1: viewer with sort-by-name sort class
	 * 	 	2: viewer with sort-by-size sort class
	 * @return ActionListener class
	 */
	private ActionListener initSortB(int i) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				viewer = model.getViewer(i);
				viewer.reset();
				frame.remove(westPanel);
				westPanel = viewer.getDetailPanel();
				frame.add(westPanel, BorderLayout.WEST);
				
				frame.remove(centerPanel);
				centerPanel = viewer.getMiddlePanel();
				frame.add(centerPanel, BorderLayout.CENTER);	
				frame.setVisible(true);	
			}
		};
	}


}

