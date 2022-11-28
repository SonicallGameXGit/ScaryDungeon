package org.sgx.sc.engine.io.audio.effect;

import org.lwjgl.openal.AL11;
import org.lwjgl.openal.EXTEfx;

public class EffectRack {
	private final int id;
	
	public EffectRack()  {
		id = EXTEfx.alGenAuxiliaryEffectSlots();
		
		AL11.alSource3i(id, EXTEfx.AL_AUXILIARY_SEND_FILTER, id, 0, EXTEfx.AL_FILTER_NULL);
	}
	
	public EffectRack setEffect(Effect effect) {
		EXTEfx.alAuxiliaryEffectSloti(id, EXTEfx.AL_EFFECTSLOT_EFFECT, effect.getId());
		
		return this;
	}
	
	public int getId() { return id; }
}