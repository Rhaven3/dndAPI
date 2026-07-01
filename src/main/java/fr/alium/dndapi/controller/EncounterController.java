package fr.alium.dndapi.controller;

import fr.alium.dndapi.entity.Encounter;
import fr.alium.dndapi.entity.dto.GenerateEncounterDTO;
import fr.alium.dndapi.repository.EncounterRepository;
import fr.alium.dndapi.service.EncounterService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/encounter")
public class EncounterController {
    EncounterRepository encounterRepository;
    EncounterService encounterService;

    public EncounterController(EncounterRepository encounterRepository, EncounterService encounterService) {
        this.encounterRepository = encounterRepository;
        this.encounterService = encounterService;
    }

    @GetMapping
    public ResponseEntity<List<Encounter>> readAll() {
        return ResponseEntity.ok(encounterRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Encounter> readById(@PathVariable Long id) {
        Encounter encounter = encounterRepository.findById(id).orElse(null);
        if (encounter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(encounter);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Encounter encounter, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        encounterRepository.save(encounter);
        return ResponseEntity.ok("encounter created");
    }

    @PutMapping("{id}")
    @PatchMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Encounter encounter, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        if (!encounterRepository.existsById(encounter.getId())) {
            return ResponseEntity.notFound().build();
        }
        encounter.setId(id);
        encounterRepository.save(encounter);
        return ResponseEntity.ok("encounter updated");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!encounterRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        encounterRepository.deleteById(id);
        return ResponseEntity.ok("encounter deleted");
    }


    @GetMapping("/generate")
    public ResponseEntity<?> generate(@RequestBody GenerateEncounterDTO generateEncounterDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        if (generateEncounterDTO.getDifficulty() == null) return ResponseEntity.badRequest().build();
        if (generateEncounterDTO.getPartySize() == null) return ResponseEntity.badRequest().build();
        if (generateEncounterDTO.getNumberCreatures() == null) return ResponseEntity.badRequest().build();

        Encounter encounter = encounterService.generate(
                generateEncounterDTO.getDifficulty(),
                generateEncounterDTO.getPartySize(),
                generateEncounterDTO.getPartyAverageLvl(),
                generateEncounterDTO.getNumberCreatures()
        );
        return ResponseEntity.ok(encounter);
    }
}
