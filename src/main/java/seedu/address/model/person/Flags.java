package seedu.address.model.person;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represent a set of preset labels to each person. Flags enables grouping of persons by multiple criteria in descending
 * significance.
 * Guarantee: Immutable.
 * */
public class Flags {
    private Set<PresetFlags> flagSet = new HashSet<>();
    /**
     * Constructor using ungrouped flags.
     * */
    Flags(PresetFlags... flags) {
        flagSet.addAll(Arrays.asList(flags));
    }
    /**
     * Constructor from a set of flags.
     * */
    private Flags(Set<PresetFlags> flags) {
        this.flagSet = flags;
    }
    /**
     * Return if the given flag is present.
     */
    public int flagIsPresent(PresetFlags flag) {
        return flagSet.contains(flag) ? 1 : 0;
    }
    /**
     * Return the number of matching flags.
     * */
    public int getNumberOfMatchingFlags(Set<PresetFlags> flags) {
        return flags.stream().map(this::flagIsPresent).reduce(0, Integer::sum);
    }
    /**
     * Return the weightage of all matching flags.
     * */
    public int getWeightedFlags(PresetFlags... flags) {
        int totalWeight = 0;
        int currentPower = 0; // Tracks consecutive powers for present flags

        for (PresetFlags flag : flags) {
            if (flagSet.contains(flag)) {
                totalWeight += (int) Math.pow(2, currentPower);
                currentPower++; // Only increment power if the flag is present
            }
        }

        return totalWeight;
    }
}
