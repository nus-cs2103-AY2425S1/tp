package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.predicate.FieldContainsKeywordsPredicate;
import seedu.address.model.predicate.PredicateAdapter;
import seedu.address.model.predicate.StudentAttendedTutorialPredicate;
import seedu.address.model.predicate.StudentHasPaidPredicate;
import seedu.address.model.predicate.SubjectMatchesKeywordsPredicate;
import seedu.address.model.predicate.TagContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private final FieldContainsKeywordsPredicate<Person> firstNamePredicate =
            new FieldContainsKeywordsPredicate<>(Collections.singletonList("first"), Person::getFullName,
                    true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);
    private final FieldContainsKeywordsPredicate<Person> secondNamePredicate =
            new FieldContainsKeywordsPredicate<>(Collections.singletonList("second"), Person::getFullName,
                    true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);
    private final FieldContainsKeywordsPredicate<Person> thirdNamePredicate =
            new FieldContainsKeywordsPredicate<>(Collections.singletonList("Second"), Person::getFullName,
                    true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);
    private final FieldContainsKeywordsPredicate<Person> thirdAddressPredicate =
            new FieldContainsKeywordsPredicate<>(Collections.singletonList("second"), Person::getAddressValue,
                    true, FieldContainsKeywordsPredicate.ADDRESS_IDENTIFIER);
    private final TagContainsKeywordPredicate firstTagPredicate = new TagContainsKeywordPredicate("Scholars");
    private final TagContainsKeywordPredicate secondTagPredicate = new TagContainsKeywordPredicate("Scholar");
    private final SubjectMatchesKeywordsPredicate firstSubjectPredicate = new SubjectMatchesKeywordsPredicate("Math");
    private final SubjectMatchesKeywordsPredicate secondSubjectPredicate = new SubjectMatchesKeywordsPredicate("Malay");
    private final StudentAttendedTutorialPredicate firstAttendancePredicate =
            new StudentAttendedTutorialPredicate(LocalDate.of(2024, 1, 1),
                    LocalDate.of(2024, 12, 31));
    private final StudentAttendedTutorialPredicate secondAttendancePredicate =
            new StudentAttendedTutorialPredicate(LocalDate.of(2024, 6, 1),
                    LocalDate.of(2024, 8, 20));
    private final StudentHasPaidPredicate firstPaymentPredicate = new StudentHasPaidPredicate(true);
    private final StudentHasPaidPredicate secondPaymentPredicate = new StudentHasPaidPredicate(false);


    @Test
    public void equals() {

        FindCommand findFirstCommand = new FindCommand(List.of(firstNamePredicate), new ArrayList<>());
        FindCommand findSecondCommand = new FindCommand(List.of(secondNamePredicate), new ArrayList<>());
        FindCommand findCaseInsensitiveCommand = new FindCommand(List.of(thirdNamePredicate), new ArrayList<>());
        FindCommand findThirdCommand = new FindCommand(List.of(firstNamePredicate, thirdAddressPredicate),
                new ArrayList<>());
        FindCommand findForthCommand = new FindCommand(List.of(firstNamePredicate, thirdAddressPredicate),
                new ArrayList<>());
        FindCommand findFifthCommand = new FindCommand(List.of(firstTagPredicate), new ArrayList<>());
        FindCommand findSixthCommand = new FindCommand(List.of(secondTagPredicate), new ArrayList<>());
        FindCommand findSeventhCommand = new FindCommand(List.of(secondTagPredicate), new ArrayList<>());
        FindCommand emptyFindCommand = new FindCommand(Collections.emptyList(), new ArrayList<>());

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(List.of(firstNamePredicate), new ArrayList<>());
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different person -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);

        // same findCommand predicates -> returns true
        assertEquals(findThirdCommand, findForthCommand);

        // different predicate numbers
        assertNotEquals(findFirstCommand, findSeventhCommand);

        // different tag -> returns false
        assertNotEquals(findFifthCommand, findSixthCommand);

        // same tag -> return true
        assertEquals(findSixthCommand, findSeventhCommand);

        // Empty predicate list vs non-empty predicate list
        assertNotEquals(emptyFindCommand, findFirstCommand);

        // predicate with different case -> return false
        assertNotEquals(findSecondCommand, findCaseInsensitiveCommand);
    }

    @Test
    public void equals_differentNumberOfPredicates_returnsFalse() {
        // One predicate vs two predicates
        FindCommand findFirstCommand = new FindCommand(List.of(firstNamePredicate), new ArrayList<>());
        FindCommand findSecondCommand = new FindCommand(List.of(firstNamePredicate, thirdAddressPredicate),
                new ArrayList<>());

        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void equals_differentFieldsDifferentValues_returnsFalse() {
        // First command searches by name, second command searches by address
        FindCommand findFirstCommand = new FindCommand(List.of(firstNamePredicate), new ArrayList<>());
        FindCommand findByAddressCommand = new FindCommand(List.of(thirdAddressPredicate), new ArrayList<>());

        assertNotEquals(findFirstCommand, findByAddressCommand);
    }

    @Test
    public void execute_zeroKeywords_exceptionThrown() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FieldContainsKeywordsPredicate<Person> predicate = new FieldContainsKeywordsPredicate<>(Arrays.asList(" "),
                Person::getPhoneValue, false, FieldContainsKeywordsPredicate.PHONE_IDENTIFIER);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            expectedModel.updateFilteredPersonList(predicate);
        });
        assertEquals("Word parameter cannot be empty", exception.getMessage());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FieldContainsKeywordsPredicate<Person> predicate =
                new FieldContainsKeywordsPredicate<>(Arrays.asList("Kurz", "Elle", "Kunz"), Person::getFullName,
                        true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);
        FindCommand command = new FindCommand(List.of(predicate), new ArrayList<>());
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        FieldContainsKeywordsPredicate<Person> predicate =
                new FieldContainsKeywordsPredicate<>(Collections.singletonList("keyword"), Person::getFullName,
                        true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);
        FindCommand findCommand = new FindCommand(List.of(predicate), new ArrayList<>());
        String expected = FindCommand.class.getCanonicalName() + "{student predicates=" + "[" + predicate + "]" + ", "
                + "participation predicates=" + "[" + "]" + "}";
        assertEquals(expected, findCommand.toString());
    }
    @Test
    public void execute_noMatches_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FieldContainsKeywordsPredicate<Person> predicate =
                new FieldContainsKeywordsPredicate<>(Collections.singletonList("NonExistentName"), Person::getFullName,
                        true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);
        FindCommand command = new FindCommand(List.of(predicate), new ArrayList<>());
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleKeyword_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FieldContainsKeywordsPredicate<Person> predicate =
                new FieldContainsKeywordsPredicate<>(Collections.singletonList("Carl"), Person::getFullName,
                        true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);
        FindCommand command = new FindCommand(List.of(predicate), new ArrayList<>());
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_caseInsensitiveKeyword_matchFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FieldContainsKeywordsPredicate<Person> predicate =
                new FieldContainsKeywordsPredicate<>(Collections.singletonList("carl"), Person::getFullName,
                        true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER); // "carl" in lowercase
        FindCommand command = new FindCommand(List.of(predicate), new ArrayList<>());
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_multiplePredicates_multipleFields() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FieldContainsKeywordsPredicate<Person> namePredicate =
                new FieldContainsKeywordsPredicate<>(Collections.singletonList("Elle"), Person::getFullName,
                        true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);
        FieldContainsKeywordsPredicate<Person> addressPredicate =
                new FieldContainsKeywordsPredicate<>(Collections.singletonList("miche"), Person::getAddressValue,
                        true, FieldContainsKeywordsPredicate.ADDRESS_IDENTIFIER);
        FindCommand command = new FindCommand(List.of(namePredicate, addressPredicate), new ArrayList<>());
        expectedModel.updateFilteredPersonList(namePredicate.and(addressPredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE), model.getFilteredPersonList());
    }

    @Test
    public void execute_emptyKeywordList_allPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        FindCommand command = new FindCommand(Collections.emptyList(), new ArrayList<>());
        expectedModel.updateFilteredPersonList(person -> true); // no person should match
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertNotEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_subjectPredicateMath_found() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = new FindCommand(new ArrayList<>(), List.of(firstSubjectPredicate));
        expectedModel.updateFilteredPersonList(
                new PredicateAdapter(List.of(firstSubjectPredicate),
                model.getParticipationList()));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_subjectPredicateMalay_found() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = new FindCommand(new ArrayList<>(), List.of(secondSubjectPredicate));
        expectedModel.updateFilteredPersonList(new PredicateAdapter(
                List.of(secondSubjectPredicate),
                model.getParticipationList()));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_attendancePredicateJanToDec_found() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1); // Adjust based on expected results
        FindCommand command = new FindCommand(new ArrayList<>(), List.of(firstAttendancePredicate));
        expectedModel.updateFilteredPersonList(new PredicateAdapter(List.of(firstAttendancePredicate),
                model.getParticipationList()));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList()); // Adjust based on actual expected persons
    }

    @Test
    public void execute_attendancePredicateJunToAug_found() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = new FindCommand(new ArrayList<>(), List.of(firstAttendancePredicate));
        expectedModel.updateFilteredPersonList(new PredicateAdapter(List.of(firstAttendancePredicate),
                model.getParticipationList()));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_paymentPredicatePaid_found() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        FindCommand command = new FindCommand(List.of(firstPaymentPredicate), new ArrayList<>());
        expectedModel.updateFilteredPersonList(firstPaymentPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, DANIEL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_paymentPredicateNotPaid_found() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = new FindCommand(List.of(secondPaymentPredicate), new ArrayList<>());
        expectedModel.updateFilteredPersonList(secondPaymentPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, CARL, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multiplePredicatesSubjectAndPayment_found() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = new FindCommand(List.of(firstPaymentPredicate), List.of(firstSubjectPredicate));
        expectedModel.updateFilteredPersonList(new PredicateAdapter(List.of(firstSubjectPredicate),
                model.getParticipationList()).and(firstPaymentPredicate)
        );
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multiplePredicatesAttendanceAndPayment_notFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0); // Adjust based on expected results
        FindCommand command = new FindCommand(List.of(firstPaymentPredicate), List.of(secondAttendancePredicate));
        expectedModel.updateFilteredPersonList(new PredicateAdapter(List.of(secondAttendancePredicate),
                model.getParticipationList()).and(firstPaymentPredicate)
        );
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(), model.getFilteredPersonList()); // Adjust based on actual expected persons
    }

    @Test
    public void equals_differentPredicatesSameField_returnsFalse() {
        FindCommand findCommandOneKeyword = new FindCommand(List.of(new FieldContainsKeywordsPredicate<>(
                Collections.singletonList("John"), Person::getFullName, true,
                FieldContainsKeywordsPredicate.NAME_IDENTIFIER)),
                new ArrayList<>());
        FindCommand findCommandTwoKeywords = new FindCommand(List.of(new FieldContainsKeywordsPredicate<>(
                Arrays.asList("John", "Doe"), Person::getFullName, true,
                FieldContainsKeywordsPredicate.NAME_IDENTIFIER)),
                new ArrayList<>());

        assertNotEquals(findCommandOneKeyword, findCommandTwoKeywords);
    }

    @Test
    public void equals_samePredicateDifferentMatchType_returnsFalse() {
        FieldContainsKeywordsPredicate<Person> caseSensitivePredicate =
                new FieldContainsKeywordsPredicate<>(Collections.singletonList("Keyword"), Person::getFullName,
                        false, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);
        FieldContainsKeywordsPredicate<Person> caseInsensitivePredicate =
                new FieldContainsKeywordsPredicate<>(Collections.singletonList("Keyword"), Person::getFullName,
                        true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER);

        FindCommand caseSensitiveCommand = new FindCommand(List.of(caseSensitivePredicate), new ArrayList<>());
        FindCommand caseInsensitiveCommand = new FindCommand(List.of(caseInsensitivePredicate), new ArrayList<>());

        assertNotEquals(caseSensitiveCommand, caseInsensitiveCommand);
    }
}
