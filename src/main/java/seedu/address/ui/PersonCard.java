package seedu.address.ui;

import java.util.Comparator;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label gender;
    @FXML
    private Label phone;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane modules;
    @FXML
    private FlowPane grades;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        gender.setText(person.getGender().getGenderWithSymbol());
        phone.setText(person.getPhone().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getModules().stream()
                .sorted(Comparator.comparing(module -> module.module))
                .forEach(moduleCode -> {
                    Label moduleLabel = new Label(moduleCode.module);

                    // Color-coding based on grade
                    String gradeString = moduleCode.getGrade();
                    int gradeValue = 0; // Default to 0 if parsing is needed

                    if (gradeString.equalsIgnoreCase("Ungraded")) {
                        // Set style for ungraded modules
                        moduleLabel.setStyle("-fx-background-color: #B0BEC5; -fx-background-radius: 5; "
                                + "-fx-text-fill: black; -fx-padding: 5 10; -fx-font-weight: bold;");
                        moduleLabel.setTooltip(new Tooltip(moduleCode.module + " (Ungraded)"));
                    } else {
                        try {
                            gradeValue = Integer.parseInt(gradeString);
                            // Set color based on grade range
                            if (gradeValue >= 50) {
                                moduleLabel.setStyle("-fx-background-color: #4CAF50; -fx-background-radius: 5; "
                                        + "-fx-text-fill: white; -fx-padding: 5 10; -fx-font-weight: bold;");
                            } else {
                                moduleLabel.setStyle("-fx-background-color: #F44336; -fx-background-radius: 5; "
                                        + "-fx-text-fill: white; -fx-padding: 5 10; -fx-font-weight: bold;");
                            }
                            // Tooltip for graded modules
                            moduleLabel.setTooltip(new Tooltip("Module: " + moduleCode.module + "\nGrade: "
                                    + gradeValue));
                        } catch (NumberFormatException e) {
                            // Handle unexpected non-numeric grades gracefully
                            moduleLabel.setStyle("-fx-background-color: #B0BEC5; -fx-background-radius: 5; "
                                    + "-fx-text-fill: black; -fx-padding: 5 10; -fx-font-weight: bold;");
                            moduleLabel.setTooltip(new Tooltip(moduleCode.module + " (Invalid grade)"));
                        }
                    }

                    // Add the moduleLabel directly to the modules FlowPane
                    modules.getChildren().add(moduleLabel);
                });
        gender.textFillProperty().bind(
                Bindings.when(gender.textProperty().isEqualTo("♂"))
                        .then(Color.LIGHTBLUE)
                        .otherwise(Color.PINK));
    }
}
