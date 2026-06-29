package fr.alium.dndapi.controller;

import fr.alium.dndapi.entity.Creature;
import fr.alium.dndapi.repository.CreatureRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/creature")
public class CreatureController {
    CreatureRepository creatureRepository;

    public CreatureController(CreatureRepository creatureRepository) {
        this.creatureRepository = creatureRepository;
    }

    @GetMapping
    public ResponseEntity<List<Creature>> readAll() {
        List<Creature> creatures = creatureRepository.findAll();
        return ResponseEntity.ok(creatures);
    }

    @GetMapping("{id}")
    public ResponseEntity<Creature> findById(@PathVariable  Long id) {
        Creature creature = creatureRepository.findById(id).orElse(null);
        if (creature == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(creature);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Creature creature, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        creatureRepository.save(creature);
        return ResponseEntity.ok("creature created");
    }

    @PatchMapping("{id}")
    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Creature creature, @PathVariable  Long id,  BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        boolean exists = creatureRepository.existsById(id);
        if (!exists) {
            return ResponseEntity.notFound().build();
        }

        creature.setId(id);
        creatureRepository.save(creature);
        return  ResponseEntity.ok("creature updated");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable  Long id) {
        boolean exists = creatureRepository.existsById(id);
        if (!exists) {
            return ResponseEntity.notFound().build();
        }
        creatureRepository.deleteById(id);
        return ResponseEntity.ok("creature deleted");
    }
}
