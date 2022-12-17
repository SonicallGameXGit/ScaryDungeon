package org.sgx.sc.game.scene;

import org.joml.Vector2d;
import org.joml.Vector4d;
import org.sgx.sc.engine.Transform;
import org.sgx.sc.engine.io.Keyboard;
import org.sgx.sc.engine.io.texture.Texture;
import org.sgx.sc.engine.time.Time;
import org.sgx.sc.game.math.Collider;
import org.sgx.sc.game.math.TimeUtil;
import org.sgx.sc.game.scene.io.TextureSetup;

import java.util.List;

public class Player extends Block {
    private static final double ACTUAL_OFFSET = 0.25;
    private static final double WALK_SPEED = 6.0;
    private static final double FALL_SPEED = 28.0;
    private static final double JUMP_SPEED = 8.5;
    private static final double LADDER_SPEED = 5.0;
    private static final double LADDER_ANIMATION_SPEED = 20.0;
    private static final double WALK_SMOOTHNESS = 4.0;
    private static final double WALK_ANIMATION_SPEED = 38.0;
    private static final double IDLE_ANIMATION_SPEED = 1.5;
    private static final double LIQUID_FALL_MOD_SPEED = 0.45;
    private static final double DIE_ANIMATION_LENGTH = 1.0;

    private static final Vector2d ACTUAL_SCALE = new Vector2d(0.5, 0.875);

    private double health;
    private double dieAnimationCountdown;

    private final TextureSetup idleTextureSetup;
    private final TextureSetup walkTextureSetup;
    private final TextureSetup onLadderTextureSetup;

    private final Vector2d direction;
    private final Vector2d finalDirection;

    private boolean onGround;
    private boolean onLadder;
    private boolean inLiquid;
    private boolean dieAnimationStarted;
    private boolean rebirth;

    private double walkSpeed;
    private double smoothedWalkSpeed;
    private double jumpMultiplier;

    public Player(Transform transform) {
        super(transform, new Material(new TextureSetup(new Texture("assets/textures/animated/player_idle.png", Texture.NEAREST), new Vector2d(), new Vector2d(1.0), TextureSetup.ANIMATED_TEXTURE_DIR_DOWN), new Vector4d(0.7)), new Collider(new Vector2d(transform.position.x() + ACTUAL_OFFSET, transform.position.y()), new Vector2d(transform.scale.x() * ACTUAL_SCALE.x(), transform.scale.y() * ACTUAL_SCALE.y()), Collider.SOLID_TYPE));
        material.textureSetup.setState(0);
        idleTextureSetup = material.textureSetup;
        onLadderTextureSetup = new TextureSetup(new Texture("assets/textures/animated/player_on_ladder.png", Texture.NEAREST), new Vector2d(), new Vector2d(1.0), TextureSetup.ANIMATED_TEXTURE_DIR_DOWN);
        walkTextureSetup = new TextureSetup(new Texture("assets/textures/animated/player_walk.png", Texture.NEAREST), new Vector2d(), new Vector2d(1.0), TextureSetup.ANIMATED_TEXTURE_DIR_DOWN);
        direction = new Vector2d();
        finalDirection = new Vector2d();
        onGround = false;
        onLadder = false;
        idleTextureSetup.setState(0);
        walkTextureSetup.setState(0);
        onLadderTextureSetup.setState(0);
        walkSpeed = 0.0;
        material.textureSetup = idleTextureSetup;
        smoothedWalkSpeed = 0.0;
        health = 100.0;
        inLiquid = false;
        dieAnimationCountdown = 0.0;
        dieAnimationStarted = false;
        rebirth = false;
        jumpMultiplier = 1.0;
    }

    public void update(List<Collider> blocks, Keyboard keyboard, Time time) {
        if(health <= 0.0 && dieAnimationCountdown < DIE_ANIMATION_LENGTH) {
            dieAnimationStarted = dieAnimationCountdown == 0.0;
            direction.y -= FALL_SPEED * time.getDelta();
            if(dieAnimationStarted) direction.y = JUMP_SPEED;
            dieAnimationCountdown += time.getDelta();
            transform.position.y += direction.y() * time.getDelta();
            rebirth = false;
        } else if(dieAnimationCountdown >= DIE_ANIMATION_LENGTH) rebirth = true;
        else {
            health = 100.0;
            dieAnimationCountdown = 0.0;
            /* Update Visual [START] */
            idleTextureSetup.playTextureAnimation(time, TextureSetup.ANIMATION_PLAY_FORWARDS, IDLE_ANIMATION_SPEED);
            walkTextureSetup.playTextureAnimation(time, TextureSetup.ANIMATION_PLAY_FORWARDS, smoothedWalkSpeed * WALK_ANIMATION_SPEED);
            material.textureSetup = idleTextureSetup;
            /* Update Visual [END] */

            /* Update Control [START] */
            // Y Control
            if(!onLadder) {
                // Change Y direction with gravity and by checking keys W / UP or S / DOWN press
                direction.y -= FALL_SPEED * (inLiquid ? LIQUID_FALL_MOD_SPEED : 1.0) * time.getDelta();

                if(keyboard.getAnyPress(new int[] { Keyboard.KEY_SPACE, Keyboard.KEY_UP, Keyboard.KEY_W }) && onGround) direction.y = JUMP_SPEED * (inLiquid ? LIQUID_FALL_MOD_SPEED : 1.0) * jumpMultiplier;
            } else {
                // Change Y direction on ladder with controls
                direction.y = 0.0;

                if(keyboard.getAnyPress(new int[] { Keyboard.KEY_SPACE, Keyboard.KEY_UP, Keyboard.KEY_W })) direction.y = LADDER_SPEED;
                if(keyboard.getAnyPress(new int[] { Keyboard.KEY_LEFT_SHIFT, Keyboard.KEY_RIGHT_SHIFT, Keyboard.KEY_DOWN, Keyboard.KEY_S })) direction.y = -LADDER_SPEED;
                if(keyboard.getAnyPress(new int[] { Keyboard.KEY_SPACE, Keyboard.KEY_UP, Keyboard.KEY_W }) && keyboard.getAnyPress(new int[] { Keyboard.KEY_LEFT_SHIFT, Keyboard.KEY_RIGHT_SHIFT, Keyboard.KEY_DOWN, Keyboard.KEY_S })) direction.y = 0.0;
            }

            onGround = false;
            onLadder = false;
            inLiquid = false;
            jumpMultiplier = 1.0;

            transform.position.y += direction.y() * time.getDelta();
            collider.position.y = transform.position.y();

            // Move player in Y direction and check collision
            for(Collider block : blocks) {
                if(Collider.getCollision(collider, block)) {
                    if(block.getType() == Collider.LADDER_TYPE) onLadder = true;
                    if(block.getType() == Collider.LAVA_TYPE) {
                        health = 0.0;
                        inLiquid = true;
                    }
                }
            }
            for(Collider block : blocks) {
                if(Collider.getCollision(collider, block)) {
                    if(block.getType() == Collider.SOLID_TYPE) {
                        transform.position.y -= direction.y() * time.getDelta();
                        onGround = direction.y < 0.0;
                        direction.y = 0.0;
                        if(!onLadder) break;
                    }
                    if(block.getType() == Collider.ORB_TYPE) {
                        onGround = true;
                        jumpMultiplier = 1.5;
                    }
                }
            }

            collider.position.y = transform.position.y();

            // X Control
            // Initialize direction to stop player on next frame, if he's not running
            direction.x = 0.0;
            walkSpeed = 0.0;

            // Change X direction by checking keys D / RIGHT or A / LEFT press
            if(keyboard.getAnyPress(new int[] { Keyboard.KEY_D, Keyboard.KEY_RIGHT })) {
                direction.x = 1.0;
                walkSpeed = 1.0;
            }
            if(keyboard.getAnyPress(new int[] { Keyboard.KEY_A, Keyboard.KEY_LEFT })) {
                direction.x = -1.0;
                walkSpeed = 1.0;
            }
            if(keyboard.getAllPress(new int[] { Keyboard.KEY_D, Keyboard.KEY_A }) || keyboard.getAllPress(new int[] { Keyboard.KEY_RIGHT, Keyboard.KEY_LEFT })) {
                direction.x = 0.0;
                walkSpeed = 0.0;
            }

            // Move player in X direction and check collision
            finalDirection.x += TimeUtil.smooth(finalDirection.x, direction.x, WALK_SMOOTHNESS);
            smoothedWalkSpeed += TimeUtil.smooth(smoothedWalkSpeed, walkSpeed, WALK_SMOOTHNESS);
            transform.position.x += finalDirection.x() * WALK_SPEED * time.getDelta();
            collider.position.x = transform.position.x() + ACTUAL_OFFSET;

            for(Collider block : blocks) {
                if(Collider.getCollision(collider, block) && block.getType() == Collider.SOLID_TYPE) {
                    transform.position.x -= finalDirection.x() * WALK_SPEED * time.getDelta();
                    finalDirection.x = 0.0;
                    smoothedWalkSpeed = 0.0;
                    break;
                }
            }

            if(finalDirection.x > 0.01 || finalDirection.x < -0.01) material.textureSetup = walkTextureSetup;
            if(direction.x < 0.0) {
                idleTextureSetup.scale.x = -1.0;
                walkTextureSetup.scale.x = -1.0;
            }
            if(direction.x > 0.0) {
                idleTextureSetup.scale.x = 1.0;
                walkTextureSetup.scale.x = 1.0;
            }

            collider.position.x = transform.position.x() + ACTUAL_OFFSET;

            if(onLadder) {
                if(direction.y < 0.0) onLadderTextureSetup.playTextureAnimation(time, TextureSetup.ANIMATION_PLAY_BACKWARDS, LADDER_ANIMATION_SPEED);
                if(direction.y > 0.0) onLadderTextureSetup.playTextureAnimation(time, TextureSetup.ANIMATION_PLAY_FORWARDS, LADDER_ANIMATION_SPEED);

                material.setTextureSetup(onLadderTextureSetup);
            }
        }

        material.color = new Vector4d(material.color.x(), material.color.y() * getHealth() / 100.0, material.color.z() * getHealth() / 100.0, material.color.w());
    }

    public boolean getRebirth() { return rebirth; }

    public double getHealth() { return health; }
}