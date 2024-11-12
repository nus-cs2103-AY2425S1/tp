package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONCERT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONCERT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.concert.Concert;
import seedu.address.model.person.Person;
import seedu.address.testutil.Assert;
import seedu.address.testutil.ConcertBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddConcertContactCommandTest {
    private static final Index INDEX_INVALID_PERSON = Index.fromOneBased(999);
    private static final Index INDEX_INVALID_CONCERT = Index.fromOneBased(999);

    @Test
    public void execute_validIndices_success() throws Exception {
        Model modelStub = new ModelManager();
        Person personToLink = new PersonBuilder().build();
        Concert concertToLink = new ConcertBuilder().build();
        modelStub.addPerson(personToLink);
        modelStub.addConcert(concertToLink);

        AddConcertContactCommand addConcertContactCommand = new AddConcertContactCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_CONCERT);

        CommandResult commandResult = addConcertContactCommand.execute(modelStub);

        String expectedMessage = String.format(AddConcertContactCommand.MESSAGE_LINK_PERSON_SUCCESS,
                Messages.format(personToLink), Messages.format(concertToLink));
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Model modelStub = new ModelManager();
        Concert concertToLink = new ConcertBuilder().build();
        modelStub.addConcert(concertToLink);

        AddConcertContactCommand addConcertContactCommand = new AddConcertContactCommand(INDEX_INVALID_PERSON,
                INDEX_FIRST_CONCERT);

        assertThrows(CommandException.class, () -> addConcertContactCommand.execute(modelStub),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidConcertIndex_throwsCommandException() {
        Model modelStub = new ModelManager();
        Person personToLink = new PersonBuilder().build();
        modelStub.addPerson(personToLink);

        AddConcertContactCommand addConcertContactCommand = new AddConcertContactCommand(INDEX_FIRST_PERSON,
                INDEX_INVALID_CONCERT);

        assertThrows(CommandException.class, () -> addConcertContactCommand.execute(modelStub),
                Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws CommandException {
        Model modelStub = new ModelManager();
        Person personToLink = new PersonBuilder().build();
        Concert concertToLink = new ConcertBuilder().build();
        modelStub.addPerson(personToLink);
        modelStub.addConcert(concertToLink);

        AddConcertContactCommand addConcertContactCommand = new AddConcertContactCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_CONCERT);

        addConcertContactCommand.execute(modelStub);

        Assert.assertThrows(CommandException.class, AddConcertContactCommand.MESSAGE_DUPLICATE_CONCERTCONTACT, ()
                -> addConcertContactCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        final AddConcertContactCommand standardCommand = new AddConcertContactCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_CONCERT);

        // same values -> returns true
        AddConcertContactCommand commandWithSameValues = new AddConcertContactCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_CONCERT);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different person index -> returns false
        assertFalse(standardCommand.equals(new AddConcertContactCommand(INDEX_SECOND_PERSON, INDEX_FIRST_CONCERT)));

        // different concert index -> returns false
        assertFalse(standardCommand.equals(new AddConcertContactCommand(INDEX_FIRST_PERSON, INDEX_SECOND_CONCERT)));
    }

    @Test
    public void toStringMethod() {
        Index indexP = Index.fromOneBased(1);
        Index indexC = Index.fromOneBased(2);
        AddConcertContactCommand addConcertContactCommand = new AddConcertContactCommand(indexP, indexC);
        String expected = AddConcertContactCommand.class.getCanonicalName() + "{indexP=" + indexP + ", indexC="
                + indexC + "}";
        assertEquals(expected, addConcertContactCommand.toString());
    }
}
