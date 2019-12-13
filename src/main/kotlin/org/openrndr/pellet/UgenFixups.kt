package org.openrndr.pellet

import net.beadsproject.beads.core.UGen
import net.beadsproject.beads.ugens.BiquadFilter
import net.beadsproject.beads.ugens.OnePoleFilter
import net.beadsproject.beads.ugens.Phasor
import net.beadsproject.beads.ugens.TrapezoidWave

/*
 * Beads Ugen API is is not so convenient when used in combination with Kotlin.
 * That's why we fix it up here using extension properties
 */

var BiquadFilter.ugain: UGen
    get() {
        return this.gainUGen
    }
    set(value) {
        setGain(value)
    }

var BiquadFilter.ufrequency: UGen
    get() {
        return this.frequencyUGen
    }
    set(value) {
        setFrequency(value)
    }

var BiquadFilter.uq: UGen
    get() {
        return this.quGen
    }
    set(value) {
        setQ(value)
    }

var OnePoleFilter.ufreq: UGen
    get() {
        return this.frequencyUGen
    }
    set(value) {
        setFrequency(value)
    }


var TrapezoidWave.ufreq: UGen
    get() {
        return this.frequencyUGen
    }
    set(value) {
        setFrequency(value)
    }

var TrapezoidWave.udutyCycle: UGen
    get() {
        return this.dutyCycleUGen
    }
    set(value) {
        setDutyCycle(value)
    }

var TrapezoidWave.uattack: UGen
    get() {
        return this.attackUGen
    }
    set(value) {
        setAttack(value)
    }

var TrapezoidWave.udecay: UGen
    get() {
        return this.decayUGen
    }
    set(value) {
        setDecay(value)
    }

var Phasor.ufrequency: UGen
    get() {
        return this.frequencyUGen
    }
    set(value) {
        setFrequency(value)
    }