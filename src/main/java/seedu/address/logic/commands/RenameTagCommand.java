package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLDTAG;
import static seedu.address.model.Model.PREDICATE_DO_NOT_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Edits the name of a tag for all contacts with that tag.
 */
public class RenameTagCommand extends Command {

    public static final String COMMAND_WORD = "renameTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Renames the specified tag.\n "
            + "Parameters: "
            + "[" + PREFIX_OLDTAG + "OLDTAG] "
            + "[" + PREFIX_NEWTAG + "NEWTAG] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_OLDTAG + "client "
            + PREFIX_NEWTAG + "ex-client";

    public static final String MESSAGE_RENAME_TAG_SUCCESS = "Tag renamed to: %1$s";

    public static final String MESSAGE_TAG_NOT_FOUND = "%1$s Tag is not found";

    private String oldTag;
    private String newTag;

    /**
     * @param oldTag of the person in the filtered person list to edit
     * @param newTag details to edit the person with
     */
    public RenameTagCommand(String oldTag, String newTag) {
        this.newTag = newTag;
        this.oldTag = oldTag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        ObservableList<Person> persons = model.getFilteredPersonList();
        boolean found = false;
        for (int j = 0; j < persons.size(); j++) {
            Person currPerson = persons.get(j);
            Set<Tag> tags = currPerson.getTags();
            Iterator<Tag> iterator = tags.iterator();
            while (iterator.hasNext()) {
                Tag tag = iterator.next();
                if (tag.getTagName().equals(oldTag)) {
                    tag.updateTagName(newTag);
                    found = true;
                }
            }
        }
        model.updateFilteredPersonList(PREDICATE_DO_NOT_SHOW_ALL_PERSONS);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (!found) {
            return new CommandResult(String.format(MESSAGE_TAG_NOT_FOUND, oldTag));
        } else {
            return new CommandResult(String.format(MESSAGE_RENAME_TAG_SUCCESS, newTag));
        }
    }
}
