/**
 * @author: Jakob Forde
 * ID: 1214487066
 * Class ID: 70605
 * Assignment: Final Project
 * 
 * This class stores all of the information about the students and the dates while the
 * program is running. It is used to update the view. This represents the "Model" portion
 * of the MVC architecture that this program implements.  
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JOptionPane;

public class Repository extends Observable{
	private ArrayList<Student> students;
	private ArrayList<String> dates = new ArrayList<String>();
	private int attendancePos = 0;
	private int dataLoaded = 0;
	private String unfoundStudents = "";
	private boolean plot = false;
	
	
	/**
	 * Creates an ArrayList of students based off of the fileContents that it parses
	 * @param fileContents: String form of the raw .csv file 
	 */
	public void createStudents(String fileContents) {
		plot = false;
		if (this.students != null) {
			students.clear();
		}
		dates.clear();
		attendancePos = 0;
		dataLoaded = 0;
		this.students = new ArrayList<Student>();
		String[] studentString = fileContents.split("\n");
		for (int i=0; i<studentString.length; i++) {
			String[] studentInfo = studentString[i].split(",");
			ArrayList<Integer> attendance = new ArrayList<Integer>();
			try {
				Student toAdd = new Student(studentInfo[0], studentInfo[1], studentInfo[2], studentInfo[3], studentInfo[4], studentInfo[5], attendance);
				students.add(toAdd);
			} catch (ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null, "File Format is incorrect", "Error", 2);
				return;
			}
			
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Adds attendance to ArrayList of attendances and to the Students ArrayList of attendance. 
	 * @param fileContents: String form of raw .csv file
	 * @param date: String representing the date the attendance was taken
	 */
	public void addAttendance(String fileContents, String date) {	
		this.unfoundStudents = "";
		if (students != null) {
			this.dates.add(date);
			for (int i=0; i<students.size(); i++) {
				students.get(i).getAttendance().add(attendancePos, 0);
			}
			String[] dateString = fileContents.split("\n");
			for (int i=0; i<dateString.length; i++) {
				boolean isFound = false;
				for (int j=0; j<students.size(); j++) {
					if (dateString[i].contains(students.get(j).getASURITE())) {
						try {
							
							String[] dateSplit = dateString[i].split(",");
							if (students.get(j).getAttendance().get(attendancePos) == 0) {
								dataLoaded++;
							}
							students.get(j).getAttendance().add(attendancePos, students.get(j).getAttendance().get(attendancePos) + Integer.parseInt(dateSplit[1]));
							isFound = true;
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "File Format is incorrect", "Error", 2);
							return;
						}
					}
				}
				if (isFound == false) {
					String[] dateSplit = dateString[i].split(",");
					unfoundStudents += dateSplit[0] + " connected for " + dateSplit[1] + " minutes.\n";
				}
			}
			
			attendancePos++;
			setChanged();
			notifyObservers();
		} else {
			JOptionPane.showMessageDialog(null, "Please load a roster before adding attendance", "Error", 2);
		}
	}
	
	/**
	 * Saves the students and their attendances to a file to the destined filePath
	 * @param filePath: String representation of filePath 
	 * @throws IOException: if filePath can't be found
	 */
	public void SaveFile(String filePath) throws IOException {
		if (students == null) {
			JOptionPane.showMessageDialog(null, "Please load a roster before saving a file", "Error", 2);
			return;
		}
		String fileContents = "ID,First Name,Last Name,Program,Level,ASURITE";
		for (int i=0; i<dates.size(); i++) {
			fileContents += "," + dates.get(i);
		}
		fileContents += "\n";
		
		for (int i=0; i<students.size(); i++) {
			fileContents+=students.get(i).toString();
			for (int j=0; j<dates.size(); j++) {
				fileContents+="," + students.get(i).getAttendance().get(j);
			}
			fileContents+="\n";
		}
		
		filePath+=".csv";
		FileWriter myFile = new FileWriter(filePath); 
		myFile.write(fileContents);
		myFile.close();
		JOptionPane.showMessageDialog(null, "File written successfully!", "Success", 1);
	}
	
	public void plotData() {
		plot = true;
		setChanged();
		notifyObservers();
	}
	
	
	/**
	 * Getter for student Array
	 * @return ArrayList of type student
	 */
	public ArrayList<Student> getStudents() {
		return this.students;
	}
	
	/**
	 * Getter for the dates
	 * @return ArrayList of type String
	 */
	public ArrayList<String> getDates() {
		return this.dates;
	}

	/**
	 * String of unfound students to be displayed to user
	 * @return String of unfound students
	 */
	public String getUnfoundStudents() {
		return this.unfoundStudents;
	}
	
	/**
	 * Getter for how many students' data was loaded
	 * @return Integer of students' data loaded
	 */
	public int getDataLoaded() {
		return this.dataLoaded;
	}
	
	/**
	 * Getter for if a plot will be constructed
	 * @return Boolean of if plot will be constructed
	 */
	public boolean getPlot() {
		return this.plot;
	}
}
