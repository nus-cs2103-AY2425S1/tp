package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

import java.util.Comparator;

/**
 * Controller class for displaying a person's details in the UI. Encapsulates
 * all features for the person object, and displays the details line by line.
 */
public class PersonDetails {
    private static final String SEPARATOR = "______________________________" +
            "_________________________________\n\n";
    @FXML
    public Label birthdayLabel;
    @FXML
    public Label historyLabel;
    @FXML
    public Label remarkLabel;
    @FXML
    public FlowPane tags;
    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label addressLabel;
    @FXML
    private ScrollPane historyScrollPane;

//    @FXML
//    private FlowPane tags;


    /**
     * Sets the details of the specified {@code Person} in the respective UI labels.
     *
     * @param person The person whose details are to be displayed.
     *               This includes the person's name, phone, email, and address.
     */
    public void setPersonDetails(Person person) {
        nameLabel.setText(person.getName().fullName);
        phoneLabel.setText(person.getPhone().value);
        emailLabel.setText(person.getEmail().value);
        addressLabel.setText(person.getAddress().value);
        birthdayLabel.setText(person.getBirthday().value.toString());
        remarkLabel.setText(person.getRemark().value);

        StringBuilder historyText = new StringBuilder();
        person.getHistory().getHistoryEntries().forEach((date, activities) -> {
            historyText.append(date.toString()).append(": \n");
            historyText.append(String.join(", \n", activities)).append("\n" + SEPARATOR); // Separate each entry
        });

        historyLabel.setText(historyText.toString());

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName)).forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    // Check if the tag contains "favourite" and add a style class
                    if (tag.tagName.equalsIgnoreCase("favourite")) {
                        tagLabel.getStyleClass().add("favourite-tag");
                    }
                    tags.getChildren().add(tagLabel);
                });

    }
}
