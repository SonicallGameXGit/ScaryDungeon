package org.sgx.sc.game.scene.io;

import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4d;
import org.sgx.sc.engine.Transform;
import org.sgx.sc.engine.io.texture.Texture;
import org.sgx.sc.engine.time.Time;
import org.sgx.sc.game.io.SceneShader;
import org.sgx.sc.game.math.Collider;
import org.sgx.sc.game.scene.Block;
import org.sgx.sc.game.scene.BlockTypes;
import org.sgx.sc.game.scene.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {
    private static final double SKULL_ANIMATION_SPEED = 4.0;
    private static final double SKULL_FLOAT_SPEED = 1.5;

    private final List<Block> staticBlocks = new ArrayList<>();
    private final List<Collider> colliders = new ArrayList<>();
    private final java.util.Map<Character, List<Block>> animatedBlocks = new HashMap<>();

    private final Block background;

    public Map(char[][] map) {
        animatedBlocks.put('K', new ArrayList<>());
        background = new Block(
                new Transform(
                        new Vector3d(-25.0, -25.0, -2.5),
                        new Vector3d(),
                        new Vector3d(50.0)),
                new Material(
                        new TextureSetup(
                                new Texture(
                                        "assets/textures/brick.png",
                                        Texture.NEAREST),
                                new Vector2d(), new Vector2d(50.0), TextureSetup.ANIMATED_TEXTURE_DIR_DOWN),
                        new Vector4d(1.0)));
        for(int x = 0; x < map.length; x++) {
            for(int y = 0; y < map[x].length; y++) {
                /* BRICK */
                if(map[x][y] == '#') {
                    Block brick = BlockTypes.BRICK().setPosition(new Vector3d(y, map.length - x - 1.0, 0.0));
                    staticBlocks.add(brick);
                    colliders.add(brick.collider);
                }
                /* SKULL */
                if(map[x][y] == 'K') animatedBlocks.get('K').add(BlockTypes.SKULL().setPosition(new Vector3d(y, map.length - x - 1.0, -0.002)));
                /* LADDER */
                if(y < map[x].length && y > 0 && map[x][y] == '^') {
                    short type = 0;
                    if(map[x][y - 1] == '#') type = 1;
                    if(map[x][y + 1] == '#') type = (short) (type == 1 ? 3 : 2);
                    Block ladder = BlockTypes.LADDER_NONE().setPosition(new Vector3d(y, map.length - x - 1.0, -0.002));
                    if(type == 1) ladder = BlockTypes.LADDER_LEFT().setPosition(new Vector3d(y, map.length - x - 1.0, -0.002));
                    if(type == 2) ladder = BlockTypes.LADDER_RIGHT().setPosition(new Vector3d(y, map.length - x - 1.0, -0.002));
                    if(type == 3) ladder = BlockTypes.LADDER_BOTH().setPosition(new Vector3d(y, map.length - x - 1.0, -0.002));

                    staticBlocks.add(ladder);
                    colliders.add(ladder.collider);
                }
            }
        }
    }

    public void render(SceneShader shader) {
        background.render(shader);
        for(Block block : staticBlocks) block.render(shader);
        for(List<Block> blocks : animatedBlocks.values()) for(Block block : blocks) block.render(shader);
    }
    public void clear() {
        for(Block block : staticBlocks) block.clear();
        for(List<Block> blocks : animatedBlocks.values()) for(Block block : blocks) block.clear();
        Block.mesh.clear();
        staticBlocks.clear();
        animatedBlocks.clear();
        colliders.clear();
    }
    public void playBlockAnimations(Collider playerCollider, Time time) {
        for(Block block : animatedBlocks.get('K')) {
            block.material.textureSetup.playTextureAnimation(time, TextureSetup.ANIMATION_PLAY_FORWARDS, SKULL_ANIMATION_SPEED);
            Vector2d latest = new Vector2d(block.getLatestTransformSave().position.x(), block.getLatestTransformSave().position.y());
            block.transform.position.y = latest.y() + Math.sin(SKULL_FLOAT_SPEED * time.getTime()) * 0.05 - 0.05;
        }
    }

    public List<Collider> getMapColliders() { return colliders; }
}