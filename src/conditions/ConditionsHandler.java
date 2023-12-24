package conditions;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class ConditionsHandler {
    GamePanel gp;

    public static Random rand = new Random();

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
    public Condition basicPoison = new Condition(ConditionType.CONDITION_POISON, -15, -10, 3, 0, 0, 1, 0);
    public Condition basicBleeding = new Condition(ConditionType.CONDITION_BLEEDING, -15, -10, 1, 0, 0, 1, 0);
// ===============================================s
// Methods now

    public void addCondition(Entity entity, Condition condition, double duration) {
        condition.timeOfEnd = gp.ui.playTime + duration;
        addConditionLogic(entity, condition);
    }

    public void addCondition(Entity entity, ConditionType type, int value, int ticks, double duration, int subId, int effectId) {
        Condition condition = new Condition(type, value, ticks, gp.ui.playTime + duration, subId, effectId); // Uses 1st constructor
        addConditionLogic(entity, condition);
    }

    public void addCondition(Entity entity, ConditionType type, int valueA, int valueB, int ticks, double duration, int subId, int valueType, int effectId) {
        Condition condition = new Condition(type, valueA, valueB, ticks, gp.ui.playTime + duration, subId, valueType, effectId); // Uses 2nd constructor
        addConditionLogic(entity, condition);
    }

    private void addConditionLogic(Entity entity, Condition condition) {
        if(entity.amountOfConditions == 0) {
            condition.lastTick = gp.ui.playTime - (condition.ticks + 1/60); // so on condition apply the value runs (e.g. damage is dealt)
            entity.conditions[entity.amountOfConditions] = condition;
            entity.amountOfConditions++;
            conditionOnAdd(entity, condition);
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
                    condition.lastTick = gp.ui.playTime;
                    entity.amountOfConditions++;
                }
                entity.conditions[indexFoundCondition] = condition;
                conditionOnAdd(entity, condition);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("addCondition index out of bounds.");
                System.out.println("Max size of conditions for entity has been breached.");
                System.out.println("Entity (target) name: " + entity.getName() + " condition type: " + convertTypeToString(condition.type));
            }
        }
    }

    public boolean hasCondition(Entity entity, ConditionType type, int subId) {
        if(entity.amountOfConditions < 1) {
            return false;
        }

        if(subId == -1) { // subId doesnt matter
            for(int i = 0; i < entity.amountOfConditions; i++) {
                if(entity.conditions[i].type == type) {
                    return true;
                }
            }
        } else {
            for(int i = 0; i < entity.amountOfConditions; i++) {
                if(entity.conditions[i].type == type && entity.conditions[i].subId == subId) {
                    return true;
                }
            }
        }

        return false;
    }

    public void removeCondition(Entity entity, ConditionType type, int subId) {
        if(entity.amountOfConditions > 0) {
            if(subId == -1) { // subId doesnt matter
                for(int i = 0; i < entity.amountOfConditions; i++) {
                    if(entity.conditions[i].type == type) {
                        if(i + 1 == entity.amountOfConditions) {
                            entity.conditions[i] = null;
                        } else { // is not the last condition so we need to move conditions AFTER to the index before
                        // otherwise it would be: { condition, condition, null, condition} for example
                            for(int j = i + 1; j < entity.amountOfConditions; j++) {
                                entity.conditions[j - 1] = entity.conditions[j];
                            }
                            entity.conditions[entity.amountOfConditions - 1] = null;
                        }
                        entity.amountOfConditions--;
                    }
                }
            } else {
                for(int i = 0; i < entity.amountOfConditions; i++) {
                    if(entity.conditions[i].type == type && entity.conditions[i].subId == subId) {
                        if(i + 1 == entity.amountOfConditions) {
                            entity.conditions[i] = null;
                        } else { // is not the last condition so we need to move conditions AFTER to the index before
                        // otherwise it would be: { condition, condition, null, condition} for example
                            for(int j = i + 1; j < entity.amountOfConditions; j++) {
                                entity.conditions[j - 1] = entity.conditions[j];
                            }
                            entity.conditions[entity.amountOfConditions - 1] = null;
                        }
                        entity.amountOfConditions--;
                    }
                }
            }
        }
    }

    public void removeAllConditions(Entity entity) {
        for(int i = 0; i < entity.amountOfConditions; i++) {
            conditionOnRemove(entity, entity.conditions[i]);
            entity.conditions[i] = null;
        }
        entity.amountOfConditions = 0;
    }

    public void removeConditionByIndex(Entity entity, int index) {
        // Condition has ended so we are removing it.
        if(index + 1 == entity.amountOfConditions) {
            conditionOnRemove(entity, entity.conditions[index]);
            entity.conditions[index] = null;
        } else { // is not the last condition so we need to move conditions AFTER to the index before
        // otherwise it would be: { condition, condition, null, condition} for example
            for(int j = index + 1; j < entity.amountOfConditions; j++) {
                entity.conditions[j - 1] = entity.conditions[j];
            }
            entity.conditions[entity.amountOfConditions - 1] = null;
        }
        entity.amountOfConditions--;
    }

    public void conditionCheckup(Entity entity, int index) {
        Condition condition = entity.conditions[index];

        if(condition.ticks != -1 && gp.ui.playTime - condition.lastTick > condition.ticks) {
            // Handle the condition type&&value e.g. adding health
            conditionOnTick(entity, condition);
        }

        if(condition.timeOfEnd != -1 && gp.ui.playTime > condition.timeOfEnd) {
            // Remove condition that isnt infinite and ended
            removeConditionByIndex(entity, index);
        }
    }

    public void conditionOnTick(Entity entity, Condition condition) {
        switch(condition.type) {
            case CONDITION_REGENHEALTH:
                entity.changeHealth(condition.value);
                break;
            case CONDITION_REGENPERCENTHEALTH:
                entity.changeHealtPercent(condition.value);
                break;
            case CONDITION_REGENMANA:
                break;
            case CONDITION_REGENPERCENTMANA:
                break;
            case CONDITION_INCREASEHEALTH:
                break;
            case CONDITION_INCREASEMAXHEALTH:
                break;
            case CONDITION_INCREASEMANA:
                break;
            case CONDITION_INCREASEMAXMANA:
                break;
            case CONDITION_POISON:
                entity.changeHealth(condition.value);
                break;
            case CONDITION_BLEEDING:
                entity.changeHealth(condition.value);
                break;
            case CONDITION_ENERGY:
                entity.changeHealth(condition.value);
                break;
            case CONDITION_FIRE:
                entity.changeHealth(condition.value);
                break;
            case CONDITION_CURSED:
                break;
            default:
                System.out.println("Unhandled condition in conditionOnTick: " + convertTypeToString(condition.type));
        }
        System.out.println("Tick done");
        condition.lastTick = gp.ui.playTime;
    }

    public void conditionOnAdd(Entity entity, Condition condition) {
        if(condition.ticks != -1) {
            conditionOnTick(entity, condition);
        } else {
            switch(condition.type) {
                case CONDITION_HASTE:
                    entity.changeSpeed(condition.value);
                    break;
                case CONDITION_PARALYZE:
                    entity.changeSpeed(-condition.value);
                    break;
                default:
                    System.out.println("Unhandled condition in conditionOnAdd: " + convertTypeToString(condition.type));
            }
        }
    }

    public void conditionOnRemove(Entity entity, Condition condition) {
        // will e.g. revert speed from haste condition
        switch(condition.type) {
            case CONDITION_HASTE:
                entity.changeSpeed(-condition.value);
                break;
            case CONDITION_PARALYZE:
                entity.changeSpeed(condition.value);
                break;
            default:
                // do nothing
                System.out.println("Unhandled condition in conditionOnRemove: " + convertTypeToString(condition.type));
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