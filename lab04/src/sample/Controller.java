package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField fullnameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private DatePicker datepickerField;

    private DateTimeFormatter dateTimeFormatter;

    @FXML
    public void initialize() {
        // Create a formatter for the date
        final String datePattern = "yyyy/mm/dd";
        dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        datepickerField.setConverter(new StringConverter<LocalDate>() {

            /**
             * Converts the object provided into its string form.
             * Format of the returned string is defined by the specific converter.
             *
             * @param date the object of type {@code T} to convert
             * @return a string representation of the object passed in.
             */
            @Override
            public String toString(LocalDate date) {
                String finalDate = null;
                if (date != null) {
                    finalDate = dateTimeFormatter.format(date);
                }
                return finalDate;
            }

            /**
             * Converts the string provided into an object defined by the specific converter.
             * Format of the string and type of the resulting object is defined by the specific converter.
             *
             * @param string the {@code String} to convert
             * @return an object representation of the string passed in.
             */
            @Override
            public LocalDate fromString(String string) {
                LocalDate date = null;
                if (string != null) {
                    date = LocalDate.parse(string, dateTimeFormatter);
                }
                return date;
            }
        });
    }


    @FXML
    public void btnOnPress(ActionEvent e) {
        if (usernameField.getText().length() > 0) {
            System.out.println(usernameField.getText());
        }
        if (fullnameField.getText().length() > 0) {
            System.out.println(fullnameField.getText());
        }
        if (emailField.getText().length() > 0) {
            System.out.println(emailField.getText());
        }
        if (phoneField.getText().length() > 0) {
            System.out.println(phoneField.getText());
        }
    }
}
