package Generic;

public interface IGeneric<T> {
    T save (T t);
    T delete(T t);
    T findById(String id);

}
