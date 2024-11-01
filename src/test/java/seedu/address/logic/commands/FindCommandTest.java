package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_EMAIL_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_NAME_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_PHONE_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_RENTAL_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_TAGS_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithRental;

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
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model modelWithRental = new ModelManager(getTypicalAddressBookWithRental(), new UserPrefs());
    private Model expectedModelWithRental = new ModelManager(getTypicalAddressBookWithRental(), new UserPrefs());

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);

        FindCommand command = new FindCommand(EMPTY_NAME_PREDICATE, EMPTY_PHONE_PREDICATE, EMPTY_EMAIL_PREDICATE,
                EMPTY_TAGS_PREDICATE, EMPTY_RENTAL_PREDICATE);

        expectedModel.updateFilteredPersonList(EMPTY_PREDICATE);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_findName_success() {
        List<String> names = List.of("alice");
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(names);

        FindCommand command = new FindCommand(namePredicate, EMPTY_PHONE_PREDICATE, EMPTY_EMAIL_PREDICATE,
                EMPTY_TAGS_PREDICATE, EMPTY_RENTAL_PREDICATE);
        expectedModel.updateFilteredPersonList(namePredicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_findPhone_success() {
        List<String> phones = List.of("948");
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PhoneContainsKeywordsPredicate phonePredicate = new PhoneContainsKeywordsPredicate(phones);

        FindCommand command = new FindCommand(EMPTY_NAME_PREDICATE, phonePredicate, EMPTY_EMAIL_PREDICATE,
                EMPTY_TAGS_PREDICATE, EMPTY_RENTAL_PREDICATE);
        expectedModel.updateFilteredPersonList(phonePredicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_findEmail_success() {
        List<String> emails = List.of("johnd", "lydia");
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(emails);

        FindCommand command = new FindCommand(EMPTY_NAME_PREDICATE, EMPTY_PHONE_PREDICATE, emailPredicate,
                EMPTY_TAGS_PREDICATE, EMPTY_RENTAL_PREDICATE);
        expectedModel.updateFilteredPersonList(emailPredicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_findTags_success() {
        List<String> tags = List.of("owesMoney");
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        TagsContainsKeywordsPredicate tagsPredicate = new TagsContainsKeywordsPredicate(tags);

        FindCommand command = new FindCommand(EMPTY_NAME_PREDICATE, EMPTY_PHONE_PREDICATE, EMPTY_EMAIL_PREDICATE,
                tagsPredicate, EMPTY_RENTAL_PREDICATE);
        expectedModel.updateFilteredPersonList(tagsPredicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_findMultipleFields_success() {
        List<String> names = List.of("alice", "daniel");
        List<String> phones = List.of("943", "944");
        List<String> emails = List.of("johnd", "lydia");
        List<String> tags = List.of("owesMoney");
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);

        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(names);
        PhoneContainsKeywordsPredicate phonePredicate = new PhoneContainsKeywordsPredicate(phones);
        EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(emails);
        TagsContainsKeywordsPredicate tagsPredicate = new TagsContainsKeywordsPredicate(tags);

        FindCommand command = new FindCommand(namePredicate, phonePredicate, emailPredicate, tagsPredicate,
                EMPTY_RENTAL_PREDICATE);
        expectedModel.updateFilteredPersonList(namePredicate.or(phonePredicate).or(emailPredicate).or(tagsPredicate));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    // TODO: move the tests below for a new "rfind" command?
    @Test
    public void execute_findAddress_success() {
        List<String> address = List.of("BLK");
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(address);
        PhoneContainsKeywordsPredicate phonePredicate = new PhoneContainsKeywordsPredicate(address);
        EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(address);
        TagsContainsKeywordsPredicate tagsPredicate = new TagsContainsKeywordsPredicate(address);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate =
                new RentalInformationContainsKeywordsPredicate(address);

        FindCommand command = new FindCommand(namePredicate, phonePredicate, emailPredicate, tagsPredicate,
                rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(tagsPredicate).or(rentalInfoPredicate));

        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

    @Test
    public void execute_findMonthlyRent_success() {
        List<String> monthlyRent = List.of("2900");
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(monthlyRent);
        PhoneContainsKeywordsPredicate phonePredicate = new PhoneContainsKeywordsPredicate(monthlyRent);
        EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(monthlyRent);
        TagsContainsKeywordsPredicate tagsPredicate = new TagsContainsKeywordsPredicate(monthlyRent);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate =
                new RentalInformationContainsKeywordsPredicate(monthlyRent);

        FindCommand command = new FindCommand(namePredicate, phonePredicate, emailPredicate, tagsPredicate,
                rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(tagsPredicate).or(rentalInfoPredicate));

        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

    @Test
    public void execute_findDeposit_success() {
        List<String> deposit = List.of("5800");
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(deposit);
        PhoneContainsKeywordsPredicate phonePredicate = new PhoneContainsKeywordsPredicate(deposit);
        EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(deposit);
        TagsContainsKeywordsPredicate tagsPredicate = new TagsContainsKeywordsPredicate(deposit);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate =
                new RentalInformationContainsKeywordsPredicate(deposit);

        FindCommand command = new FindCommand(namePredicate, phonePredicate, emailPredicate, tagsPredicate,
                rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(tagsPredicate).or(rentalInfoPredicate));

        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

    @Test
    public void execute_findTenants_success() {
        List<String> tenants = List.of("carl");
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(tenants);
        PhoneContainsKeywordsPredicate phonePredicate = new PhoneContainsKeywordsPredicate(tenants);
        EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(tenants);
        TagsContainsKeywordsPredicate tagsPredicate = new TagsContainsKeywordsPredicate(tenants);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate =
                new RentalInformationContainsKeywordsPredicate(tenants);

        FindCommand command = new FindCommand(namePredicate, phonePredicate, emailPredicate, tagsPredicate,
                rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(tagsPredicate).or(rentalInfoPredicate));

        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

    @Test
    public void execute_findRentalStartDate_success() {
        List<String> rentalStartDate = List.of("01 Jan");
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(rentalStartDate);
        PhoneContainsKeywordsPredicate phonePredicate = new PhoneContainsKeywordsPredicate(rentalStartDate);
        EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(rentalStartDate);
        TagsContainsKeywordsPredicate tagsPredicate = new TagsContainsKeywordsPredicate(rentalStartDate);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate =
                new RentalInformationContainsKeywordsPredicate(rentalStartDate);

        FindCommand command = new FindCommand(namePredicate, phonePredicate, emailPredicate, tagsPredicate,
                rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(tagsPredicate).or(rentalInfoPredicate));

        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

    @Test
    public void execute_findRentalEndDate_success() {
        List<String> rentalEndDate = List.of("31 Dec");
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(rentalEndDate);
        PhoneContainsKeywordsPredicate phonePredicate = new PhoneContainsKeywordsPredicate(rentalEndDate);
        EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(rentalEndDate);
        TagsContainsKeywordsPredicate tagsPredicate = new TagsContainsKeywordsPredicate(rentalEndDate);
        RentalInformationContainsKeywordsPredicate rentalInfoPredicate =
                new RentalInformationContainsKeywordsPredicate(rentalEndDate);

        FindCommand command = new FindCommand(namePredicate, phonePredicate, emailPredicate, tagsPredicate,
                rentalInfoPredicate);
        expectedModelWithRental.updateFilteredPersonList(
                namePredicate.or(phonePredicate).or(emailPredicate).or(tagsPredicate).or(rentalInfoPredicate));

        assertCommandSuccess(command, modelWithRental, expectedMessage, expectedModelWithRental);
        assertEquals(expectedModelWithRental.getFilteredPersonList(), modelWithRental.getFilteredPersonList());
    }

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
}
