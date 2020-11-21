package converters;

import javafx.util.StringConverter;

public class CustomDoubleStringConverter extends StringConverter<Double> {

    @Override
    public Double fromString(String value) {
        if (value == null) {
            return 0.0;
        }
        value = value.trim().replace(',', '.');
        if (value.length() < 1) {
            return 0.0;
        }

        Double result;
        try{
            result = Double.valueOf(value);
        }catch(NumberFormatException e){
           result = 0.0;
        }
        return result;
    }

    @Override public String toString(Double value) {
        if (value == null) {
            return "";
        }
        return Double.toString(value.doubleValue());
    }
}
