package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.company.Company;
import seedu.address.model.job.Job;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // Test Data for Contacts
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ROLE_AMY = "Software Engineer";
    public static final String VALID_ROLE_BOB = "ML Engineer";
    public static final String VALID_SKILL_CUDA = "CUDA";
    public static final String VALID_SKILL_PYTHON = "python";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ROLE_DESC_AMY = " " + PREFIX_ROLE + VALID_ROLE_AMY;
    public static final String ROLE_DESC_BOB = " " + PREFIX_ROLE + VALID_ROLE_BOB;
    public static final String SKILL_DESC_FRIEND = " " + PREFIX_SKILL + VALID_SKILL_PYTHON;
    public static final String SKILL_DESC_HUSBAND = " " + PREFIX_SKILL + VALID_SKILL_CUDA;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE; // empty string not allowed for addresses
    public static final String INVALID_SKILL_DESC = " " + PREFIX_SKILL + "hubby*"; // '*' not allowed in skills

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withRole(VALID_ROLE_AMY)
                .withSkills(VALID_SKILL_PYTHON).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withRole(VALID_ROLE_BOB)
                .withSkills(VALID_SKILL_CUDA, VALID_SKILL_PYTHON).build();
    }

    // Test data for Jobs
    public static final String VALID_JOBNAME_BARISTA = "Full-time Barista";
    public static final String VALID_COMPANY_BARISTA = "Starbucks, Singapore";
    public static final String VALID_SALARY_BARISTA = "2500";
    public static final String[] VALID_REQUIREMENTS_BARISTA = {"Strong", "Pleasant"};
    public static final String VALID_DESCRIPTION_BARISTA =
            "At Starbucks, we are looking for someone who brings a " + "lot to the table";

    public static final String JOBNAME_DESC_BARISTA = " " + PREFIX_NAME + VALID_JOBNAME_BARISTA;
    public static final String COMPANY_DESC_BARISTA = " " + PREFIX_COMPANY + VALID_COMPANY_BARISTA;
    public static final String SALARY_DESC_BARISTA = " " + PREFIX_SALARY + VALID_SALARY_BARISTA;
    public static final String REQUIREMENTS_DESC_BARISTA = " " + PREFIX_REQUIREMENTS + VALID_REQUIREMENTS_BARISTA;
    public static final String DESCRIPTION_DESC_BARISTA = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BARISTA;

    public static final String INVALID_JOBNAME_DESC = " " + PREFIX_NAME + "!val!d"; // '!' not allowed in name
    public static final String INVALID_COMPANY_DESC = " " + PREFIX_COMPANY + "!val!d"; // '!' not allowed in name
    public static final String INVALID_SALARY_DESC = " " + PREFIX_SALARY + "$100/day"; // only numbers are allowed
    public static final String INVALID_REQUIREMENTS_DESC = " " + PREFIX_REQUIREMENTS + "!val!d"; // '!' not allowed
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + "!val!d"; //'!' not allowed

    // test data for companies
    public static final String VALID_COMPANY_NAME_NUS = "NUS";
    public static final String VALID_COMPANY_ADDRESS_NUS = "21 Lower Kent Ridge Rd, Singapore 119077";
    public static final String VALID_COMPANY_BILLING_DATE_NUS = "5";
    public static final String VALID_COMPANY_PHONE_NUS = "65166666";

    public static final String COMPANY_NAME_DESC_NUS = " " + PREFIX_NAME + VALID_COMPANY_NAME_NUS;
    public static final String COMPANY_ADDRESS_DESC_NUS = " " + PREFIX_ADDRESS + VALID_COMPANY_ADDRESS_NUS;
    public static final String COMPANY_BILLING_DATE_DESC_NUS = " " + PREFIX_BILLING_DATE
        + VALID_COMPANY_BILLING_DATE_NUS;
    public static final String COMPANY_PHONE_DESC_NUS = " " + PREFIX_PHONE + VALID_COMPANY_PHONE_NUS;

    public static final String INVALID_COMPANY_NAME_DESC = " " + PREFIX_NAME + "ඞ"; // unicode chars not allowed
    public static final String INVALID_COMPANY_ADDRESS_DESC = " " + PREFIX_ADDRESS + "ඞ"; // unicode chars not allowed
    public static final String INVALID_COMPANY_BILLING_DATE_DESC = " " + PREFIX_BILLING_DATE + "0"; // 0 not allowed
    public static final String INVALID_COMPANY_PHONE_DESC = " " + PREFIX_PHONE + "phone"; // alphabets not allowed

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered lists and selected entity in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredPersonList = new ArrayList<>(actualModel.getFilteredPersonList());
        List<Job> expectedFilteredJobList = new ArrayList<>(actualModel.getFilteredJobList());
        List<Company> expectedFilteredCompanyList = new ArrayList<>(actualModel.getFilteredCompanyList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredPersonList, actualModel.getFilteredPersonList());
        assertEquals(expectedFilteredJobList, actualModel.getFilteredJobList());
        assertEquals(expectedFilteredCompanyList, actualModel.getFilteredCompanyList());
    }
    /**
     * Updates {@code model}'s person filtered list to show only the person at the
     * given {@code targetIndex} in the {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s company filtered list to show only the company at the
     * given {@code targetIndex} in the {@code model}'s address book.
     */
    public static void showCompanyAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCompanyList().size());

        Company company = model.getFilteredCompanyList().get(targetIndex.getZeroBased());

        model.updateFilteredCompanyList(c -> c.equals(company));

        assertEquals(1, model.getFilteredCompanyList().size());
    }

}
