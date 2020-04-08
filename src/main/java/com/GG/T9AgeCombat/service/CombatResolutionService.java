package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Round;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

@Service
public class CombatResolutionService {
    DiceRollingService diceRollingService;
    public CombatResolutionService(DiceRollingService diceRollingService) {
        this.diceRollingService = diceRollingService;
    }

    Round determineResult(Unit primary, Unit secondary, Integer primaryWoundsDealt, Integer secondaryWoundsDealt, Integer firstRound) {
        if (primary.getCount() <= 0) {
            return Round.builder().primaryWoundsDealt(primaryWoundsDealt).secondaryWoundsDealt(secondaryWoundsDealt).winner(secondary.getName()).wipedOut(true).flee(false).build();
        }
        if (secondary.getCount() <= 0) {
            return Round.builder().secondaryWoundsDealt(secondaryWoundsDealt).primaryWoundsDealt(primaryWoundsDealt).winner(primary.getName()).wipedOut(true).flee(false).build();
        }

        Integer primaryRankBonus = this.calculateRankBonus(primary);
        Integer secondaryRankBonus = this.calculateRankBonus(secondary);

        Integer primarySum = primaryRankBonus + primaryWoundsDealt + firstRound + primary.getStandardBearer();
        Integer secondarySum = secondaryRankBonus + secondaryWoundsDealt + secondary.getStandardBearer();
        Integer differential = primarySum - secondarySum;

        if (differential == 0) {
           primarySum = primarySum + primary.getMusician();
           secondarySum = secondarySum + secondary.getMusician();

           differential = primarySum - secondarySum;

           if (differential == 0) {
               return  Round.builder().secondaryWoundsDealt(secondaryWoundsDealt).primaryWoundsDealt(primaryWoundsDealt).flee(false).wipedOut(false).build();
           }
        }

        if(differential > 0) {
            Boolean steadfast = secondaryRankBonus > primaryRankBonus ? true : false;
            Boolean flee = this.breakTest(secondary, differential, steadfast);
            Boolean caught = null;

            if(flee) {
                Integer fleeDistance = diceRollingService.rollWithSum(2);
                Integer pursuitDistance = diceRollingService.rollWithSum(2);

                caught =  fleeDistance > pursuitDistance ? false : true;
            }
            return Round.builder().secondaryWoundsDealt(secondaryWoundsDealt).primaryWoundsDealt(primaryWoundsDealt).flee(flee).caught(caught).wipedOut(false).winner(primary.getName()).build();
        } else {
            Boolean steadfast = secondaryRankBonus < primaryRankBonus ? true : false;
            Boolean flee = this.breakTest(primary, Math.abs(differential), steadfast);
            Boolean caught = null;

            if(flee) {
                Integer fleeDistance = diceRollingService.rollWithSum(2);
                Integer pursuitDistance = diceRollingService.rollWithSum(2);

                caught =  fleeDistance > pursuitDistance ? false : true;
            }
            return Round.builder().secondaryWoundsDealt(secondaryWoundsDealt).primaryWoundsDealt(primaryWoundsDealt).flee(flee).caught(caught).wipedOut(false).winner(secondary.getName()).build();
        }

    }

    Integer calculateRankBonus(Unit unit) {
        return (int)Math.floor((double)unit.getCount()/(double)unit.getWidth());
    }

    Boolean breakTest(Unit unit, Integer differential, Boolean steadfast) {
        Integer leadership = unit.getLd();
        Integer ldCheck = diceRollingService.rollWithSum(2);

        if(!steadfast) {
            leadership = leadership - differential;
        }

        return ldCheck > leadership ? true : false;
    }
}
