package org.sgx.sc.engine.io.audio.effect;

import org.lwjgl.openal.EXTEfx;

public class Distortion extends Effect {
	public Distortion() { super(EXTEfx.AL_EFFECT_DISTORTION); }

	public Distortion edge(float edge) {
		EXTEfx.alEffectf(id, EXTEfx.AL_DISTORTION_EDGE, edge);
		
		return this;
	}
	public Distortion gain(float gain) {
		EXTEfx.alEffectf(id, EXTEfx.AL_DISTORTION_GAIN, gain);
		
		return this;
	}
	public Distortion filterCenter(float center) {
		EXTEfx.alEffectf(id, EXTEfx.AL_DISTORTION_EQCENTER, center);
		
		return this;
	}
	public Distortion filterBandwith(float bandwidth) {
		EXTEfx.alEffectf(id, EXTEfx.AL_DISTORTION_EQBANDWIDTH, bandwidth);
		
		return this;
	}
	public Distortion lowpass(float cutoff) {
		EXTEfx.alEffectf(id, EXTEfx.AL_DISTORTION_LOWPASS_CUTOFF, cutoff);
		
		return this;
	}
	
}