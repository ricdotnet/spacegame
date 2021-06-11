package Util;

import javax.swing.*;

public class Label extends JLabel {

    public static final JLabel gameDifficulty = new JLabel();

//    private String labelName;
//    private String labelText;

    public Label(String labelName, String labelText) {
        setName(labelName);
        setText(labelText);
    }

//    public void setLabelName(String labelName) {
//        this.labelName = labelName;
//    }
//    public String getLabelName() {
//        return labelName;
//    }
//
    public void setLabelText(String labelText) {
        setText(labelText);
    }
//    public String getLabelText() {
//        return labelText;
//    }

}
