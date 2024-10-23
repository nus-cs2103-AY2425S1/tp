package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.PetNameContainsKeywordsPredicate;

/**
 * Finds and lists all pets in PawPatrol whose name contains any of the argument keywords.
 */
public class FindPetCommand extends FindCommand<Pet> {

    public static final String COMMAND_WORD = "find pet";

    /**
     * Constructs a {@code FindPetCommand} with the specified {@code PetNameContainsKeywordsPredicate}.
     */
    public FindPetCommand(PetNameContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    /**
     * Executes the find pet command.
     * @param model {@code Model} which the command should operate on.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPetList(super.predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PETS_LISTED_OVERVIEW, model.getFilteredPetList().size()));
    }
}
