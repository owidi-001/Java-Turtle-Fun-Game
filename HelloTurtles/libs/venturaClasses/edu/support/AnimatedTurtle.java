package edu.support;

/**
 * DO NOT MAKE CHANGES TO THIS FILE!
 */

import edu.gatech.mediacomp.ModelDisplay;
import edu.gatech.mediacomp.Turtle;

/**
 * A version of the Turtle class that slows it
 * down so that it can be seen creating drawings.
 *
 * @author Phil Ventura, Ph.D. <pventura@usf.edu>
 */
public class AnimatedTurtle extends Turtle {

    /**
     * The default value for the delay.
     */
    public static final int DEFAULT_DELAY = 50;

    /**
     * The number of milliseconds to pause
     * each time the Turtle is drawn.
     */
    private int drawDelay;

    /**
     * Creates a new AnimatedTurtle.
     */
    public AnimatedTurtle(ModelDisplay m) {
        super(m);
        setDelay(DEFAULT_DELAY);
    } // end constructor

    /**
     * Sets the number of milliseconds to pause
     * each time the turtle is drawn.
     * @param ms the number of milliseconds to pause
     *           each time the turtle is drawn.
     */
    public void setDelay(int ms) {
        drawDelay = Math.max(ms, 0);
    } // end setDelay

    /**
     * Gets the number of milliseconds paused
     * each time the turtle is drawn.
     *
     * @return the number of milliseconds paused
     * each time the turtle is drawn.
     */
    public int getDelay() {
        return drawDelay;
    } // end getDelay

    @Override
    public void updateDisplay() {
        super.updateDisplay();
        try {
            Thread.sleep(drawDelay);
        } catch (InterruptedException e) {
            // do nothing
        }
    } // end updateDisplay

} // end class AnimatedTurtle
