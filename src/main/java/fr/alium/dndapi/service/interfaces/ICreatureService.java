package fr.alium.dndapi.service.interfaces;

import fr.alium.dndapi.entity.Creature;

import java.util.List;

public interface ICreatureService {
    List<Creature> findAll();
}
