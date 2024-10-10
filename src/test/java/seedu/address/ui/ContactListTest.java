package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class ContactListTest {

    private ObservableList<Person> personList;

    @BeforeEach
    public void setup() {
        new JFXPanel(); // Initializes the JavaFX runtime
    }

    @Test
    void testComponentNotNull() {
        this.personList = FXCollections.observableArrayList(TypicalPersons.getTypicalPersons());

        Scene scene = new Scene(new StackPane());
        assertDoesNotThrow(() -> new ContactList(this.personList));

    }

}
