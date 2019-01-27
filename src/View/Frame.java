package View;

import Controller.Controller;
import Controller.AStar;
import Model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener {
    private Controller ch;
    private AStar pathfinding;
    private boolean showSteps;
    private int size;
    private char currentKey = (char) 0;
    private Node startNode, endNode;
    private String mode;

    private Timer timer = new Timer(100, this);

    public static void main(String[] args) {
        new Frame();
    }

    public Frame() {
        ch = new Controller(this);
        size = 25;
        mode = "Map Creation";
        showSteps = true;
//        btnHover = false;
        setLayout(null);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        // Set up pathfinding
        pathfinding = new AStar(this, size);
        pathfinding.setDiagonal(true);

        // Set up window
        JFrame window = new JFrame();
        window.setContentPane(this);
        window.setTitle("A* Algorithm");
        window.getContentPane().setPreferredSize(new Dimension(800, 700));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Add all controls
        ch.addAll();

        this.revalidate();
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Grab dimensions of panel
        int height = getHeight();

        // If no path is found
        if (pathfinding.isNoPath()) {
            // Set timer for animation
            timer.setDelay(30);
            timer.start();

            // Set text of "run" button to "clear"
            ch.getB("run").setText("clear");

            // Set mode to "No Path"
            mode = "No Path";

            // Place "No Path" text on screen in center
            ch.noPathTBounds();
            ch.getL("noPathT").setVisible(true);
            this.add(ch.getL("noPathT"));
            this.revalidate();
        }

        // If pathfinding is complete (found path)
        if (pathfinding.isComplete()) {
            // Set run button to clear
            ch.getB("run").setText("clear");

            // Set timer delay, start for background animation
            timer.setDelay(30);
            timer.start();

            // Set completed mode
            if(showSteps) {
                mode = "Completed";
            }
        }

        // Draws grid
        g.setColor(Color.lightGray);
        for (int j = 0; j < this.getHeight(); j += size) {
            for (int i = 0; i < this.getWidth(); i += size) {
                g.drawRect(i, j, size, size);
            }
        }

        // Draws all borders
        g.setColor(Color.black);
        for (int i = 0; i < pathfinding.getBorderList().size(); i++) {
            g.fillRect(pathfinding.getBorderList().get(i).getX() + 1, pathfinding.getBorderList().get(i).getY() + 1,
                    size - 1, size - 1);
        }

        // Draws all open Nodes (path finding nodes)
        for (int i = 0; i < pathfinding.getOpenList().size(); i++) {
            Node current = pathfinding.getOpenList().get(i);
            g.setColor(style.greenHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);

            drawInfo(current, g);
        }

        // Draws all closed nodes
        for (int i = 0; i < pathfinding.getClosedList().size(); i++) {
            Node current = pathfinding.getClosedList().get(i);

            g.setColor(style.redHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);

            drawInfo(current, g);
        }

        // Draw all final path nodes
        for (int i = 0; i < pathfinding.getPathList().size(); i++) {
            Node current = pathfinding.getPathList().get(i);

            g.setColor(style.blueHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);

            drawInfo(current, g);
        }

        // Draws start of path
        if (startNode != null) {
            g.setColor(Color.green);
            g.fillRect(startNode.getX() + 1, startNode.getY() + 1, size - 1, size - 1);
        }
        // Draws end of path
        if (endNode != null) {
            g.setColor(Color.red);
            g.fillRect(endNode.getX() + 1, endNode.getY() + 1, size - 1, size - 1);
        }

        g.setColor(style.btnPanel);
        ch.nonHoverColour();

        // Drawing control panel rectangle
        g.fillRect(10, height-96, 322, 90);

        // Setting mode text
        ch.getL("modeText").setText("Mode: " + mode);

        // Position all controls
        ch.position();

        // Setting numbers in pathfinding lists
        ch.getL("openC").setText(Integer.toString(pathfinding.getOpenList().size()));
        ch.getL("closedC").setText(Integer.toString(pathfinding.getClosedList().size()));
        ch.getL("pathC").setText(Integer.toString(pathfinding.getPathList().size()));

        // Getting values from checkboxes
        showSteps = ch.getC("showStepsCheck").isSelected();
        pathfinding.setDiagonal(ch.getC("diagonalCheck").isSelected());
    }

    // Draws info (f, g, h) on current node
    private void drawInfo(Node current, Graphics g) {
        if (size > 50) {
            g.setFont(style.numbers);
            g.setColor(Color.black);
            g.drawString(Integer.toString(current.getF()), current.getX() + 4, current.getY() + 16);
            g.setFont(style.smallNumbers);
            g.drawString(Integer.toString(current.getG()), current.getX() + 4, current.getY() + size - 7);
            g.drawString(Integer.toString(current.getH()), current.getX() + size - 26, current.getY() + size - 7);
        }
    }

    private void MapCalculations(MouseEvent e) {
        // If left mouse button is clicked
        if (SwingUtilities.isLeftMouseButton(e)) {
            // If 's' is pressed create start node
            if (currentKey == 's') {
                int xRollover = e.getX() % size;
                int yRollover = e.getY() % size;

                if (startNode == null) {
                    startNode = new Node(e.getX() - xRollover, e.getY() - yRollover);
                } else {
                    startNode.setXY(e.getX() - xRollover, e.getY() - yRollover);
                }
                repaint();
            }
            // If 'e' is pressed create end node
            else if (currentKey == 'e') {
                int xRollover = e.getX() % size;
                int yRollover = e.getY() % size;

                if (endNode == null) {
                    endNode = new Node(e.getX() - xRollover, e.getY() - yRollover);
                } else {
                    endNode.setXY(e.getX() - xRollover, e.getY() - yRollover);
                }
                repaint();
            }
            // Otherwise, create a wall
            else {
                int xBorder = e.getX() - (e.getX() % size);
                int yBorder = e.getY() - (e.getY() % size);

                Node newBorder = new Node(xBorder, yBorder);
                pathfinding.addBorder(newBorder);

                repaint();
            }
        }
        // If right mouse button is clicked
        else if (SwingUtilities.isRightMouseButton(e)) {
            int mouseBoxX = e.getX() - (e.getX() % size);
            int mouseBoxY = e.getY() - (e.getY() % size);

            // If 's' is pressed remove start node
            if (currentKey == 's') {
                if (startNode != null && mouseBoxX == startNode.getX() && startNode.getY() == mouseBoxY) {
                    startNode = null;
                    repaint();
                }
            }
            // If 'e' is pressed remove end node
            else if (currentKey == 'e') {
                if (endNode != null && mouseBoxX == endNode.getX() && endNode.getY() == mouseBoxY) {
                    endNode = null;
                    repaint();
                }
            }
            // Otherwise, remove wall
            else {
                int Location = pathfinding.searchBorder(mouseBoxX, mouseBoxY);
                if (Location != -1) {
                    pathfinding.removeBorder(Location);
                }
                repaint();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        MapCalculations(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        MapCalculations(e);
    }

    @Override
    // Track mouse on movement
    public void mouseMoved(MouseEvent e) {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        currentKey = e.getKeyChar();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentKey = (char) 0;
    }

    // Starts path finding
    private void start() {
        if(startNode != null && endNode != null) {
            if (!showSteps) {
                pathfinding.start(startNode, endNode);
            } else {
                pathfinding.setup(startNode, endNode);
                setSpeed();
                timer.start();
            }
        }
        else {
            System.out.println("ERROR: Needs start and end points to run.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Moves one step ahead in path finding (called on timer)
        if (pathfinding.isRunning() && showSteps) {
            pathfinding.findPath(pathfinding.getPar());
            mode = "Running";
        }
        // Actions of run/stop/clear button
        if(e.getActionCommand() != null) {
            if(e.getActionCommand().equals("run") && !pathfinding.isRunning()) {
                ch.getB("run").setText("stop");
                start();
            }
            else if(e.getActionCommand().equals("clear")) {
                ch.getB("run").setText("run");
                mode = "Map Creation";
                ch.getL("noPathT").setVisible(false);
                pathfinding.reset();
            }
            else if(e.getActionCommand().equals("stop")) {
                ch.getB("run").setText("start");
                timer.stop();
            }
            else if(e.getActionCommand().equals("start")) {
                ch.getB("run").setText("stop");
                timer.start();
            }
        }
        repaint();
    }

    // Calculates delay with two exponential functions
    public void setSpeed() {
        timer.setDelay(30);
    }

    public boolean showSteps() {
        return showSteps;
    }
}