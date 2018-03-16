package gui;

import components.Doily;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

public class ControlPanel extends Container {

	private Doily doily;
	private int width;
	private int height;
	private Color selectedColor;
	private boolean eraserSet = false;
	private int penSize = 1;
	private boolean linesVisible = false;
	private boolean reflectSet = false;
	private int sectors = 1;
	
	public ControlPanel(int width, int height, Doily doily) {
		this.width = width;
		this.height = height;
		this.doily = doily;
		setLayout(new GridLayout(1, 3));
		setPreferredSize(new Dimension(width, height));
		
		//Adds all sections to Control Panel
		add(penGui());
		add(doilyGui());
		add(utilityGui());
	}

	/**
	 * Initialises and adds GUI Components and listeners for Pen section of settings pane
	 * @return Container : Container with correctly configured pen GUI section
	 */
	private Container penGui() {
		//Components and Containers in Pen Configuration Section
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
		//Colour Selector
		colourLabel = new JLabel("Colour:");
		topPenRow.add(colourLabel);
		colourSelector = new JButton();
		colourSelectorBg = new JPanel();
		colourSelectorBg.setBackground(Color.WHITE);
		colourSelector.add(colourSelectorBg);
		topPenRow.add(colourSelector);
		//Eraser Toggle
		eraserLabel = new JLabel("Eraser:");
		topPenRow.add(eraserLabel);
		eraser = new JCheckBox();
		topPenRow.add(eraser);
		container.add(topPenRow, BorderLayout.NORTH);
		//Size slider
		penSizeLabel = new JLabel("Size:");
		penSizeContainer.add(penSizeLabel);
		penSizeSlider = new JSlider(JSlider.HORIZONTAL, 0, 24, 0);
		penSizeSlider.setMajorTickSpacing(4);
		penSizeSlider.setMinorTickSpacing(1);
		penSizeSlider.setPaintTicks(true);
		penSizeSlider.setPaintLabels(true);
		penSizeContainer.add(penSizeSlider);
		container.add(penSizeContainer, BorderLayout.CENTER);

		//Settings section label
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
		});
		
		penSizeSlider.addChangeListener(e -> {
			JSlider slider = (JSlider) e.getSource(); 
			penSize = slider.getValue();
			doily.setPenSize(penSize);
		});
		
		return container;
	}

	/**
	 * Initialises and adds GUI Components and listeners for Doily section of settings pane
	 * @return Container : Container with correctly configured doily GUI section
	 */
	private Container doilyGui() {
		//Components and Containers in Doily Configuration section
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
		//Sector lines checkbox
		linesLabel = new JLabel("Sector lines:");
		topDoilyRow.add(linesLabel);
		lines = new JCheckBox();
		topDoilyRow.add(lines);
		//Reflect checkbox
		reflectLabel = new JLabel("Reflect:");
		topDoilyRow.add(reflectLabel);
		reflect = new JCheckBox();
		topDoilyRow.add(reflect);
		container.add(topDoilyRow, BorderLayout.NORTH);
		//Sectors slider
		sectorsLabel = new JLabel("Sectors:");
		sectorsContainer.add(sectorsLabel, BorderLayout.WEST);
		sectorSlider = new JSlider(JSlider.HORIZONTAL, 0, 36, 0);
		sectorSlider.setMajorTickSpacing(4);
		sectorSlider.setMinorTickSpacing(1);
		sectorSlider.setPaintTicks(true);
		sectorSlider.setPaintLabels(true);
		sectorsContainer.add(sectorSlider, BorderLayout.EAST);
		
		container.add(sectorsContainer, BorderLayout.CENTER);

		//Doily Label
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
			//Slider's default position is zero, for aesthetic reasons, however there is always at least 1 sector.
			if (sectors == 0) {
				sectors = 1;
			}
			doily.setSectors(sectors);
		});
		
		return container;
	}

	/**
	 * Initialises and adds GUI Components and listeners for Utility section of settings pane
	 * @return Container : Container with correctly configured utility GUI section
	 */
	private Container utilityGui() {
		//Containers and Components in Utility section
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
		//Undo button
		undo = new JButton("Undo");
		topUtilRow.add(undo);
		//Redo button
		redo = new JButton("Redo");
		topUtilRow.add(redo);
		container.add(topUtilRow, BorderLayout.NORTH);
		//Clear button
		clear = new JButton("Clear");
		midUtilRow.add(clear);
		//Save button
		save = new JButton("Save");
		midUtilRow.add(save);
		container.add(midUtilRow, BorderLayout.CENTER);
		//Utilities label
		utilLabel = new JLabel("UTILITIES", SwingConstants.CENTER);
		utilLabel.setPreferredSize(new Dimension(width/3, 30));
		
		container.add(utilLabel, BorderLayout.SOUTH);
		
		//Creates relevant listeners for Utility buttons
		undo.addActionListener(e -> doily.undo() );
		redo.addActionListener(e -> doily.redo() );
		clear.addActionListener(e -> doily.clear() );
		save.addActionListener(e -> doily.save() );
		
		return container;
	}
}
