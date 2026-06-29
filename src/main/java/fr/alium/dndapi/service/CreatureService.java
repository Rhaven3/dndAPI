package fr.alium.dndapi.service;

import fr.alium.dndapi.entity.Creature;
import fr.alium.dndapi.repository.CreatureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreatureService implements fr.alium.dndapi.service.interfaces.ICreatureService {



    public List<Creature> findAll(){
        return null;
    }
}
