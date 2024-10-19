package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithRental;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.EmailContainsKeywordsPredicate;
import seedu.address.model.client.NameContainsKeywordsPredicate;
import seedu.address.model.client.PhoneContainsKeywordsPredicate;
import seedu.address.model.client.RentalInformationContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Model modelWithRental = new ModelManager(getTypicalAddressBookWithRental(), new UserPrefs());
    private Model expectedModelWithRental = new ModelManager(getTypicalAddressBookWithRental(), new UserPrefs());
    @Test
    public void equals() {
        NameContainsKeywordsPredicate namePredicate1 =
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        PhoneContainsKeywordsPredicate phonePredicate1 =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("98761111"));
        EmailContainsKeywordsPredicate emailPredicate1 =
                new EmailContainsKeywordsPredicate(Collections.singletonList("alice@example.com"));
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate1 =
                new RentalInformationContainsKeywordsPredicate(Collections.singletonList("8100"));

        NameContainsKeywordsPredicate namePredicate2 =
                new NameContainsKeywordsPredicate(Collections.singletonList("Bob"));
        PhoneContainsKeywordsPredicate phonePredicate2 =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("22222222"));
        EmailContainsKeywordsPredicate emailPredicate2 =
                new EmailContainsKeywordsPredicate(Collections.singletonList("bob@example.com"));
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate2 =
                new RentalInformationContainsKeywordsPredicate(Collections.singletonList("2700"));

        FindCommand findFirstCommand =
                new FindCommand(namePredicate1, phonePredicate1, emailPredicate1, rentalInfoPredicate1);
        FindCommand findSecondCommand =
                new FindCommand(namePredicate2, phonePredicate2, emailPredicate2, rentalInfoPredicate2);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy =
                new FindCommand(namePredicate1, phonePredicate1, emailPredicate1, rentalInfoPredicate1);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different client -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(" ");
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate(" ");
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(" ");
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate = prepareRentalInfoPredicate(" ");

        FindCommand command = new FindCommand(namePredicate, phonePredicate, emailPredicate, rentalInfoPredicate);
        expectedModel.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(rentalInfoPredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_addressKeyword_success() {
        String address = "BLK";
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(address);
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate(address);
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(address);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate = prepareRentalInfoPredicate(address);

        FindCommand command = new FindCommand(namePredicate, phonePredicate, emailPredicate, rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(rentalInfoPredicate));
        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

    @Test
    public void execute_monthlyRentKeyword_success() {
        String address = "2900";
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(address);
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate(address);
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(address);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate = prepareRentalInfoPredicate(address);

        FindCommand command = new FindCommand(namePredicate, phonePredicate, emailPredicate, rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(rentalInfoPredicate));
        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

    @Test
    public void execute_depositKeyword_success() {
        String address = "5800";
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(address);
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate(address);
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(address);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate = prepareRentalInfoPredicate(address);

        FindCommand command = new FindCommand(namePredicate, phonePredicate, emailPredicate, rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(rentalInfoPredicate));
        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

    @Test
    public void execute_tenantsKeyword_success() {
        String address = "carl";
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(address);
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate(address);
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(address);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate = prepareRentalInfoPredicate(address);

        FindCommand command = new FindCommand(namePredicate, phonePredicate, emailPredicate, rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(rentalInfoPredicate));
        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

    @Test
    public void execute_rentalStartDateKeyword_success() {
        String address = "01 Jan";
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(address);
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate(address);
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(address);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate = prepareRentalInfoPredicate(address);

        FindCommand command = new FindCommand(namePredicate, phonePredicate, emailPredicate, rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(rentalInfoPredicate));
        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

    @Test
    public void execute_rentalEndDateKeyword_success() {
        String address = "31 Dec";
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(address);
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate(address);
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(address);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate = prepareRentalInfoPredicate(address);

        FindCommand command = new FindCommand(namePredicate, phonePredicate, emailPredicate, rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(rentalInfoPredicate));
        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        PhoneContainsKeywordsPredicate phonePredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("98761111"));
        EmailContainsKeywordsPredicate emailPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("alice@example.com"));
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate =
                new RentalInformationContainsKeywordsPredicate(Collections.singletonList("8100"));

        FindCommand findCommand = new FindCommand(namePredicate, phonePredicate, emailPredicate, rentalInfoPredicate);
        String expected = FindCommand.class.getCanonicalName() + "{namePredicate=" + namePredicate
                + ", phonePredicate=" + phonePredicate
                + ", emailPredicate=" + emailPredicate
                + ", rentalInfoPredicate=" + rentalInfoPredicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput));
    }

    private PhoneContainsKeywordsPredicate preparePhonePredicate(String userInput) {
        return new PhoneContainsKeywordsPredicate(Arrays.asList(userInput));
    }

    private EmailContainsKeywordsPredicate prepareEmailPredicate(String userInput) {
        return new EmailContainsKeywordsPredicate(Arrays.asList(userInput));
    }

    private RentalInformationContainsKeywordsPredicate prepareRentalInfoPredicate(String userInput) {
        return new RentalInformationContainsKeywordsPredicate(Arrays.asList(userInput));
    }
}
