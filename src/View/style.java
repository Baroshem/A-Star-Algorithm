package View;

import java.awt.Color;
import java.awt.Font;

class style {
    private static Font bigText = new Font("arial", Font.BOLD, 24);
    private static Font REALBIGText = new Font("arial", Font.BOLD, 72);
    private static Font numbers = new Font("arial", Font.BOLD, 12);
    private static Color greenHighlight = new Color(132,230,170);
    private static Color redHighlight = new Color(253,140,90);
    private static Color blueHighlight = new Color(22,200,235);
    private static Color btnPanel = new Color(120,120,120,80);
    private static Color darkText = new Color(48,48,48);

    static Font getBigText() {
        return bigText;
    }

    static Font getREALBIGText() {
        return REALBIGText;
    }

    static Font getNumbers() {
        return numbers;
    }

    static Color getGreenHighlight() {
        return greenHighlight;
    }

    static Color getRedHighlight() {
        return redHighlight;
    }

    static Color getBlueHighlight() {
        return blueHighlight;
    }

    static Color getBtnPanel() {
        return btnPanel;
    }

    static Color getDarkText() {
        return darkText;
    }
}
