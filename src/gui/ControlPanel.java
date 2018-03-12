package gui;
import components.Doily;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControlPanel extends Container {

	private Doily doily;
	private int width;
	private int height;
	private Color selectedColor;
	private boolean eraserSet;
	private int penSize;
	private boolean linesVisible;
	private boolean reflectSet;
	private int sectors;
	
	public ControlPanel(int width, int height, Doily doily) {
		this.width = width;
		this.height = height;
		this.doily = doily;
		setLayout(new GridLayout(1, 3));
		setPreferredSize(new Dimension(width, height));
		eraserSet = false;
		penSize = 1;
		linesVisible = false;
		reflectSet = false;
		sectors = 1;

		
		//Adds all sections to Control Panel
		this.add(penGui());
		this.add(doilyGui());
		this.add(utilityGui());
	}
	
	public Container penGui() {
		//Components / Containers
		Container container = new Container();
		container.setLayout(new BorderLayout());
		
		Container topPenRow = new Container();
		topPenRow.setLayout(new FlowLayout());
		JLabel colourLabel;
		JButton colourSelector;
		JPanel colourSelectorBg;
		JLabel eraserLabel;
		JCheckBox eraser;
		Container penSizeContainer = new Container();
		penSizeContainer.setLayout(new FlowLayout());
		JLabel penSizeLabel;
		JSlider penSizeSlider;
		JLabel penLabel;
		
		
		//Pen section of configuration UI
		colourLabel = new JLabel("Colour:");
		topPenRow.add(colourLabel);
		colourSelector = new JButton();
		colourSelectorBg = new JPanel();
		colourSelectorBg.setBackground(Color.WHITE);
		colourSelector.add(colourSelectorBg);
		topPenRow.add(colourSelector);
		
		eraserLabel = new JLabel("Eraser:");
		topPenRow.add(eraserLabel);
		eraser = new JCheckBox();
		topPenRow.add(eraser);
		container.add(topPenRow, BorderLayout.NORTH);
		
		penSizeLabel = new JLabel("Size:");
		penSizeContainer.add(penSizeLabel);
		penSizeSlider = new JSlider(JSlider.HORIZONTAL, 0, 24, 0);
		penSizeSlider.setMajorTickSpacing(4);
		penSizeSlider.setMinorTickSpacing(1);
		penSizeSlider.setPaintTicks(true);
		penSizeSlider.setPaintLabels(true);
		penSizeContainer.add(penSizeSlider);
		container.add(penSizeContainer, BorderLayout.CENTER);
		
		penLabel = new JLabel("PEN", SwingConstants.CENTER);
		penLabel.setPreferredSize(new Dimension(width/3, 30));
		container.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.EAST);
		container.add(penLabel, BorderLayout.SOUTH);
		
		//Creates relevant listeners for Pen Components
		colourSelector.addActionListener(e -> {
	    	selectedColor = JColorChooser.showDialog(null, "Choose Colour", Color.WHITE);
	    	colourSelectorBg.setBackground(selectedColor);
	    	doily.setColour(selectedColor);
		});
		
		eraser.addActionListener(e -> {
			eraserSet = !eraserSet;
			doily.setEraser(eraserSet);
			System.out.println("Toggling eraser state, now: " + eraserSet);
		});
		
		penSizeSlider.addChangeListener(e -> {
			JSlider slider = (JSlider) e.getSource(); 
			penSize = slider.getValue();
			doily.setPenSize(penSize);
		});
		
		return container;
	}
	
	public Container doilyGui() {
		Container container = new Container();
		container.setLayout(new BorderLayout());
		
		Container topDoilyRow = new Container();
		topDoilyRow.setLayout(new FlowLayout());
		JLabel linesLabel;
		JCheckBox lines;
		JLabel reflectLabel;
		JCheckBox reflect;
		Container sectorsContainer = new Container();
		sectorsContainer.setLayout(new BorderLayout());
		JLabel sectorsLabel;
		JSlider sectorSlider;
		JLabel doilyLabel;
		
		
		//Doily section of configuration UI
		linesLabel = new JLabel("Show lines:");
		topDoilyRow.add(linesLabel);
		lines = new JCheckBox();
		topDoilyRow.add(lines);
		
		reflectLabel = new JLabel("Reflect:");
		topDoilyRow.add(reflectLabel);
		reflect = new JCheckBox();
		topDoilyRow.add(reflect);
		container.add(topDoilyRow, BorderLayout.NORTH);
		
		sectorsLabel = new JLabel("Sectors:");
		sectorsContainer.add(sectorsLabel, BorderLayout.WEST);
		sectorSlider = new JSlider(JSlider.HORIZONTAL, 0, 36, 0);
		sectorSlider.setMajorTickSpacing(4);
		sectorSlider.setMinorTickSpacing(1);
		sectorSlider.setPaintTicks(true);
		sectorSlider.setPaintLabels(true);
		sectorsContainer.add(sectorSlider, BorderLayout.EAST);
		
		container.add(sectorsContainer, BorderLayout.CENTER);
		
		doilyLabel = new JLabel("DOILY", SwingConstants.CENTER);
		doilyLabel.setPreferredSize(new Dimension(width/3, 30));
		container.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.EAST);
		container.add(doilyLabel, BorderLayout.SOUTH);
		
		//Creates relevant listeners for Doily Components
		
		lines.addActionListener(e -> {
			linesVisible = !linesVisible;
			doily.setLinesVisible(linesVisible);
		});

		reflect.addActionListener(e -> {
			reflectSet = !reflectSet;
			doily.setReflect(reflectSet);
		});
		
		sectorSlider.addChangeListener(e -> {
			JSlider slider = (JSlider) e.getSource(); 
			sectors = slider.getValue();
			if (sectors == 0) {
				sectors = 1;
			}
			doily.setSectors(sectors);
		});
		
		return container;
	}
	
	public Container utilityGui() {
		Container container = new Container();
		container.setLayout(new BorderLayout());
		
		Container topUtilRow = new Container();
		topUtilRow.setLayout(new FlowLayout());
		JButton undo;
		JButton redo;
		Container midUtilRow = new Container();
		midUtilRow.setLayout(new FlowLayout());
		JButton clear;
		JButton save;
		JLabel utilLabel;
		
		
		//Utility section of configuration UI
		undo = new JButton("Undo");
		topUtilRow.add(undo);
		
		redo = new JButton("Redo");
		topUtilRow.add(redo);
		container.add(topUtilRow, BorderLayout.NORTH);
		
		clear = new JButton("Clear");
		midUtilRow.add(clear);
		
		save = new JButton("Save");
		midUtilRow.add(save);
		container.add(midUtilRow, BorderLayout.CENTER);
		
		utilLabel = new JLabel("UTILITIES", SwingConstants.CENTER);
		utilLabel.setPreferredSize(new Dimension(width/3, 30));
		
		container.add(utilLabel, BorderLayout.SOUTH);
		
		//Creates relevant listeners for Utility buttons
		
		undo.addActionListener(e -> {
			System.out.println("Undo button pressed");
			doily.undo();
		});
		redo.addActionListener(e -> {
			System.out.println("Redo button pressed");
			doily.redo();
		});
		clear.addActionListener(e -> {
			System.out.println("Clear button pressed");
			doily.clear();
		});
		save.addActionListener(e -> {
			System.out.println("Save button pressed");
			doily.save();
		});
		
		return container;
	}
	
	/**
	 * Returns the pen colour to use, as selected by the user
	 * @return Color : Color object for the user's selected Colour
	 */
	private Color getPenColor() {
		return selectedColor;
	}
	
	/**
	 * Returns the size pen to use, as selected by the user
	 * @return int : Integer size of pen
	 */
	private int getPenSize() {
		return penSize;
	}
	
	/**
	 * Returns the state of the eraser
	 * @return boolean : True if the eraser is set, False if the eraser is not set
	 */
	private boolean eraserSet() {
		return eraserSet;
	}
	
	/**
	 * Returns whether the lines between doily sectors are visible
	 * @return boolean : True if visible, False if not.
	 */
	private boolean linesVisible() {
		return linesVisible;
	}
	
	/**
	 * Returns whether the sectors will reflect or not
	 * @return boolean : True if reflecting, False if not.
	 */
	private boolean reflectSet() {
		return reflectSet;
	}
	
	/**
	 * Returns the number of sectors to display, as selected by the User
	 * @return int : Integer number of sectors to display
	 */
	private int getSectors() {
		return sectors;
	}
}
