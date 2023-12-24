package conditions;

import java.awt.image.BufferedImage;

import conditions.ConditionsHandler.ConditionType;

public class Condition {
    public double timeOfEnd = 0; // os.time would be used IF it was multiplayer. For singleplayer we will use playtime.
    // timeOfEnd == -1 means that condition is infinite

    public ConditionType type;
    public int subId = 0; // 0 -> default, > 0 -> sub id

    public int value;
    public int valueType = 0; // 0 - only valueA, 1 - random between valueA&&valueB, 2 - only valueA in %, 3 - random between valueA&&B in %

    public int ticks; // e.g. add *value* health every *ticks* seconds
    // ticks == -1 means that its e.g. haste, meaning that we handle the values onAdd and its not e.g. increasing speed every 3s, but just on adding.
    public double lastTick;

    public BufferedImage effect;

    public Condition(ConditionType type, int value, int ticks, double timeOfEnd, int subId, BufferedImage effect) {
        this.type = type;
        this.value = value;
        this.valueType = 0;

        this.ticks = ticks;

        this.timeOfEnd = timeOfEnd;

        this.subId = subId;

        this.effect = effect;
    }

    public Condition(ConditionType type, int valueA, int valueB, int ticks, double timeOfEnd, int subId, int valueType, BufferedImage effect) {
        this.type = type;
        this.value = ConditionsHandler.rand.nextInt(valueA, valueB + 1); // +1 because its exclusive
        this.valueType = valueType;

        this.ticks = ticks;

        this.timeOfEnd = timeOfEnd;

        this.subId = subId;

        this.effect = effect;
    }
}