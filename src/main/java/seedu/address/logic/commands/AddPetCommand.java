package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pet.Pet;

/**
 * Adds a pet to PawPatrol.
 */
public class AddPetCommand extends Command {

    public static final String COMMAND_WORD = "pet";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a pet to PawPatrol. "
        + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_SPECIES + "SPECIES "
        + PREFIX_BREED + "BREED "
        + PREFIX_AGE + "AGE "
        + PREFIX_SEX + "SEX "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "Fluffy "
        + PREFIX_SPECIES + "Dog "
        + PREFIX_BREED + "Golden Retriever "
        + PREFIX_AGE + "5 "
        + PREFIX_SEX + "F "
        + PREFIX_TAG + "playful "
        + PREFIX_TAG + "cute";

    public static final String MESSAGE_SUCCESS = "New pet added: %1$s";
    public static final String MESSAGE_DUPLICATE_PET = "This pet already exists in PawPatrol";

    private final Pet toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Pet}
     */
    public AddPetCommand(Pet pet) {
        requireNonNull(pet);
        toAdd = pet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPet(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PET);
        }

        model.addPet(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPetCommand)) {
            return false;
        }

        AddPetCommand otherAddPetCommand = (AddPetCommand) other;
        return toAdd.equals(otherAddPetCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("toAdd", toAdd)
            .toString();
    }
}
