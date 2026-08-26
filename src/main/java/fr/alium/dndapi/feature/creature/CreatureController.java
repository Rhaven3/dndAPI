package fr.alium.dndapi.feature.creature;

import fr.alium.dndapi.feature.actionDnd.ActionDnDRepository;
import fr.alium.dndapi.feature.actionDnd.ActionMapper;
import fr.alium.dndapi.feature.actionDnd.entity.ActionDnD;
import fr.alium.dndapi.feature.actionDnd.entity.dto.ActionDTO;
import fr.alium.dndapi.feature.creature.entity.Creature;
import fr.alium.dndapi.feature.creature.entity.dto.CreatureDTO;
import fr.alium.dndapi.feature.creature.entity.dto.CreatureResponseDTO;
import fr.alium.dndapi.feature.language.Language;
import fr.alium.dndapi.feature.language.LanguageRepository;
import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/creature")
public class CreatureController {
    private final CreatureRepository creatureRepository;
    private final CreatureMapper creatureMapper;
    private final CreatureService creatureService;

    public CreatureController(
            CreatureRepository creatureRepository, CreatureMapper creatureMapper, CreatureService creatureService
    ) {
        this.creatureRepository = creatureRepository;
        this.creatureMapper = creatureMapper;
        this.creatureService = creatureService;
    }

    @GetMapping
    public ResponseEntity<List<CreatureResponseDTO>> readAll() {
        List<CreatureResponseDTO> creatureResponseDTOS = creatureService.getAllCreatures();
        return ResponseEntity.ok(creatureResponseDTOS);
    }

    @GetMapping("{id}")
    public ResponseEntity<CreatureResponseDTO> findById(@PathVariable Long id) {
        Creature creature = creatureService.findById(id);
        if (creature == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(creatureMapper.toResponseDto(creature));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreatureDTO creatureDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        creatureService.create(creatureDTO);
        return ResponseEntity.ok("creature created");
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody CreatureResponseDTO creatureResponseDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        boolean exist = creatureService.update(creatureResponseDTO);
        if (!exist) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("creature updated");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean exists = creatureRepository.existsById(id);
        if (!exists) {
            return ResponseEntity.notFound().build();
        }
        creatureRepository.deleteById(id);
        return ResponseEntity.ok("creature deleted");
    }
}
