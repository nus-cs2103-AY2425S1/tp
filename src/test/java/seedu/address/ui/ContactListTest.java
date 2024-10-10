package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class ContactListTest {

    private ObservableList<Person> personList;

    @BeforeEach
    void setUpStage() throws TimeoutException {
        // Set a dummy stage such that our JavaFx controller can operate
        FxToolkit.registerPrimaryStage();
        this.personList = FXCollections.observableArrayList(TypicalPersons.getTypicalPersons());
    }

    @Test
    void testComponentNotNull() {

        assertDoesNotThrow(() -> new ContactList(personList),
            "We should not be getting an exception");
    }

    @Test
    void testWithEmptyPersonList() {

        personList.clear();

        assertDoesNotThrow(() -> new ContactList(personList),
            "We should not be getting an exception");
    }

    @Test
    void testGetPersonListPanel() {

        ContactList contactList = new ContactList(personList);

        assertNotNull(contactList.getPersonListPanel(),
            "We should not be getting a null person list panel");
    }
}
