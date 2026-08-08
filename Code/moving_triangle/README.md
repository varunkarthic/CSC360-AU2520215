# Moving Triangle

A small Java Swing project that demonstrates two basic forms of interactive 2D graphics using the mouse:

1. **FollowCursor** — a red equilateral triangle follows the cursor around the window.
2. **Zoom (Triangle Zoom)** — a green equilateral triangle remains fixed at the centre of the window while its size changes based only on the cursor's vertical (`Y`) position.

The project uses only the standard Java Swing and AWT libraries and does not require any external dependencies.

---

## Project Structure

```text
moving-triangle/
├── README.md
├── src/
│   ├── FollowCursor.java
│   └── Zoom.java
└── media/
    ├── FollowCursor.mp4
    └── TriangleZoom.mp4
```

---

## Development Environment

This project was built and tested using:

```text
openjdk 26.0.2 2026-07-21
OpenJDK Runtime Environment Homebrew (build 26.0.2)
OpenJDK 64-Bit Server VM Homebrew (build 26.0.2, mixed mode, sharing)
```

No third-party Java libraries are required.

---

## Build and Run

Open a terminal and move into the `src` directory:

```bash
cd src
```

### FollowCursor

Compile:

```bash
javac FollowCursor.java
```

Run:

```bash
java FollowCursor
```

### Zoom

Compile:

```bash
javac Zoom.java
```

Run:

```bash
java Zoom
```

### Compile Both Modules Together

```bash
javac FollowCursor.java Zoom.java
```

Then run either module independently:

```bash
java FollowCursor
```

or:

```bash
java Zoom
```

> `javac` is given the source filename, such as `FollowCursor.java`, while `java` is given the class name without the `.class` extension.

---

# Module 1 — FollowCursor

`FollowCursor.java` creates a red equilateral triangle with a black outline. The triangle follows the mouse cursor around the window, with the cursor positioned at the triangle's centroid.

## Demonstration

[View the FollowCursor demonstration video](./media/FollowCursor.mp4)

---

## 1. Imports

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
```

These imports provide the classes needed for the graphical interface and mouse input.

- `javax.swing.*` provides Swing components such as `JFrame` and `JPanel`.
- `java.awt.*` provides graphics-related classes such as `Graphics`, `Graphics2D`, `Color`, `BasicStroke`, and `RenderingHints`.
- `java.awt.event.*` provides event-handling classes such as `MouseEvent` and `MouseMotionAdapter`.

---

## 2. Creating the Drawing Panel

```java
public class FollowCursor extends JPanel {
```

`FollowCursor` extends `JPanel`.

A `JPanel` is a rectangular Swing component that can be placed inside a window. By extending it, the program can override its default drawing behaviour and render custom graphics.

The structure is approximately:

```text
JFrame
└── FollowCursor (JPanel)
    └── Triangle drawn using Graphics2D
```

---

## 3. Storing the Cursor Position and Triangle Size

```java
private int mouseX = 400;
private int mouseY = 300;

private final int SIDE = 100;
```

`mouseX` and `mouseY` store the latest known cursor coordinates.

The initial values are:

```text
X = 400
Y = 300
```

which approximately correspond to the centre of an `800 × 600` window.

`SIDE` stores the length of every side of the equilateral triangle.

Because it is declared using `final`, its value cannot be reassigned after initialization.

---

## 4. Constructor and Background

```java
public FollowCursor() {
    setBackground(Color.WHITE);
```

The constructor sets the panel background to white.

Swing clears the previous frame using this background whenever the panel is repainted.

---

## 5. Detecting Mouse Movement

```java
addMouseMotionListener(new MouseMotionAdapter() {

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        repaint();
    }
});
```

A mouse-motion listener is attached to the panel.

`MouseMotionAdapter` is used so that only the required mouse-motion methods need to be overridden.

### `mouseMoved`

```java
mouseX = e.getX();
mouseY = e.getY();
```

`e.getX()` returns the horizontal cursor position relative to the panel.

`e.getY()` returns the vertical cursor position relative to the panel.

Java's coordinate system begins at the top-left corner:

```text
(0,0) ───────────────→ +X
  |
  |
  |
  ↓
 +Y
```

After the new coordinates are stored:

```java
repaint();
```

requests that Swing redraw the panel.

### `mouseDragged`

`mouseDragged` performs the same operation while the mouse is moved with a button held down.

---

## 6. Drawing with `paintComponent`

```java
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
```

Swing calls `paintComponent` whenever the panel needs to be rendered.

Calling:

```java
super.paintComponent(g);
```

allows the normal `JPanel` drawing process to occur first, including clearing the previous frame.

Without this call, old triangle positions could remain visible and create trails.

---

## 7. Converting to `Graphics2D`

```java
Graphics2D g2 = (Graphics2D) g;
```

Swing provides the drawing context as a `Graphics` object.

It is converted to `Graphics2D` because `Graphics2D` provides additional rendering capabilities such as:

- stroke thickness,
- anti-aliasing,
- transformations,
- improved 2D rendering control.

---

## 8. Enabling Anti-Aliasing

```java
g2.setRenderingHint(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON
);
```

Diagonal lines can appear jagged because a display consists of square pixels.

Anti-aliasing smooths the triangle edges by blending pixels around the boundary.

---

## 9. Calculating the Triangle Height

```java
double height = Math.sqrt(3) / 2 * SIDE;
```

For an equilateral triangle with side length `s`, the height is:

```text
h = (sqrt(3) / 2) × s
```

For a side length of `100` pixels:

```text
h ≈ 86.60 pixels
```

This height is used to calculate the three vertices.

---

## 10. Keeping the Cursor at the Triangle's Centroid

The program does not place the top vertex directly at the cursor.

Instead, the triangle is positioned so that its centroid lies at:

```text
(mouseX, mouseY)
```

For an equilateral triangle, the centroid divides the vertical median in a `2 : 1` ratio:

```text
             top
              ▲
              |
            2h/3
              |
              ●  ← cursor / centroid
              |
             h/3
              |
        ______|______
```

Therefore:

```text
Distance from centroid to top vertex  = 2h / 3
Distance from centroid to bottom edge = h / 3
```

---

## 11. Calculating the Three Vertices

### Top Vertex

```java
int topX = mouseX;
int topY = (int) (mouseY - (2.0 / 3.0) * height);
```

The top vertex has the same X-coordinate as the cursor.

Its Y-coordinate is shifted upward by `2h / 3`.

### Bottom-Left Vertex

```java
int leftX = mouseX - SIDE / 2;
int leftY = (int) (mouseY + (1.0 / 3.0) * height);
```

The bottom-left vertex is shifted:

- half the side length to the left,
- one third of the triangle height downward.

### Bottom-Right Vertex

```java
int rightX = mouseX + SIDE / 2;
int rightY = leftY;
```

The bottom-right vertex is symmetrical to the left vertex.

Both lower vertices share the same Y-coordinate.

---

## 12. Storing the Polygon Coordinates

```java
int[] xPoints = {
        topX,
        leftX,
        rightX
};

int[] yPoints = {
        topY,
        leftY,
        rightY
};
```

Java's polygon methods take separate arrays for X and Y coordinates.

Together, these arrays represent:

```text
Point 1 → (topX, topY)
Point 2 → (leftX, leftY)
Point 3 → (rightX, rightY)
```

---

## 13. Filling the Triangle

```java
g2.setColor(Color.RED);
g2.fillPolygon(xPoints, yPoints, 3);
```

The drawing colour is changed to red.

`fillPolygon` draws a filled polygon using the three supplied vertices.

The final argument, `3`, specifies that the polygon has three points.

---

## 14. Drawing the Black Outline

```java
g2.setColor(Color.BLACK);
g2.setStroke(new BasicStroke(3));
g2.drawPolygon(xPoints, yPoints, 3);
```

The drawing colour is changed to black.

`BasicStroke(3)` creates a three-pixel-wide outline.

`drawPolygon` then draws the border around the already-filled triangle.

---

## 15. Creating the Window

```java
public static void main(String[] args) {

    JFrame frame = new JFrame("Moving Triangle");

    FollowCursor panel = new FollowCursor();

    frame.add(panel);

    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);

    frame.setVisible(true);
}
```

### `JFrame`

```java
JFrame frame = new JFrame("Moving Triangle");
```

creates the main application window.

### Creating the Panel

```java
FollowCursor panel = new FollowCursor();
```

creates an instance of the custom drawing panel.

### Adding the Panel

```java
frame.add(panel);
```

places the custom panel inside the window.

### Window Size

```java
frame.setSize(800, 600);
```

sets the initial window size.

### Closing Behaviour

```java
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
```

causes the application to terminate when the window is closed.

### Centre the Window

```java
frame.setLocationRelativeTo(null);
```

places the window near the centre of the screen.

### Display the Window

```java
frame.setVisible(true);
```

makes the window visible.

---

## FollowCursor Logic Summary

The complete interaction can be simplified to:

```text
Mouse moves
    ↓
Read cursor X and Y
    ↓
Store mouseX and mouseY
    ↓
Call repaint()
    ↓
Calculate triangle height
    ↓
Calculate three vertices around the cursor
    ↓
Fill triangle red
    ↓
Draw black outline
```

The triangle itself is not moved as a persistent graphical object.

Instead, its vertex coordinates are recalculated and the entire triangle is redrawn at the new location whenever the cursor moves.

---

# Module 2 — Zoom / Triangle Zoom

`Zoom.java` creates a green equilateral triangle with a black outline.

Unlike `FollowCursor`, the triangle does not move around the window.

Its centre remains fixed, while only its size changes according to the cursor's Y-coordinate.

- Cursor near the **top** → larger triangle.
- Cursor near the **bottom** → smaller triangle.
- Horizontal cursor movement does not affect the result.

## Demonstration

[View the Triangle Zoom demonstration video](./media/TriangleZoom.mp4)

---

## 1. Imports

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
```

These imports provide the same Swing, graphics, and mouse-event functionality used by `FollowCursor`.

---

## 2. Creating the Zoom Panel

```java
public class Zoom extends JPanel {
```

`Zoom` extends `JPanel`, allowing custom drawing through `paintComponent`.

---

## 3. Storing the Cursor's Y-Coordinate

```java
private int mouseY = 300;
```

Only the vertical cursor position is required.

Unlike `FollowCursor`, there is no `mouseX` variable because horizontal movement is intentionally ignored.

---

## 4. Minimum and Maximum Triangle Sizes

```java
private final int MIN_SIZE = 30;
private final int MAX_SIZE = 400;
```

These constants define the allowed triangle-size range.

```text
Minimum side length = 30 pixels
Maximum side length = 400 pixels
```

This prevents the triangle from shrinking to zero or becoming excessively large.

---

## 5. Background and Mouse Listener

```java
public Zoom() {

    setBackground(Color.WHITE);

    addMouseMotionListener(new MouseMotionAdapter() {

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseY = e.getY();
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseY = e.getY();
            repaint();
        }
    });
}
```

The background is white.

Whenever the mouse moves, only:

```java
mouseY = e.getY();
```

is recorded.

The X-coordinate is never read.

After the Y-coordinate changes:

```java
repaint();
```

requests a new frame.

---

## 6. Preparing the Drawing Context

```java
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;
```

As in `FollowCursor`, the panel is cleared before each redraw and the `Graphics` object is converted to `Graphics2D`.

---

## 7. Anti-Aliasing

```java
g2.setRenderingHint(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON
);
```

This smooths the triangle edges.

---

## 8. Reading the Current Panel Height

```java
int panelHeight = getHeight();

if (panelHeight <= 0) {
    return;
}
```

The program needs the current panel height because the mouse Y-coordinate must be interpreted relative to the available vertical space.

The check prevents invalid calculations if the panel height is zero.

---

## 9. Converting Mouse Position into a Ratio

```java
double ratio = (double) mouseY / panelHeight;
```

This converts the cursor's vertical position into a value approximately between `0.0` and `1.0`.

For a `600`-pixel-high panel:

```text
mouseY = 0    → ratio = 0.0
mouseY = 300  → ratio = 0.5
mouseY = 600  → ratio = 1.0
```

This is a form of **normalization**.

It converts a value from one range:

```text
0 → panelHeight
```

into:

```text
0.0 → 1.0
```

---

## 10. Clamping the Ratio

```java
ratio = Math.max(0, Math.min(1, ratio));
```

The ratio is forced to remain within:

```text
0 ≤ ratio ≤ 1
```

This ensures that the calculated triangle size always remains between `MIN_SIZE` and `MAX_SIZE`.

---

## 11. Mapping Cursor Position to Triangle Size

```java
int side = (int) (
        MAX_SIZE -
        ratio * (MAX_SIZE - MIN_SIZE)
);
```

This is the core zoom logic.

The mouse Y-coordinate is mapped onto the triangle's allowed size range.

Because screen Y-values increase downward:

```text
Top of window    → Y is small
Bottom of window → Y is large
```

the mapping is reversed.

### At the Top

If:

```text
ratio = 0
```

then:

```text
side = MAX_SIZE
```

So the triangle is largest.

### At the Bottom

If:

```text
ratio = 1
```

then:

```text
side = MIN_SIZE
```

So the triangle is smallest.

For a `600`-pixel-high panel:

```text
Cursor Y     Ratio     Approx. side length
0            0.00      400 px
150          0.25      307 px
300          0.50      215 px
450          0.75      122 px
600          1.00       30 px
```

Conceptually:

```text
Cursor Y
0 ───────────────────────────── 600
│                                 │
▼                                 ▼
400 ───────────────────────────── 30
Triangle side length
```

---

## 12. Calculating Triangle Height

```java
double triangleHeight = Math.sqrt(3) / 2 * side;
```

Because the side length is now variable, the triangle height must also be recalculated every frame.

The same equilateral-triangle relationship is used:

```text
h = (sqrt(3) / 2) × side
```

---

## 13. Keeping the Triangle Fixed at the Centre

```java
int centerX = getWidth() / 2;
int centerY = getHeight() / 2;
```

Unlike `FollowCursor`, these coordinates do not depend on the mouse.

The triangle centre is always the centre of the panel.

Therefore:

- mouse movement changes the scale,
- mouse movement does not change the triangle position.

---

## 14. Recalculating Vertices for the Current Size

```java
int topX = centerX;
int topY = (int) (
        centerY - (2.0 / 3.0) * triangleHeight
);

int leftX = centerX - side / 2;
int leftY = (int) (
        centerY + (1.0 / 3.0) * triangleHeight
);

int rightX = centerX + side / 2;
int rightY = leftY;
```

These calculations are almost identical to `FollowCursor`.

The important difference is that the reference point is:

```text
(centerX, centerY)
```

instead of:

```text
(mouseX, mouseY)
```

and the value of `side` changes continuously with the cursor's Y-coordinate.

---

## 15. Storing the Vertices

```java
int[] xPoints = {
        topX,
        leftX,
        rightX
};

int[] yPoints = {
        topY,
        leftY,
        rightY
};
```

The three vertices are passed to Java's polygon-drawing methods.

---

## 16. Drawing the Green Triangle

```java
g2.setColor(Color.GREEN);
g2.fillPolygon(xPoints, yPoints, 3);
```

The triangle is filled in green.

---

## 17. Drawing the Black Outline

```java
g2.setColor(Color.BLACK);
g2.setStroke(new BasicStroke(3));
g2.drawPolygon(xPoints, yPoints, 3);
```

A three-pixel black outline is drawn around the triangle.

---

## 18. Creating the Zoom Window

```java
public static void main(String[] args) {

    JFrame frame = new JFrame("Triangle Zoom");

    Zoom panel = new Zoom();

    frame.add(panel);

    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);

    frame.setVisible(true);
}
```

This creates an `800 × 600` Swing window, adds the `Zoom` panel, centres the window, and displays it.

---

## Zoom Logic Summary

The complete process is:

```text
Mouse moves vertically
        ↓
Read cursor Y
        ↓
Normalize Y to a 0–1 ratio
        ↓
Map ratio to triangle size
        ↓
Calculate new triangle height
        ↓
Keep centroid fixed at window centre
        ↓
Recalculate the three vertices
        ↓
Fill triangle green
        ↓
Draw black outline
```

The X-coordinate of the mouse does not participate in any of these calculations.

---

# Comparison of the Two Modules

| Feature | FollowCursor | Zoom |
|---|---|---|
| Triangle colour | Red | Green |
| Outline | Black | Black |
| Shape | Equilateral triangle | Equilateral triangle |
| Uses cursor X | Yes | No |
| Uses cursor Y | Yes | Yes |
| Triangle position changes | Yes | No |
| Triangle size changes | No | Yes |
| Cursor controls | Position | Scale |
| Triangle centre | Cursor position | Window centre |

The two modules demonstrate two different graphical transformations:

### Translation

`FollowCursor` changes the position of the triangle without changing its size.

```text
Same triangle → different position
```

### Scaling

`Zoom` changes the size of the triangle without changing its centre position.

```text
Same centre → different triangle size
```

---

# Key Concepts Demonstrated

This project demonstrates several fundamental ideas in event-driven graphical programming:

- Java Swing windows and panels
- custom rendering with `paintComponent`
- 2D drawing with `Graphics2D`
- polygon rendering
- mouse-motion event handling
- screen coordinate systems
- anti-aliasing
- equilateral-triangle geometry
- centroid-based positioning
- translation
- scaling
- normalization
- mapping one numerical range to another
- continuous redraws using `repaint()`

---

# Source Files

## `FollowCursor.java`

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FollowCursor extends JPanel {

    private int mouseX = 400;
    private int mouseY = 300;

    private final int SIDE = 100;

    public FollowCursor() {

        setBackground(Color.WHITE);

        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        double height = Math.sqrt(3) / 2 * SIDE;

        int topX = mouseX;
        int topY = (int) (mouseY - (2.0 / 3.0) * height);

        int leftX = mouseX - SIDE / 2;
        int leftY = (int) (mouseY + (1.0 / 3.0) * height);

        int rightX = mouseX + SIDE / 2;
        int rightY = leftY;

        int[] xPoints = {
                topX,
                leftX,
                rightX
        };

        int[] yPoints = {
                topY,
                leftY,
                rightY
        };

        g2.setColor(Color.RED);
        g2.fillPolygon(xPoints, yPoints, 3);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawPolygon(xPoints, yPoints, 3);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Moving Triangle");

        FollowCursor panel = new FollowCursor();

        frame.add(panel);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
```

## `Zoom.java`

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Zoom extends JPanel {

    private int mouseY = 300;

    private final int MIN_SIZE = 30;
    private final int MAX_SIZE = 400;

    public Zoom() {

        setBackground(Color.WHITE);

        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseY = e.getY();
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseY = e.getY();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int panelHeight = getHeight();

        if (panelHeight <= 0) {
            return;
        }

        double ratio = (double) mouseY / panelHeight;

        ratio = Math.max(0, Math.min(1, ratio));

        int side = (int) (
                MAX_SIZE -
                ratio * (MAX_SIZE - MIN_SIZE)
        );

        double triangleHeight = Math.sqrt(3) / 2 * side;

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int topX = centerX;
        int topY = (int) (
                centerY - (2.0 / 3.0) * triangleHeight
        );

        int leftX = centerX - side / 2;
        int leftY = (int) (
                centerY + (1.0 / 3.0) * triangleHeight
        );

        int rightX = centerX + side / 2;
        int rightY = leftY;

        int[] xPoints = {
                topX,
                leftX,
                rightX
        };

        int[] yPoints = {
                topY,
                leftY,
                rightY
        };

        g2.setColor(Color.GREEN);
        g2.fillPolygon(xPoints, yPoints, 3);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawPolygon(xPoints, yPoints, 3);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Triangle Zoom");

        Zoom panel = new Zoom();

        frame.add(panel);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
```