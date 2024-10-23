package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_CONCERT_CONCERTS_LISTED_OVERVIEW;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalConcertContacts.ALICE_COACHELLA;
import static seedu.address.testutil.TypicalConcertContacts.ALICE_GLASTONBURY;
import static seedu.address.testutil.TypicalConcertContacts.BENSON_COACHELLA;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONCERT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONCERT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CONCERT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertContact;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code FindConcertContactCommand}.
 */
public class FindConcertContactCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Index personIndex = INDEX_FIRST_PERSON;
        Index personIndex2 = INDEX_SECOND_PERSON;
        Index concertIndex = INDEX_FIRST_CONCERT;

        FindConcertContactCommand findFirstCommand = new FindConcertContactCommand(personIndex, concertIndex);
        FindConcertContactCommand findSecondCommand = new FindConcertContactCommand(personIndex, concertIndex);
        FindConcertContactCommand findThirdCommand = new FindConcertContactCommand(personIndex2, concertIndex);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        assertTrue(findFirstCommand.equals(findSecondCommand));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(findFirstCommand.equals(findThirdCommand));
    }

    @Test
    public void execute_indexOutOfBounds_throwsCommandException() {
        // Person index too big
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FindConcertContactCommand findCommand = new FindConcertContactCommand(outOfBoundIndex, null);
        assertCommandFailure(findCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // Concert index too big
        outOfBoundIndex = Index.fromOneBased(model.getFilteredConcertList().size() + 1);
        findCommand = new FindConcertContactCommand(null, outOfBoundIndex);
        assertCommandFailure(findCommand, model, MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personKeyword_noConcertContactsFound() {
        String expectedMessage = String.format(MESSAGE_CONCERT_CONCERTS_LISTED_OVERVIEW, 0);
        FindConcertContactCommand command = new FindConcertContactCommand(INDEX_THIRD_PERSON, null);
        Person p = getTypicalAddressBook().getPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Predicate<ConcertContact> predicate = cc -> cc.isAssociated(p);
        expectedModel.updateFilteredConcertContactList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredConcertContactList());
    }

    @Test
    public void execute_personKeyword_multipleConcertContactsFound() {
        String expectedMessage = String.format(MESSAGE_CONCERT_CONCERTS_LISTED_OVERVIEW, 2);
        FindConcertContactCommand command = new FindConcertContactCommand(INDEX_FIRST_PERSON, null);
        Person p = getTypicalAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Predicate<ConcertContact> predicate = cc -> cc.isAssociated(p);
        expectedModel.updateFilteredConcertContactList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_COACHELLA, ALICE_GLASTONBURY), model.getFilteredConcertContactList());
    }

    @Test
    public void execute_concertKeyword_noConcertContactsFound() {
        String expectedMessage = String.format(MESSAGE_CONCERT_CONCERTS_LISTED_OVERVIEW, 0);
        FindConcertContactCommand command = new FindConcertContactCommand(null, INDEX_THIRD_CONCERT);
        Concert c = getTypicalAddressBook().getConcertList().get(INDEX_THIRD_CONCERT.getZeroBased());
        Predicate<ConcertContact> predicate = cc -> cc.isAssociated(c);
        expectedModel.updateFilteredConcertContactList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredConcertContactList());
    }

    @Test
    public void execute_concertKeyword_multipleConcertContactsFound() {
        String expectedMessage = String.format(MESSAGE_CONCERT_CONCERTS_LISTED_OVERVIEW, 2);
        FindConcertContactCommand command = new FindConcertContactCommand(null, INDEX_FIRST_CONCERT);
        Concert c = getTypicalAddressBook().getConcertList().get(INDEX_FIRST_CONCERT.getZeroBased());
        Predicate<ConcertContact> predicate = cc -> cc.isAssociated(c);
        expectedModel.updateFilteredConcertContactList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_COACHELLA, BENSON_COACHELLA), model.getFilteredConcertContactList());
    }

    @Test
    public void execute_personAndConcertKeyword_noConcertContactsFound() {
        String expectedMessage = String.format(MESSAGE_CONCERT_CONCERTS_LISTED_OVERVIEW, 0);
        FindConcertContactCommand command = new FindConcertContactCommand(
                INDEX_FIRST_PERSON,
                INDEX_THIRD_CONCERT);
        Person p = getTypicalAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Concert c = getTypicalAddressBook().getConcertList().get(INDEX_THIRD_CONCERT.getZeroBased());
        Predicate<ConcertContact> predicate = cc -> cc.isAssociated(p) && cc.isAssociated(c);
        expectedModel.updateFilteredConcertContactList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredConcertContactList());
    }

    @Test
    public void execute_personAndConcertKeyword_oneConcertContactFound() {
        String expectedMessage = String.format(MESSAGE_CONCERT_CONCERTS_LISTED_OVERVIEW, 1);
        FindConcertContactCommand command = new FindConcertContactCommand(
                INDEX_FIRST_PERSON,
                INDEX_FIRST_CONCERT);
        Person p = getTypicalAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Concert c = getTypicalAddressBook().getConcertList().get(INDEX_FIRST_CONCERT.getZeroBased());
        Predicate<ConcertContact> predicate = cc -> cc.isAssociated(p) && cc.isAssociated(c);
        expectedModel.updateFilteredConcertContactList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE_COACHELLA), model.getFilteredConcertContactList());
    }

    @Test
    public void toStringMethod() {
        FindConcertContactCommand findCommand = new FindConcertContactCommand(INDEX_SECOND_PERSON,
                INDEX_SECOND_CONCERT);
        String expected = FindConcertContactCommand.class.getCanonicalName()
                + "{indexP=" + INDEX_SECOND_PERSON
                + ", indexC=" + INDEX_SECOND_CONCERT + "}";
        assertEquals(expected, findCommand.toString());
    }
}
