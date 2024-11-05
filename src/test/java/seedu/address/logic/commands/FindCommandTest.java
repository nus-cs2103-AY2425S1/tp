package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import seedu.address.model.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonPredicateBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CommandHistory());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CommandHistory());

    @Test
    public void equals() {
        PersonPredicateBuilder firstPredicateBuilder = new PersonPredicateBuilder()
                .withNameKeywords(List.of("first"));
        PersonPredicateBuilder secondPredicateBuilder = new PersonPredicateBuilder()
                .withNameKeywords(List.of("second"));
        PersonPredicateBuilder thirdPredicateBuilder = new PersonPredicateBuilder()
                .withClassIdKeywords(List.of("third"));
        PersonPredicateBuilder fourthPredicateBuilder = new PersonPredicateBuilder()
                .withClassIdKeywords(List.of("fourth"));
        PersonPredicateBuilder fifthPredicateBuilder = new PersonPredicateBuilder()
                .withClassIdKeywords(List.of("fifth")).withNameKeywords(List.of("fifth"));
        PersonPredicateBuilder sixthPredicateBuilder = new PersonPredicateBuilder()
                .withClassIdKeywords(List.of("sixth")).withNameKeywords(List.of("fifth"));


        FindCommand findFirstCommand = new FindCommand(firstPredicateBuilder);
        FindCommand findSecondCommand = new FindCommand(secondPredicateBuilder);
        FindCommand findThirdCommand = new FindCommand(thirdPredicateBuilder);
        FindCommand findFourthCommand = new FindCommand(fourthPredicateBuilder);
        FindCommand findFifthCommand = new FindCommand(fifthPredicateBuilder);
        FindCommand findSixthCommand = new FindCommand(sixthPredicateBuilder);


        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));
        assertTrue(findThirdCommand.equals(findThirdCommand));
        assertTrue(findFifthCommand.equals(findFifthCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicateBuilder);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        FindCommand findThirdCommandCopy = new FindCommand(thirdPredicateBuilder);
        assertTrue(findThirdCommand.equals(findThirdCommandCopy));

        FindCommand findFifthCommandCopy = new FindCommand(fifthPredicateBuilder);
        assertTrue(findFifthCommand.equals(findFifthCommandCopy));


        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));
        assertFalse(findThirdCommand.equals(1));
        assertFalse(findFifthCommand.equals(1));

        assertFalse(findThirdCommand.equals(findFirstCommand));
        assertFalse(findThirdCommand.equals(findFourthCommand));
        assertFalse(findFifthCommand.equals(findFirstCommand));
        assertFalse(findFifthCommand.equals(findThirdCommand));
        assertFalse(findFifthCommand.equals(findSixthCommand));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));
        assertFalse(findThirdCommand.equals(null));
        assertFalse(findFifthCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
        assertFalse(findThirdCommand.equals(findFourthCommand));
    }

    @Test
    public void test_executeZeroKeywordsNoPersonFound_returnSuccess() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonPredicateBuilder predicateBuilder = preparePredicate(" ", "name");
        FindCommand command = new FindCommand(predicateBuilder);
        expectedModel.updateFilteredPersonList(predicateBuilder.build());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void test_executeNoPersonsFoundClassId_returnSuccess() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonPredicateBuilder predicateBuilder = preparePredicate(" ", "classId");
        FindCommand command = new FindCommand(predicateBuilder);
        expectedModel.updateFilteredPersonList(predicateBuilder.build());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void test_monthPaidContainsKeywordsWithMatchers_returnsTrue() {
        // Matcher for month paid
        PersonPredicateBuilder predicateBuilder = preparePredicate("2022-12", "monthPaid");
        assertTrue(predicateBuilder.build().test(new PersonBuilder().withMonthsPaid("2022-12").build()));

        // Only one matching keyword
        predicateBuilder = preparePredicate("2022-11 2022-12", "monthPaid");
        assertTrue(predicateBuilder.build().test(new PersonBuilder().withMonthsPaid("2022-12").build()));
    }

    @Test
    public void test_monthPaidContainsKeywordsWithMatchers_returnsFalse() {
        // Non-matching month paid
        PersonPredicateBuilder predicateBuilder =
                new PersonPredicateBuilder().withMonthPaidKeywords(Collections.singletonList("2022-11"));
        assertFalse(predicateBuilder.build().test(new PersonBuilder().withMonthsPaid("2022-12").build()));

        // Non-matching both months paid
        predicateBuilder =
                new PersonPredicateBuilder().withMonthPaidKeywords(Arrays.asList("2022-10", "2022-11"));
        assertFalse(predicateBuilder.build().test(new PersonBuilder().withMonthsPaid("2022-12").build()));
    }

    @Test
    public void test_notMonthPaidContainsKeywordsWithMatchers_returnsTrue() {
        // Matcher for not month paid
        PersonPredicateBuilder predicateBuilder =
                new PersonPredicateBuilder().withNotMonthPaidKeywords(Collections.singletonList("2022-11"));
        assertTrue(predicateBuilder.build().test(new PersonBuilder().withMonthsPaid("2022-12").build()));

        // Only one non-matching keyword
        predicateBuilder =
                new PersonPredicateBuilder().withNotMonthPaidKeywords(Arrays.asList("2022-10", "2022-11"));
        assertTrue(predicateBuilder.build().test(new PersonBuilder().withMonthsPaid("2022-12").build()));
    }

    @Test
    public void test_notMonthPaidContainsKeywordsWithMatchers_returnsFalse() {
        // Matcher for not month paid
        PersonPredicateBuilder predicateBuilder =
                new PersonPredicateBuilder().withNotMonthPaidKeywords(Collections.singletonList("2022-12"));
        assertFalse(predicateBuilder.build().test(new PersonBuilder().withMonthsPaid("2022-12").build()));

        // Only one non-matching keyword
        predicateBuilder =
                new PersonPredicateBuilder().withNotMonthPaidKeywords(Arrays.asList("2022-11", "2022-12"));
        assertFalse(predicateBuilder.build().test(new PersonBuilder().withMonthsPaid("2022-12").build()));
    }

    @Test
    public void test_executeWithMultipleKeywordsMultiplePersonsFound_returnSuccess() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonPredicateBuilder predicate = preparePredicate("Kurz Elle Kunz", "name");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate.build());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code PredicateBuilder} with specified type keywords.
     */
    private PersonPredicateBuilder preparePredicate(String userInput, String type) {
        if (Objects.equals(type, "name")) {
            return new PersonPredicateBuilder().withNameKeywords(Arrays.asList(userInput.split("\\s+")));
        }
        if (Objects.equals(type, "classId")) {
            return new PersonPredicateBuilder().withClassIdKeywords(Arrays.asList(userInput.split("\\s+")));
        }
        if (Objects.equals(type, "monthPaid")) {
            return new PersonPredicateBuilder().withMonthPaidKeywords(Arrays.asList(userInput.split("\\s+")));
        }
        if (Objects.equals(type, "notMonthPaid")) {
            return new PersonPredicateBuilder().withNotMonthPaidKeywords(Arrays.asList(userInput.split("\\s+")));
        }
        throw new RuntimeException();
    }
}
