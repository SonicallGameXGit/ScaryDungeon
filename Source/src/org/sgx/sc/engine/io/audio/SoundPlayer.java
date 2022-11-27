package org.sgx.sc.engine.io.audio;

import org.joml.Vector2d;
import org.lwjgl.openal.*;
import org.sgx.sc.engine.io.audio.effect.EffectRack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class SoundPlayer {
    private final int id;

    public SoundPlayer() {
        long device = ALC10.alcOpenDevice((ByteBuffer) null);

        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        long context = ALC10.alcCreateContext(device, (IntBuffer) null);

        ALC10.alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);

        id = AL10.alGenSources();
        AL10.alSourcei(id, AL10.AL_BUFFER, 0);
        AL10.alSourcei(id, AL10.AL_LOOPING, 0);
        AL10.alSourcei(id, AL10.AL_POSITION, 0);
        AL10.alSourcef(id, AL10.AL_GAIN, 0);
    }

    public void play(Sound sound, Vector2d position, double volume, double pitch, boolean loop, EffectRack rack) {
        AL11.alSource3i(id, EXTEfx.AL_AUXILIARY_SEND_FILTER, rack.getId(), 0, EXTEfx.AL_FILTER_NULL);
        AL10.alSourcei(id, AL10.AL_BUFFER, sound.getId());
        AL10.alSourcei(id, AL10.AL_LOOPING, loop ? 1 : 0);
        AL10.alSourcef(id, AL10.AL_GAIN, (float) volume);
        AL10.alSourcef(id, AL10.AL_PITCH, (float) pitch);
        AL10.alSource3f(id, AL10.AL_POSITION, (float) position.x(), (float) position.y(), 0.0f);
        AL10.alSourcePlay(id);
    }
    public void delete() { AL10.alDeleteSources(id); }
    public void setListenerPos(Vector2d position) { AL11.alListener3f(AL11.AL_POSITION, (float) position.x(), (float) position.y(), 0); }

    public int getId() { return id; }
}