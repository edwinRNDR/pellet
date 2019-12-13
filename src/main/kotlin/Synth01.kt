import net.beadsproject.beads.data.Buffer
import net.beadsproject.beads.ugens.BiquadFilter
import org.openrndr.application
import org.openrndr.pellet.*
import kotlin.math.pow


fun main() = application {
    configure {
        width = 768
        height = 576
    }

    program {
        val pc = context {
            out += biquadFilter {
                type = BiquadFilter.Type.BUTTERWORTH_LP

                mouse.clicked.listen {
                    println("reset")
                    reset()
                }

                ufrequency = wave(Buffer.TRIANGLE) {
                    frequency = 100.0f
                    update {
                        frequency = (4.0f + (40.0 * mouse.position.y / height)).coerceAtLeast(4.0).toFloat()
                        //phase = (mouse.position.x / width).toFloat()
                    }
                }.map { (1440f * (it * 0.4f + 0.6f)) }

                q = 0.5f

                this += gain {
                    gain = 0.25f
                    this += scalingMixer {
                        for (i in 0 until 4) {
                            this += trapezoid {
                                frequency = 55.0f * 2.0f.pow(i) + i * 3.0f
                                udutyCycle = wave {
                                    frequency = 0.05f * i
                                }.map { it * 0.4f + 0.5f }

                                uattack = wave(Buffer.SINE) {
                                    frequency = 0.0532f * i
                                }.map { it * 0.4f + 0.5f }

                                udecay = wave {
                                    phase = 0.3f * i
                                    frequency = 0.04932f * i
                                }.map { it * 0.4f + 0.5f }
                            }
                        }
                    }
                }
            }
            //context.start()
        }
        Thread.sleep(1000)
        pc.context.start()

        extend {
            pc.update()
        }

    }
}

