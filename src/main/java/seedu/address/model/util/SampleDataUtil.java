package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.PawPatrol;
import seedu.address.model.ReadOnlyPawPatrol;
import seedu.address.model.link.Link;
import seedu.address.model.owner.Address;
import seedu.address.model.owner.Email;
import seedu.address.model.owner.IdentificationCardNumber;
import seedu.address.model.owner.Name;
import seedu.address.model.owner.Owner;
import seedu.address.model.owner.Phone;
import seedu.address.model.pet.Age;
import seedu.address.model.pet.Breed;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.Sex;
import seedu.address.model.pet.Species;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code PawPatrol} with sample data.
 */
public class SampleDataUtil {
    public static Owner[] getSampleOwners() {
        return new Owner[] {
            new Owner(new IdentificationCardNumber("S0000001I"), new Name("Alex Yeoh"), new Phone("87438807"),
                new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40")),
            new Owner(new IdentificationCardNumber("S0000002G"), new Name("Bernice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18")),
            new Owner(new IdentificationCardNumber("S0000003E"), new Name("Charlotte Oliveiro"),
                    new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04")),
            new Owner(new IdentificationCardNumber("S0000004C"), new Name("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43")),
            new Owner(new IdentificationCardNumber("S0000005A"), new Name("Irfan Ibrahim"),
                    new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35")),
            new Owner(new IdentificationCardNumber("S0000006Z"), new Name("Roy Balakrishnan"),
                    new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"))
        };
    }

    public static Pet[] getSamplePets() {
        return new Pet[] {
            new Pet(new seedu.address.model.pet.Name("Fluffy"), new Species("Cat"), new Breed("Siamese"),
                new Age("3"), new Sex("Male"), getTagSet("cute", "gentle")),
            new Pet(new seedu.address.model.pet.Name("Honey"), new Species("Dog"), new Breed("Golden Retriever"),
                new Age("5"), new Sex("Female"), getTagSet("energetic")),
            new Pet(new seedu.address.model.pet.Name("Bison"), new Species("Cat"), new Breed("Bengal"),
                new Age("2"), new Sex("Male"), getTagSet("loyal", "loud")),
            new Pet(new seedu.address.model.pet.Name("Daisy"), new Species("Dog"), new Breed("Poodle"),
                new Age("4"), new Sex("Female"), getTagSet("playful"))
        };
    }

    public static ReadOnlyPawPatrol getSamplePawPatrol() {
        PawPatrol sampleAb = new PawPatrol();
        Owner[] owners = getSampleOwners();
        Pet[] pets = getSamplePets();
        for (Owner sampleOwner : owners) {
            sampleAb.addOwner(sampleOwner);
        }
        for (Pet samplePet : pets) {
            sampleAb.addPet(samplePet);
        }
        for (int i = 0; i < owners.length && i < pets.length; i++) {
            sampleAb.addLink(new Link(owners[i], pets[i]));
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
