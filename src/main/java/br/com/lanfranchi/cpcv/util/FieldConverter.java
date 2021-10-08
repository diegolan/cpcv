package br.com.lanfranchi.cpcv.util;

import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

public class FieldConverter {
	
	public static JFormattedTextField createField(Double price) {
		NumberFormat format = NumberFormat.getInstance(new Locale("pt", "BR"));
	    format.setMaximumFractionDigits(2);
	    format.setMinimumFractionDigits(2);
	    format.setMinimumIntegerDigits(0);
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Double.class);
	    formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true);
	    JFormattedTextField field = new JFormattedTextField(formatter);
	    field.setValue(price);
		return field;
	}
	
	public static JFormattedTextField createField() {
		return createField(0.0d);
	}
	
	public static Double parse(JFormattedTextField field) {
		Double price = (Double) field.getValue();
	    return price;
	}
	
	public static Integer parseInteger(JFormattedTextField field) {
		try {
			Long price = (Long) field.getValue();
		    return price.intValue();
		} catch (Exception e) {
		    return (Integer) field.getValue();
		}
	}
	
	public static NumberFormat getNumberFormat(int FractionDigits) {
		NumberFormat format = NumberFormat.getInstance(new Locale("pt", "BR"));
	    format.setMaximumFractionDigits(FractionDigits);
	    format.setMinimumFractionDigits(FractionDigits);
	    format.setMinimumIntegerDigits(1);
		return format;
	}
	
	public static NumberFormat getNumberFormat() {
		return getNumberFormat(2);
	}
	
	public static JFormattedTextField createIntegerField(int value) {
		NumberFormat format = NumberFormat.getIntegerInstance(new Locale("pt", "BR"));
	    format.setMaximumFractionDigits(0);
	    format.setMaximumIntegerDigits(2);
	    format.setGroupingUsed(false);
	    format.setParseIntegerOnly(true);
	    JFormattedTextField field = new JFormattedTextField(format);
	    field.setValue(value);
		return field;
	}
	
}
