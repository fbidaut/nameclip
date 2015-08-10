package org.fbidaut.nameclip;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.InsetsUIResource;
import java.awt.*;
import java.io.IOException;

public class Name2ClipMain {

    /** The Constant darkestBlue. */
    public static final Color DARKEST_BLUE = new Color(0, 59, 116);

    /** The Constant darkBlue. */
    public static final Color DARK_BLUE = new Color(102, 141, 179);

    /** The Constant lightBlue. */
    public static final Color LIGHT_BLUE = new Color(198, 222, 245);

    /** The Constant lighterBlue. */
    public static final Color LIGHTER_BLUE = new Color(223, 229, 235);

    /** The Constant extraLighterBlue. */
    public static final Color EXTRA_LIGHTER_BLUE = new Color(242, 245, 247);

    /** The Constant lightestBlue. */
    public static final Color LIGHTEST_BLUE = new Color(250, 251, 252);

    /** The Constant selectionBg. */
    public static final Color SELECTION_BG = new Color(255, 235, 144);

    /** The Constant warningAlarm. */
    public static final Color WARNING_ALARM = new Color(255, 129, 46);

    /** The Constant errorAlarm. */
    public static final Color ERROR_ALARM = new Color(255, 48, 48);


    public static void main(String[] args) {
        initLookAndFeel();
        ApplicationEngine engine = null;
        try {
            engine = new ApplicationEngineImpl();
            Name2ClipRootWindow rootWindow = new Name2ClipRootWindow(engine);
            rootWindow.pack();
            rootWindow.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void initLookAndFeel() {
        Font fontNormal = new Font("Sans Serif", Font.PLAIN, 12);
        Font fontBold = new Font("Sans Serif", Font.BOLD, 12);

        applyColors();

        UIManager.getLookAndFeelDefaults().put("defaultFont", fontNormal);
        UIManager.getLookAndFeelDefaults().put("Label.font", fontBold);
        UIManager.getLookAndFeelDefaults().put("TableHeader.font", fontBold);

        UIManager.getLookAndFeelDefaults().put("Button.contentMargins", new InsetsUIResource(10, 14, 10, 14));
        UIManager.getLookAndFeelDefaults().put("Label.contentMargins", new InsetsUIResource(10, 0, 10, 0));
        UIManager.getLookAndFeelDefaults().put("TableHeader:\"TableHeader.renderer\".contentMargins",
                new InsetsUIResource(10, 14, 10, 14));

        UIManager.getLookAndFeelDefaults().put("Table.rowHeight", 90);
        UIManager.getLookAndFeelDefaults().put("Tree.rowHeight", 45);
        UIManager.getLookAndFeelDefaults().put("ComboBox.contentMargins", new InsetsUIResource(0, 0, 0, 0));
        UIManager.getLookAndFeelDefaults().put("ComboBox:\"ComboBox.listRenderer\".contentMargins",
                new InsetsUIResource(10, 14, 10, 14));
        UIManager.getLookAndFeelDefaults().put("ComboBox:\"ComboBox.textField\".contentMargins",
                new InsetsUIResource(10, 14, 10, 14));
        UIManager.getLookAndFeelDefaults().put("ComboBox:\"ComboBox.renderer\".contentMargins",
                new InsetsUIResource(0, 10, 0, 0));
        UIManager.getLookAndFeelDefaults().put("TabbedPane:TabbedPaneTab.contentMargins",
                new InsetsUIResource(10, 14, 10, 14));

        UIManager.getLookAndFeelDefaults().put("TextField.contentMargins", new InsetsUIResource(12, 14, 12, 14));
        UIManager.getLookAndFeelDefaults().put("PasswordField.contentMargins", new InsetsUIResource(12, 14, 12, 14));

        UIManager.getLookAndFeelDefaults().put("ScrollBar.thumbHeight", 40);
        UIManager.getLookAndFeelDefaults().put("ScrollBar:\"ScrollBar.button\".size", 40);
        UIManager.getLookAndFeelDefaults().put("TitledBorder.position", TitledBorder.TOP);
    }

    /**
     * Apply color bmx.
     */
    private static void applyColors() {

        //primary colors
        UIDefaults lookAndFeelDefaults = UIManager.getLookAndFeelDefaults();
        lookAndFeelDefaults.put("info", LIGHTER_BLUE);
        lookAndFeelDefaults.put("nimbusAlertYellow", WARNING_ALARM);
        lookAndFeelDefaults.put("control", Color.WHITE);

        UIManager.put("nimbusBase", DARKEST_BLUE);
        lookAndFeelDefaults.put("nimbusBase", DARKEST_BLUE);
        UIManager.put("nimbusBlueGrey", Color.LIGHT_GRAY);
        UIManager.put("nimbusDisabledText", Color.GRAY);

        UIManager.put("nimbusFocus", SELECTION_BG);

        lookAndFeelDefaults.put("nimbusFocus", SELECTION_BG);
        lookAndFeelDefaults.put("nimbusInfoBlue", DARK_BLUE);
        lookAndFeelDefaults.put("nimbusLightBackground", LIGHTEST_BLUE);

        lookAndFeelDefaults.put("nimbusOrange", WARNING_ALARM);
        lookAndFeelDefaults.put("nimbusRed", ERROR_ALARM);
        UIManager.put("nimbusSelectedText", Color.BLACK);
        lookAndFeelDefaults.put("nimbusSelectedText", SELECTION_BG);
        UIManager.put("nimbusSelectionBackground", SELECTION_BG);
        lookAndFeelDefaults.put("nimbusSelectionBackground", SELECTION_BG);

        //secondary colors

        UIManager.put("activeCaption", LIGHTEST_BLUE);

        UIManager.put("background", LIGHTEST_BLUE);
        lookAndFeelDefaults.put("background", LIGHTEST_BLUE);

        UIManager.put("controlHighlight", Color.BLACK);
        UIManager.put("controlLHighlight", Color.BLACK);
        UIManager.put("controlText", DARKEST_BLUE);

        UIManager.put("nimbusBorder", DARKEST_BLUE);
        UIManager.put("nimbusSelection", SELECTION_BG);

        UIManager.put("textForeground", Color.BLACK);

        UIManager.put("textHighlight", Color.BLACK);
        UIManager.put("textHighlightText", Color.BLACK);
        UIManager.put("textInactiveText", Color.GRAY);

        lookAndFeelDefaults.put("Table[Disabled+Selected].textBackground", Color.BLACK);
        lookAndFeelDefaults.put("Table[Enabled+Selected].textBackground", SELECTION_BG);
        lookAndFeelDefaults.put("Table[Enabled+Selected].textForeground", Color.BLACK);
        lookAndFeelDefaults.put("\"Table.editor\"[Selected].textForeground", Color.BLACK);
        lookAndFeelDefaults.put("Table.editor[Selected].textForeground", Color.BLACK);

        //button

        lookAndFeelDefaults.put("Button[Disabled].textForeground", Color.BLACK);
        lookAndFeelDefaults.put("Button.disabled", Color.YELLOW);
        lookAndFeelDefaults.put("Button.disabledText", Color.RED);
        lookAndFeelDefaults.put("Button.background", DARK_BLUE);
        UIManager.put("Button.background", DARK_BLUE);
        lookAndFeelDefaults.put("TableHeader.background", DARK_BLUE);
        UIManager.put("TableHeader.background", DARK_BLUE);
        lookAndFeelDefaults.put("TableHeader.disabled", DARK_BLUE);
        UIManager.put("TableHeader.disabled", DARK_BLUE);


    }

}
