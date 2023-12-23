package conditions;

import java.awt.image.BufferedImage;

import conditions.ConditionsHandler.ConditionType;

public class Condition {
    public double timeOfEnd = 0; // os.time would be used IF it was multiplayer. For singleplayer we will use playtime.

    public ConditionType type;
    public int subId = 0; // 0 -> default, 1 -> not removable, > 1 -> usual sub id

    public int valueA;
    public int valueB;
    public int valueType = 0; // 0 - only valueA, 1 - random between valueA&&valueB, 2 - only valueA in %, 3 - random between valueA&&B in %

    public int ticks; // e.g. add *value* health every *ticks* seconds
    public double lastTick;

    public BufferedImage effect;

    public Condition(ConditionType type, int value, int ticks, double timeOfEnd, int subId, BufferedImage effect) {
        this.type = type;
        this.valueA = value;
        this.valueType = 0;

        this.ticks = ticks;

        this.timeOfEnd = timeOfEnd;

        this.subId = subId;

        this.effect = effect;
    }

    public Condition(ConditionType type, int valueA, int valueB, int ticks, double timeOfEnd, int subId, int valueType, BufferedImage effect) {
        this.type = type;
        this.valueA = valueA;
        this.valueB = valueB;
        this.valueType = valueType;

        this.ticks = ticks;

        this.timeOfEnd = timeOfEnd;

        this.subId = subId;

        this.effect = effect;
    }
}