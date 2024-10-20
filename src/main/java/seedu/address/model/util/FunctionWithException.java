package seedu.address.model.util;

public interface FunctionWithException<S, T, R extends Throwable> {
    public T apply(S value) throws R;
}
