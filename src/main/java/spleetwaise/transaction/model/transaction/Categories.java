package spleetwaise.transaction.model.transaction;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Represents a Transaction's categories in the transaction book.
 */
public class Categories {
    private final HashSet<String> catSet;

    /**
     * Constructs a {@code Categories}
     */
    public Categories() {
        catSet = new HashSet<>();
    }

    /**
     * Constructs a {@code Categories}
     *
     * @param catStr A String representation of all categories that needs to be added.
     */
    public Categories(String catStr) {
        requireNonNull(catStr);
        String[] arrTagStr = catStr.split(" ");
        this.catSet = new HashSet<>();

        this.catSet.addAll(Arrays.asList(arrTagStr));
    }

    /**
     * Add a categories into the catSet
     *
     * @param cat The categories to be added
     */
    public boolean add(String cat) {
        requireNonNull(cat);
        return catSet.add(cat);
    }

    /**
     * remove a categories into the catSet
     *
     * @param cat The categories to be removed
     */
    public boolean remove(String cat) {
        requireNonNull(cat);
        return catSet.remove(cat);
    }

    /**
     * Check if a category is within categories
     *
     * @param cat The category to be checked
     */
    public boolean contains(String cat) {
        requireNonNull(cat);
        return catSet.contains(cat);
    }
}
