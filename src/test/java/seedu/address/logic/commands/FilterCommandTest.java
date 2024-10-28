package seedu.address.logic.commands;

import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.*;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.tag.Tag;

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
    public void execute_tagNotCreated_failure() {
        String expectedMessage = "Tag [friends] must be created before being used to filter.";
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(FRIENDS);
        FilterCommand filterCommand = new FilterCommand(tagSet, new HashSet<>());
        assertCommandFailure(filterCommand, model, expectedMessage);
    }

    @Test
    public void execute_chainMultipleFilters_success() {
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

        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        Tag oweMoney = new Tag("owesMoney");
        model.addTag(oweMoney);
        expectedModel.updateFilteredPersonList(new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return person.getTags().contains(oweMoney);
            }
        });
        tagSet.add(oweMoney);
        FilterCommand secondFilterCommand = new FilterCommand(tagSet, new HashSet<>());
        assertCommandSuccess(secondFilterCommand, model, expectedMessage, expectedModel);

        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        model.addTag(COLLEAGUES);
        expectedModel.updateFilteredPersonList(new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return person.getTags().contains(COLLEAGUES);
            }
        });
        tagSet.add(COLLEAGUES);
        FilterCommand newFilterCommand = new FilterCommand(tagSet, new HashSet<>());
        assertCommandSuccess(newFilterCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_chainInvalidFilter_failure() {
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

        expectedMessage = "Tag [family] must be created before being used to filter.";
        tagSet.add(FAMILY);
        FilterCommand newFilterCommand = new FilterCommand(tagSet, new HashSet<>());
        assertCommandFailure(newFilterCommand, model, expectedMessage);
    }

    @Test
    public void execute_multipleArgsAndChaining_success() {

        // Setting up RSVP Status and owesMoney tag
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Person personToUpdate = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person updatedPerson = new Person(personToUpdate.getName(), personToUpdate.getPhone(),
                personToUpdate.getEmail(), RsvpStatus.PENDING, personToUpdate.getTags());
        expectedModel.setPerson(personToUpdate, updatedPerson);
        model.setPerson(personToUpdate, updatedPerson);
        Tag owesMoney = new Tag("owesMoney");

        // Filtering by FRIENDS and owesMoney
        expectedModel.updateFilteredPersonList(new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return person.hasTag(FRIENDS) && person.hasTag(owesMoney);
            }
        });
        model.addTag(FRIENDS);
        model.addTag(owesMoney);
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(FRIENDS);
        tagSet.add(owesMoney);
        FilterCommand filterCommand = new FilterCommand(tagSet, new HashSet<>());
        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);

        // Filtering by RSVP status
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        expectedModel.updateFilteredPersonList(new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return person.getRsvpStatus().equals(RsvpStatus.PENDING);
            }
        });
        Set<RsvpStatus> statuses = new HashSet<>();
        statuses.add(RsvpStatus.PENDING);
        FilterCommand nextFilterCommand = new FilterCommand(tagSet, statuses);
        assertCommandSuccess(nextFilterCommand, model, expectedMessage, expectedModel);
    }
}
