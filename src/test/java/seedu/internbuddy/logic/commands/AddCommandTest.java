package seedu.internbuddy.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.testutil.Assert.assertThrows;
import static seedu.internbuddy.testutil.TypicalCompanies.GOOGLE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.internbuddy.commons.core.GuiSettings;
import seedu.internbuddy.logic.Messages;
import seedu.internbuddy.logic.commands.exceptions.CommandException;
import seedu.internbuddy.model.AddressBook;
import seedu.internbuddy.model.Model;
import seedu.internbuddy.model.ReadOnlyAddressBook;
import seedu.internbuddy.model.ReadOnlyUserPrefs;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.testutil.CompanyBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullcompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_companyAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingcompanyAdded modelStub = new ModelStubAcceptingcompanyAdded();
        Company validcompany = new CompanyBuilder().build();

        CommandResult commandResult = new AddCommand(validcompany).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validcompany)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validcompany), modelStub.companiesAdded);
    }

    @Test
    public void execute_duplicateCompany_throwsCommandException() {
        Company validCompany = new CompanyBuilder().build();
        AddCommand addCommand = new AddCommand(validCompany);
        ModelStub modelStub = new ModelStubWithCompany(validCompany);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_COMPANY, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Company google = new CompanyBuilder().withName("Google").build();
        Company microsoft = new CompanyBuilder().withName("Microsoft").build();
        AddCommand addGoogleCommand = new AddCommand(google);
        AddCommand addMicrosoftCommand = new AddCommand(microsoft);

        // same object -> returns true
        assertTrue(addGoogleCommand.equals(addGoogleCommand));

        // same values -> returns true
        AddCommand addGoogleCommandCopy = new AddCommand(google);
        assertTrue(addGoogleCommand.equals(addGoogleCommandCopy));

        // different types -> returns false
        assertFalse(addGoogleCommand.equals(1));

        // null -> returns false
        assertFalse(addGoogleCommand.equals(null));

        // different company -> returns false
        assertFalse(addGoogleCommand.equals(addMicrosoftCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(GOOGLE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + GOOGLE + "}";
        assertEquals(expected, addCommand.toString());
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
        public void addCompany(Company company) {
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
        public boolean hasCompany(Company company) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCompany(Company target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCompany(Company target, Company editedcompany) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Company> getFilteredCompanyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCompanyList(Predicate<Company> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void hideAllDetails() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void viewAppDetails(Company company) {
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
        public boolean hasCompany(Company company) {
            requireNonNull(company);
            return this.company.isSameCompany(company);
        }
    }

    /**
     * A Model stub that always accept the company being added.
     */
    private class ModelStubAcceptingcompanyAdded extends ModelStub {
        final ArrayList<Company> companiesAdded = new ArrayList<>();

        @Override
        public boolean hasCompany(Company company) {
            requireNonNull(company);
            return companiesAdded.stream().anyMatch(company::isSameCompany);
        }

        @Override
        public void addCompany(Company company) {
            requireNonNull(company);
            companiesAdded.add(company);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
