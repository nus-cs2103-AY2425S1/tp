package seedu.address.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryIsUpcomingAfterPredicate;
import seedu.address.model.delivery.DeliveryIsUpcomingBeforePredicate;
import seedu.address.model.delivery.Status;
import seedu.address.model.supplier.Supplier;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the supplier in the {@code model}'s supplier list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredSupplierList().size() / 2);
    }

    /**
     * Returns the last index of the supplier in the {@code model}'s supplier list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredSupplierList().size());
    }

    /**
     * Returns the supplier in the {@code model}'s supplier list at {@code index}.
     */
    public static Supplier getSupplier(Model model, Index index) {
        return model.getFilteredSupplierList().get(index.getZeroBased());
    }

    /**
     * Parses user date input and Status into a predicate that checks if pending deliveries are
     * earlier than specified date.
     *
     * @param userInput The specified date and time.
     * @param status Status of delivery.
     * @return DeliveryIsUpcomingBeforePredicate object.
     */
    public static DeliveryIsUpcomingBeforePredicate prepareBeforePredicate(String userInput, Status status) {
        return new DeliveryIsUpcomingBeforePredicate(new DateTime(userInput), status);
    }

    /**
     * Parses user date input and Status into a predicate that checks if pending deliveries are
     * later than specified date.
     *
     * @param userInput The specified date and time.
     * @param status Status of delivery.
     * @return DeliveryIsUpcomingAfterPredicate object.
     */
    public static DeliveryIsUpcomingAfterPredicate prepareAfterPredicate(String userInput, Status status) {
        return new DeliveryIsUpcomingAfterPredicate(new DateTime(userInput), status);
    }

    /**
     * Creates a List of predicates.
     *
     * @param predicate The predicate to be added into a list.
     * @return A list containing the input predicate.
     */
    public static List<Predicate<Delivery>> createPredicateList(Predicate<Delivery> predicate) {
        List<Predicate<Delivery>> predicates = new ArrayList<>();
        predicates.add(predicate);
        return predicates;
    }
}
