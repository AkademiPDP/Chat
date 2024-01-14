package uz.pdp.DB;

import java.util.List;

public interface Reposstory<T> {
    void save(T t);

    List<T>finAll();

    void delete(T t);
}
