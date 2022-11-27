package org.sgx.sc.game;

import org.joml.Vector2d;
import org.joml.Vector3d;
import org.sgx.sc.engine.Camera;
import org.sgx.sc.engine.Transform;
import org.sgx.sc.engine.io.Keyboard;
import org.sgx.sc.engine.io.Mouse;
import org.sgx.sc.engine.io.Window;
import org.sgx.sc.engine.io.audio.Sound;
import org.sgx.sc.engine.io.audio.SoundPlayer;
import org.sgx.sc.engine.io.audio.effect.EffectRack;
import org.sgx.sc.engine.io.audio.effect.Reverb;
import org.sgx.sc.engine.time.Time;
import org.sgx.sc.game.io.SceneShader;
import org.sgx.sc.game.math.TimeUtil;
import org.sgx.sc.game.scene.Block;
import org.sgx.sc.game.scene.Player;
import org.sgx.sc.game.scene.io.Map;

public class Main {
    private static final char[][] testMap = new char[][] {
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', ' ', 'I', 'L', ' ', ' ', ' ', ' ', 'L', ' ', ' ', ' ', 'I', 'K'},
            {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'D', 'E'},
            {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', '#'},
            {'#', ' ', 'D', ' ', ' ', ' ', '#', ' ', '^', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', ' ', ' ', '#', ' ', '^', ' ', '^', ' ', ' ', ' ', 'S', '#'},
            {'#', 'T', '#', 'G', '^', ' ', '^', 'G', '^', ' ', '-', 'G', 'G', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
    };

    public static void main(String[] args) {

        Window window = new Window(1920.0 * 1.5, 1080.0 * 1.5, "Scary Dungeon", false, false);
        window.changeBackground(new Vector3d(0.25f, 0.75f, 1.0f));

        Keyboard keyboard = new Keyboard(window);
        Mouse mouse = new Mouse(window);

        SceneShader shader = new SceneShader();

        Map map = new Map(testMap);

        Vector2d cameraPos = new Vector2d(3.0, 3.0);

        Vector2d spawnpoint = new Vector2d();

        for(int y = 0; y < testMap.length; y++) {
            for(int x = 0; x < testMap[y].length; x++) {
                if(testMap[y][x] == 'T') spawnpoint = new Vector2d(x, testMap.length - y);
            }
        }

        final double CAMERA_SMOOTHNESS = 20.0;

        SoundPlayer soundPlayer = new SoundPlayer();

        Sound testSound = new Sound("assets/sounds/soundtracks/perfect_count.ogg");

        soundPlayer.play(testSound, new Vector2d(), 0.0, 1.0, true, new EffectRack());

        Time time = new Time();

        Player player = new Player(time, new Transform(new Vector3d(spawnpoint.x(), spawnpoint.y() + 0.1, -0.001), new Vector3d(), new Vector3d(1.0)));

        while(window.getRunning()) {
            /* Prepare [START] */
            time.update();
            player.update(map.getMapColliders(), keyboard, time);
            map.playBlockAnimations(player.collider, time);
            /* Prepare [END] */

            //System.out.println(mouse.getAbsolutePosition());

            cameraPos.x += TimeUtil.smooth(cameraPos.x(), player.transform.position.x(), CAMERA_SMOOTHNESS);
            cameraPos.y += TimeUtil.smooth(cameraPos.y(), player.transform.position.y(), CAMERA_SMOOTHNESS);

            /* Render [START] */
            window.update();

            shader.load();
            shader.setProject(100.0, window.getScale(), 0.01f, 1000.0);
            shader.setView(new Camera(new Vector3d(cameraPos, 5.0), new Vector3d()));

            Block.mesh.load();

            map.render(shader);
            player.render(shader);

            Block.mesh.unload();

            shader.unload();
            /* Render [END] */

            /* IO [START] */
            if(keyboard.getPress(Keyboard.KEY_F5)) {
                shader = new SceneShader();
            }
            /* IO [END] */
        }
        map.clear();
        shader.clear();
        window.close();
    }
}