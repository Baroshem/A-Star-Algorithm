package View;

import Controller.Controller;
import Controller.AStar;
import Model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JPanel implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
    Controller c;
    JFrame window;
    AStar pathfinding;
    boolean showSteps, btnHover;
    int size;
    double a1, a2;
    char currentKey = (char) 0;
    Node startNode, endNode;
    String mode;

    Timer timer = new Timer(100, this);
    int r = randomWithRange(0, 255);
    int G = randomWithRange(0, 255);
    int b = randomWithRange(0, 255);

    public Frame() {
        c = new Controller(this);
        size = 25;
        mode = "Map Creation";
        showSteps = true;
        btnHover = false;
        setLayout(null);
        addMouseListener(this);
        addMouseWheelListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        pathfinding = new AStar(this, size);
        pathfinding.setDiagonal(true);

        a1 = (5000.0000 / (Math.pow(25.0000/5000, 1/49)));
        a2 = 625.0000;

        window = new JFrame();
        window.setContentPane(this);
        window.setTitle("A * Pathfinding");
        window.getContentPane().setPreferredSize(new Dimension(700,600));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        c.addAll();

        this.revalidate();
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int height = getHeight();
        int width = getWidth();

        if(pathfinding.isNoPath()) {
            timer.setDelay(50);
            timer.start();

            c.getB("run").setText("clear");
            mode = "No Path";

            Color flicker = new Color(r, G, b);
            g.setColor(flicker);
            g.fillRect(0, 0, getWidth(), getHeight());

            c.noPathTBounds();
            c.getL("noPathT").setVisible(true);
            this.add(c.getL("noPathT"));
            this.revalidate();
        }

        if(pathfinding.isComplete()) {
            c.getB("run").setText("clear");
            timer.setDelay(50);
            timer.start();

            Color flicker = new Color(r, G, b);
            g.setColor(flicker);
            g.fillRect(0, 0, getWidth(), getHeight());

            if(showSteps) {
                mode = "Completed";
            } else {
                mode = "Completed in " + pathfinding.getRunTime() + " ms";
            }
        }

        g.setColor(Color.lightGray);
        for(int j = 0; j < this.getHeight(); j += size) {
            for(int i = 0; i < this.getWidth(); i += size) {
                g.drawRect(i, j, size, size);
            }
        }

        g.setColor(Color.black);
        for(int i = 0; i < pathfinding.getBorderList().size(); i++) {
            g.fillRect(pathfinding.getBorderList().get(i).getX() + 1, pathfinding.getBorderList().get(i).getY() + 1, size-1, size -1);
        }

        for(int i = 0; i < pathfinding.getOpenList().size(); i++) {
            Node current = pathfinding.getOpenList().get(i);
            g.setColor(Styles.greenHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);

            drawInfo(current, g);
        }

        for(int i = 0; i < pathfinding.getClosedList().size(); i++) {
            Node current = pathfinding.getClosedList().get(i);
            g.setColor(Styles.redHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size -1 );

            drawInfo(current, g);
        }

        for(int i = 0; i < pathfinding.getPathList().size(); i++) {
            Node current = pathfinding.getPathList().get(i);
            g.setColor(Styles.blueHighlight);
            g.fillRect(current.getX() + 1, current.getY() + 1, size - 1, size - 1);

            drawInfo(current, g);
        }

        if(startNode != null) {
            g.setColor(Color.blue);
            g.fillRect(startNode.getX() + 1, startNode.getY() + 1, size - 1, size -1 );
        }

        if(endNode != null) {
            g.setColor(Color.red);
            g.fillRect(endNode.getX() + 1, endNode.getY() + 1, size - 1, size - 1);
        }

        if(btnHover) {
            g.setColor(Styles.darkText);
            c.hoverColour();
        } else {
            g.setColor(Styles.btnPanel);
            c.nonHoverColour();
        }

        g.fillRect(10, height - 96, 322, 90);

        c.getL("modeText").setText("Mode: " + mode);

        c.position();

        c.getL("openC").setText(Integer.toString(pathfinding.getOpenList().size()));
        c.getL("closedC").setText(Integer.toString(pathfinding.getClosedList().size()));
        c.getL("pathC").setText(Integer.toString(pathfinding.getPathList().size()));

        if(showSteps) {
            c.getL("speedC").setText(Integer.toString(c.getS("speed").getValue()));
        } else {
            c.getL("speedC").setText("N/A");
        }

        showSteps = c.getC("showStepsCheck").isSelected();
        pathfinding.setDiagonal(c.getC("diagonalCheck").isSelected());
        pathfinding.setTrig(c.getC("trigCheck").isSelected());
    }

    public void drawInfo(Node current, Graphics g) {
        if(size > 50) {
            g.setFont(Styles.numbers);
            g.setColor(Color.black);
            g.drawString(Integer.toString(current.getF()), current.getX() + 4, current.getY() + 16);
            g.setFont(Styles.smallNumbers);
            g.drawString(Integer.toString(current.getG()), current.getX() + 4, current.getY() - 7);
            g.drawString(Integer.toString(current.getH()), current.getX() + size - 26, current.getY() + size - 7);
        }
    }

    public void MapCalculations(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)) {
            if(currentKey == 's') {
                int xRollover = e.getX() % size;
                int yRollover = e.getY() % size;

                if(startNode == null) {
                    startNode = new Node(e.getX() - xRollover, e.getY() - yRollover);
                } else {
                    startNode.setXY(e.getX() - xRollover, e.getY() - yRollover);
                }
                repaint();
            }

            else if(currentKey == 'e') {
                int xRollover = e.getX() % size;
                int yRollover = e.getY() % size;

                if(endNode == null) {
                    endNode = new Node(e.getX() - xRollover, e.getY() - yRollover);
                } else {
                    endNode.setXY(e.getX() - xRollover, e.getY()- yRollover);
                }
                repaint();
            }

            else {
                int xBorder = e.getX() - (e.getX() % size);
                int yBorder = e.getY() - (e.getY() % size);

                Node newBorder = new Node(xBorder, yBorder);
                pathfinding.addBorder(newBorder);

                repaint();
            }
        } else if(SwingUtilities.isRightMouseButton(e)) {
            int mouseBoxX = e.getX() - (e.getX() % size);
            int mouseBoxY = e.getX() - (e.getX() % size);

            if(currentKey == 's') {
                if(startNode != null && mouseBoxX == startNode.getX() && startNode.getY() == mouseBoxY) {
                    startNode = null;
                    repaint();
                }
            } else if(currentKey == 'e') {
                if(endNode != null && mouseBoxX == endNode.getX() && mouseBoxY == endNode.getY()) {
                    endNode = null;
                    repaint();
                }
            } else {
                int Location = pathfinding.searchBorder(mouseBoxX, mouseBoxY);
                if(Location != -1) {
                    pathfinding.removeBorder(Location);
                }
                repaint();
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(pathfinding.isRunning() && showSteps) {
            pathfinding.findPath(pathfinding.getPar());
            mode = "Running";
        }
        if(pathfinding.isComplete() || pathfinding.isNoPath()) {
            r = (int) (Math.random() * ((r + 15) - (r - 15)) + (r - 15));
            G = (int) (Math.random() * ((G + 15) - (G - 15)) + (G - 15));
            b = (int) (Math.random() * ((b + 15) - (b - 15)) + (b - 15));

            if(r >= 240 | r <= 15) {
                r = randomWithRange(0, 255);
            }
            if(G >= 240 | G <= 15) {
                G = randomWithRange(0, 255);
            }
            if(b >= 240 | b <= 15) {
                b = randomWithRange(0, 255);
            }
        }

        if(e.getActionCommand() != null) {
            if(e.getActionCommand().equals("run") && !pathfinding.isRunning()) {
                c.getB("run").setText("stop");
                start();
            } else if(e.getActionCommand().equals("clear")){
                c.getB("run").setText("run");
                mode = "Map creation";
                c.getL("noPathT").setVisible(false);
                pathfinding.reset();
            } else if(e.getActionCommand().equals("stop")){
                c.getB("run").setText("start");
                timer.stop();
            } else if(e.getActionCommand().equals("start")){
                c.getB("run").setText("stop");
                timer.stop();
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        currentKey = key;

        if(currentKey == KeyEvent.VK_SPACE) {
            c.getB("run").setText("stop");
            start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentKey = (char) 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        MapCalculations(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        MapCalculations(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int height = this.getHeight();

        if(x >= 10 && x <= 332 && y >= (height - 96) && y <= (height - 6)) {
            btnHover = true;
        } else {
            btnHover = false;
        }
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = e.getWheelRotation();
        double prevSize = size;
        int scroll = 3;

        // Changes size of grid based on scroll
        if (rotation == -1 && size + scroll < 200) {
            size += scroll;
        } else if (rotation == 1 && size - scroll > 2) {
            size += -scroll;
        }
        pathfinding.setSize(size);
        double ratio = size / prevSize;

        // new X and Y values for Start
        if (startNode != null) {
            int sX = (int) Math.round(startNode.getX() * ratio);
            int sY = (int) Math.round(startNode.getY() * ratio);
            startNode.setXY(sX, sY);
        }

        // new X and Y values for End
        if (endNode != null) {
            int eX = (int) Math.round(endNode.getX() * ratio);
            int eY = (int) Math.round(endNode.getY() * ratio);
            endNode.setXY(eX, eY);
        }

        // new X and Y values for borders
        for (int i = 0; i < pathfinding.getBorderList().size(); i++) {
            int newX = (int) Math.round((pathfinding.getBorderList().get(i).getX() * ratio));
            int newY = (int) Math.round((pathfinding.getBorderList().get(i).getY() * ratio));
            pathfinding.getBorderList().get(i).setXY(newX, newY);
        }

        // New X and Y for Open nodes
        for (int i = 0; i < pathfinding.getOpenList().size(); i++) {
            int newX = (int) Math.round((pathfinding.getOpenList().get(i).getX() * ratio));
            int newY = (int) Math.round((pathfinding.getOpenList().get(i).getY() * ratio));
            pathfinding.getOpenList().get(i).setXY(newX, newY);
        }

        // New X and Y for Closed Nodes
        for (int i = 0; i < pathfinding.getClosedList().size(); i++) {
            if (!Node.isEqual(pathfinding.getClosedList().get(i), startNode)) {
                int newX = (int) Math.round((pathfinding.getClosedList().get(i).getX() * ratio));
                int newY = (int) Math.round((pathfinding.getClosedList().get(i).getY() * ratio));
                pathfinding.getClosedList().get(i).setXY(newX, newY);
            }
        }
        repaint();
    }

    void start() {
        if(startNode != null && endNode != null) {
            if(!showSteps) {
                pathfinding.start(startNode, endNode);
            } else {
                pathfinding.setup(startNode, endNode);
                setSpeed();
                timer.start();
            }
        } else {
            System.out.println("Error. Needed start and end points to run");
        }
    }

    int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
    void setSpeed() {
        int delay = 0;
        int value = c.getS("speed").getValue();

        if(value == 0) {
            timer.stop();
        } else if(value >= 1 && value < 50) {
            if(!timer.isRunning()) {
                timer.start();
            }
            delay = (int) (a1 * (Math.pow(25/5000.0000, value / 49.0000)));
        } else if(value >= 50 && value <= 100) {
            if(!timer.isRunning()) {
                timer.start();
            }
            delay = (int) (a2 * (Math.pow(1/25.0000, value / 50.0000)));
        }
        timer.setDelay(delay);
    }

    boolean showSteps() {
        return showSteps;
    }
}