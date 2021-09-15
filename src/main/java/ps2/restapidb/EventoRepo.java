package ps2.restapidb;

import org.springframework.data.repository.CrudRepository;

public interface EventoRepo extends CrudRepository<Evento, Long> {
}

