package fr.alium.dndapi.controller;

import fr.alium.dndapi.entity.Creature;
import fr.alium.dndapi.repository.CreatureRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/creature")
public class CreatureController {
    CreatureRepository creatureRepository;

    @GetMapping()
    public ResponseEntity<List<Creature>> readAll() {
        List<Creature> creatures = creatureRepository.findAll();
        return ResponseEntity.ok(creatures);
    }
}
