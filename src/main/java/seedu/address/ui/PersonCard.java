package seedu.address.ui;

import java.util.Comparator;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
                    modules.getChildren().add(moduleLabel);
                    Label gradeLabel = new Label("(" + moduleCode.getGrade() + ")");
                    grades.getChildren().add(gradeLabel);
                });
        gender.textFillProperty().bind(
                Bindings.when(gender.textProperty().isEqualTo("♂"))
                        .then(Color.LIGHTBLUE)
                        .otherwise(Color.PINK));
    }
}
