package seedu.address.model.util;

/**
 * A functional interface similar to {@code Function<T,R>} but allows for a specified exception to be thrown.
 */
public interface FunctionWithException<S, T, R extends Throwable> {
    T apply(S value) throws R;
}
