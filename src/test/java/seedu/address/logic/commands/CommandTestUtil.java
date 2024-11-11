package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAREER_PAGE_URL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK; // New prefix for remark
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.company.Company;
import seedu.address.model.company.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditCompanyDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // Valid constants
    public static final String VALID_NAME_TESLA = "Tesla";
    public static final String VALID_NAME_MICROSOFT = "Microsoft";
    public static final String VALID_PHONE_TESLA = "11111111";
    public static final String VALID_PHONE_MICROSOFT = "22222222";
    public static final String VALID_EMAIL_TESLA = "tesla@example.com";
    public static final String VALID_EMAIL_MICROSOFT = "microsoft@example.com";
    public static final String VALID_ADDRESS_TESLA = "Block 312, Tesla Street 1";
    public static final String VALID_ADDRESS_MICROSOFT = "Block 123, Microsoft Street 3";
    public static final String VALID_CAREER_PAGE_URL_TESLA = "https://www.tesla.com/careers";
    public static final String VALID_CAREER_PAGE_URL_MICROSOFT = "https://careers.microsoft.com";
    public static final String VALID_REMARK_COMPANY = "remark";
    public static final String VALID_TAG_BIGTECH = "bigTech";
    public static final String VALID_TAG_COMPANY = "company";
    public static final String VALID_STATUS_ONGOING = "ongoing";
    public static final String VALID_STATUS_OFFER = "offer";
    public static final String VALID_REMARK = "Hard to schedule interview"; // New valid remark

    public static final String NAME_DESC_TESLA = " " + PREFIX_NAME + VALID_NAME_TESLA;
    public static final String NAME_DESC_MICROSOFT = " " + PREFIX_NAME + VALID_NAME_MICROSOFT;
    public static final String PHONE_DESC_TESLA = " " + PREFIX_PHONE + VALID_PHONE_TESLA;
    public static final String PHONE_DESC_MICROSOFT = " " + PREFIX_PHONE + VALID_PHONE_MICROSOFT;
    public static final String EMAIL_DESC_TESLA = " " + PREFIX_EMAIL + VALID_EMAIL_TESLA;
    public static final String EMAIL_DESC_MICROSOFT = " " + PREFIX_EMAIL + VALID_EMAIL_MICROSOFT;
    public static final String ADDRESS_DESC_TESLA = " " + PREFIX_ADDRESS + VALID_ADDRESS_TESLA;
    public static final String ADDRESS_DESC_MICROSOFT = " " + PREFIX_ADDRESS + VALID_ADDRESS_MICROSOFT;
    public static final String CAREER_PAGE_URL_DESC_TESLA = " " + PREFIX_CAREER_PAGE_URL + VALID_CAREER_PAGE_URL_TESLA;
    public static final String CAREER_PAGE_URL_DESC_MICROSOFT = " " + PREFIX_CAREER_PAGE_URL
            + VALID_CAREER_PAGE_URL_MICROSOFT;
    public static final String REMARK_DESC_COMPANY = " " + PREFIX_REMARK + VALID_REMARK_COMPANY;
    public static final String TAG_DESC_COMPANY = " " + PREFIX_TAG + VALID_TAG_COMPANY;
    public static final String TAG_DESC_BIGTECH = " " + PREFIX_TAG + VALID_TAG_BIGTECH;
    public static final String REMARK_DESC_VALID = " " + PREFIX_REMARK + VALID_REMARK; // New valid remark description

    // Invalid constants
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_CAREER_PAGE_URL_DESC = " " + PREFIX_CAREER_PAGE_URL
            + " "; // Invalid URL format
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_REMARK_DESC = " " + PREFIX_REMARK + "q8ElAFCNZ5)-:Q7$-}m4KH#1QF*HI-tiR,1o"
            + "`Hg}1K86Lm`t-0$oS)hY,31+*`t#^W|?tm3%2-px#[,/>Mqrgcvam~Kq7|.EN6,7a8jxI1mZw]p_n|0<DA=Rl)[rwh3+gyL$S')"
            + "dtBasijifhihsfihoajod9138943409734102914/1afC60L~5(^Ph?eO9Q-mP,</^OL&+q}oWnkIY)*Uv_-aUmYfe^"
            + ";({K1B"; // Remark > 200 not allowed

    // Preambles
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    // EditCommand descriptors
    public static final EditCommand.EditCompanyDescriptor DESC_TESLA;
    public static final EditCommand.EditCompanyDescriptor DESC_MICROSOFT;

    static {
        DESC_TESLA = new EditCompanyDescriptorBuilder().withName(VALID_NAME_TESLA)
                .withPhone(VALID_PHONE_TESLA).withEmail(VALID_EMAIL_TESLA).withAddress(VALID_ADDRESS_TESLA)
                .withRemark(VALID_REMARK).withCareerPageUrl(VALID_CAREER_PAGE_URL_TESLA)
                .withTags(VALID_TAG_COMPANY).build(); // Added remark
        DESC_MICROSOFT = new EditCompanyDescriptorBuilder().withName(VALID_NAME_MICROSOFT)
                .withPhone(VALID_PHONE_MICROSOFT).withCareerPageUrl(VALID_CAREER_PAGE_URL_MICROSOFT)
                .withEmail(VALID_EMAIL_MICROSOFT).withAddress(VALID_ADDRESS_MICROSOFT)
                .withTags(VALID_TAG_BIGTECH, VALID_TAG_COMPANY).withRemark(VALID_REMARK).build(); // Added remark
    }

    /**
     * Executes the given {@code command}, confirms that:
     * - the returned {@link CommandResult} matches {@code expectedCommandResult}
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
     * Executes the given {@code command}, confirms that:
     * - a {@code CommandException} is thrown
     * - the CommandException message matches {@code expectedMessage}
     * - the address book, filtered company list, and selected company in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // We are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Company> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCompanyList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredCompanyList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the company at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showCompanyAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCompanyList().size());

        Company company = model.getFilteredCompanyList().get(targetIndex.getZeroBased());
        final String[] splitName = company.getName().fullName.split("\\s+");
        model.updateFilteredCompanyList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCompanyList().size());
    }

}
