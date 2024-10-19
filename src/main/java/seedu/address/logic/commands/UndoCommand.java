package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddCommandParser;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.DeleteCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.LogicManager.pastCommands;

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

            case "edit":

            case "delete":
                DeleteCommand currCommand = (DeleteCommand) latestCommand;
                ArrayList<Person> personsToAddBack = currCommand.getPersonsToDelete();
                for (int i = 0; i < personsToAddBack.size(); i++) {
                    model.addPerson(personsToAddBack.get(i));
                }
                pastCommands.remove(pastCommands.size() - 1);
                break;
            case "find":

            case "clear":

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

    public String convertPersonToInput(Person person) {
        String name = String.format("n/%s ", person.getName());
        String phoneNumber = String.format("p/%s ", person.getPhone());
        String address = String.format("a/%s ", person.getAddress());
        String email = String.format("e/%s ", person.getEmail());
        String dob = String.format("dob/%s ", person.getDateOfBirth());
        String income = String.format("income/%s ", person.getIncome());
        String tagList = person.getTags().stream()
                .map(tag -> String.format("t/%s", tag))
                .collect(Collectors.joining(" "));
        String s = name + phoneNumber + address + email + dob + income + tagList;

        return String.format("add %s", s);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
