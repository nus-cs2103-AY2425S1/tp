package spleetwaise.commons.model;

import static java.util.Objects.requireNonNull;
import static spleetwaise.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.ReadOnlyAddressBook;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.model.person.Phone;
import spleetwaise.commons.core.GuiSettings;
import spleetwaise.transaction.model.ReadOnlyTransactionBook;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * The CommonModelManager class represents the common model that will be used by multiple parts of the application.
 */
public class CommonModelManager implements CommonModel {
    // Singleton instance
    private static CommonModelManager model = null;

    private final UserPrefs userPrefs;
    private AddressBookModel addressBookModel;
    private TransactionBookModel transactionBookModel;

    private CommonModelManager(AddressBookModel abModel, TransactionBookModel tbModel, ReadOnlyUserPrefs userPrefs) {
        addressBookModel = abModel;
        transactionBookModel = tbModel;
        this.userPrefs = new UserPrefs(userPrefs);
    }

    public static synchronized CommonModelManager getInstance() {
        requireNonNull(model);
        return model;
    }

    /**
     * Initialises the singleton instance of this class with the given address book and transaction book models.
     *
     * @param abModel   The address book model to use
     * @param tbModel   The transaction book model to use
     * @param userPrefs The user prefs to use
     */
    public static synchronized void initialise(
            AddressBookModel abModel, TransactionBookModel tbModel,
            ReadOnlyUserPrefs userPrefs
    ) {
        model = new CommonModelManager(abModel, tbModel, userPrefs);
    }


    public static synchronized void initialise(AddressBookModel abModel, TransactionBookModel tbModel) {
        model = new CommonModelManager(abModel, tbModel, new UserPrefs());
    }

    /**
     * De-initialises the singleton instance of this class.
     */
    public static synchronized void deinitialise() {
        model = null;
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        requireNonNull(addressBookModel, "AddressBook model cannot be null");
        return addressBookModel.getAddressBook();
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        requireNonNull(addressBookModel, "AddressBook model cannot be null");
        addressBookModel.setAddressBook(addressBook);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(addressBookModel, "AddressBook model cannot be null");
        return addressBookModel.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        requireNonNull(addressBookModel, "AddressBook model cannot be null");
        addressBookModel.deletePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        requireNonNull(addressBookModel, "AddressBook model cannot be null");
        addressBookModel.addPerson(person);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(addressBookModel, "AddressBook model cannot be null");
        addressBookModel.setPerson(target, editedPerson);
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        requireNonNull(addressBookModel, "AddressBook model cannot be null");
        return addressBookModel.getFilteredPersonList();
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(addressBookModel, "AddressBook model cannot be null");
        addressBookModel.updateFilteredPersonList(predicate);
    }

    @Override
    public Optional<Person> getPersonById(String id) {
        requireNonNull(addressBookModel, "AddressBook model cannot be null");
        return addressBookModel.getPersonById(id);
    }

    @Override
    public Optional<Person> getPersonByPhone(Phone phone) {
        requireNonNull(addressBookModel, "AddressBook model cannot be null");
        requireNonNull(phone);
        return addressBookModel.getPersonByPhone(phone);
    }

    // TransactionBook
    @Override
    public ReadOnlyTransactionBook getTransactionBook() {
        requireNonNull(transactionBookModel, "TransactionBook model cannot be null");
        return transactionBookModel.getTransactionBook();
    }

    @Override
    public void setTransactionBook(ReadOnlyTransactionBook replacementBook) {
        requireNonNull(transactionBookModel, "TransactionBook model cannot be null");
        transactionBookModel.setTransactionBook(replacementBook);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        requireNonNull(transactionBookModel, "TransactionBook model cannot be null");
        transactionBookModel.addTransaction(transaction);
    }

    @Override
    public boolean hasTransaction(Transaction transaction) {
        requireNonNull(transactionBookModel, "TransactionBook model cannot be null");
        return transactionBookModel.hasTransaction(transaction);
    }

    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        requireNonNull(transactionBookModel, "TransactionBook model cannot be null");
        return transactionBookModel.getFilteredTransactionList();
    }

    @Override
    public ObjectProperty<Predicate<Transaction>> getCurrentPredicate() {
        requireNonNull(transactionBookModel, "TransactionBook model cannot be null");
        return transactionBookModel.getCurrentPredicate();
    }

    @Override
    public void updateFilteredTransactionList() {
        requireNonNull(transactionBookModel, "TransactionBook model cannot be null");
        transactionBookModel.updateFilteredTransactionList();
    }

    @Override
    public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
        requireNonNull(transactionBookModel, "TransactionBook model cannot be null");
        transactionBookModel.updateFilteredTransactionList(predicate);
    }

    @Override
    public void deleteTransactionsOfPersonId(String personId) {
        requireNonNull(personId);
        transactionBookModel.deleteTransactionsOfPersonId(personId);
    }

    @Override
    public void deleteTransaction(Transaction target) {
        requireNonNull(transactionBookModel, "TransactionBook model cannot be null");
        transactionBookModel.deleteTransaction(target);
    }

    @Override
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireNonNull(transactionBookModel, "TransactionBook model cannot be null");
        requireAllNonNull(target, editedTransaction);
        transactionBookModel.setTransaction(target, editedTransaction);
    }
}
