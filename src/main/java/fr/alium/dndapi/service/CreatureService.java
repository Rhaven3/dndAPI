package fr.alium.dndapi.service;

import fr.alium.dndapi.entity.Creature;
import fr.alium.dndapi.entity.dto.CreatureXpDTO;
import fr.alium.dndapi.repository.CreatureRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreatureService implements fr.alium.dndapi.service.interfaces.ICreatureService {
    CreatureRepository creatureRepository;

    public CreatureService(CreatureRepository creatureRepository) {
        this.creatureRepository = creatureRepository;
    }



    public List<Creature> findAll(){
        return creatureRepository.findAll();
    }

    public List<CreatureXpDTO> findAllAsCreatureXpDTO(){
        return creatureRepository.findAllAsCreatureXpDTO();
    }

    @Override
    public List<Creature> findByXpTreshold(int xptreshold, int numberCreatures) {
        CreatureXpDTO[] allAsCreatureXpDTO = findAllAsCreatureXpDTO().toArray(CreatureXpDTO[]::new);

        // Initialize window
        int s = 0, e = 0;
        ArrayList<CreatureXpDTO> res = new ArrayList<>();

        int curr = 0;
        for (int i = 0; i < allAsCreatureXpDTO.length; i++) {
            curr += allAsCreatureXpDTO[i].getXp();

            // If current sum becomes more or equal,
            // set end and try adjusting start
            if (curr >= xptreshold) {
                e = i;

                // While current sum is greater,
                // remove starting elements of current window
                while (curr > xptreshold && s < e) {
                    curr -= allAsCreatureXpDTO[s].getXp();
                    ++s;
                }

                // If we found a subarray
                if (curr == xptreshold) {
                    res.add(s + 1);
                    res.add(e + 1);
                    return res;
                }
            }
        }
        // If no subarray is found
        res.add(-1);
        return res;
    }
}
