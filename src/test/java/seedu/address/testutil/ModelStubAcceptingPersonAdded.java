package seedu.address.testutil;

import javafx.collections.ObservableList;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

import java.util.ArrayList;

import static java.util.Objects.requireNonNull;

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

    public boolean hasPhone(Person person) {
        requireNonNull(person);
        return personsAdded.stream().anyMatch(person::hasSamePhone);
    }

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
