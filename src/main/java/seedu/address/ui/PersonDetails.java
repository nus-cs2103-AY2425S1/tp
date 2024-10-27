package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;



/**
 * Controller class for displaying a person's details in the UI. Encapsulates
 * all features for the person object, and displays the details line by line.
 */
public class PersonDetails {
    @FXML
    private Label birthdayLabel;
    @FXML
    private VBox history;
    @FXML
    private Label remarkLabel;
    @FXML
    private FlowPane tags;
    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label addressLabel;




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

        person.getHistory().getHistoryEntries().forEach((date, activities) -> {
            Label historyLabel = new Label(date.toString());
            historyLabel.setStyle("-fx-background-color: #293f3f; -fx-text-fill: #D9B08C; -fx-padding: 5");
            history.getChildren().add(historyLabel);
            activities.forEach(entry -> {
                Label activityLabel = new Label("\t - " + entry);
                activityLabel.setStyle("-fx-font-size: 1em; -fx-text-fill: #D9B08C; -fx-padding: 2");
                history.getChildren().add(activityLabel);
            });
        });

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
