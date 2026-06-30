package fr.alium.dndapi.service.interfaces;

import fr.alium.dndapi.entity.Creature;

import java.util.List;

public interface ICreatureService {
    List<Creature> findAll();

    List<Creature> findByXpTreshold(int xptreshold, int numberCreatures);
}
