package cat.aubricoc.xolis.server.utils;

public interface Callback<T> {

    void execute(T data);
}
