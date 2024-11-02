package spleetwaise.transaction.model.transaction;

/**
 * Represents the status of a transaction, indicating whether it is done or not.
 */
public class Status {
    private final boolean isDone;

    /**
     * Constructs a {@code Status} with the specified completion state.
     *
     * @param isDone {@code true} if the status is marked as done, {@code false} otherwise.
     */
    public Status(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Returns the completion state of the status.
     *
     * @return {@code true} if the status is marked as done, {@code false} otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    @Override
    public String toString() {
        return isDone ? "Done" : "Not Done";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Status otherStatus)) {
            return false;
        }

        return otherStatus.isDone == isDone;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isDone);
    }
}
