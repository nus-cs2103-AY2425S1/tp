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
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.EmailContainsKeywordsPredicate;
import seedu.address.model.client.NameContainsKeywordsPredicate;
import seedu.address.model.client.PhoneContainsKeywordsPredicate;
import seedu.address.model.client.RentalInformationContainsKeywordsPredicate;
import seedu.address.model.tag.TagsContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private static final NameContainsKeywordsPredicate EMPTY_NAME_PREDICATE =
            new NameContainsKeywordsPredicate(List.of());
    private static final PhoneContainsKeywordsPredicate EMPTY_PHONE_PREDICATE =
            new PhoneContainsKeywordsPredicate(List.of());
    private static final EmailContainsKeywordsPredicate EMPTY_EMAIL_PREDICATE =
            new EmailContainsKeywordsPredicate(List.of());
    private static final TagsContainsKeywordsPredicate EMPTY_TAGS_PREDICATE =
            new TagsContainsKeywordsPredicate(List.of());
    private static final RentalInformationContainsKeywordsPredicate EMPTY_RENTAL_PREDICATE =
            new RentalInformationContainsKeywordsPredicate(List.of());

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
        TagsContainsKeywordsPredicate tagsPredicate1 =
                new TagsContainsKeywordsPredicate(Collections.singletonList("friends"));
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate1 =
                new RentalInformationContainsKeywordsPredicate(Collections.singletonList("8100"));

        NameContainsKeywordsPredicate namePredicate2 =
                new NameContainsKeywordsPredicate(Collections.singletonList("Bob"));
        PhoneContainsKeywordsPredicate phonePredicate2 =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("22222222"));
        EmailContainsKeywordsPredicate emailPredicate2 =
                new EmailContainsKeywordsPredicate(Collections.singletonList("bob@example.com"));
        TagsContainsKeywordsPredicate tagsPredicate2 =
                new TagsContainsKeywordsPredicate(Collections.singletonList("owesMoney"));
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate2 =
                new RentalInformationContainsKeywordsPredicate(Collections.singletonList("2700"));

        FindCommand findFirstCommand =
                new FindCommand(namePredicate1, phonePredicate1, emailPredicate1, tagsPredicate1, rentalInfoPredicate1);
        FindCommand findSecondCommand =
                new FindCommand(namePredicate2, phonePredicate2, emailPredicate2, tagsPredicate2, rentalInfoPredicate2);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy =
                new FindCommand(namePredicate1, phonePredicate1, emailPredicate1, tagsPredicate1, rentalInfoPredicate1);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicates -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(" ");
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate(" ");
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(" ");
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate = prepareRentalInfoPredicate(" ");

        FindCommand command = new FindCommand(namePredicate,
                phonePredicate,
                emailPredicate,
                EMPTY_TAGS_PREDICATE,
                rentalInfoPredicate);
        expectedModel.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(rentalInfoPredicate));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_findAddress_success() {
        String address = "BLK";
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(address);
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate(address);
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(address);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate = prepareRentalInfoPredicate(address);

        FindCommand command = new FindCommand(namePredicate,
                phonePredicate,
                emailPredicate,
                EMPTY_TAGS_PREDICATE,
                rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(rentalInfoPredicate));

        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

    @Test
    public void execute_findMonthlyRent_success() {
        String address = "2900";
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(address);
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate(address);
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(address);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate = prepareRentalInfoPredicate(address);

        FindCommand command = new FindCommand(namePredicate,
                phonePredicate,
                emailPredicate,
                EMPTY_TAGS_PREDICATE,
                rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(rentalInfoPredicate));

        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

    @Test
    public void execute_findDeposit_success() {
        String address = "5800";
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(address);
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate(address);
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(address);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate = prepareRentalInfoPredicate(address);

        FindCommand command = new FindCommand(namePredicate,
                phonePredicate,
                emailPredicate,
                EMPTY_TAGS_PREDICATE,
                rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(rentalInfoPredicate));

        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

    @Test
    public void execute_findTenants_success() {
        String address = "carl";
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(address);
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate(address);
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(address);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate = prepareRentalInfoPredicate(address);

        FindCommand command = new FindCommand(namePredicate,
                phonePredicate,
                emailPredicate,
                EMPTY_TAGS_PREDICATE,
                rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(rentalInfoPredicate));

        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

    @Test
    public void execute_findRentalStartDate_success() {
        String address = "01 Jan";
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(address);
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate(address);
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(address);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate = prepareRentalInfoPredicate(address);

        FindCommand command = new FindCommand(namePredicate,
                phonePredicate,
                emailPredicate,
                EMPTY_TAGS_PREDICATE,
                rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(rentalInfoPredicate));

        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

    @Test
    public void execute_findRentalEndDate_success() {
        String address = "31 Dec";
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(address);
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate(address);
        EmailContainsKeywordsPredicate emailPredicate = prepareEmailPredicate(address);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate = prepareRentalInfoPredicate(address);

        FindCommand command = new FindCommand(namePredicate,
                phonePredicate,
                emailPredicate,
                EMPTY_TAGS_PREDICATE,
                rentalInfoPredicate);
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
        TagsContainsKeywordsPredicate tagsPredicate =
                new TagsContainsKeywordsPredicate(Collections.singletonList("friends"));
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate =
                new RentalInformationContainsKeywordsPredicate(Collections.singletonList("8100"));

        FindCommand findCommand =
                new FindCommand(namePredicate, phonePredicate, emailPredicate, tagsPredicate, rentalInfoPredicate);
        String expected = FindCommand.class.getCanonicalName() + "{namePredicate=" + namePredicate
                + ", phonePredicate=" + phonePredicate
                + ", emailPredicate=" + emailPredicate
                + ", tagsPredicate=" + tagsPredicate
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
