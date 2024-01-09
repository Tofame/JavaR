package conditions;

import conditions.ConditionsHandler.ConditionType;

public class Condition {
    public double timeOfEnd = 0; // os.time would be used IF it was multiplayer. For singleplayer we will use playTime.
    // timeOfEnd == -1 means that condition is infinite

    public ConditionType type;
    public int subId = 0; // 0 -> default, > 0 -> sub id

    public int value;
    public int value2;

    public int ticks; // e.g. add *value* health every *ticks* seconds
    // ticks == -1 means that its e.g. haste, meaning that we handle the values onAdd and its not e.g. increasing speed every 3s, but just on adding.
    public double lastTick;

    public int effectId; // 0 = no effect

    public Condition(ConditionType type, int value, int ticks, double timeOfEnd, int subId, int effectId) {
        this.type = type;
        this.value = value;

        this.ticks = ticks;

        this.timeOfEnd = timeOfEnd;

        this.subId = subId;

        this.effectId = effectId;
    }

    public Condition(ConditionType type, int value, int value2, int ticks, double timeOfEnd, int subId, int effectId) {
        this.type = type;
        this.value = value;
        this.value2 = value2;

        this.ticks = ticks;

        this.timeOfEnd = timeOfEnd;

        this.subId = subId;

        this.effectId = effectId;
    }

    public Condition(ConditionType type, int ticks, double timeOfEnd, int subId, int effectId) {
        this.type = type;

        this.ticks = ticks;

        this.timeOfEnd = timeOfEnd;

        this.subId = subId;

        this.effectId = effectId;
    }
}