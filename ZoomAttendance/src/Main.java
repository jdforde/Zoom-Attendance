/**
 * @author: Jakob Forde
 * ID: 1214487066
 * Class ID: 70605
 * Assignment: Final Project
 * 
 * This class acts as the driver code. It creates the view which will be observing the Repository. 
 * It also adds the File and About menus so the user can start adding their CSV files. This is part
 * of the "View" in the MVC model that this program follows. 
 */

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private View v;
	
	/**
	 * Main constructor builds the JFrame by adding view and the two Menu items in their correct positions. 
	 */
	public Main() {
		JMenuBar menuBar = new JMenuBar();
		JMenuItem about = new JMenuItem("About");
		
		add(menuBar, BorderLayout.PAGE_START);
		v = new View();
		add(v, BorderLayout.CENTER);
		
		JMenu file = new JMenu("File");
		JMenuItem loadARoster = new JMenuItem("Load a Roster");
		JMenuItem addAttendance = new JMenuItem("Add Attendance");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem plotData = new JMenuItem("Plot Data");

		file.add(loadARoster);
		file.add(addAttendance);
		file.add(save);
		file.add(plotData);
		
		menuBar.add(file);
		menuBar.add(about);
		
		ActionListener buttonControl = new ButtonController(about, loadARoster,addAttendance, save, plotData, v);
		about.addActionListener(buttonControl);
		loadARoster.addActionListener(buttonControl);
		save.addActionListener(buttonControl);
		plotData.addActionListener(buttonControl);
		addAttendance.addActionListener(buttonControl);		
		
	}

	/**
	 * Builds the world and displays it to the user. 
	 * @param args
	 */
	public static void main(String[] args) {
		Main world = new Main();
		world.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		world.setSize(800,600);
		world.setResizable(false);
		world.setTitle("CSE360 Final Project");
		world.setVisible(true);

	}

}
