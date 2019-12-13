import net.beadsproject.beads.data.Buffer
import net.beadsproject.beads.ugens.BiquadFilter
import org.openrndr.application
import org.openrndr.pellet.*



fun main() = application {
    configure {
        width = 768
        height = 576
    }

    program {
        val pc = context {
            out += biquadFilter {
                type = BiquadFilter.Type.BUTTERWORTH_LP

                ufrequency = function(wave {
                    frequency = 1.0f
                }) { (220f * (it*0.4f+0.6f)).toFloat() }

                q = 0.5f
//                update {
//                    frequency = mouse.position.x.toFloat()
//                }

                this += scalingMixer {
                    this += wave {
                        buffer = Buffer.SAW
                        frequency = 220.0f
                        update {
                            frequency = (220.0f + Math.cos(seconds) * 1.0).toFloat()
                        }
                    }
                    this += wave {
                        buffer = Buffer.SAW
                        frequency = 440.0f
                        update {
                            frequency = (440.0f + Math.cos(seconds) * 2.0).toFloat()
                        }
                    }
                }
            }
            context.start()
        }

        extend {
            pc.update()
        }

    }
}

