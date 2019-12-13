package org.openrndr.pellet

import net.beadsproject.beads.core.AudioContext
import net.beadsproject.beads.core.IOAudioFormat
import net.beadsproject.beads.core.UGen
import net.beadsproject.beads.data.Buffer
import net.beadsproject.beads.ugens.*
import net.beadsproject.beads.ugens.Function

class PelletContext() {
    val alio = OpenALIO()
    val context = AudioContext(alio, 44100/60, IOAudioFormat(44100.0f, 16, 0, 1))

    init {
        context.out.gain
    }

    val out get() = context.out

    val updateFunctions = mutableListOf<() -> Unit>()

    fun UGen.update(f: () -> Unit) {
        updateFunctions.add(f)
    }

    operator fun UGen.plusAssign(other:UGen) {
        addInput(other)
    }

    fun update() {
        updateFunctions.forEach {
            it.invoke()
        }
    }
}

fun context(init: PelletContext.() -> Unit):PelletContext {
    val context = PelletContext()
    context.init()
    return context
}

/**
 * Create a onepole filter
 */
fun PelletContext.onePoleFilter(init: OnePoleFilter.() -> Unit) : OnePoleFilter {
    val filter = OnePoleFilter(context, 100.0f)
    filter.init()
    return filter
}

/**
 * Create a biquad filter
 */
fun PelletContext.biquadFilter(init: BiquadFilter.() -> Unit) : BiquadFilter{
    val filter = BiquadFilter(context, 1)
    filter.init()
    return filter
}

fun PelletContext.function(input:UGen, f:(Float)->Float):Function {
    return object:Function(input) {
        override fun calculate(): Float {
            return f(x[0])
        }
    }
}

fun UGen.map(f:(Float)->Float):Function {
    return object:Function(this) {
        override fun calculate(): Float {
            return f(x[0])
        }
    }
}

/**
 * Create a gain UGen
 */
fun PelletContext.gain(init: Gain.() -> Unit): Gain {
    val gain = Gain(context, 1, 1.0f)
    gain.init()
    return gain
}

fun PelletContext.scalingMixer(init: ScalingMixer.() -> Unit) : ScalingMixer{
    val mixer = ScalingMixer(context)
    mixer.init()
    return mixer
}

/**
 * Create a waveplayer / oscillator UGen
 */
fun PelletContext.wave(buffer: Buffer = Buffer.SINE, init: WavePlayer.() -> Unit):WavePlayer {
    val wave = WavePlayer(context, 440.0f, buffer)
    wave.init()
    return wave
}

fun PelletContext.envelope(init: Envelope.() -> Unit):Envelope {
    val env = Envelope(context)
    env.init()
    return env
}
fun PelletContext.noise(init: Noise.() -> Unit) : Noise {
    val noise = Noise(context)
    noise.init()
    return noise
}

fun PelletContext.phasor(init: Phasor.() -> Unit) : Phasor {
    val phasor = Phasor(context)
    phasor.init()
    return phasor
}

/**
 * Create a trapezoid UGen
 */
fun PelletContext.trapezoid(init: TrapezoidWave.() -> Unit):TrapezoidWave {
    val trap = TrapezoidWave(context)
    trap.init()
    return trap
}

fun PelletContext.revert(init: Reverb.() -> Unit):Reverb {
    val reverb = Reverb(context)
    reverb.init()
    return reverb
}