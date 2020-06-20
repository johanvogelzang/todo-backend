package nl.birdsongit.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Repository<T> {

    void save(T item);

    void deleteAll();

    List<T> getAll();

    Optional<T> retrieve(UUID id);

    int update(T item);

    void delete(UUID id);
}
