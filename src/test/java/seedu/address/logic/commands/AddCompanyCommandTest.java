package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCompanies.NUS;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.company.Company;
import seedu.address.testutil.CompanyBuilder;
import seedu.address.testutil.ModelStub;


public class AddCompanyCommandTest {
    @Test
    public void constructor_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCompanyCommand(null));
    }

    @Test
    public void execute_companyAddedByModel_addSuccessful() throws Exception {
        ModelAcceptingCompanyAdded modelStub = new ModelAcceptingCompanyAdded();
        Company validCompany = NUS;
        CommandResult commandResult = new AddCompanyCommand(validCompany).execute(modelStub);

        assertEquals(
                String.format(AddCompanyCommand.MESSAGE_SUCCESS, validCompany.getName().fullName),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCompany), modelStub.companiesAdded);
    }

    @Test
    public void execute_duplicateCompany_throwsCommandException() {
        Company validCompany = NUS;
        AddCompanyCommand command = new AddCompanyCommand(validCompany);
        ModelStub modelStub = new ModelStubWithCompany(validCompany);

        assertThrows(CommandException.class, AddCompanyCommand.MESSAGE_DUPLICATE_COMPANY, ()
                -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        Company nus = NUS;
        Company apple = new CompanyBuilder().withName("Apple").build();

        AddCompanyCommand addNusCommand = new AddCompanyCommand(nus);
        AddCompanyCommand addAppleCommand = new AddCompanyCommand(apple);

        // same object -> return true
        assertTrue(addNusCommand.equals(addNusCommand));

        // same values -> return true
        AddCompanyCommand addNusCommandCopy = new AddCompanyCommand(nus);
        assertTrue(addNusCommand.equals(addNusCommandCopy));

        // different types -> return false
        assertFalse(addNusCommand.equals(1));

        // null -> returns false
        assertFalse(addNusCommand.equals(null));

        // different person -> return false
        assertFalse(addNusCommand.equals(addAppleCommand));
    }

    @Test
    public void toStringMethod() {
        AddCompanyCommand command = new AddCompanyCommand(NUS);
        String expected = AddCompanyCommand.class.getCanonicalName() + "{toAdd=" + NUS + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * A model stub that always accepts the company being added.
     */
    private class ModelAcceptingCompanyAdded extends ModelStub {
        final ArrayList<Company> companiesAdded = new ArrayList<>();

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

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


    }

    /**
     * A model stub that contains a single company.
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
}


