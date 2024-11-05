package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;
import seedu.address.testutil.PersonBuilder;

public class ViewWeddingCommandTest {

    private Model model;
    private Model modelMultiple;
    private Model modelEmpty;

    @BeforeEach
    public void setUp() throws CommandException {
        model = new ModelManager();
        modelMultiple = new ModelManager();
        modelEmpty = new ModelManager();

        Person planner = new PersonBuilder().withName("Alice").withTags("Alice & Bob").build();
        Person florist = new PersonBuilder().withName("Bob").withTags("Bob & Charlie").build();
        Person planner2 = new PersonBuilder().withName("Charlie").withTags("Alice & Bob").build();

        model.addPerson(planner);
        model.addPerson(florist);

        Set<Person> stubParticipantSet = new HashSet<>();
        stubParticipantSet.add(planner);
        model.addWedding(new Wedding(new WeddingName("Alice & Bob"), new Venue("woodlands"),
                new Date("12/12/2024"), stubParticipantSet));


        modelMultiple.addPerson(planner);
        modelMultiple.addPerson(planner2);

        Set<Person> stubParticipantSet2 = new HashSet<>();
        stubParticipantSet2.add(planner);
        stubParticipantSet2.add(planner2);
        modelMultiple.addWedding(new Wedding(new WeddingName("Alice & Bob"), new Venue("woodlands"),
                new Date("12/12/2024"), stubParticipantSet2));

        modelEmpty.addWedding(new Wedding(new WeddingName("James & Charles"), new Venue("admiralty"),
                new Date("25/01/2034")));
    }

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate("Alice & Bob");
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate("Charlie & Danielle");

        ViewWeddingCommand viewWeddingFirstCommand = new ViewWeddingCommand(firstPredicate);
        ViewWeddingCommand viewWeddingSecondCommand = new ViewWeddingCommand(secondPredicate);

        // same object -> returns true
        assertEquals(viewWeddingFirstCommand, viewWeddingFirstCommand);

        // same values -> returns true
        ViewWeddingCommand viewWeddingFirstCommandCopy = new ViewWeddingCommand(firstPredicate);
        assertEquals(viewWeddingFirstCommand, viewWeddingFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(viewWeddingFirstCommand, 1);

        // null -> returns false
        assertNotEquals(viewWeddingFirstCommand, null);

        // different person -> returns false
        assertNotEquals(viewWeddingFirstCommand, viewWeddingSecondCommand);
    }

    @Test
    public void execute_noWeddingExists_throwsCommandException() {
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(null);
        ViewWeddingCommand command = new ViewWeddingCommand(predicate);
        model.updateFilteredPersonList(predicate);

        assertThrows(CommandException.class,
                ViewWeddingCommand.MESSAGE_WEDDING_DOESNT_EXIST, () -> command.execute(model));
    }

    @Test
    public void execute_existingWeddingWithNoParticipants_throwsCommandException() {
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate("James & Charles");
        ViewWeddingCommand command = new ViewWeddingCommand(predicate);
        modelEmpty.updateFilteredPersonList(predicate);

        assertThrows(CommandException.class,
                ViewWeddingCommand.MESSAGE_NO_PARTICIPANTS_ADDED, () -> command.execute(modelEmpty));
        assertEquals(0, modelEmpty.getFilteredPersonList().size());
    }

    @Test
    public void execute_singleMatchingKeyword_oneWeddingFound() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_PARTICIPANTS_LISTED_OVERVIEW, 1);
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate("Alice & Bob");
        model.updateFilteredPersonList(predicate);
        ViewWeddingCommand command = new ViewWeddingCommand(predicate);
        CommandResult commandResult = command.execute(model);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(1, model.getFilteredPersonList().size());
    }

    @Test
    public void execute_singleMatchingKeyword_multiplePersonsFound() throws Exception {
        String expectedMessage = String.format(Messages.MESSAGE_PARTICIPANTS_LISTED_OVERVIEW, 2);
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate("Alice & Bob");
        modelMultiple.updateFilteredPersonList(predicate);
        ViewWeddingCommand command = new ViewWeddingCommand(predicate);
        CommandResult commandResult = command.execute(modelMultiple);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(2, modelMultiple.getFilteredPersonList().size());
    }

    @Test
    public void toStringMethod() {
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate("wedding");
        ViewWeddingCommand command = new ViewWeddingCommand(predicate);
        String expected = "seedu.address.logic.commands.ViewWeddingCommand{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }
}
