package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

class Controller {
    private Frame frame;
    private JLabel modeText, openT, closedT, pathT, openC, closedC, pathC, noPathT;
    private JCheckBox showStepsCheck;
    private JButton run;
    private ArrayList<JLabel> labels;
    private ArrayList<JCheckBox> checks;
    private ArrayList<JButton> buttons;
    private Dimension npD;

    Controller(Frame frame) {
        this.frame = frame;
        labels = new ArrayList<>();
        checks = new ArrayList<>();
        buttons = new ArrayList<>();

        // Set up JLabels
        modeText = new JLabel("Mode: ");
        modeText.setName("modeText");
        modeText.setFont(style.getBigText());
        modeText.setForeground(style.getDarkText());
        modeText.setVisible(true);

        openT = new JLabel("Open");
        openT.setName("openT");
        openT.setFont(style.getNumbers());
        openT.setVisible(true);

        openC = new JLabel("0");
        openC.setName("openC");
        openC.setFont(style.getNumbers());
        openC.setVisible(true);

        closedT = new JLabel("Closed");
        closedT.setName("closedT");
        closedT.setFont(style.getNumbers());
        closedT.setVisible(true);

        closedC = new JLabel("0");
        closedC.setName("closedC");
        closedC.setFont(style.getNumbers());
        closedC.setVisible(true);

        pathT = new JLabel("Path");
        pathT.setName("pathT");
        pathT.setFont(style.getNumbers());
        pathT.setVisible(true);

        pathC = new JLabel("0");
        pathC.setName("pathC");
        pathC.setFont(style.getNumbers());
        pathC.setVisible(true);

        noPathT = new JLabel("NO PATH");
        noPathT.setName("noPathT");
        noPathT.setForeground(Color.white);
        noPathT.setFont(style.getREALBIGText());
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
        showStepsCheck.setText("Show Steps");
        showStepsCheck.setName("showStepsCheck");
        showStepsCheck.setSelected(true);
        showStepsCheck.setOpaque(false);
        showStepsCheck.setFocusable(false);
        showStepsCheck.setVisible(true);

        // Add JCheckboxes to list
        checks.add(showStepsCheck);

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

    JLabel getL(String t) {
        for (JLabel label : labels) {
            if (label.getName().equals(t)) {
                return label;
            }
        }
        return null;
    }

    JCheckBox getC(String t) {
        for (JCheckBox check : checks) {
            if (check.getName().equals(t)) {
                return check;
            }
        }
        return null;
    }

    JButton getB(String t) {
        for (JButton button : buttons) {
            if (button.getName().equals(t)) {
                return button;
            }
        }
        return null;
    }

    void noPathTBounds() {
        noPathT.setBounds((int)((frame.getWidth()/2)-(npD.getWidth()/2)),
                (frame.getHeight()/2)-70,
                (int)npD.getWidth(), (int)npD.getHeight());
    }

    void position() {
        // Set label bounds
        openT.setBounds(250, 75, 60, 20);
        openC.setBounds(295, 75, 60, 20);
        closedT.setBounds(250, 60, 60, 20);
        closedC.setBounds(295, 60, 60, 20);
        pathT.setBounds(250, 44, 60, 20);
        pathC.setBounds(295, 44, 60, 20);
        Dimension size = modeText.getPreferredSize();
        modeText.setBounds(20,  15, size.width, size.height);

        // Set check box bounds
        showStepsCheck.setBounds(140, 58, 110, 20);

        // Set button bounds
        run.setBounds(26, 50, 100, 40);
    }

    void addAll() {
        frame.add(showStepsCheck);
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