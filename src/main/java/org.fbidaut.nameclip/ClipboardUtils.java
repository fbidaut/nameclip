package org.fbidaut.nameclip;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;


/**
 * The Class ClipboardUtils.
 */
public class ClipboardUtils {

    /**
     * Private default constructor for utility class
     */
    private ClipboardUtils() {
        //EMPTY
    }

    /**
     * Gets the clipboard data as string.
     *
     * @return the clipboard data as string
     */
    public static String getClipboardDataAsString() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipData = clipboard.getContents(clipboard);
        if (clipData != null) {
            try {
                if (clipData.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    return (String) (clipData.getTransferData(DataFlavor.stringFlavor));
                }
            } catch (UnsupportedFlavorException ufe) {
                //LOGGER.info("Flavor unsupported: ", ufe);
            } catch (IOException ioe) {
                //LOGGER.error("Data not available: ", ioe);
            }
        }
        return null;
    }
}

