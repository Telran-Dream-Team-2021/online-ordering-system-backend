package telran.oos.service;

import java.util.List;

public interface CrudService<T, S> {
    public T create(T entity);
    public T read(S id);
    public List<T> getAll();
    public T update(S id, T newEntity);
    public T remove(S id);
}
