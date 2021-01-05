import java.util.ArrayList;
/**
 * @author: Jakob Forde
 * ID: 1214487066
 * Class ID: 70605
 * Assignment: Final Project
 * 
 * This class represents the individual student iself. Repository will create many students
 * based off of the .csv file. This represents the "model" in the MVC that this program
 * implements. 
 */

public class Student {

	private String ID;
	private String firstName;
	private String lastName;
	private String program;
	private String level;
	private String ASURITE;
	private ArrayList<Integer> attendance;
	
	/**
	 * Constructor for Student
	 * @param ID
	 * @param firstName
	 * @param lastName
	 * @param program
	 * @param level
	 * @param ASURITE
	 * @param attendance
	 */
	public Student(String ID, String firstName, String lastName, String program, String level, String ASURITE, ArrayList<Integer> attendance) {
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.program = program;
		this.level = level;
		this.ASURITE = ASURITE;
		attendance = new ArrayList<Integer>();
		this.attendance = attendance;
	}
	
	/**
	 * Getter for ID
	 * @return ID
	 */
	public String getID() {
		return ID;
	}
	
	/**
	 * Getter for first Name
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Getter for last name
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * getter for Program
	 * @return program
	 */
	public String getProgram() {
		return program;
	}
	
	/**
	 * getter for Level
	 * @return level
	 */
	public String getLevel() {
		return level;
	}
	
	/**
	 * getter for ASURITE
	 * @return ASURITE
	 */
	public String getASURITE() {
		return ASURITE;
	}
	
	/**
	 * getter for attendance
	 * @return ArrayList<Intenger>
	 */
	public ArrayList<Integer> getAttendance() {
		return attendance;
	}
	
	/**
	 * toString method for all of student's information
	 * @return String
	 */
	public String toString() {
		return this.ID + "," + this.firstName + "," + this.lastName + "," + this.program + "," + this.level + "," + this.ASURITE;
	}
	
}
