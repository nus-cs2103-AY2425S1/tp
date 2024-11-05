package seedu.address.logic.commands.personcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.nowPlusDays;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.types.event.Event;
import seedu.address.model.types.person.Person;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

public class LinkPersonCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToLink = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
                .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();;
        Event eventToLink = new EventBuilder().withName("Concert Night")
                .withAddress("123, Orchard Rd, #05-01").withStartTime(nowPlusDays(6))
                .withTags("music").build();

        LinkPersonCommand linkPersonCommand = new LinkPersonCommand(INDEX_FIRST_PERSON, eventToLink.getName());

        String expectedMessage = String.format(LinkPersonCommand.MESSAGE_LINK_SUCCESS,
                Messages.format(eventToLink));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.linkPersonToEvent(personToLink, eventToLink);

        assertCommandSuccess(linkPersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Event eventToLink = new EventBuilder().withName("Book Fair")
                .withAddress("311, Clementi Ave 2, #02-25").withStartTime(nowPlusDays(4))
                .withTags("friends").build();

        LinkPersonCommand linkPersonCommand = new LinkPersonCommand(outOfBoundIndex, eventToLink.getName());

        assertCommandFailure(linkPersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidEventName_throwsCommandException() {
        Event eventToLink = new EventBuilder().withName("NON EXISTENT EVENT")
                .withAddress("311, Clementi Ave 2, #02-25").withStartTime(nowPlusDays(4))
                .withTags("friends").build();

        LinkPersonCommand linkPersonCommand = new LinkPersonCommand(INDEX_FIRST_PERSON, eventToLink.getName());

        String expectedMessage = LinkPersonCommand.MESSAGE_EVENT_NOT_FOUND;

        assertCommandFailure(linkPersonCommand, model, expectedMessage);
    }

    @Test
    public void execute_duplicateLinkedPerson_throwsCommandException() {
        Event eventToLink = new EventBuilder().withName("Anime Expo")
                .withAddress("123, Jurong West Ave 6, #08-111").withStartTime(nowPlusDays(2))
                .withTags("hobby").build();

        LinkPersonCommand linkPersonCommand = new LinkPersonCommand(INDEX_FIRST_PERSON, eventToLink.getName());

        String expectedMessage = String.format(LinkPersonCommand.MESSAGE_DUPLICATE_PERSON);

        assertCommandFailure(linkPersonCommand, model, expectedMessage);
    }
}
