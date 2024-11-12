package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryList;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluates to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    /** {@code Predicate} that always evaluates to true */
    Predicate<Delivery> PREDICATE_SHOW_ALL_DELIVERIES = unused -> true;
    Predicate<Person> PREDICATE_SHOW_ONLY_CLIENTS = person -> person.getRole().getValue().equals("client");
    Predicate<Person> PREDICATE_SHOW_ONLY_EMPLOYEES = person -> person.getRole().getValue().equals("employee");

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with the same phone as {@code person} exists in the address book.
     */
    boolean hasPhone(Person person);

    /**
     * Returns true if a person with the same email as {@code person} exists in the address book.
     */
    boolean hasEmail(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Sorts the {@code UniquePersonList} using the {@code Date} and {@code Time} attribute of each {@code Person},
     * in ascending order.
     */
    void sortByDate();

    /**
     * Sorts the {@code UniquePersonList} using the {@code Name} attribute of each {@code Person}, in ascending order.
     */
    void sortByName();

    /**
     * Sorts the {@code UniquePersonList} using the {@code Phone} attribute of each {@code Person}, in ascending order.
     */
    void sortByPhone();

    /**
     * Sorts the {@code UniquePersonList} using the {@code Email} attribute of each {@code Person}, in ascending order.
     */
    void sortByEmail();

    /**
     * Sorts the {@code UniquePersonList} using the {@code Role} attribute of each {@code Person}, in ascending order.
     */
    void sortByRole();

    /**
     * Reverses the {@code UniquePersonList}.
     * <p>
     * Used when sorting the {@code UniquePersonList} by a specified attribute, in descending order.
     */
    void reversePersonList();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    ObservableList<Person> getUnfilteredPersonList();

    ObservableList<Person> getOnlyClientList();

    ObservableList<Person> getOnlyEmployeeList();

    /** Returns the {@code Index} of the first archived person in the list. */
    Index getFirstArchivedIndex();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Adds the given delivery.
     */
    void addDelivery(Person person, Delivery delivery);

    /**
     * Returns an unmodifiable view of the filtered delivery list
     */
    ObservableList<Delivery> getFilteredDeliveryList();

    /**
     * Updates the filter of the filtered delivery list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDeliveryList(Predicate<Delivery> predicate);

    /**
     * Sets the filtered delivery list to the deliveryList given
     */
    void setFilteredDeliveryList(DeliveryList deliveryList);
}
