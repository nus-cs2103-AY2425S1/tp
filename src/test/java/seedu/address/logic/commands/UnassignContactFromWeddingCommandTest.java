package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;
import static seedu.address.testutil.TypicalWeddings.WEDDING_TWO;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Phone;
import seedu.address.model.wedding.WeddingName;

public class UnassignContactFromWeddingCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        model.addWedding(WEDDING_ONE);
        model.addWedding(WEDDING_TWO);

        Person person1 = new Person(new PersonId(), new Name("Alice"), new Phone("123456"),
                new Email("alice@example.com"),
                new Address("123 Wonderland"), Set.of());
        Person person2 = new Person(new PersonId(), new Name("Bob"), new Phone("234567"),
                new Email("bob@example.com"),
                new Address("456 Wonderland"), Set.of());
        model.addPerson(person1);
        model.addPerson(person2);

        WEDDING_ONE.getAssignees().add(person1.getId());
        WEDDING_ONE.getAssignees().add(person2.getId());


    }

    @Test
    public void execute_unassignContactsFromWedding_success() throws Exception {
        Set<Index> contactIndices = Set.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        model.setCurrentWeddingName(new WeddingName("John and Jane Wedding"));
        UnassignContactFromWeddingCommand unassignCommand =
                new UnassignContactFromWeddingCommand(contactIndices);

        CommandResult commandResult = unassignCommand.execute(model);

        assertEquals(String.format(UnassignContactFromWeddingCommand.MESSAGE_UNASSIGN_FROM_WEDDING_SUCCESS,
                WEDDING_ONE.getWeddingName(), "Alice, Bob"), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_unassignNotAssignedContact_throwsCommandException() throws CommandException {
        Set<Index> contactIndices = Set.of(Index.fromOneBased(4));
        model.setCurrentWeddingName(new WeddingName("Mike and Anna Wedding"));
        UnassignContactFromWeddingCommand unassignCommand =
                new UnassignContactFromWeddingCommand(contactIndices);

        assertThrows(CommandException.class,
                "1 or more contact indexes provided is invalid", () -> unassignCommand.execute(model));
    }
}
