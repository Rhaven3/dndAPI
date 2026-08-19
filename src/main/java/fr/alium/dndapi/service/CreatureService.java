package fr.alium.dndapi.service;

import fr.alium.dndapi.entity.Creature;
import fr.alium.dndapi.repository.CreatureRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CreatureService implements fr.alium.dndapi.service.interfaces.ICreatureService {
    CreatureRepository creatureRepository;

    private final List<List<Integer>> subsetXP;
    private final List<Integer> xpTable;

    public CreatureService(CreatureRepository creatureRepository) {
        this.creatureRepository = creatureRepository;
        this.subsetXP = new ArrayList<>();
        this.xpTable = Arrays.asList(25, 50, 100,
                200, 450, 700, 1100, 1800, 2300, 2900, 3900, 5000, 5900,
                7200, 8400, 10000, 11500, 13000, 15000, 18000, 20000, 22000, 25000,
                33000, 41000, 5000, 62000, 75000, 90000, 105000, 120000, 135000, 155000
        );
    }

    @Override
    public List<Creature> findByXpTreshold(int xptreshold, int numberCreatures) {
        Random random = new Random();
        List<Creature> creatures = new ArrayList<>();
        List<List<Integer>> allSubsetCR = findAllSubsetCR(xptreshold, numberCreatures);
        List<Integer> subsetCR = allSubsetCR.get(random.nextInt(allSubsetCR.size()));

        for (Integer cr : subsetCR) {
            creatures.add(findRandomByCR(cr));
        }
        return creatures;
    }

    @Override
    public List<List<Integer>> findAllSubsetCR(int sumTarget, int numberCreatures) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(numberCreatures, sumTarget, 0, new Stack<>(), 0, 0);

        for (List<Integer> listofXP : subsetXP) {
            List<Integer> newList = new ArrayList<>();

            for (Integer XP : listofXP) {
                newList.add(xpTable.indexOf(XP) + 1);
            }
            result.add(newList);
        }

        subsetXP.clear();
        return result;
    }

    private void backtrack(int numberCR, int targetSum, int start, Stack<Integer> current, int currentSum, int countCR) {
        if (countCR == numberCR && currentSum == targetSum) {
            subsetXP.add(current);
            return;
        }
        if (countCR >= numberCR || currentSum >= targetSum) {
            return;
        }
        for (int i = start; i < xpTable.size(); i++) {
            current.push(xpTable.get(i));
            backtrack(numberCR, targetSum, i + 1, current, currentSum + xpTable.get(i), countCR + 1);
            current.pop();
        }
    }

    @Override
    public Creature findRandomByCR(int cr) {
        List<Creature> creatures = creatureRepository.findAllByCR(cr);
        Random random = new Random();
        int index = random.nextInt(creatures.size());
        return creatures.get(index);
    }

    @Override
    public int getXp(Creature creature) {
        return xpTable.get(creature.getCR()-1);
    }

}