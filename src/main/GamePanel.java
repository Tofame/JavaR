package main;

import java.util.ArrayList;

import javax.swing.JPanel;

import conditions.ConditionsHandler;
import effects.MagicEffectHandler;
import entity.Entity;
import entity.Player;

public class GamePanel extends JPanel implements Runnable {
    
    // SCREEN SETTINGS
    public final int originalTileSize = 32; // size of tile
    public static final int scale = 2;

    public final int tileSize = originalTileSize * scale; // originalTileSize multiplied by scale in which we are showing
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 14;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS
    public static final int maxWorldCol = 50;
    public static final int maxWorldRow = 50;
    public final int worldWith = tileSize * maxWorldCol; // not used anywhere
    public final int worldRow = tileSize * maxWorldRow; // not used anywhere -> useless, but lets keep it

    // HEALTH (AND MANA TOO) BAR SETTINGS
    public final int healthBarWidth = 30;
    public final int healthBarHeight = 3;

    // REFRESH RATE (akin to FPS)
    private static final int maxFPS = 60;

    // OTHER SETTINGS
    public static final boolean skipTitleScreen = true; // new game = going right into the game
    public static final boolean drawCollisions = true; // entities, objects
    public static final boolean drawTileCollisions = false; // tiles
    public static final boolean drawNonSolidTileCollision = false; // non-solid tiles

    // SYSTEM
    TileManager  tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    public ConditionsHandler conditionHandler = new ConditionsHandler(this);
    public MagicEffectHandler magicEffectHandler = new MagicEffectHandler(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10]; // [10] means 10 object MAX at one time at screen
    public Entity npc[] = new Entity[10]; // [10] means 10 NPC MAX at one time at screen
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    // SYSTEM: Character creator (must be initialized after Player is already existing btw)
    CharacterCreation charCreator = new CharacterCreation(this);

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public static int itemsCount = 0; // serves as id, tells how many items there is in the game

    public static enum slots {
        SLOT_ANY,
        SLOT_NONE,
        SLOT_HEAD,
        SLOT_BODY,
        SLOT_LEGS,
        SLOT_FEET,
        SLOT_NECKLACE,
        SLOT_LEFTHAND,
        SLOT_RIGHTHAND,
        SLOT_RING1,
        SLOT_RING2,
        SLOT_AMMO,
        SLOT_BACKPACK
    }

    public GamePanel() {
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setupObjects();
        aSetter.setupNPCs();
        aSetter.setupMonsters();
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / maxFPS; // ≈ 0.0167 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                delta--;
            }

            if (timer >= 1000000000) { // runs every second
                timer = 0;
            }
        }
    }

    public void update() {
        if(gameState == playState) {
            player.update();
            player.updateConditions();
            // NPC UPDATE
            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    npc[i].update();
                    // NPC wont (?) have conditions npc[i].updateConditions();
                }
            }
            // Monster UPDATE
            for(int i = 0; i < monster.length; i++) {
                if(monster[i] != null) {
                    monster[i].update();
                    monster[i].updateConditions();
                }
            }
        }
        if(gameState == pauseState) {
            // nothing
        }
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    public void enterTheGame(String selectedClass) {
        System.out.println("You have selected " + selectedClass);
        player.setDefaultImages("", "characters", true);
        player.characterSpriteSheet = player.spriteSheet;
        gameState = playState;
        // playMusic(0);

        //player.setDefaultImages("squares.png", "characters", false);
    }
}