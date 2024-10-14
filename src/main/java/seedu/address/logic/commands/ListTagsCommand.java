package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

import java.util.HashMap;

public class ListTagsCommand extends Command {
    public static final String COMMAND_WORD = "taglist";

    public static final String MESSAGE_SUCCESS = "Listed tags currently in use: %1$s";
    @Override
    public CommandResult execute(Model model) {
        HashMap<Tag, Integer> activeTags = model.getActiveTags();
        String tagString = Tag.tagSetToString(activeTags.keySet());
        return new CommandResult(String.format(MESSAGE_SUCCESS, tagString));
    }
}
