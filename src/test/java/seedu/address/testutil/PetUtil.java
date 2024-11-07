package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddPetCommand;
import seedu.address.logic.commands.EditPetCommand;
import seedu.address.model.pet.Pet;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Pet.
 */
public class PetUtil {

    /**
     * Returns an add command string for adding the {@code pet}.
     */
    public static String getAddPetCommand(Pet pet) {
        return AddPetCommand.COMMAND_WORD + " " + getPetDetails(pet);
    }

    /**
     * Returns the part of command string for the given {@code pet}'s details.
     */
    public static String getPetDetails(Pet pet) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + pet.getName().name + " ");
        sb.append(PREFIX_SPECIES + pet.getSpecies().value + " ");
        sb.append(PREFIX_BREED + pet.getBreed().value + " ");
        sb.append(PREFIX_AGE + pet.getAge().value + " ");
        sb.append(PREFIX_SEX + pet.getSex().value + " ");
        pet.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPetDescriptor}'s details.
     */
    public static String getEditPetDescriptorDetails(EditPetCommand.EditPetDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.name).append(" "));
        descriptor.getSpecies().ifPresent(species -> sb.append(PREFIX_SPECIES).append(species.value).append(" "));
        descriptor.getBreed().ifPresent(breed -> sb.append(PREFIX_BREED).append(breed.value).append(" "));
        descriptor.getAge().ifPresent(age -> sb.append(PREFIX_AGE).append(age.value).append(" "));
        descriptor.getSex().ifPresent(sex -> sb.append(PREFIX_SEX).append(sex.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }

}
