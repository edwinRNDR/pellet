package org.openrndr.pellet

import net.beadsproject.beads.core.AudioIO
import net.beadsproject.beads.core.UGen
import java.nio.ByteBuffer
import java.nio.ByteOrder
import org.openrndr.openal.*

class OpenALIO : AudioIO() {

    private var aqs:AudioSource? = null

    val source: AudioSource
        get() {
            return aqs?: error("not started")
        }

    override fun start(): Boolean {
        println("starting OpenALIO")
        aqs = AudioSystem.createQueueSource(queueSize = 2) {
            this.update()
            val bs = context.bufferSize
            val buffer = context.out.getOutBuffer(0)
            val bb = ByteBuffer.allocateDirect(bs * 2)
            bb.order(ByteOrder.nativeOrder())
            for (i in 0 until bs) {
                bb.putShort((buffer[i].coerceIn(-1.0f, 1.0f) * 32767).toShort())
            }
            bb.rewind()
            AudioData(AudioFormat.MONO_16, 44100, bb)
        }
        (aqs as AudioQueueSource).play()
        return true
    }

    override fun getAudioInput(p0: IntArray?): UGen {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
