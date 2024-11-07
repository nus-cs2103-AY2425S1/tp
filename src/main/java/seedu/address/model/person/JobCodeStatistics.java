package seedu.address.model.person;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to store statistics for each job code
 */
public class JobCodeStatistics {
    private final Map<String, Integer> tagCounts = createTagCounts();

    // Initialize the tagCounts map with all tag codes set to 0
    private static HashMap<String, Integer> createTagCounts() {
        HashMap<String, Integer> tagCounts = new HashMap<>();
        tagCounts.put("N", 0);
        tagCounts.put("BP", 0);
        tagCounts.put("BC", 0);
        tagCounts.put("TP", 0);
        tagCounts.put("TC", 0);
        tagCounts.put("A", 0);
        tagCounts.put("R", 0);
        return tagCounts;
    }

    /**
     * Increases a specific tag in the tagCode dictionary
     * @param tagCode tagCode String
     */
    // Attribution: The code below this line onwards was written with the aid of ChatGPT.
    // Increment the count for a specific tag
    public void incrementTag(String tagCode) {
        tagCode = tagCode.toUpperCase();
        tagCounts.put(tagCode, tagCounts.getOrDefault(tagCode, 0) + 1);
    }

    // Get the total count of applicants across all tags
    public int getTotalApplicants() {
        return tagCounts.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int getN() {
        return tagCounts.getOrDefault("N", 0);
    }
    public int getBP() {
        return tagCounts.getOrDefault("BP", 0);
    }
    public int getBC() {
        return tagCounts.getOrDefault("BC", 0);
    }
    public int getTP() {
        return tagCounts.getOrDefault("TP", 0);
    }
    public int getTC() {
        return tagCounts.getOrDefault("TC", 0);
    }
    public int getA() {
        return tagCounts.getOrDefault("A", 0);
    }
    public int getR() {
        return tagCounts.getOrDefault("R", 0);
    }
}

