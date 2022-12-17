package org.sgx.sc.engine.io.audio.effect;

import org.lwjgl.openal.EXTEfx;

public abstract class Effect {
	protected int id;
	
	public Effect(int type) {
		id = EXTEfx.alGenEffects();
		
		EXTEfx.alEffecti(id, EXTEfx.AL_EFFECT_TYPE, type);
	}
	
	public int getId() { return id; }
}