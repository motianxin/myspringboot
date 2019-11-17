/******************************************************************************
 *  Compilation:  javac Draw.java
 *  Execution:    java Draw
 *  Dependencies: none
 *
 *  Drawing library. This class provides a basic capability for creating
 *  drawings with your programs. It uses a simple graphics model that
 *  allows you to create drawings consisting of points, lines, and curves
 *  in a window on your computer and to save the drawings to a file.
 *  This is the object-oriented version of standard draw; it supports
 *  multiple indepedent drawing windows.
 *
 *  Todo
 *  ----
 *    -  Add support for gradient fill, etc.
 *
 *  Remarks
 *  -------
 *    -  don't use AffineTransform for rescaling since it inverts
 *       images and strings
 *    -  careful using setFont in inner loop within an animation -
 *       it can cause flicker
 *
 ******************************************************************************/

package edu.princeton.cs.algs4.base;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * <i>Draw</i>. This class provides a basic capability for
 * creating drawings with your programs. It uses a simple graphics model that
 * allows you to create drawings consisting of points, lines, and curves
 * in a window on your computer and to save the drawings to a file.
 * This is the object-oriented version of standard draw; it supports
 * multiple indepedent drawing windows.
 * <p>
 * For additional documentation, see
 * <a href="https://introcs.cs.princeton.edu/31datatype">Section 3.1</a> of
 * <i>Computer Science: An Interdisciplinary Approach</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public final class Draw implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

    /**
     * The color black.
     */
    public static final Color BLACK = Color.BLACK;

    /**
     * The color blue.
     */
    public static final Color BLUE = Color.BLUE;

    /**
     * The color cyan.
     */
    public static final Color CYAN = Color.CYAN;

    /**
     * The color dark gray.
     */
    public static final Color DARK_GRAY = Color.DARK_GRAY;

    /**
     * The color gray.
     */
    public static final Color GRAY = Color.GRAY;

    /**
     * The color green.
     */
    public static final Color GREEN = Color.GREEN;

    /**
     * The color light gray.
     */
    public static final Color LIGHT_GRAY = Color.LIGHT_GRAY;

    /**
     * The color magenta.
     */
    public static final Color MAGENTA = Color.MAGENTA;

    /**
     * The color orange.
     */
    public static final Color ORANGE = Color.ORANGE;

    /**
     * The color pink.
     */
    public static final Color PINK = Color.PINK;

    /**
     * The color red.
     */
    public static final Color RED = Color.RED;

    /**
     * The color white.
     */
    public static final Color WHITE = Color.WHITE;

    /**
     * The color yellow.
     */
    public static final Color YELLOW = Color.YELLOW;

    /**
     * Shade of blue used in Introduction to Programming in Java.
     * It is Pantone 300U. The RGB values are approximately (9, 90, 166).
     */
    public static final Color BOOK_BLUE = new Color(9, 90, 166);

    /**
     * Shade of light blue used in Introduction to Programming in Java.
     * The RGB values are approximately (103, 198, 243).
     */
    public static final Color BOOK_LIGHT_BLUE = new Color(103, 198, 243);

    /**
     * Shade of red used in <em>Algorithms, 4th edition</em>.
     * It is Pantone 1805U. The RGB values are approximately (150, 35, 31).
     */
    public static final Color BOOK_RED = new Color(150, 35, 31);

    /**
     * Shade of orange used in Princeton's identity.
     * It is PMS 158. The RGB values are approximately (245, 128, 37).
     */
    public static final Color PRINCETON_ORANGE = new Color(245, 128, 37);

    // default colors
    private static final Color DEFAULT_PEN_COLOR = Draw.BLACK;
    private static final Color DEFAULT_CLEAR_COLOR = Draw.WHITE;

    // boundary of drawing canvas, 0% border
    private static final double BORDER = 0.0;
    private static final double DEFAULT_XMIN = 0.0;
    private static final double DEFAULT_XMAX = 1.0;
    private static final double DEFAULT_YMIN = 0.0;
    private static final double DEFAULT_YMAX = 1.0;

    // default canvas size is SIZE-by-SIZE
    private static final int DEFAULT_SIZE = 512;

    // default pen radius
    private static final double DEFAULT_PEN_RADIUS = 0.002;

    // default font
    private static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 16);
    // for synchronization
    private final Object mouseLock = new Object();
    private final Object keyLock = new Object();
    // keyboard state
    private final LinkedList<Character> keysTyped = new LinkedList<Character>();
    private final TreeSet<Integer> keysDown = new TreeSet<Integer>();
    // event-based listeners
    private final ArrayList<DrawListener> listeners = new ArrayList<DrawListener>();
    // current pen color
    private Color penColor;
    // canvas size
    private int width = Draw.DEFAULT_SIZE;
    private int height = Draw.DEFAULT_SIZE;
    // current pen radius
    private double penRadius;
    // show we draw immediately or wait until next show?
    private boolean defer = false;
    private double xmin, ymin, xmax, ymax;
    // name of window
    private String name = "Draw";
    // current font
    private Font font;
    // the JLabel for drawing
    private JLabel draw;
    // double buffered graphics
    private BufferedImage offscreenImage, onscreenImage;
    private Graphics2D offscreen, onscreen;
    // the frame for drawing to the screen
    private JFrame frame = new JFrame();
    // mouse state
    private boolean isMousePressed = false;
    private double mouseX = 0;
    private double mouseY = 0;


    /**
     * Initializes an empty drawing object with the given name.
     *
     * @param name the title of the drawing window.
     */
    public Draw(String name) {
        this.name = name;
        init();
    }

    /**
     * Initializes an empty drawing object.
     */
    public Draw() {
        init();
    }

    /***************************************************************************
     *  User and screen coordinate systems.
     ***************************************************************************/

    // throw an IllegalArgumentException if x is NaN or infinite
    private static void validate(double x, String name) {
        if (Double.isNaN(x)) {
            throw new IllegalArgumentException(name + " is NaN");
        }
        if (Double.isInfinite(x)) {
            throw new IllegalArgumentException(name + " is infinite");
        }
    }

    // throw an IllegalArgumentException if s is null
    private static void validateNonnegative(double x, String name) {
        if (x < 0) {
            throw new IllegalArgumentException(name + " negative");
        }
    }

    // throw an IllegalArgumentException if s is null
    private static void validateNotNull(Object x, String name) {
        if (x == null) {
            throw new IllegalArgumentException(name + " is null");
        }
    }

    /***************************************************************************
     *  Drawing images.
     ***************************************************************************/

    // get an image from the given filename
    private static Image getImage(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }

        // to read from file
        ImageIcon icon = new ImageIcon(filename);

        // try to read from URL
        if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
            try {
                URL url = new URL(filename);
                icon = new ImageIcon(url);
            } catch (MalformedURLException e) {
                /* not a url */
            }
        }

        // in case file is inside a .jar (classpath relative to StdDraw)
        if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
            URL url = StdDraw.class.getResource(filename);
            if (url != null) {
                icon = new ImageIcon(url);
            }
        }

        // in case file is inside a .jar (classpath relative to root of jar)
        if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
            URL url = Draw.class.getResource("/" + filename);
            if (url == null) {
                throw new IllegalArgumentException("image " + filename + " not found");
            }
            icon = new ImageIcon(url);
        }

        return icon.getImage();
    }

    /**
     * Test client.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        // create one drawing window
        Draw draw1 = new Draw("Test client 1");
        draw1.square(0.2, 0.8, 0.1);
        draw1.filledSquare(0.8, 0.8, 0.2);
        draw1.circle(0.8, 0.2, 0.2);
        draw1.setPenColor(Draw.MAGENTA);
        draw1.setPenRadius(0.02);
        draw1.arc(0.8, 0.2, 0.1, 200, 45);


        // create another one
        Draw draw2 = new Draw("Test client 2");
        draw2.setCanvasSize(900, 200);
        // draw a blue diamond
        draw2.setPenRadius();
        draw2.setPenColor(Draw.BLUE);
        double[] x = {0.1, 0.2, 0.3, 0.2};
        double[] y = {0.2, 0.3, 0.2, 0.1};
        draw2.filledPolygon(x, y);

        // text
        draw2.setPenColor(Draw.BLACK);
        draw2.text(0.2, 0.5, "bdfdfdfdlack text");
        draw2.setPenColor(Draw.WHITE);
        draw2.text(0.8, 0.8, "white text");
    }

    private void init() {
        if (this.frame != null) {
            this.frame.setVisible(false);
        }
        this.frame = new JFrame();
        this.offscreenImage = new BufferedImage(2 * this.width, 2 * this.height, BufferedImage.TYPE_INT_ARGB);
        this.onscreenImage = new BufferedImage(2 * this.width, 2 * this.height, BufferedImage.TYPE_INT_ARGB);
        this.offscreen = this.offscreenImage.createGraphics();
        this.onscreen = this.onscreenImage.createGraphics();
        this.offscreen.scale(2.0, 2.0);  // since we made it 2x as big

        setXscale();
        setYscale();
        this.offscreen.setColor(Draw.DEFAULT_CLEAR_COLOR);
        this.offscreen.fillRect(0, 0, this.width, this.height);
        setPenColor();
        setPenRadius();
        setFont();
        clear();

        // add antialiasing
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        this.offscreen.addRenderingHints(hints);

        // frame stuff
        RetinaImageIcon icon = new RetinaImageIcon(this.onscreenImage);
        this.draw = new JLabel(icon);

        this.draw.addMouseListener(this);
        this.draw.addMouseMotionListener(this);

        this.frame.setContentPane(this.draw);
        this.frame.addKeyListener(this);    // JLabel cannot get keyboard focus
        this.frame.setResizable(false);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            // closes all windows
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);      // closes only current window
        this.frame.setFocusTraversalKeysEnabled(false);  // to recognize VK_TAB with isKeyPressed()
        this.frame.setTitle(this.name);
        this.frame.setJMenuBar(createMenuBar());
        this.frame.pack();
        this.frame.requestFocusInWindow();
        this.frame.setVisible(true);
    }

    /**
     * Sets the upper-left hand corner of the drawing window to be (x, y), where (0, 0) is upper left.
     *
     * @param x the number of pixels from the left
     * @param y the number of pixels from the top
     *
     * @throws IllegalArgumentException if the width or height is 0 or negative
     */
    public void setLocationOnScreen(int x, int y) {
        if (x <= 0 || y <= 0) {
            throw new IllegalArgumentException();
        }
        this.frame.setLocation(x, y);
    }

    /**
     * Sets the default close operation.
     *
     * @param value the value, typically {@code JFrame.EXIT_ON_CLOSE}
     * (close all windows) or {@code JFrame.DISPOSE_ON_CLOSE}
     * (close current window)
     */
    public void setDefaultCloseOperation(int value) {
        this.frame.setDefaultCloseOperation(value);
    }

    /**
     * Sets the canvas (drawing area) to be <em>width</em>-by-<em>height</em> pixels.
     * This also erases the current drawing and resets the coordinate system, pen radius,
     * pen color, and font back to their default values.
     * Ordinarly, this method is called once, at the very beginning of a program.
     *
     * @param canvasWidth the width as a number of pixels
     * @param canvasHeight the height as a number of pixels
     *
     * @throws IllegalArgumentException unless both {@code canvasWidth}
     * and {@code canvasHeight} are positive
     */
    public void setCanvasSize(int canvasWidth, int canvasHeight) {
        if (canvasWidth < 1 || canvasHeight < 1) {
            throw new IllegalArgumentException("width and height must be positive");
        }
        this.width = canvasWidth;
        this.height = canvasHeight;
        init();
    }

    // create the menu bar (changed to private)
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem menuItem1 = new JMenuItem(" Save...   ");
        menuItem1.addActionListener(this);
        // Java 10+: replace getMenuShortcutKeyMask() with getMenuShortcutKeyMaskEx()
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menu.add(menuItem1);
        return menuBar;
    }

    /**
     * Sets the x-scale to be the default (between 0.0 and 1.0).
     */
    public void setXscale() {
        setXscale(Draw.DEFAULT_XMIN, Draw.DEFAULT_XMAX);
    }

    /**
     * Sets the y-scale to be the default (between 0.0 and 1.0).
     */
    public void setYscale() {
        setYscale(Draw.DEFAULT_YMIN, Draw.DEFAULT_YMAX);
    }

    /**
     * Sets the x-scale.
     *
     * @param min the minimum value of the x-scale
     * @param max the maximum value of the x-scale
     *
     * @throws IllegalArgumentException if {@code (max == min)}
     * @throws IllegalArgumentException if either {@code min} or {@code max} is either NaN or infinite
     */
    public void setXscale(double min, double max) {
        Draw.validate(min, "min");
        Draw.validate(max, "max");
        double size = max - min;
        if (size == 0.0) {
            throw new IllegalArgumentException("the min and max are the same");
        }
        this.xmin = min - Draw.BORDER * size;
        this.xmax = max + Draw.BORDER * size;
    }

    /**
     * Sets the y-scale.
     *
     * @param min the minimum value of the y-scale
     * @param max the maximum value of the y-scale
     *
     * @throws IllegalArgumentException if {@code (max == min)}
     * @throws IllegalArgumentException if either {@code min} or {@code max} is either NaN or infinite
     */
    public void setYscale(double min, double max) {
        Draw.validate(min, "min");
        Draw.validate(max, "max");
        double size = max - min;
        if (size == 0.0) {
            throw new IllegalArgumentException("the min and max are the same");
        }
        this.ymin = min - Draw.BORDER * size;
        this.ymax = max + Draw.BORDER * size;
    }

    // helper functions that scale from user coordinates to screen coordinates and back
    private double scaleX(double x) {
        return this.width * (x - this.xmin) / (this.xmax - this.xmin);
    }

    private double scaleY(double y) {
        return this.height * (this.ymax - y) / (this.ymax - this.ymin);
    }

    private double factorX(double w) {
        return w * this.width / Math.abs(this.xmax - this.xmin);
    }

    private double factorY(double h) {
        return h * this.height / Math.abs(this.ymax - this.ymin);
    }

    private double userX(double x) {
        return this.xmin + x * (this.xmax - this.xmin) / this.width;
    }

    private double userY(double y) {
        return this.ymax - y * (this.ymax - this.ymin) / this.height;
    }

    /**
     * Clears the screen to the default color (white).
     */
    public void clear() {
        clear(Draw.DEFAULT_CLEAR_COLOR);
    }

    /**
     * Clears the screen to the given color.
     *
     * @param color the color to make the background
     *
     * @throws IllegalArgumentException if {@code color} is {@code null}
     */
    public void clear(Color color) {
        Draw.validateNotNull(color, "color");
        this.offscreen.setColor(color);
        this.offscreen.fillRect(0, 0, this.width, this.height);
        this.offscreen.setColor(this.penColor);
        draw();
    }

    /**
     * Gets the current pen radius.
     *
     * @return the current pen radius
     */
    public double getPenRadius() {
        return this.penRadius;
    }

    /**
     * Sets the radius of the pen to the given size.
     *
     * @param radius the radius of the pen
     *
     * @throws IllegalArgumentException if {@code radius} is negative, NaN, or infinite
     */
    public void setPenRadius(double radius) {
        Draw.validate(radius, "pen radius");
        Draw.validateNonnegative(radius, "pen radius");

        this.penRadius = radius * Draw.DEFAULT_SIZE;
        BasicStroke stroke = new BasicStroke((float) this.penRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        // BasicStroke stroke = new BasicStroke((float) penRadius);
        this.offscreen.setStroke(stroke);
    }

    /**
     * Sets the pen size to the default (.002).
     */
    public void setPenRadius() {
        setPenRadius(Draw.DEFAULT_PEN_RADIUS);
    }

    /**
     * Gets the current pen color.
     *
     * @return the current pen color
     */
    public Color getPenColor() {
        return this.penColor;
    }

    /**
     * Sets the pen color to the given color.
     *
     * @param color the color to make the pen
     *
     * @throws IllegalArgumentException if {@code color} is {@code null}
     */
    public void setPenColor(Color color) {
        Draw.validateNotNull(color, "color");
        this.penColor = color;
        this.offscreen.setColor(this.penColor);
    }

    /**
     * Sets the pen color to the default color (black).
     */
    public void setPenColor() {
        setPenColor(Draw.DEFAULT_PEN_COLOR);
    }

    /**
     * Sets the pen color to the given RGB color.
     *
     * @param red the amount of red (between 0 and 255)
     * @param green the amount of green (between 0 and 255)
     * @param blue the amount of blue (between 0 and 255)
     *
     * @throws IllegalArgumentException if {@code red}, {@code green},
     * or {@code blue} is outside its prescribed range
     */
    public void setPenColor(int red, int green, int blue) {
        if (red < 0 || red >= 256) {
            throw new IllegalArgumentException("red must be between 0 and 255");
        }
        if (green < 0 || green >= 256) {
            throw new IllegalArgumentException("green must be between 0 and 255");
        }
        if (blue < 0 || blue >= 256) {
            throw new IllegalArgumentException("blue must be between 0 and 255");
        }
        setPenColor(new Color(red, green, blue));
    }

    /**
     * Turns on xor mode.
     */
    public void xorOn() {
        this.offscreen.setXORMode(Draw.DEFAULT_CLEAR_COLOR);
    }

    /**
     * Turns off xor mode.
     */
    public void xorOff() {
        this.offscreen.setPaintMode();
    }

    /**
     * Gets the current {@code JLabel} for use in some other GUI.
     *
     * @return the current {@code JLabel}
     */
    public JLabel getJLabel() {
        return this.draw;
    }

    /**
     * Gets the current font.
     *
     * @return the current font
     */
    public Font getFont() {
        return this.font;
    }


    /***************************************************************************
     *  Drawing geometric shapes.
     ***************************************************************************/

    /**
     * Sets the font to the given value.
     *
     * @param font the font
     *
     * @throws IllegalArgumentException if {@code font} is {@code null}
     */
    public void setFont(Font font) {
        Draw.validateNotNull(font, "font");
        this.font = font;
    }

    /**
     * Sets the font to the default font (sans serif, 16 point).
     */
    public void setFont() {
        setFont(Draw.DEFAULT_FONT);
    }

    /**
     * Draws a line from (x0, y0) to (x1, y1).
     *
     * @param x0 the x-coordinate of the starting point
     * @param y0 the y-coordinate of the starting point
     * @param x1 the x-coordinate of the destination point
     * @param y1 the y-coordinate of the destination point
     *
     * @throws IllegalArgumentException if any coordinate is either NaN or infinite
     */
    public void line(double x0, double y0, double x1, double y1) {
        Draw.validate(x0, "x0");
        Draw.validate(y0, "y0");
        Draw.validate(x1, "x1");
        Draw.validate(y1, "y1");
        this.offscreen.draw(new Line2D.Double(scaleX(x0), scaleY(y0), scaleX(x1), scaleY(y1)));
        draw();
    }

    /**
     * Draws one pixel at (x, y).
     *
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     *
     * @throws IllegalArgumentException if {@code x} or {@code y} is either NaN or infinite
     */
    private void pixel(double x, double y) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        this.offscreen.fillRect((int) Math.round(scaleX(x)), (int) Math.round(scaleY(y)), 1, 1);
    }

    /**
     * Draws a point at (x, y).
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     *
     * @throws IllegalArgumentException if either {@code x} or {@code y} is either NaN or infinite
     */
    public void point(double x, double y) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double r = this.penRadius;
        // double ws = factorX(2*r);
        // double hs = factorY(2*r);
        // if (ws <= 1 && hs <= 1) pixel(x, y);
        if (r <= 1) {
            pixel(x, y);
        } else {
            this.offscreen.fill(new Ellipse2D.Double(xs - r / 2, ys - r / 2, r, r));
        }
        draw();
    }

    /**
     * Draws a circle of the specified radius, centered at (<em>x</em>, <em>y</em>).
     *
     * @param x the x-coordinate of the center of the circle
     * @param y the y-coordinate of the center of the circle
     * @param radius the radius of the circle
     *
     * @throws IllegalArgumentException if {@code radius} is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public void circle(double x, double y, double radius) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validate(radius, "radius");
        Draw.validateNonnegative(radius, "radius");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * radius);
        double hs = factorY(2 * radius);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            this.offscreen.draw(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();
    }

    /**
     * Draws a filled circle of the specified radius, centered at (<em>x</em>, <em>y</em>).
     *
     * @param x the x-coordinate of the center of the circle
     * @param y the y-coordinate of the center of the circle
     *
     * @throws IllegalArgumentException if {@code radius} is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public void filledCircle(double x, double y, double radius) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validate(radius, "radius");
        Draw.validateNonnegative(radius, "radius");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * radius);
        double hs = factorY(2 * radius);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            this.offscreen.fill(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();
    }

    /**
     * Draws an ellipse with the specified semimajor and semiminor axes,
     * centered at (<em>x</em>, <em>y</em>).
     *
     * @param x the <em>x</em>-coordinate of the center of the ellipse
     * @param y the <em>y</em>-coordinate of the center of the ellipse
     * @param semiMajorAxis is the semimajor axis of the ellipse
     * @param semiMinorAxis is the semiminor axis of the ellipse
     *
     * @throws IllegalArgumentException if either {@code semiMajorAxis}
     * or {@code semiMinorAxis} is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public void ellipse(double x, double y, double semiMajorAxis, double semiMinorAxis) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validate(semiMajorAxis, "semimajor axis");
        Draw.validate(semiMinorAxis, "semiminor axis");
        Draw.validateNonnegative(semiMajorAxis, "semimajor axis");
        Draw.validateNonnegative(semiMinorAxis, "semiminor axis");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * semiMajorAxis);
        double hs = factorY(2 * semiMinorAxis);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            this.offscreen.draw(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();
    }

    /**
     * Draws a filled ellipse with the specified semimajor and semiminor axes,
     * centered at (<em>x</em>, <em>y</em>).
     *
     * @param x the <em>x</em>-coordinate of the center of the ellipse
     * @param y the <em>y</em>-coordinate of the center of the ellipse
     * @param semiMajorAxis is the semimajor axis of the ellipse
     * @param semiMinorAxis is the semiminor axis of the ellipse
     *
     * @throws IllegalArgumentException if either {@code semiMajorAxis}
     * or {@code semiMinorAxis} is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public void filledEllipse(double x, double y, double semiMajorAxis, double semiMinorAxis) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validate(semiMajorAxis, "semimajor axis");
        Draw.validate(semiMinorAxis, "semiminor axis");
        Draw.validateNonnegative(semiMajorAxis, "semimajor axis");
        Draw.validateNonnegative(semiMinorAxis, "semiminor axis");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * semiMajorAxis);
        double hs = factorY(2 * semiMinorAxis);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            this.offscreen.fill(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();
    }

    /**
     * Draws a circular arc of the specified radius,
     * centered at (<em>x</em>, <em>y</em>), from angle1 to angle2 (in degrees).
     *
     * @param x the <em>x</em>-coordinate of the center of the circle
     * @param y the <em>y</em>-coordinate of the center of the circle
     * @param radius the radius of the circle
     * @param angle1 the starting angle. 0 would mean an arc beginning at 3 o'clock.
     * @param angle2 the angle at the end of the arc. For example, if
     * you want a 90 degree arc, then angle2 should be angle1 + 90.
     *
     * @throws IllegalArgumentException if {@code radius} is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public void arc(double x, double y, double radius, double angle1, double angle2) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validate(radius, "arc radius");
        Draw.validate(angle1, "angle1");
        Draw.validate(angle2, "angle2");
        Draw.validateNonnegative(radius, "arc radius");

        while (angle2 < angle1) {
            angle2 += 360;
        }
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * radius);
        double hs = factorY(2 * radius);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            this.offscreen.draw(new Arc2D.Double(xs - ws / 2, ys - hs / 2, ws, hs, angle1, angle2 - angle1, Arc2D.OPEN));
        }
        draw();
    }

    /**
     * Draws a square of the specified size, centered at (<em>x</em>, <em>y</em>).
     *
     * @param x the <em>x</em>-coordinate of the center of the square
     * @param y the <em>y</em>-coordinate of the center of the square
     * @param halfLength one half the length of any side of the square
     *
     * @throws IllegalArgumentException if {@code halfLength} is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public void square(double x, double y, double halfLength) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validate(halfLength, "halfLength");
        Draw.validateNonnegative(halfLength, "half length");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * halfLength);
        double hs = factorY(2 * halfLength);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            this.offscreen.draw(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();
    }

    /**
     * Draws a square of the specified size, centered at (<em>x</em>, <em>y</em>).
     *
     * @param x the <em>x</em>-coordinate of the center of the square
     * @param y the <em>y</em>-coordinate of the center of the square
     * @param halfLength one half the length of any side of the square
     *
     * @throws IllegalArgumentException if {@code halfLength} is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public void filledSquare(double x, double y, double halfLength) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validate(halfLength, "halfLength");
        Draw.validateNonnegative(halfLength, "half length");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * halfLength);
        double hs = factorY(2 * halfLength);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            this.offscreen.fill(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();
    }

    /**
     * Draws a rectangle of the specified size, centered at (<em>x</em>, <em>y</em>).
     *
     * @param x the <em>x</em>-coordinate of the center of the rectangle
     * @param y the <em>y</em>-coordinate of the center of the rectangle
     * @param halfWidth one half the width of the rectangle
     * @param halfHeight one half the height of the rectangle
     *
     * @throws IllegalArgumentException if either {@code halfWidth} or {@code halfHeight} is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public void rectangle(double x, double y, double halfWidth, double halfHeight) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validate(halfWidth, "halfWidth");
        Draw.validate(halfHeight, "halfHeight");
        Draw.validateNonnegative(halfWidth, "half width");
        Draw.validateNonnegative(halfHeight, "half height");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * halfWidth);
        double hs = factorY(2 * halfHeight);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            this.offscreen.draw(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();
    }

    /**
     * Draws a filled rectangle of the specified size, centered at (<em>x</em>, <em>y</em>).
     *
     * @param x the <em>x</em>-coordinate of the center of the rectangle
     * @param y the <em>y</em>-coordinate of the center of the rectangle
     * @param halfWidth one half the width of the rectangle
     * @param halfHeight one half the height of the rectangle
     *
     * @throws IllegalArgumentException if either {@code halfWidth} or {@code halfHeight} is negative
     * @throws IllegalArgumentException if any argument is either NaN or infinite
     */
    public void filledRectangle(double x, double y, double halfWidth, double halfHeight) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validate(halfWidth, "halfWidth");
        Draw.validate(halfHeight, "halfHeight");
        Draw.validateNonnegative(halfWidth, "half width");
        Draw.validateNonnegative(halfHeight, "half height");

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * halfWidth);
        double hs = factorY(2 * halfHeight);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            this.offscreen.fill(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();
    }

    /**
     * Draws a polygon with the vertices
     * (<em>x</em><sub>0</sub>, <em>y</em><sub>0</sub>),
     * (<em>x</em><sub>1</sub>, <em>y</em><sub>1</sub>), ...,
     * (<em>x</em><sub><em>n</em>–1</sub>, <em>y</em><sub><em>n</em>–1</sub>).
     *
     * @param x an array of all the <em>x</em>-coordinates of the polygon
     * @param y an array of all the <em>y</em>-coordinates of the polygon
     *
     * @throws IllegalArgumentException unless {@code x[]} and {@code y[]}
     * are of the same length
     * @throws IllegalArgumentException if any coordinate is either NaN or infinite
     * @throws IllegalArgumentException if either {@code x[]} or {@code y[]} is {@code null}
     */
    public void polygon(double[] x, double[] y) {
        Draw.validateNotNull(x, "x-coordinate array");
        Draw.validateNotNull(y, "y-coordinate array");
        for (int i = 0; i < x.length; i++) {
            Draw.validate(x[i], "x[" + i + "]");
        }
        for (int i = 0; i < y.length; i++) {
            Draw.validate(y[i], "y[" + i + "]");
        }

        int n1 = x.length;
        int n2 = y.length;
        if (n1 != n2) {
            throw new IllegalArgumentException("arrays must be of the same length");
        }
        int n = n1;
        if (n == 0) {
            return;
        }

        GeneralPath path = new GeneralPath();
        path.moveTo((float) scaleX(x[0]), (float) scaleY(y[0]));
        for (int i = 0; i < n; i++) {
            path.lineTo((float) scaleX(x[i]), (float) scaleY(y[i]));
        }
        path.closePath();
        this.offscreen.draw(path);
        draw();
    }

    /**
     * Draws a filled polygon with the vertices
     * (<em>x</em><sub>0</sub>, <em>y</em><sub>0</sub>),
     * (<em>x</em><sub>1</sub>, <em>y</em><sub>1</sub>), ...,
     * (<em>x</em><sub><em>n</em>–1</sub>, <em>y</em><sub><em>n</em>–1</sub>).
     *
     * @param x an array of all the <em>x</em>-coordinates of the polygon
     * @param y an array of all the <em>y</em>-coordinates of the polygon
     *
     * @throws IllegalArgumentException unless {@code x[]} and {@code y[]}
     * are of the same length
     * @throws IllegalArgumentException if any coordinate is either NaN or infinite
     * @throws IllegalArgumentException if either {@code x[]} or {@code y[]} is {@code null}
     */
    public void filledPolygon(double[] x, double[] y) {
        Draw.validateNotNull(x, "x-coordinate array");
        Draw.validateNotNull(y, "y-coordinate array");
        for (int i = 0; i < x.length; i++) {
            Draw.validate(x[i], "x[" + i + "]");
        }
        for (int i = 0; i < y.length; i++) {
            Draw.validate(y[i], "y[" + i + "]");
        }

        int n1 = x.length;
        int n2 = y.length;
        if (n1 != n2) {
            throw new IllegalArgumentException("arrays must be of the same length");
        }
        int n = n1;
        if (n == 0) {
            return;
        }

        GeneralPath path = new GeneralPath();
        path.moveTo((float) scaleX(x[0]), (float) scaleY(y[0]));
        for (int i = 0; i < n; i++) {
            path.lineTo((float) scaleX(x[i]), (float) scaleY(y[i]));
        }
        path.closePath();
        this.offscreen.fill(path);
        draw();
    }

    /**
     * Draws the specified image centered at (<em>x</em>, <em>y</em>).
     * The supported image formats are JPEG, PNG, and GIF.
     * As an optimization, the picture is cached, so there is no performance
     * penalty for redrawing the same image multiple times (e.g., in an animation).
     * However, if you change the picture file after drawing it, subsequent
     * calls will draw the original picture.
     *
     * @param x the center <em>x</em>-coordinate of the image
     * @param y the center <em>y</em>-coordinate of the image
     * @param filename the name of the image/picture, e.g., "ball.gif"
     *
     * @throws IllegalArgumentException if the image filename is invalid
     * @throws IllegalArgumentException if either {@code x} or {@code y} is either NaN or infinite
     */
    public void picture(double x, double y, String filename) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validateNotNull(filename, "filename");

        Image image = Draw.getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = image.getWidth(null);
        int hs = image.getHeight(null);
        if (ws < 0 || hs < 0) {
            throw new IllegalArgumentException("image " + filename + " is corrupt");
        }

        this.offscreen.drawImage(image, (int) Math.round(xs - ws / 2.0), (int) Math.round(ys - hs / 2.0), null);
        draw();
    }

    /**
     * Draws the specified image centered at (<em>x</em>, <em>y</em>),
     * rotated given number of degrees.
     * The supported image formats are JPEG, PNG, and GIF.
     *
     * @param x the center <em>x</em>-coordinate of the image
     * @param y the center <em>y</em>-coordinate of the image
     * @param filename the name of the image/picture, e.g., "ball.gif"
     * @param degrees is the number of degrees to rotate counterclockwise
     *
     * @throws IllegalArgumentException if the image filename is invalid
     * @throws IllegalArgumentException if {@code x}, {@code y}, {@code degrees} is NaN or infinite
     * @throws IllegalArgumentException if {@code filename} is {@code null}
     */
    public void picture(double x, double y, String filename, double degrees) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validate(degrees, "degrees");
        Draw.validateNotNull(filename, "filename");

        Image image = Draw.getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = image.getWidth(null);
        int hs = image.getHeight(null);
        if (ws < 0 || hs < 0) {
            throw new IllegalArgumentException("image " + filename + " is corrupt");
        }

        this.offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        this.offscreen.drawImage(image, (int) Math.round(xs - ws / 2.0), (int) Math.round(ys - hs / 2.0), null);
        this.offscreen.rotate(Math.toRadians(+degrees), xs, ys);

        draw();
    }

    /**
     * Draws the specified image centered at (<em>x</em>, <em>y</em>),
     * rescaled to the specified bounding box.
     * The supported image formats are JPEG, PNG, and GIF.
     *
     * @param x the center <em>x</em>-coordinate of the image
     * @param y the center <em>y</em>-coordinate of the image
     * @param filename the name of the image/picture, e.g., "ball.gif"
     * @param scaledWidth the width of the scaled image (in screen coordinates)
     * @param scaledHeight the height of the scaled image (in screen coordinates)
     *
     * @throws IllegalArgumentException if either {@code scaledWidth}
     * or {@code scaledHeight} is negative
     * @throws IllegalArgumentException if the image filename is invalid
     * @throws IllegalArgumentException if {@code x} or {@code y} is either NaN or infinite
     * @throws IllegalArgumentException if {@code filename} is {@code null}
     */
    public void picture(double x, double y, String filename, double scaledWidth, double scaledHeight) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validate(scaledWidth, "scaled width");
        Draw.validate(scaledHeight, "scaled height");
        Draw.validateNotNull(filename, "filename");
        Draw.validateNonnegative(scaledWidth, "scaled width");
        Draw.validateNonnegative(scaledHeight, "scaled height");

        Image image = Draw.getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(scaledWidth);
        double hs = factorY(scaledHeight);
        if (ws < 0 || hs < 0) {
            throw new IllegalArgumentException("image " + filename + " is corrupt");
        }
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            this.offscreen.drawImage(image, (int) Math.round(xs - ws / 2.0), (int) Math.round(ys - hs / 2.0),
                    (int) Math.round(ws), (int) Math.round(hs), null);
        }
        draw();
    }


    /***************************************************************************
     *  Drawing text.
     ***************************************************************************/

    /**
     * Draws the specified image centered at (<em>x</em>, <em>y</em>), rotated
     * given number of degrees, and rescaled to the specified bounding box.
     * The supported image formats are JPEG, PNG, and GIF.
     *
     * @param x the center <em>x</em>-coordinate of the image
     * @param y the center <em>y</em>-coordinate of the image
     * @param filename the name of the image/picture, e.g., "ball.gif"
     * @param scaledWidth the width of the scaled image (in screen coordinates)
     * @param scaledHeight the height of the scaled image (in screen coordinates)
     * @param degrees is the number of degrees to rotate counterclockwise
     *
     * @throws IllegalArgumentException if either {@code scaledWidth}
     * or {@code scaledHeight} is negative
     * @throws IllegalArgumentException if the image filename is invalid
     */
    public void picture(double x, double y, String filename, double scaledWidth, double scaledHeight, double degrees) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validate(scaledWidth, "scaled width");
        Draw.validate(scaledHeight, "scaled height");
        Draw.validate(degrees, "degrees");
        Draw.validateNotNull(filename, "filename");
        Draw.validateNonnegative(scaledWidth, "scaled width");
        Draw.validateNonnegative(scaledHeight, "scaled height");

        Image image = Draw.getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(scaledWidth);
        double hs = factorY(scaledHeight);
        if (ws < 0 || hs < 0) {
            throw new IllegalArgumentException("image " + filename + " is corrupt");
        }
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        }

        this.offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        this.offscreen.drawImage(image, (int) Math.round(xs - ws / 2.0), (int) Math.round(ys - hs / 2.0),
                (int) Math.round(ws), (int) Math.round(hs), null);
        this.offscreen.rotate(Math.toRadians(+degrees), xs, ys);

        draw();
    }

    /**
     * Writes the given text string in the current font, centered at (<em>x</em>, <em>y</em>).
     *
     * @param x the center <em>x</em>-coordinate of the text
     * @param y the center <em>y</em>-coordinate of the text
     * @param text the text to write
     *
     * @throws IllegalArgumentException if {@code text} is {@code null}
     * @throws IllegalArgumentException if {@code x} or {@code y} is either NaN or infinite
     */
    public void text(double x, double y, String text) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validateNotNull(text, "text");

        this.offscreen.setFont(this.font);
        FontMetrics metrics = this.offscreen.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = metrics.stringWidth(text);
        int hs = metrics.getDescent();
        this.offscreen.drawString(text, (float) (xs - ws / 2.0), (float) (ys + hs));
        draw();
    }

    /**
     * Writes the given text string in the current font, centered at (<em>x</em>, <em>y</em>) and
     * rotated by the specified number of degrees.
     *
     * @param x the center <em>x</em>-coordinate of the text
     * @param y the center <em>y</em>-coordinate of the text
     * @param text the text to write
     * @param degrees is the number of degrees to rotate counterclockwise
     *
     * @throws IllegalArgumentException if {@code text} is {@code null}
     * @throws IllegalArgumentException if {@code x}, {@code y}, or {@code degrees} is either NaN or infinite
     */
    public void text(double x, double y, String text, double degrees) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validate(degrees, "degrees");
        Draw.validateNotNull(text, "text");

        double xs = scaleX(x);
        double ys = scaleY(y);
        this.offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        text(x, y, text);
        this.offscreen.rotate(Math.toRadians(+degrees), xs, ys);
    }

    /**
     * Writes the given text string in the current font, left-aligned at (<em>x</em>, <em>y</em>).
     *
     * @param x the <em>x</em>-coordinate of the text
     * @param y the <em>y</em>-coordinate of the text
     * @param text the text
     *
     * @throws IllegalArgumentException if {@code text} is {@code null}
     * @throws IllegalArgumentException if {@code x} or {@code y} is either NaN or infinite
     */
    public void textLeft(double x, double y, String text) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validateNotNull(text, "text");

        this.offscreen.setFont(this.font);
        FontMetrics metrics = this.offscreen.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);
        // int ws = metrics.stringWidth(text);
        int hs = metrics.getDescent();
        this.offscreen.drawString(text, (float) xs, (float) (ys + hs));
        draw();
    }

    /**
     * Writes the given text string in the current font, right-aligned at (<em>x</em>, <em>y</em>).
     *
     * @param x the <em>x</em>-coordinate of the text
     * @param y the <em>y</em>-coordinate of the text
     * @param text the text to write
     *
     * @throws IllegalArgumentException if {@code text} is {@code null}
     * @throws IllegalArgumentException if {@code x} or {@code y} is either NaN or infinite
     */
    public void textRight(double x, double y, String text) {
        Draw.validate(x, "x");
        Draw.validate(y, "y");
        Draw.validateNotNull(text, "text");

        this.offscreen.setFont(this.font);
        FontMetrics metrics = this.offscreen.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = metrics.stringWidth(text);
        int hs = metrics.getDescent();
        this.offscreen.drawString(text, (float) (xs - ws), (float) (ys + hs));
        draw();
    }

    /**
     * Copies the offscreen buffer to the onscreen buffer, pauses for t milliseconds
     * and enables double buffering.
     *
     * @param t number of milliseconds
     *
     * @deprecated replaced by {@link #enableDoubleBuffering()}, {@link #show()}, and {@link #pause(int t)}
     */
    @Deprecated
    public void show(int t) {
        show();
        pause(t);
        enableDoubleBuffering();
    }

    /**
     * Pause for t milliseconds. This method is intended to support computer animations.
     *
     * @param t number of milliseconds
     */
    public void pause(int t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            System.out.println("Error sleeping");
        }
    }

    /**
     * Copies offscreen buffer to onscreen buffer. There is no reason to call
     * this method unless double buffering is enabled.
     */
    public void show() {
        this.onscreen.drawImage(this.offscreenImage, 0, 0, null);
        this.frame.repaint();
    }

    // draw onscreen if defer is false
    private void draw() {
        if (!this.defer) {
            show();
        }
    }

    /**
     * Enable double buffering. All subsequent calls to
     * drawing methods such as {@code line()}, {@code circle()},
     * and {@code square()} will be deferred until the next call
     * to show(). Useful for animations.
     */
    public void enableDoubleBuffering() {
        this.defer = true;
    }

    /**
     * Disable double buffering. All subsequent calls to
     * drawing methods such as {@code line()}, {@code circle()},
     * and {@code square()} will be displayed on screen when called.
     * This is the default.
     */
    public void disableDoubleBuffering() {
        this.defer = false;
    }

    /**
     * Saves the drawing to using the specified filename.
     * The supported image formats are JPEG and PNG;
     * the filename suffix must be {@code .jpg} or {@code .png}.
     *
     * @param filename the name of the file with one of the required suffixes
     *
     * @throws IllegalArgumentException if {@code filename} is {@code null}
     */
    public void save(String filename) {
        Draw.validateNotNull(filename, "filename");
        File file = new File(filename);
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);

        // png files
        if ("png".equalsIgnoreCase(suffix)) {
            try {
                ImageIO.write(this.offscreenImage, suffix, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // need to change from ARGB to RGB for jpeg
        // reference: http://archives.java.sun.com/cgi-bin/wa?A2=ind0404&L=java2d-interest&D=0&P=2727
        else if ("jpg".equalsIgnoreCase(suffix)) {
            WritableRaster raster = this.offscreenImage.getRaster();
            WritableRaster newRaster;
            newRaster = raster.createWritableChild(0, 0, this.width, this.height, 0, 0, new int[]{0, 1, 2});
            DirectColorModel cm = (DirectColorModel) this.offscreenImage.getColorModel();
            DirectColorModel newCM = new DirectColorModel(cm.getPixelSize(), cm.getRedMask(), cm.getGreenMask(),
                    cm.getBlueMask());
            BufferedImage rgbBuffer = new BufferedImage(newCM, newRaster, false, null);
            try {
                ImageIO.write(rgbBuffer, suffix, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid image file type: " + suffix);
        }
    }


    /***************************************************************************
     *  Event-based interactions.
     ***************************************************************************/

    /**
     * This method cannot be called directly.
     */
    public void actionPerformed(ActionEvent e) {
        FileDialog chooser = new FileDialog(this.frame, "Use a .png or .jpg extension", FileDialog.SAVE);
        chooser.setVisible(true);
        String filename = chooser.getFile();
        if (filename != null) {
            save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }


    /***************************************************************************
     *  Mouse interactions.
     ***************************************************************************/

    /**
     * Adds a {@link DrawListener} to listen to keyboard and mouse events.
     *
     * @param listener the {\tt DrawListener} argument
     */
    public void addListener(DrawListener listener) {
        // ensure there is a window for listenting to events
        show();
        this.listeners.add(listener);
        this.frame.addKeyListener(this);
        this.frame.addMouseListener(this);
        this.frame.addMouseMotionListener(this);
        this.frame.setFocusable(true);
    }

    /**
     * Returns true if the mouse is being pressed.
     *
     * @return {@code true} if the mouse is being pressed;
     * {@code false} otherwise
     */
    public boolean isMousePressed() {
        synchronized (this.mouseLock) {
            return this.isMousePressed;
        }
    }

    /**
     * Returns true if the mouse is being pressed.
     *
     * @return {@code true} if the mouse is being pressed;
     * {@code false} otherwise
     *
     * @deprecated replaced by {@link #isMousePressed()}
     */
    @Deprecated
    public boolean mousePressed() {
        synchronized (this.mouseLock) {
            return this.isMousePressed;
        }
    }

    /**
     * Returns the x-coordinate of the mouse.
     *
     * @return the x-coordinate of the mouse
     */
    public double mouseX() {
        synchronized (this.mouseLock) {
            return this.mouseX;
        }
    }

    /**
     * Returns the y-coordinate of the mouse.
     *
     * @return the y-coordinate of the mouse
     */
    public double mouseY() {
        synchronized (this.mouseLock) {
            return this.mouseY;
        }
    }

    /**
     * This method cannot be called directly.
     */
    public void mouseEntered(MouseEvent e) {
        // this body is intentionally left empty
    }

    /**
     * This method cannot be called directly.
     */
    public void mouseExited(MouseEvent e) {
        // this body is intentionally left empty
    }

    /**
     * This method cannot be called directly.
     */
    public void mousePressed(MouseEvent e) {
        synchronized (this.mouseLock) {
            this.mouseX = userX(e.getX());
            this.mouseY = userY(e.getY());
            this.isMousePressed = true;
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (DrawListener listener : this.listeners) {
                listener.mousePressed(userX(e.getX()), userY(e.getY()));
            }
        }

    }

    /**
     * This method cannot be called directly.
     */
    public void mouseReleased(MouseEvent e) {
        synchronized (this.mouseLock) {
            this.isMousePressed = false;
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (DrawListener listener : this.listeners) {
                listener.mouseReleased(userX(e.getX()), userY(e.getY()));
            }
        }
    }

    /**
     * This method cannot be called directly.
     */
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (DrawListener listener : this.listeners) {
                listener.mouseClicked(userX(e.getX()), userY(e.getY()));
            }
        }
    }

    /**
     * This method cannot be called directly.
     */
    public void mouseDragged(MouseEvent e) {
        synchronized (this.mouseLock) {
            this.mouseX = userX(e.getX());
            this.mouseY = userY(e.getY());
        }
        // doesn't seem to work if a button is specified
        for (DrawListener listener : this.listeners) {
            listener.mouseDragged(userX(e.getX()), userY(e.getY()));
        }
    }


    /***************************************************************************
     *  Keyboard interactions.
     ***************************************************************************/

    /**
     * This method cannot be called directly.
     */
    public void mouseMoved(MouseEvent e) {
        synchronized (this.mouseLock) {
            this.mouseX = userX(e.getX());
            this.mouseY = userY(e.getY());
        }
    }

    /**
     * Returns true if the user has typed a key.
     *
     * @return {@code true} if the user has typed a key; {@code false} otherwise
     */
    public boolean hasNextKeyTyped() {
        synchronized (this.keyLock) {
            return !this.keysTyped.isEmpty();
        }
    }

    /**
     * The next key typed by the user.
     *
     * @return the next key typed by the user
     */
    public char nextKeyTyped() {
        synchronized (this.keyLock) {
            return this.keysTyped.removeLast();
        }
    }

    /**
     * Returns true if the keycode is being pressed.
     * <p>
     * This method takes as an argument the keycode (corresponding to a physical key).
     * It can handle action keys (such as F1 and arrow keys) and modifier keys
     * (such as shift and control).
     * See {@link KeyEvent} for a description of key codes.
     *
     * @param keycode the keycode to check
     *
     * @return {@code true} if {@code keycode} is currently being pressed;
     * {@code false} otherwise
     */
    public boolean isKeyPressed(int keycode) {
        synchronized (this.keyLock) {
            return this.keysDown.contains(keycode);
        }
    }

    /**
     * This method cannot be called directly.
     */
    public void keyTyped(KeyEvent e) {
        synchronized (this.keyLock) {
            this.keysTyped.addFirst(e.getKeyChar());
        }

        // notify all listeners
        for (DrawListener listener : this.listeners) {
            listener.keyTyped(e.getKeyChar());
        }
    }

    /**
     * This method cannot be called directly.
     */
    public void keyPressed(KeyEvent e) {
        synchronized (this.keyLock) {
            this.keysDown.add(e.getKeyCode());
        }

        // notify all listeners
        for (DrawListener listener : this.listeners) {
            listener.keyPressed(e.getKeyCode());
        }
    }

    /**
     * This method cannot be called directly.
     */
    public void keyReleased(KeyEvent e) {
        synchronized (this.keyLock) {
            this.keysDown.remove(e.getKeyCode());
        }

        // notify all listeners
        for (DrawListener listener : this.listeners) {
            listener.keyPressed(e.getKeyCode());
        }
    }

    /***************************************************************************
     *  For improved resolution on Mac Retina displays.
     ***************************************************************************/

    private static class RetinaImageIcon extends ImageIcon {

        private static final long serialVersionUID = -8197939038901939363L;

        public RetinaImageIcon(Image image) {
            super(image);
        }

        @Override
        public int getIconWidth() {
            return super.getIconWidth() / 2;
        }

        /**
         * Gets the height of the icon.
         *
         * @return the height in pixels of this icon
         */
        @Override
        public int getIconHeight() {
            return super.getIconHeight() / 2;
        }

        @Override
        public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.scale(0.5, 0.5);
            super.paintIcon(c, g2, x * 2, y * 2);
            g2.dispose();
        }
    }

}

/******************************************************************************
 *  Copyright 2002-2018, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
