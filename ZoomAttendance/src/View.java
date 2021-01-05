/**
 * @author: Jakob Forde
 * ID: 1214487066
 * Class ID: 70605
 * Assignment: Final Project
 * 
 * This updates the view for the user based off of whatever the user is clicking. It is 
 * observing the repository and will update the GUI accordingly. This represents the "view"
 * in the MVC model that this program follows. 
 */

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class View extends JPanel implements Observer{
	private static final long serialVersionUID = 1L;
	ArrayList<Student> students;
	ArrayList<String> dates;


	@Override
	/**
	 * This update method updates the GUI according to the user-provided input.
	 * @param o:   The observable object repository that View needs in order to update
	 * 			   the GUI with the correct information.
	 * @param arg: Object argument required with Observable pattern. 
	 */
	public void update(Observable o, Object arg) {
		this.removeAll();
		
		this.students = ((Repository)o).getStudents();
		this.dates = ((Repository)o).getDates();
		
		String[] columnNames = {"ID", "First Name", "Last Name", "Program", "Level", "ASURITE"};
		JTable table = new JTable();
		DefaultTableModel dtm = new DefaultTableModel(0,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		dtm.setColumnIdentifiers(columnNames);
		table.setModel(dtm);
		
		table.setFillsViewportHeight(true);
		
		
		for (int i=0; i<students.size(); i++) {
			dtm.addRow(new Object[] {students.get(i).getID(), students.get(i).getFirstName(), students.get(i).getLastName(),
									 students.get(i).getProgram(), students.get(i).getLevel(), students.get(i).getASURITE()});
		}
		
		for (int i=0; i<dates.size(); i++) {
			
			ArrayList<Integer> toInsert = new ArrayList<Integer>();
			for (int j=0; j<students.size(); j++) {
				toInsert.add(students.get(j).getAttendance().get(i));
			}
		
			dtm.addColumn(dates.get(i), toInsert.toArray());
		}
		
		if (dates.size() > 0 && !((Repository)o).getUnfoundStudents().equals("") && ((Repository)o).getPlot() == false) {
			String[] lines = ((Repository)o).getUnfoundStudents().split("\n", -1);
			int count = lines.length -1;
			JOptionPane.showMessageDialog(null, "Data loaded for " + ((Repository)o).getDataLoaded() + " users in the roster\n\n" + count + 
												" additional users found:\n" + ((Repository)o).getUnfoundStudents());
		}
		
				
			
		table.getColumnModel().getColumn(0).setPreferredWidth(125);
		table.getColumnModel().getColumn(1).setPreferredWidth(125);
		table.getColumnModel().getColumn(2).setPreferredWidth(125);
		table.getColumnModel().getColumn(3).setPreferredWidth(125);
		table.getColumnModel().getColumn(4).setPreferredWidth(125);
		table.getColumnModel().getColumn(5).setPreferredWidth(125);
				
        JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        pane.setPreferredSize(new Dimension(750, 500));
		this.revalidate();
		this.repaint();
		this.add(pane);
	
		
		if (((Repository)o).getPlot() == true) {
			this.remove(pane);
			XYDataset dataset = createDataset();
			JFreeChart chart = ChartFactory.createScatterPlot("Class Attendance", "% of attendance", "Number of students", dataset);
			XYPlot plot = (XYPlot)chart.getPlot();
			plot.setBackgroundPaint(new Color(211,211,211));
			ChartPanel panel = new ChartPanel(chart);
			this.add(panel);
			
		}
		

		
	}
	
	/**
	 * Dynamically reates a dataset based off of how many attendances are loaded
	 * @return dataset: an XYSeriesCollection that has the number of students who attended 
	 * 					a percentage of a particular class. 
	 */
	private XYDataset createDataset() {
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (int i=0; i<dates.size(); i++) {
			XYSeries addDate = new XYSeries(dates.get(i));
			int[] percentAttended = new int[11];
			for (int j=0; j<students.size(); j++) {
				if (students.get(j).getAttendance().get(i)/75 >= 1) {
					percentAttended[10]++;
				} else if(Double.valueOf(students.get(j).getAttendance().get(i))/75>= .9) {
					percentAttended[9]++;
				} else if(Double.valueOf(students.get(j).getAttendance().get(i))/75 >= .8) {
					percentAttended[8]++;
				} else if(Double.valueOf(students.get(j).getAttendance().get(i))/75 >= .7) {
					percentAttended[7]++;
				} else if(Double.valueOf(students.get(j).getAttendance().get(i))/75 >= .6) {
					percentAttended[6]++;
				} else if(Double.valueOf(students.get(j).getAttendance().get(i))/75 >= .5) {
					percentAttended[5]++;
				} else if(Double.valueOf(students.get(j).getAttendance().get(i))/75 >= .4) {
					percentAttended[4]++;
				} else if(Double.valueOf(students.get(j).getAttendance().get(i))/75 >= .3) {
					percentAttended[3]++;
				} else if(Double.valueOf(students.get(j).getAttendance().get(i))/75 >= .2) {
					percentAttended[2]++;
				} else if(Double.valueOf(students.get(j).getAttendance().get(i))/75 >= .1) {
					percentAttended[1]++;
				} else if(Double.valueOf(students.get(j).getAttendance().get(i))/75 >= 0) {
					percentAttended[0]++;
				}
			}
			for (int j=0; j<11; j++) {
				addDate.add(j*10, percentAttended[j]);
			}
			dataset.addSeries(addDate);
		}
		return dataset;
	}
	

}
