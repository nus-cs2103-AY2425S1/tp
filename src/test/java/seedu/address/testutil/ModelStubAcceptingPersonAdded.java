package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * A Model stub that always accept the person being added.
 */
public class ModelStubAcceptingPersonAdded extends ModelStub {
    public final ArrayList<Person> personsAdded = new ArrayList<>();

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return personsAdded.stream().anyMatch(person::isSamePerson);
    }

    /**
     * Checks if a person with the same phone number already exists in the list.
     *
     * @param person The person to check.
     * @return true if a person with the same phone number exists; otherwise, false.
     */
    public boolean hasPhone(Person person) {
        requireNonNull(person);
        return personsAdded.stream().anyMatch(person::hasSamePhone);
    }

    /**
     * Checks if a person with the same email already exists in the list.
     *
     * @param person The person to check.
     * @return true if a person with the same email exists; otherwise, false.
     */
    public boolean hasEmail(Person person) {
        requireNonNull(person);
        return personsAdded.stream().anyMatch(person::hasSameEmail);
    }

    @Override
    public void addPerson(Person person) {
        requireNonNull(person);
        personsAdded.add(person);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return new AddressBook();
    }

    @Override
    public ObservableList<Wedding> getFilteredWeddingList() {
        return javafx.collections.FXCollections.observableArrayList();
    }
}
