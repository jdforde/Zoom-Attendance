/**
 * @author: Jakob Forde
 * ID: 1214487066
 * Class ID: 70605
 * Assignment: Final Project
 * 
 * This class formats a date into a string. It is part of JDatePicker and was provided from the link
 * https://www.codejava.net/java-se/swing/how-to-use-jdatepicker-to-display-calendar-component
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

public class DateLabelFormatter extends AbstractFormatter {

    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}