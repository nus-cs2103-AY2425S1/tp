package spleetwaise.commons;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import spleetwaise.address.commons.core.GuiSettings;
import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.ReadOnlyAddressBook;
import spleetwaise.address.model.ReadOnlyUserPrefs;
import spleetwaise.address.model.person.Person;
import spleetwaise.transaction.model.ReadOnlyTransactionBook;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * The CommonModel class represents the common model that will be used by multiple parts of the application.
 */
public class CommonModel implements Model {
    // Singleton instance
    private static CommonModel model = null;

    private AddressBookModel addressBookModel;
    private TransactionBookModel transactionBookModel;

    private CommonModel(AddressBookModel abModel, TransactionBookModel tbModel) {
        addressBookModel = abModel;
        transactionBookModel = tbModel;
    }

    public static synchronized CommonModel getInstance() {
        requireNonNull(model);
        return model;
    }

    /**
     * Initialises the singleton instance of this class with the given address book and transaction book models.
     *
     * @param abModel The address book model to use
     * @param tbModel The transaction book model to use
     * @throws Exception If the CommonModel is already initialised
     */
    public static synchronized void initialise(AddressBookModel abModel, TransactionBookModel tbModel) {
        model = new CommonModel(abModel, tbModel);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return addressBookModel.getUserPrefs();
    }

    // AddressBook
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        addressBookModel.setUserPrefs(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return addressBookModel.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        addressBookModel.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return addressBookModel.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        addressBookModel.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBookModel.getAddressBook();
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        addressBookModel.setAddressBook(addressBook);
    }

    @Override
    public boolean hasPerson(Person person) {
        return addressBookModel.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBookModel.deletePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBookModel.addPerson(person);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        addressBookModel.setPerson(target, editedPerson);
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return addressBookModel.getFilteredPersonList();
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        addressBookModel.updateFilteredPersonList(predicate);
    }

    @Override
    public Optional<Person> getPersonById(String id) {
        return addressBookModel.getPersonById(id);
    }

    // TransactionBook
    @Override
    public ReadOnlyTransactionBook getTransactionBook() {
        return transactionBookModel.getTransactionBook();
    }

    @Override
    public void setTransactionBook(ReadOnlyTransactionBook replacementBook) {
        transactionBookModel.setTransactionBook(replacementBook);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactionBookModel.addTransaction(transaction);
    }

    @Override
    public boolean hasTransaction(Transaction transaction) {
        return transactionBookModel.hasTransaction(transaction);
    }

    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return transactionBookModel.getFilteredTransactionList();
    }

    @Override
    public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
        transactionBookModel.updateFilteredTransactionList(predicate);
    }
}
