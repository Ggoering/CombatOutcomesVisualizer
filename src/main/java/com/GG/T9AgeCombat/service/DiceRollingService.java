package com.GG.T9AgeCombat.service;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiceRollingService {
    public DiceRollingService(){}

    List<Integer> roll(Integer quantity) {
        List<Integer> resultList = new ArrayList<Integer>();
        int i = 0;
        while (i < quantity) {
            Integer result = (int)Math.ceil(Math.random() * 6);
            resultList.add(result);
            i++;
        }
        return resultList;
    }

    Integer rollWithSum(Integer quantity) {
        int total = 0;
        int i = 0;
        while (i < quantity) {
            Integer result = (int)Math.ceil(Math.random() * 6);
            total += (result);
            i++;
        }
        return total;
    }
}

