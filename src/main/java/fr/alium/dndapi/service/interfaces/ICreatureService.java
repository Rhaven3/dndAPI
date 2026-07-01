package fr.alium.dndapi.service.interfaces;

import fr.alium.dndapi.entity.Creature;

import java.util.List;

public interface ICreatureService {
    List<Creature> findByXpTreshold(int xptreshold, int numberCreatures);

    List<List<Integer>> findAllSubsetCR(int sumTarget, int numberCreatures);

    Creature findRandomByCR(int cr);

    int getXp(Creature creature);
}
