package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import seedu.address.model.person.Person;

/**
 * A random {@code Person} object generator to be used in stress tests.
 */
public class RandomPersonGenerator {
    // Pools of possible values for random generation
    private static final String[] BASE_NAMES = {"Alice", "Benson", "Carl", "Daniel", "Elle", "Fiona",
                                                "George", "Hoon", "Ida", "Amy", "Bob"};
    private static final String[] GENDERS = {"male", "female"};
    private static final String[] MODULES = {"MA1522", "CS1101", "EL1101", "CS2103T"};
    private static final String[] TAGS = {"friends", "owesMoney", "runner", "husband"};

    private static final Random RANDOM = new Random();
    private static final Set<String> USED_NAMES = new HashSet<>(); // To ensure unique names

    /**
     * Generates a list of random Person objects with unique names.
     *
     * @param count The number of persons to generate.
     * @return List of randomly generated Person objects with unique names.
     */
    public static List<Person> generateRandomPersons(int count) {
        List<Person> randomPersons = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            randomPersons.add(generateRandomPerson());
        }

        return randomPersons;
    }

    /**
     * Generates a single random Person object with a unique name.
     *
     * @return Randomly generated Person with a unique name.
     */
    private static Person generateRandomPerson() {
        String uniqueName = generateUniqueName();
        String phone = generateRandomPhone();
        String gender = getRandomElement(GENDERS);
        List<String> personTags = getRandomTags();

        // Build the Person object
        return new PersonBuilder()
                .withName(uniqueName)
                .withPhone(phone)
                .withGender(gender)
                .withTags(personTags.toArray(new String[0]))
                .withModules(getRandomElement(MODULES))
                .build();
    }

    /**
     * Generates a unique name by combining a base name with a unique number suffix.
     *
     * @return A unique name.
     */
    private static String generateUniqueName() {
        String baseName = getRandomElement(BASE_NAMES);
        String uniqueName;

        // Ensure the name is unique by appending a number
        do {
            int suffix = RANDOM.nextInt(10000); // Random 4-digit number as suffix
            uniqueName = baseName + suffix;
        } while (USED_NAMES.contains(uniqueName));

        USED_NAMES.add(uniqueName); // Mark the name as used
        return uniqueName;
    }

    /**
     * Selects a random element from an array.
     *
     * @param array Array of possible values.
     * @return Random element from the array.
     */
    private static String getRandomElement(String[] array) {
        return array[RANDOM.nextInt(array.length)];
    }

    /**
     * Generates a random phone number.
     *
     * @return Randomly generated phone number.
     */
    private static String generateRandomPhone() {
        return String.valueOf(80000000 + RANDOM.nextInt(10000000));
    }

    /**
     * Generates a random set of tags (1-2 random tags).
     *
     * @return List of random tags.
     */
    private static List<String> getRandomTags() {
        int tagCount = RANDOM.nextInt(2) + 1; // Randomly select 1 or 2 tags
        List<String> tags = new ArrayList<>();

        for (int i = 0; i < tagCount; i++) {
            tags.add(getRandomElement(TAGS));
        }

        return tags;
    }

    /**
     * Generates a random set of modules (1-2 random modules).
     *
     * @return List of random modules.
     */
    private static List<String> getRandomModules() {
        int moduleCount = RANDOM.nextInt(2) + 1; // Randomly select 1 or 2 modules
        List<String> modules = new ArrayList<>();

        for (int i = 0; i < moduleCount; i++) {
            modules.add(getRandomElement(MODULES));
        }

        return modules;
    }
}
