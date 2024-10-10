package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class ContactListTest {

    private ObservableList<Person> personList;

    @Test
    void testComponentNotNull() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        this.personList = FXCollections.observableArrayList(TypicalPersons.getTypicalPersons());

        assertDoesNotThrow(() -> new ContactList(personList),
            "We should not be getting an exception");
    }

    @Test
    void testWithEmptyPersonList() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        this.personList = FXCollections.observableArrayList(TypicalPersons.getTypicalPersons());
        personList.clear();

        assertDoesNotThrow(() -> new ContactList(personList),
            "We should not be getting an exception");
    }

    @Test
    void testGetPersonListPanel() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        this.personList = FXCollections.observableArrayList(TypicalPersons.getTypicalPersons());
        ContactList contactList = new ContactList(personList);

        assertNotNull(contactList.getPersonListPanel(),
            "We should not be getting a null person list panel");
    }
}
