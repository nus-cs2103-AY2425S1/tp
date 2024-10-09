package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to a person. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TAG + "TAG ";

    public static final String MESSAGE_SUCCESS = "New tag added";
    private final Index targetIndex;
    private final Set<Tag> tagList;
    public TagCommand(Index targetIndex, Set<Tag> tagList) {
        this.targetIndex = targetIndex;
        this.tagList = tagList;
    }
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = lastShownList.get(targetIndex.getZeroBased());
        model.setTag(person, tagList);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(person)));
    }

}
