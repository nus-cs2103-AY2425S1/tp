package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.PetNameContainsKeywordsPredicate;

public class FindPetCommand extends FindCommand<Pet> {
    public FindPetCommand(PetNameContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPetList(super.predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPetList().size()));
    }
}
