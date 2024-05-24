package ps2.restapidb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class EventoController {

    @Autowired
    private EventoRepo eventoRepo;

    @Autowired
    private ParticipanteRepo participanteRepo;

    @GetMapping("/api/eventos")
    Iterable<Evento> getEventos() {
        return eventoRepo.findAll();
    }

    @GetMapping("/api/eventos/{id}")
    Optional<Evento> getEvento(@PathVariable long id) {
        return eventoRepo.findById(id);
    }

    @PostMapping("/api/eventos")
    Evento createEvento(@RequestBody Evento evento) {
        Evento createdEvent = eventoRepo.save(evento);
        return createdEvent;
    }

    @PutMapping("/api/eventos/{eventoId}")
    Optional<Evento> updateEvento(@RequestBody Evento eventoReq, @PathVariable long eventoId) {
        Optional<Evento> opt = eventoRepo.findById(eventoId);
        if (opt.isPresent()) {
            if (eventoReq.getId() == eventoId) {
                eventoRepo.save(eventoReq);
                return opt;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao alterar dados do evento com id " + eventoId);
    }

    @DeleteMapping("/api/eventos/{id}")
    void deleteEvento(@PathVariable long id) {
        eventoRepo.deleteById(id);
    }

    @PostMapping("/api/eventos/{eventoId}/participantes")
    Participante addParticipante(@PathVariable long eventoId, @RequestBody Participante participante) {
        return eventoRepo.findById(eventoId).map(evento -> {
            participante.setEvento(evento);
            return participanteRepo.save(participante);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));
    }

    @GetMapping("/api/eventos/{eventoId}/participantes")
    Iterable<Participante> getParticipantes(@PathVariable long eventoId) {
        return participanteRepo.findAll();
    }
}
