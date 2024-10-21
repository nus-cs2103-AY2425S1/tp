package seedu.address.model.person;

public class Range<T> {
    public final T lowerRange;
    public final T upperRange;

    public Range(T lowerRange, T upperRange) {
        this.lowerRange = lowerRange;
        this.upperRange = upperRange;
    }

}
