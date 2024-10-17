package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.HOON;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.company.Company;
import seedu.address.testutil.CompanyBuilder;

public class AddCompanyCommandTest {

    @Test
    public void constructor_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCompanyCommand(null));
    }

    @Test
    public void execute_companyAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCompanyAdded modelStub = new ModelStubAcceptingCompanyAdded();
        Company validCompany = new CompanyBuilder().build();

        CommandResult commandResult = new AddCompanyCommand(validCompany).execute(modelStub);

        assertEquals(String.format(AddCompanyCommand.MESSAGE_SUCCESS, Messages.format(validCompany)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCompany), modelStub.companiesAdded);
    }

    @Test
    public void execute_duplicateCompany_throwsCommandException() {
        Company validCompany = new CompanyBuilder().build();
        AddCompanyCommand addCompanyCommand = new AddCompanyCommand(validCompany);
        ModelStub modelStub = new ModelStubWithCompany(validCompany);

        assertThrows(CommandException.class,
                AddCompanyCommand.MESSAGE_DUPLICATE_PERSON, () -> addCompanyCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Company ntu = new CompanyBuilder().withName("NTU").build();
        Company nus = new CompanyBuilder().withName("NUS").build();
        AddCompanyCommand addNtuCommand = new AddCompanyCommand(ntu);
        AddCompanyCommand addNusCommand = new AddCompanyCommand(nus);

        // same object -> returns true
        assertTrue(addNtuCommand.equals(addNtuCommand));

        // same values -> returns true
        AddCompanyCommand addNtuCommandCopy = new AddCompanyCommand(ntu);
        assertTrue(addNtuCommand.equals(addNtuCommandCopy));

        // different types -> returns false
        assertFalse(addNtuCommand.equals(1));

        // null -> returns false
        assertFalse(addNtuCommand.equals(null));

        // different company -> returns false
        assertFalse(addNtuCommand.equals(addNusCommand));
    }

    @Test
    public void toStringMethod() {
        AddCompanyCommand addCompanyCommand = new AddCompanyCommand(HOON);
        String expected = AddCompanyCommand.class.getCanonicalName() + "{toAdd=" + HOON + "}";
        assertEquals(expected, addCompanyCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single company.
     */
    private class ModelStubWithCompany extends ModelStub {
        private final Company company;

        ModelStubWithCompany(Company company) {
            requireNonNull(company);
            this.company = company;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.company.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the company being added.
     */
    private class ModelStubAcceptingCompanyAdded extends ModelStub {
        final ArrayList<Company> companiesAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return companiesAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            companiesAdded.add((Company) person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
