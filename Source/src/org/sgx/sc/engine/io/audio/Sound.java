package org.sgx.sc.engine.io.audio;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryUtil;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Sound {
    private final int id;

    public Sound(String location) {
        id = AL10.alGenBuffers();

        STBVorbisInfo info = STBVorbisInfo.malloc();

        ShortBuffer pcm = readVorbis(location, info);

        AL10.alBufferData(id, info.channels() == 1 ? AL10.AL_FORMAT_MONO16 : AL10.AL_FORMAT_STEREO16, pcm, info.sample_rate());
    }

    public void delete() { AL10.alDeleteBuffers(id); }

    public int getId() { return id; }

    private ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);

        buffer.flip();
        newBuffer.put(buffer);

        return newBuffer;
    }
    private ByteBuffer ioResourceToByteBuffer(String resource) throws Exception {
        ByteBuffer buffer;

        Path path = Paths.get(resource);

        if(Files.isReadable(path)) {
            try(SeekableByteChannel channel = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int) channel.size() + 1);

                while(channel.read(buffer) != -1);
            }
        } else {
            try (
                    InputStream source = Sound.class.getClassLoader().getResourceAsStream(resource);
                    ReadableByteChannel readableByteChannel = Channels.newChannel(source);
            ) {
                buffer = BufferUtils.createByteBuffer(32768);

                while(true) {
                    int bytes = readableByteChannel.read(buffer);

                    if(bytes == -1) break;
                    if(buffer.remaining() == 0) buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2);
                }
            }
        }

        buffer.flip();

        return MemoryUtil.memSlice(buffer);
    }

    private ShortBuffer readVorbis(String resource, STBVorbisInfo info) {
        ByteBuffer vorbis = null;

        try { vorbis = ioResourceToByteBuffer(resource); } catch(Exception exception) { exception.printStackTrace(); }

        IntBuffer error = BufferUtils.createIntBuffer(1);

        long decoder = STBVorbis.stb_vorbis_open_memory(vorbis, error, null);

        STBVorbis.stb_vorbis_get_info(decoder, info);

        int channels = info.channels();

        ShortBuffer pcm = BufferUtils.createShortBuffer(STBVorbis.stb_vorbis_stream_length_in_samples(decoder) * channels);

        STBVorbis.stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm);
        STBVorbis.stb_vorbis_close(decoder);

        return pcm;
    }
}