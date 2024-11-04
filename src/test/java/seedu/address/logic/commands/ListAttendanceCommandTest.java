package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_MEMBERS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.role.Member;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListAttendanceCommand.
 */
public class ListAttendanceCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listAttendanceIsNotFiltered_showsAllMembers() {
        Person person1 = getTypicalAddressBook().getPersonList().get(0);
        Person person2 = getTypicalAddressBook().getPersonList().get(2);

        Person member1 = new Person(person1.getName(), person1.getPhone(), person1.getEmail(),
                person1.getTelegram(), Set.of(new Member()), new HashSet<>(), person1.getFavouriteStatus());
        Person member2 = new Person(person2.getName(), person2.getPhone(), person2.getEmail(),
                person2.getTelegram(), Set.of(new Member()), new HashSet<>(), person2.getFavouriteStatus());
        model.setPerson(model.getAddressBook().getPersonList().get(0), member1);
        model.setPerson(model.getAddressBook().getPersonList().get(2), member2);

        expectedModel.addPerson(member1);
        expectedModel.addPerson(member2);

        CommandResult commandResult = new ListAttendanceCommand().execute(model);
        String expectedMessage = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 2);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_listAttendanceIsFiltered_showsAllMembers() {
        Person person1 = getTypicalAddressBook().getPersonList().get(0);
        Person person2 = getTypicalAddressBook().getPersonList().get(2);

        Person member1 = new Person(person1.getName(), person1.getPhone(), person1.getEmail(),
                person1.getTelegram(), Set.of(new Member()), new HashSet<>(), person1.getFavouriteStatus());
        Person member2 = new Person(person2.getName(), person2.getPhone(), person2.getEmail(),
                person2.getTelegram(), Set.of(new Member()), new HashSet<>(), person2.getFavouriteStatus());
        model.setPerson(model.getAddressBook().getPersonList().get(0), member1);
        model.setPerson(model.getAddressBook().getPersonList().get(2), member2);

        expectedModel.addPerson(member1);
        expectedModel.addPerson(member2);

        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        CommandResult commandResult = new ListAttendanceCommand().execute(model);
        String expectedMessage = String.format(MESSAGE_MEMBERS_LISTED_OVERVIEW, 2);


        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }
}
