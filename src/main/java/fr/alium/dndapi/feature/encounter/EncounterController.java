package fr.alium.dndapi.feature.encounter;

import fr.alium.dndapi.feature.encounter.entity.Encounter;
import fr.alium.dndapi.feature.encounter.entity.EncounterDifficultyEnum;
import fr.alium.dndapi.feature.encounter.entity.dto.EncounterDTO;
import fr.alium.dndapi.feature.encounter.entity.dto.EncounterResponseDTO;
import fr.alium.dndapi.feature.encounter.entity.dto.GenerateEncounterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/encounter")
public class EncounterController {
    private final EncounterRepository encounterRepository;
    private final EncounterService encounterService;

    public EncounterController(EncounterRepository encounterRepository, EncounterService encounterService) {
        this.encounterRepository = encounterRepository;
        this.encounterService = encounterService;
    }

    @GetMapping
    public ResponseEntity<List<EncounterResponseDTO>> readAll() {
        return ResponseEntity.ok(encounterService.getAll());
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
    public ResponseEntity<?> create(@RequestBody EncounterDTO encounterDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        encounterService.create(encounterDTO);
        return ResponseEntity.ok("encounter created");
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EncounterDTO encounterDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        if (!encounterRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Encounter encounter = encounterService.update(id, encounterDTO);
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
    public ResponseEntity<?> generateSoft(@RequestBody GenerateEncounterDTO generateEncounterDTO, BindingResult result) {
        if (verifyGenerateEncounterDTO(generateEncounterDTO, result)) return ResponseEntity.badRequest().build();

        EncounterResponseDTO encounterResponseDto = encounterService.generate(
                false,
                EncounterDifficultyEnum.fromInt(generateEncounterDTO.getDifficulty()),
                generateEncounterDTO.getPartySize(),
                generateEncounterDTO.getPartyAverageLvl(),
                generateEncounterDTO.getNumberCreatures()
        );
        return ResponseEntity.ok(encounterResponseDto);
    }

    @GetMapping("/generate/hard")
    public ResponseEntity<?> generate(@RequestBody GenerateEncounterDTO generateEncounterDTO, BindingResult result) {
        if (verifyGenerateEncounterDTO(generateEncounterDTO, result)) return ResponseEntity.badRequest().build();

        EncounterResponseDTO encounterResponseDto = encounterService.generate(
                true,
                EncounterDifficultyEnum.fromInt(generateEncounterDTO.getDifficulty()),
                generateEncounterDTO.getPartySize(),
                generateEncounterDTO.getPartyAverageLvl(),
                generateEncounterDTO.getNumberCreatures()
        );
        return ResponseEntity.ok(encounterResponseDto);
    }

    private boolean verifyGenerateEncounterDTO(@RequestBody GenerateEncounterDTO generateEncounterDTO, BindingResult result) {
        if (result.hasErrors()) {
            return true;
        }

        if (generateEncounterDTO.getDifficulty() == null) return true;
        if (generateEncounterDTO.getPartySize() == null) return true;
        if (generateEncounterDTO.getNumberCreatures() == null) return true;
        return false;
    }


}
