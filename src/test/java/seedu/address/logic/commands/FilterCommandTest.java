package seedu.address.logic.commands;

import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.COLLEAGUES;
import static seedu.address.testutil.TypicalTags.FRIENDS;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_filterCommandWithValidArgs_success() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        model.addTag(FRIENDS);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return person.getTags().contains(FRIENDS);
            }
        });
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(FRIENDS);
        FilterCommand filterCommand = new FilterCommand(tagSet, new HashSet<>());
        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleArgs_success() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return person.hasTag(FRIENDS) && person.hasTag(COLLEAGUES);
            }
        });
        model.addTag(FRIENDS);
        model.addTag(COLLEAGUES);
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(FRIENDS);
        tagSet.add(COLLEAGUES);
        FilterCommand filterCommand = new FilterCommand(tagSet, new HashSet<>());
        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_TagNotCreated_failure() {
        String expectedMessage = "Tag [friends] must be created before being used to filter.";
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(FRIENDS);
        FilterCommand filterCommand = new FilterCommand(tagSet, new HashSet<>());
        assertCommandFailure(filterCommand, model, expectedMessage);
    }
}
