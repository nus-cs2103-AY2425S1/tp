package spleetwaise.address.model;

import static java.util.Objects.requireNonNull;
import static spleetwaise.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.model.person.Phone;
import spleetwaise.commons.core.LogsCenter;

/**
 * Represents the in-memory model of the address book data.
 */
public class AddressBookModelManager implements AddressBookModel {

    private static final Logger logger = LogsCenter.getLogger(AddressBookModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a AddressBookModelManager with the given addressBook and userPrefs.
     */
    public AddressBookModelManager(ReadOnlyAddressBook addressBook) {
        requireAllNonNull(addressBook);

        logger.fine(
                "Initializing AddressBook Model with address book: " + addressBook);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public AddressBookModelManager() {
        this(new AddressBook());
    }

    //=========== AddressBook ================================================================================

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        requireNonNull(target);
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public Optional<Person> getPersonById(String id) {
        requireNonNull(id);
        return addressBook.getPersonById(id);
    }

    @Override
    public Optional<Person> getPersonByPhone(Phone phone) {
        requireNonNull(phone);
        return addressBook.getPersonByPhone(phone);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBookModelManager)) {
            return false;
        }

        AddressBookModelManager otherModelManager = (AddressBookModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
