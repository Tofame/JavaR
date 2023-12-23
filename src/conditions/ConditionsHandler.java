package conditions;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class ConditionsHandler {
    GamePanel gp;

    public ConditionsHandler(GamePanel gp) {
        this.gp = gp;
    }

    public static enum ConditionType {
        CONDITION_REGENHEALTH,
        CONDITION_REGENPERCENTHEALTH,
        CONDITION_REGENMANA,
        CONDITION_REGENPERCENTMANA,
        CONDITION_INCREASEHEALTH,
        CONDITION_INCREASEMAXHEALTH,
        CONDITION_INCREASEMANA,
        CONDITION_INCREASEMAXMANA,
        CONDITION_POISON,
        CONDITION_BLEEDING,
        CONDITION_ENERGY,
        CONDITION_FIRE,
        CONDITION_CURSED,
        CONDITION_HASTE,
        CONDITION_PARALYZE
    };
// ===============================================
// Ready to use conditions
    Condition basicPoison = new Condition(ConditionType.CONDITION_POISON, -10, -15, 3, 0, 0, 1, null);
// ===============================================s
// Methods now

    public void addCondition(Entity entity, Condition condition, double duration) {
        condition.timeOfEnd = gp.ui.playTime + duration;
        addConditionLogic(entity, condition);
    }

    public void addCondition(Entity entity, ConditionType type, int value, int ticks, double duration, int subId, BufferedImage effect) {
        Condition condition = new Condition(type, value, ticks, gp.ui.playTime + duration, subId, effect); // Uses 1st constructor
        addConditionLogic(entity, condition);
    }

    public void addCondition(Entity entity, ConditionType type, int valueA, int valueB, int ticks, double duration, int subId, int valueType, BufferedImage effect) {
        Condition condition = new Condition(type, valueA, valueB, ticks, gp.ui.playTime + duration, subId, valueType, effect); // Uses 2nd constructor
        addConditionLogic(entity, condition);
    }

    private void addConditionLogic(Entity entity, Condition condition) {
        if(entity.amountOfConditions == 0) {
            condition.lastTick = gp.ui.playTime - (condition.ticks + 1/60); // so on condition apply the value runs (e.g. damage is dealt)
            entity.conditions[entity.amountOfConditions] = condition;
            entity.amountOfConditions++;
        } else {
            // we check if there is already a condition with same subid and if there is we will substitute it
            int indexFoundCondition = entity.amountOfConditions;

            for(int i = 0; i < indexFoundCondition; i++) {
                Condition foundCondition = entity.conditions[i];
                if(foundCondition.subId == condition.subId && foundCondition.type == condition.type) {
                    condition.lastTick = foundCondition.lastTick;
                    indexFoundCondition = i;
                    break;
                }
            }

            // We did: amount of conditions + 1 so its possible that we go out of max conditions lenghts in entity
            try {
                if(indexFoundCondition == entity.amountOfConditions) // check if it wasnt substitution <= true => we increase count var
                {
                    condition.lastTick = gp.ui.playTime - (condition.ticks + 1/60); // so on condition apply the value runs (e.g. damage is dealt)
                    entity.amountOfConditions++;
                }
                entity.conditions[indexFoundCondition] = condition;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("addCondition index out of bounds.");
                System.out.println("Max size of conditions for entity has been breached.");
                System.out.println("Entity (target) name: " + entity.getName() + " condition type: " + convertTypeToString(condition.type));
            }
        }
    }

    private String convertTypeToString(ConditionType type) {
        switch(type) {
            case CONDITION_REGENHEALTH:
                return "CONDITION_REGENHEALTH";
            case CONDITION_REGENPERCENTHEALTH:
                return "CONDITION_REGENPERCENTHEALTH";
            case CONDITION_REGENMANA:
                return "CONDITION_REGENMANA";
            case CONDITION_REGENPERCENTMANA:
                return "CONDITION_REGENPERCENTMANA";
            case CONDITION_INCREASEHEALTH:
                return "CONDITION_INCREASEHEALTH";
            case CONDITION_INCREASEMAXHEALTH:
                return "CONDITION_INCREASEMAXHEALTH";
            case CONDITION_INCREASEMANA:
                return "CONDITION_INCREASEMANA";
            case CONDITION_INCREASEMAXMANA:
                return "CONDITION_INCREASEMAXMANA";
            case CONDITION_POISON:
                return "CONDITION_POISON";
            case CONDITION_BLEEDING:
                return "CONDITION_BLEEDING";
            case CONDITION_ENERGY:
                return "CONDITION_ENERGY";
            case CONDITION_FIRE:
                return "CONDITION_FIRE";
            case CONDITION_CURSED:
                return "CONDITION_CURSED";
            case CONDITION_HASTE:
                return "CONDITION_HASTE";
            case CONDITION_PARALYZE:
                return "CONDITION_PARALYZE";
            default:
                return "UNKNOWN CONDITION";
        }
    }
}