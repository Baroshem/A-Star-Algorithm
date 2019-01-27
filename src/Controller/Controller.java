package Controller;

import View.Frame;
import View.style;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class Controller {
    private Frame frame;
    private JLabel modeText, openT,
            closedT, pathT, openC, closedC, pathC, noPathT;
    private JCheckBox showStepsCheck, diagonalCheck;
    private JButton run;
    private ArrayList<JLabel> labels;
    private ArrayList<JCheckBox> checks;
    private ArrayList<JButton> buttons;
    private Dimension npD;

    public Controller(Frame frame) {
        this.frame = frame;
        labels = new ArrayList<>();
        checks = new ArrayList<>();
        buttons = new ArrayList<>();

        // Set up JLabels
        modeText = new JLabel("Mode: ");
        modeText.setName("modeText");
        modeText.setFont(style.bigText);
        modeText.setForeground(style.darkText);
        modeText.setVisible(true);

        openT = new JLabel("Open");
        openT.setName("openT");
        openT.setFont(style.numbers);
        openT.setVisible(true);

        openC = new JLabel("0");
        openC.setName("openC");
        openC.setFont(style.numbers);
        openC.setVisible(true);

        closedT = new JLabel("Closed");
        closedT.setName("closedT");
        closedT.setFont(style.numbers);
        closedT.setVisible(true);

        closedC = new JLabel("0");
        closedC.setName("closedC");
        closedC.setFont(style.numbers);
        closedC.setVisible(true);

        pathT = new JLabel("Path");
        pathT.setName("pathT");
        pathT.setFont(style.numbers);
        pathT.setVisible(true);

        pathC = new JLabel("0");
        pathC.setName("pathC");
        pathC.setFont(style.numbers);
        pathC.setVisible(true);

        noPathT = new JLabel("NO PATH");
        noPathT.setName("noPathT");
        noPathT.setForeground(Color.white);
        noPathT.setFont(style.REALBIGText);
        npD = noPathT.getPreferredSize();

        // Add JLabels to list
        labels.add(modeText);
        labels.add(openT);
        labels.add(openC);
        labels.add(closedT);
        labels.add(closedC);
        labels.add(pathT);
        labels.add(pathC);
        labels.add(noPathT);

        // Set up JCheckBoxes
        showStepsCheck = new JCheckBox();
        showStepsCheck.setText("showSteps");
        showStepsCheck.setName("showStepsCheck");
        showStepsCheck.setSelected(true);
        showStepsCheck.setOpaque(false);
        showStepsCheck.setFocusable(false);
        showStepsCheck.setVisible(true);

        diagonalCheck = new JCheckBox();
        diagonalCheck.setText("Diagonal");
        diagonalCheck.setName("diagonalCheck");
        diagonalCheck.setOpaque(false);
        diagonalCheck.setSelected(true);
        diagonalCheck.setFocusable(false);
        diagonalCheck.setVisible(true);

        // Add JCheckboxes to list
        checks.add(showStepsCheck);
        checks.add(diagonalCheck);

        // Set up JButtons
        run = new JButton();
        run.setText("run");
        run.setName("run");
        run.setFocusable(false);
        run.addActionListener(frame);
        run.setMargin(new Insets(0,0,0,0));
        run.setVisible(true);

        // Add JButtons to list
        buttons.add(run);
    }

    // Gets a specific JLabel by name
    public JLabel getL(String t) {
        for (JLabel label : labels) {
            if (label.getName().equals(t)) {
                return label;
            }
        }
        return null;
    }

    // Gets specific JCheckBox by name
    public JCheckBox getC(String t) {
        for (JCheckBox check : checks) {
            if (check.getName().equals(t)) {
                return check;
            }
        }
        return null;
    }

    // Gets specific JCheckBox by name
    public JButton getB(String t) {
        for (JButton button : buttons) {
            if (button.getName().equals(t)) {
                return button;
            }
        }
        return null;
    }

    public void noPathTBounds() {
        noPathT.setBounds((int)((frame.getWidth()/2)-(npD.getWidth()/2)),
                (frame.getHeight()/2)-70,
                (int)npD.getWidth(), (int)npD.getHeight());
    }

    public void position() {
        // Set label bounds
        openT.setBounds(254, frame.getHeight()-92, 60, 20);
        openC.setBounds(300, frame.getHeight()-92, 60, 20);
        closedT.setBounds(254, frame.getHeight()-76, 60, 20);
        closedC.setBounds(300, frame.getHeight()-76, 60, 20);
        pathT.setBounds(254, frame.getHeight()-60, 60, 20);
        pathC.setBounds(300, frame.getHeight()-60, 60, 20);
        Dimension size = modeText.getPreferredSize();
        modeText.setBounds(20, frame.getHeight() - 39, size.width, size.height);

        // Set check box bounds
        showStepsCheck.setBounds(20, frame.getHeight()-88, 90, 20);
        diagonalCheck.setBounds(20, frame.getHeight()-64, 90, 20);


        // Set button bounds
        run.setBounds(116, frame.getHeight()-88, 52, 22);
    }

    // Sets text of JLabels to lightText
    public void hoverColour() {
        modeText.setForeground(style.lightText);
        showStepsCheck.setForeground(style.lightText);
        diagonalCheck.setForeground(style.lightText);
        openT.setForeground(style.lightText);
        openC.setForeground(style.lightText);
        closedT.setForeground(style.lightText);
        closedC.setForeground(style.lightText);
        pathT.setForeground(style.lightText);
        pathC.setForeground(style.lightText);
    }

    // Sets text of JLabels to darkText
    public void nonHoverColour() {
        modeText.setForeground(style.darkText);
        showStepsCheck.setForeground(style.darkText);
        diagonalCheck.setForeground(style.darkText);
        openT.setForeground(style.darkText);
        openC.setForeground(style.darkText);
        closedC.setForeground(style.darkText);
        closedT.setForeground(style.darkText);
        pathT.setForeground(style.darkText);
        pathC.setForeground(style.darkText);
    }

    // Adds all components to frame
    public void addAll() {
        frame.add(showStepsCheck);
        frame.add(diagonalCheck);
        frame.add(run);
        frame.add(modeText);
        frame.add(openT);
        frame.add(openC);
        frame.add(closedT);
        frame.add(closedC);
        frame.add(pathT);
        frame.add(pathC);
    }

}