package conditions;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import main.UI;

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
        CONDITION_INCREASEMAXHEALTH,
        CONDITION_INCREASEMAXMANA,
        CONDITION_POISON,
        CONDITION_BLEEDING,
        CONDITION_ENERGY,
        CONDITION_FIRE,
        CONDITION_CURSED,
        CONDITION_HASTE,
        CONDITION_PARALYZE,
        CONDITION_NOMOVE,
        CONDITION_STUN
    };
// ===============================================
// Ready to use conditions
    public Condition basicRegenHealth = new Condition(ConditionType.CONDITION_REGENHEALTH, 15, 3, 0, 0, 0);
    public Condition basicRegenHealthPercent = new Condition(ConditionType.CONDITION_REGENPERCENTHEALTH, 5, 8, 3, 0, 0, 0);
    public Condition basicRegenMana = new Condition(ConditionType.CONDITION_REGENMANA, 15, 3, 0, 0, 0);
    public Condition basicRegenManaPercent = new Condition(ConditionType.CONDITION_REGENPERCENTMANA, 5, 8, 3, 0, 0, 0);
    public Condition basicPoison = new Condition(ConditionType.CONDITION_POISON, -15, 3, 0, 0, 0);
    public Condition basicBleeding = new Condition(ConditionType.CONDITION_BLEEDING, -15, 3, 0, 1, 0);
    public Condition basicEnergy = new Condition(ConditionType.CONDITION_ENERGY, -15, 3, 0, 0,0);
    public Condition basicFire = new Condition(ConditionType.CONDITION_FIRE, -15, 3, 0, 0, 0);
    // No plans for usage of cursed condition at the moment.

    public Condition basicIncreaseMaxHealth = new Condition(ConditionType.CONDITION_INCREASEMAXHEALTH, 150, -1, 0, 0, 0);
    public Condition basicIncreaseMaxMana = new Condition(ConditionType.CONDITION_INCREASEMAXMANA, 150, -1, 0, 0, 0);

    public Condition basicNoMove = new Condition(ConditionType.CONDITION_NOMOVE, -1, 0, 0, 0);
    public Condition basicStun = new Condition(ConditionType.CONDITION_STUN, -1, 0, 0, 0);
// ===============================================s
// Methods now

    public void addCondition(Entity entity, Condition condition, double duration) {
        condition.timeOfEnd = UI.playTime + duration;
        addConditionLogic(entity, condition);
    }

    public void addCondition(Entity entity, ConditionType type, int value, int ticks, double duration, int subId, int effectId) {
        Condition condition = new Condition(type, value, ticks, UI.playTime + duration, subId, effectId); // Uses 1st constructor
        addConditionLogic(entity, condition);
    }

    public void addCondition(Entity entity, ConditionType type, int valueA, int valueB, int ticks, double duration, int subId, int effectId) {
        Condition condition = new Condition(type, valueA, valueB, ticks, UI.playTime + duration, subId, effectId); // Uses 2nd constructor
        addConditionLogic(entity, condition);
    }

    private void addConditionLogic(Entity entity, Condition condition) {
        if(entity.amountOfConditions == 0) {
            condition.lastTick = UI.playTime;
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
                    conditionOnRemove(entity, entity.conditions[i]);
                    break;
                }
            }

            // We did: amount of conditions + 1 so its possible that we go out of max conditions lenghts in entity
            try {
                if(indexFoundCondition == entity.amountOfConditions) // check if it wasnt substitution <= true => we increase count var
                {
                    condition.lastTick = UI.playTime;
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
                        conditionOnRemove(entity, entity.conditions[i]);
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
                        conditionOnRemove(entity, entity.conditions[i]);
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
        conditionOnRemove(entity, entity.conditions[index]);
        // Condition has ended so we are removing it.
        if(index + 1 == entity.amountOfConditions) {
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

        if(condition.ticks != -1 && UI.playTime - condition.lastTick > condition.ticks) {
            // Handle the condition type&&value e.g. adding health
            conditionOnTick(entity, condition);
        }

        if(condition.timeOfEnd != -1 && UI.playTime > condition.timeOfEnd) {
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
                entity.changeHealthByPercent(condition.value, false);
                break;
            case CONDITION_REGENMANA:
                entity.changeMana(condition.value);
                break;
            case CONDITION_REGENPERCENTMANA:
                entity.changeManaByPercent(condition.value, false);
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
        condition.lastTick = UI.playTime;
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
                case CONDITION_INCREASEMAXHEALTH:
                    entity.increaseMaxHealth(condition.value);
                    break;
                case CONDITION_INCREASEMAXMANA:
                    entity.increaseMaxMana(condition.value);
                    break;
                case CONDITION_NOMOVE:
                    entity.noMoveConditions += 1;
                    break;
                case CONDITION_STUN:
                    entity.noMoveConditions += 1;
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
            case CONDITION_INCREASEMAXHEALTH:
                entity.increaseMaxHealth(-condition.value);
                break;
            case CONDITION_INCREASEMAXMANA:
                entity.increaseMaxMana(-condition.value);
                break;
            case CONDITION_NOMOVE:
                entity.noMoveConditions -= 1;
                break;
            case CONDITION_STUN:
                entity.noMoveConditions -= 1;
                break;
            default:
                // Do nothing
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
            case CONDITION_INCREASEMAXHEALTH:
                return "CONDITION_INCREASEMAXHEALTH";
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