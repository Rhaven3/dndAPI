package fr.alium.dndapi.feature.creature;

import fr.alium.dndapi.feature.actionDnd.ActionDnDRepository;
import fr.alium.dndapi.feature.actionDnd.ActionMapper;
import fr.alium.dndapi.feature.actionDnd.entity.ActionDnD;
import fr.alium.dndapi.feature.actionDnd.entity.dto.ActionDTO;
import fr.alium.dndapi.feature.creature.entity.Creature;
import fr.alium.dndapi.feature.creature.entity.dto.CreatureDTO;
import fr.alium.dndapi.feature.language.Language;
import fr.alium.dndapi.feature.language.LanguageRepository;
import jakarta.validation.Valid;
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
    private final LanguageRepository languageRepository;
    private final ActionDnDRepository actionDnDRepository;
    private final CreatureMapper creatureMapper;
    private final ActionMapper actionMapper;

    public CreatureController(
            CreatureRepository creatureRepository,
            LanguageRepository languageRepository,
            ActionDnDRepository actionDnDRepository,
            CreatureMapper creatureMapper, ActionMapper actionMapper
    ) {
        this.creatureRepository = creatureRepository;
        this.languageRepository = languageRepository;
        this.actionDnDRepository = actionDnDRepository;
        this.creatureMapper = creatureMapper;
        this.actionMapper = actionMapper;
    }

    @GetMapping
    public ResponseEntity<List<Creature>> readAll() {
        List<Creature> creatures = creatureRepository.findAll();
        return ResponseEntity.ok(creatures);
    }

    @GetMapping("{id}")
    public ResponseEntity<Creature> findById(@PathVariable Long id) {
        Creature creature = creatureRepository.findById(id).orElse(null);
        if (creature == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(creature);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreatureDTO creatureDTO, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        // creation language enfant
        List<Language> languages = new ArrayList<>();
        if (!creatureDTO.getLanguages().isEmpty()) {
            for (String language : creatureDTO.getLanguages()) {
                Language languageEntity = Language.builder()
                        .name(language)
                        .build();
                languageRepository.save(languageEntity);
                languages.add(languageEntity);
            }
        }
        // creation ActionDnD enfants
        List<ActionDnD> actions = new ArrayList<>();
        if (!creatureDTO.getActions().isEmpty()) {
            for (ActionDTO action : creatureDTO.getActions()) {
                ActionDnD actionDnD = actionMapper.toEntity(action);
                actionDnDRepository.save(actionDnD);
                actions.add(actionDnD);
            }
        }

        Creature creature = creatureMapper.toEntity(creatureDTO);
        creature.setLanguages(languages);
        creature.setActions(actions);

        creatureRepository.save(creature);
        return ResponseEntity.ok("creature created");
    }

    @PatchMapping("{id}")
    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Creature creature, @PathVariable Long id, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        boolean exists = creatureRepository.existsById(id);
        if (!exists) {
            return ResponseEntity.notFound().build();
        }

        creature.setId(id);
        creatureRepository.save(creature);
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
