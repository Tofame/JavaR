package effects;

import main.Position;

public class MagicEffectHandler {
    public static int singleFrameInterval = 700;

    public void sendMagicEffect(Position position, int id) {
        // TO-DO
        // The general idea is to bind an effect to the tile and draw it either before the character or at the end of the drawing (so draw it above everything)
        // Proof of concept is:
        // Tile tile = tile[position.x][position.y];
        // tile.downEffects[lastEffect] = id;
        // tile.aboveEffects[lastEffect] = id;
    }
}