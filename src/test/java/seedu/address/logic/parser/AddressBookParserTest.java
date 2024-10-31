package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.DESC_3A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNUSED_STUDY_GROUP_TAG_3A;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDY_GROUP_TAG;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.AgeContainsKeywordsPredicate;
import seedu.address.model.person.predicates.DetailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.GenderMatchesKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PredicateGroup;
import seedu.address.model.person.predicates.StudyGroupsContainKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.FindUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(List.of(INDEX_FIRST_PERSON)), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        Set<String> generalKeywords = Set.of("foo", "bar", "baz");
        Set<String> genderKeywords = Set.of("m", "F");
        Set<String> ageKeywords = Set.of("12", "34", "56");

        // parse name criteria
        FindCommand nameCommand = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " "
                        + FindUtil.getFindCriteria(PREFIX_NAME, generalKeywords));
        assertEquals(new FindCommand(
                FindUtil.getPredicateGroup(new NameContainsKeywordsPredicate(generalKeywords))),
                nameCommand);

        // parse email criteria
        FindCommand emailCommand = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " "
                        + FindUtil.getFindCriteria(PREFIX_EMAIL, generalKeywords));
        assertEquals(new FindCommand(
                FindUtil.getPredicateGroup(new EmailContainsKeywordsPredicate(generalKeywords))),
                emailCommand);

        // parse gender criteria
        FindCommand genderCommand = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " "
                        + FindUtil.getFindCriteria(PREFIX_GENDER, genderKeywords));
        assertEquals(new FindCommand(
                FindUtil.getPredicateGroup(new GenderMatchesKeywordsPredicate(genderKeywords))),
                genderCommand);

        // parse age criteria
        FindCommand ageCommand = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + FindUtil.getFindCriteria(PREFIX_AGE, ageKeywords));
        assertEquals(new FindCommand(FindUtil.getPredicateGroup(new AgeContainsKeywordsPredicate(ageKeywords))),
                ageCommand);

        // parse study groups criteria
        FindCommand studyGroupsCommand = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " "
                        + FindUtil.getFindCriteria(PREFIX_STUDY_GROUP_TAG, generalKeywords));
        assertEquals(new FindCommand(FindUtil.getPredicateGroup(
                new StudyGroupsContainKeywordsPredicate(generalKeywords))),
                studyGroupsCommand);

        // parse details criteria
        FindCommand detailsCommand = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " "
                        + FindUtil.getFindCriteria(PREFIX_DETAIL, generalKeywords));
        assertEquals(new FindCommand(FindUtil.getPredicateGroup(
                new DetailContainsKeywordsPredicate(generalKeywords))),
                detailsCommand);

        // parse multiple mixed criteria
        FindCommand mixedCriteriaCommand = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + FindUtil.getFindCriteria(PREFIX_NAME, generalKeywords)
                        + " " + FindUtil.getFindCriteria(PREFIX_EMAIL, generalKeywords)
                        + " " + FindUtil.getFindCriteria(PREFIX_GENDER, genderKeywords)
                        + " " + FindUtil.getFindCriteria(PREFIX_AGE, ageKeywords)
                        + " "
                        + FindUtil.getFindCriteria(PREFIX_STUDY_GROUP_TAG, generalKeywords)
                        + " " + FindUtil.getFindCriteria(PREFIX_DETAIL, generalKeywords));
        PredicateGroup expectedPredicateGroup = FindUtil.getPredicateGroup(
                new NameContainsKeywordsPredicate(generalKeywords),
                new EmailContainsKeywordsPredicate(generalKeywords),
                new GenderMatchesKeywordsPredicate(genderKeywords),
                new AgeContainsKeywordsPredicate(ageKeywords),
                new StudyGroupsContainKeywordsPredicate(generalKeywords),
                new DetailContainsKeywordsPredicate(generalKeywords));
        assertEquals(new FindCommand(expectedPredicateGroup), mixedCriteriaCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_assign() throws Exception {
        AssignCommand command = (AssignCommand) parser.parseCommand(
                AssignCommand.COMMAND_WORD + " " + VALID_UNUSED_STUDY_GROUP_TAG_3A);
        assertEquals(new AssignCommand(Collections.singletonList(DESC_3A)), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), () ->
                parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("unknownCommand"));
    }
}
