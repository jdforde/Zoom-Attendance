/**
 * @author: Jakob Forde
 * ID: 1214487066
 * Class ID: 70605
 * Assignment: Final Project
 * 
 * This class handles all of the button pushing by the user. It represents the
 * "Controller" in the MVC architecture that this program implements. 
 */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class ButtonController implements ActionListener {
	private JMenuItem about;
	private JMenuItem addAttendance;
	private JMenuItem save;
	private JMenuItem plotData;
	private JMenuItem loadARoster;
	private Repository r = new Repository();
	private View v;
	
	/**
	 * Constructor, takes in all the menu items and assigns them a particular action if they are pressed. 
	 * @param about
	 * @param loadARoster
	 * @param addAttendance
	 * @param save
	 * @param plotData
	 * @param v
	 */
	public ButtonController(JMenuItem about, JMenuItem loadARoster, JMenuItem addAttendance, JMenuItem save, JMenuItem plotData, View v) {
		this.about = about;
		this.addAttendance = addAttendance;
		this.save = save;
		this.plotData = plotData;
		this.loadARoster = loadARoster;
		this.v = v;
	}
	
	/**
	 * If a file needs to be chosen with JFileChooser, then this method will be called
	 * @param attendance boolean value representing whether or not this is an attendance file
	 */
	public void ChooseFile(boolean attendance) {
		JButton open = new JButton();
		JFileChooser fc = new JFileChooser();
		
		FileNameExtensionFilter filter2 = new FileNameExtensionFilter("TXT Files", "*.txt", "txt");
		fc.setFileFilter(filter2);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "*.csv", "csv");
		fc.setFileFilter(filter);
		
		fc.setCurrentDirectory(new java.io.File("user.home"));
		fc.setDialogTitle("Choose a File");
		if (fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
			File test = fc.getSelectedFile();
			BufferedReader in;
			String fileContents = "";
			try {
				in = new BufferedReader(new FileReader(test));
				String line = in.readLine();
				while (line != null) {
					fileContents+= line + "\n";
					line = in.readLine();
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			r.addObserver(v);
			
			if (attendance == true) {
			
				JFrame t = new JFrame();
				t.setVisible(true);
				t.setTitle("Date Picker");
				UtilDateModel model = new UtilDateModel();
				model.setDate(2020, 11, 30);
				Properties p = new Properties();
				p.put("text.today", "Today");
				p.put("text.month", "Month");
				p.put("text.year", "Year");
				JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
				JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
				JButton b = new JButton("Add");
				b.setBounds(75,30, 100, 30);
				t.add(b);
				t.add(datePicker);
				t.setSize(new Dimension(300,100));
				final String finalFileContents = fileContents;
				b.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						if (!datePicker.getJFormattedTextField().getText().equals("")) {
							t.setVisible(false);
							String month = datePicker.getJFormattedTextField().getText().substring(5, 7);
							String monthString = "";
							if (month.equals("01")) {
								monthString = "January";
							} else if (month.equals("02")) {
								monthString = "February";
							} else if (month.equals("03")) {
								monthString = "March";
							} else if (month.equals("04")) {
								monthString = "April";
							} else if (month.equals("05")) {
								monthString = "May";
							} else if (month.equals("06")) {
								monthString = "June";
							} else if (month.equals("07")) {
								monthString = "July";
							} else if (month.equals("08")) {
								monthString = "August";
							} else if (month.equals("09")) {
								monthString = "September";
							} else if (month.equals("10")) {
								monthString = "October";
							} else if (month.equals("11")) {
								monthString = "November";
							} else if (month.equals("12")) {
								monthString = "December";
							}
							
							monthString += " " + datePicker.getJFormattedTextField().getText().substring(8, 10);
							r.addAttendance(finalFileContents, monthString);				
						}
					}
				});
				
			} else {
				r.createStudents(fileContents);
			}
		}
		
		
	}
	
	/**
	 * Method is called if the user wants to save their file
	 */
	public void SaveFile() {
		JButton save = new JButton("Save to file");
		JFileChooser fc = new JFileChooser();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "*.csv", "csv");
		fc.setFileFilter(filter);
		
	    int retval = fc.showSaveDialog(save);
	    if (retval == JFileChooser.APPROVE_OPTION) {
	    	try {
				r.SaveFile(fc.getSelectedFile().getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	
	    }
	    
	}
	
	/**
	 * Method is called if the user wants to plot their data
	 */
	public void PlotData() {
		r.plotData();
	}

	@Override
	/**
	 * Action Listener, goes to specific method depending on which button is pressed. 
	 */
	public void actionPerformed(ActionEvent a) {
		if (a.getActionCommand().equals("Load a Roster")) {
			ChooseFile(false);
		}
		
		if (a.getActionCommand().equals("Add Attendance")) {
			ChooseFile(true);
		}
		
		if (a.getActionCommand().equals("About")) {
			JOptionPane.showMessageDialog(null, "Jakob Forde is a junior studying Computer Science. Jakob also has \na minor in Piano Performance as well as Mathematics", "About Me", 1);
		}
		
		if (a.getActionCommand().equals("Save")) {
			SaveFile();
		}
		
		if (a.getActionCommand().equals("Plot Data")) {
			PlotData();
		}
		
		
	}
	
}

