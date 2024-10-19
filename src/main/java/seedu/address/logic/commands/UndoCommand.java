package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.AddCommandParser;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.LogicManager.pastCommands;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;

/**
 * Undoes the latest command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";


    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes the latest command and reverts person list to the state before it.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_COMMAND_SUCCESS = "Undo successful.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (pastCommands.size() == 0) {
            throw new CommandException(Messages.MESSAGE_NO_LATEST_COMMAND);
        }
        Command latestCommand = pastCommands.get(pastCommands.size() - 1);
        String latestCommandWord = latestCommand.getCommandWord();

        switch (latestCommandWord) {
            case "add":
                AddCommand addCommand = (AddCommand) latestCommand;
                Person personToRemove = addCommand.getToAdd();
                model.deletePerson(personToRemove);
                pastCommands.remove(pastCommands.size() - 1);
                break;
            case "edit":
                EditCommand editCommand = (EditCommand) latestCommand;

                Person bfrEdit = editCommand.getUneditedPerson();

                Person afterEdit = editCommand.getEditedPerson();

                if (model.hasPerson(bfrEdit)) {
                    pastCommands.remove(pastCommands.size() - 1);
                    throw new CommandException(MESSAGE_DUPLICATE_PERSON);
                }
                model.setPerson(afterEdit, bfrEdit);
                pastCommands.remove(pastCommands.size() - 1);
                break;

            case "delete":
                DeleteCommand dltCommand = (DeleteCommand) latestCommand;
                ArrayList<Person> personsToAddBack = dltCommand.getPersonsToDelete();
                for (int i = 0; i < personsToAddBack.size(); i++) {
                    model.addPerson(personsToAddBack.get(i), dltCommand.getTargetIndexes()[i].getZeroBased());
                }
                pastCommands.remove(pastCommands.size() - 1);
                break;
            case "find":

                break;
             case "clear":
                ClearCommand clearCommand = (ClearCommand) latestCommand;
                model.setAddressBook(clearCommand.getModel().getAddressBook());
                model.setUserPrefs(clearCommand.getModel().getUserPrefs());
                pastCommands.remove(pastCommands.size() - 1);
                break;
            default:
                break;

        }


/*
          if latest command is add, then undo deletes
          if latest command is edit, then undo changes back edit
          if latest command is delete, then undo changes add back people
          if latest command is find, then undo changes bring back unfiltered list
          if latest command is clear, then undo changes bring back person list
 */
        return new CommandResult(String.format(MESSAGE_UNDO_COMMAND_SUCCESS));
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
