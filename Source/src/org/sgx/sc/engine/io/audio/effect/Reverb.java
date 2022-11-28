package org.sgx.sc.engine.io.audio.effect;

import org.lwjgl.openal.EXTEfx;

public class Reverb extends Effect {
	public Reverb() { super(EXTEfx.AL_EFFECT_REVERB); }
	
	public Reverb decay(float time) {
		EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_DECAY_TIME, time);
		
		return this;
	}
	public Reverb density(float density) {
		EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_DENSITY, density);
		
		return this;
	}
	public Reverb gain(float gain) {
		EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_GAIN, gain);
		
		return this;
	}
	public Reverb diffuse(float diffusion) {
		EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_DIFFUSION, diffusion);
		
		return this;
	}
	public Reverb delay(float delay) {
		EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_LATE_REVERB_DELAY, delay);
		
		return this;
	}
}