package jbp.jpmc.entity.show;

import org.springframework.data.repository.CrudRepository;

public interface ShowRepository extends CrudRepository<Show, Long> {
    Show findByShowNumber(Long showNumber);
}
