package edu.support;

/**
 * DO NOT MAKE CHANGES TO THIS FILE!
 */

import edu.gatech.mediacomp.World;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.List;

/**
 * A version of the World class that ends the program
 * when the window is closed.
 *
 * @author Phil Ventura, Ph.D. <pventura@usf.edu>
 */
public class EndWorld extends World {
    /**
     * The most amount of time to pause in
     * between turtle drawing.
     */
    private static final int SLOWEST = 100;

    /**
     * The slider.
     */
    private JSlider slider;

    /**
     * Creates a World.
     */
    public EndWorld() {
        initWorld();
    }

    /**
     * Creates a World of the specified size.
     * @param width the width.
     * @param height the height.
     */
    public EndWorld(int width, int height) {
        super(width, height);
        initWorld();
    }

    /**
     * Helper method for setup and GUI (re)layout.
     */
    private void initWorld() {
        JFrame frame = getFrame();
        // kill program when JFrame closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // remove this world from the content pane
        frame.getContentPane().remove(this);

        // now use BorderLayout instead and
        // add this object back this time in the CENTER
        // and add thE slider GUI
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(makeSliderGUI(), BorderLayout.NORTH);
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.pack();
    }

    /**
     * Returns the JFrame that the World is displayed in.
     * @return the JFrame that the World is displayed in.
     */
    public JFrame getFrame() {
        // Using reflection so we don't need to modify
        // the original World code

        // based on code taken from
        // http://tutorials.jenkov.com/java-reflection/private-fields-and-methods.html,
        // last access Feb 4, 2017
        try {
            Field theFrame = null;

            // NOTE: originally I had getClass() instead of
            //       World.class which does NOT work
            theFrame = World.class.getDeclaredField("frame");

            theFrame.setAccessible(true);
            return (JFrame) theFrame.get(this);
        } catch (NoSuchFieldException e) {
            System.err.println("Frame field in World does not exist! Returning null");
        } catch (IllegalAccessException e) {
            System.err.println("Could not access frame field of World, returning null");
        }
        return null;  // fail
    } // end getFrame()

    /**
     * Creates and returns the slider GUI.
     * @return the slider GUI.
     */
    private JComponent makeSliderGUI() {
        Box b = new Box(BoxLayout.Y_AXIS);
        b.add(new JLabel("Turtle Speed", JLabel.CENTER));
        slider = new JSlider(0, SLOWEST, AnimatedTurtle.DEFAULT_DELAY);
        slider.setMajorTickSpacing(SLOWEST / 10);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setInverted(true);

        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(0, new JLabel("Fastest"));
        labels.put(SLOWEST, new JLabel("Slowest"));
        slider.setLabelTable(labels);
        slider.addChangeListener(evt -> {
            // first make sure we are done adjusting
            if (!slider.getValueIsAdjusting()) {
                List<AnimatedTurtle> turtles = this.getTurtleList();
                for (AnimatedTurtle t : turtles) {
                    t.setDelay(slider.getValue());
                }
            }
        });
        b.add(slider);
        return b;
    }
} // end class EndWorld
