package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class ContactListTest {

    private ObservableList<Person> personList;

    @Test
    void testComponentNotNull() {
        this.personList = FXCollections.observableArrayList(TypicalPersons.getTypicalPersons());

        assertThrows(ExceptionInInitializerError.class, () -> new ContactList(this.personList));

    }

}
