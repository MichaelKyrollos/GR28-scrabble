// Import libraries
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * The FontManager class models a font manager, where it
 * stores the two main fonts that will be used in the Scrabble
 * game GUI. The font family that is used for the GUI is called
 * Manrope, and the Regular and Bold font weight is used.
 *
 * @author Pathum Danthanarayana, 101181411
 * @version 1.0
 * @date November 13, 2022
 */
public class FontManager
{
    /** Fields **/
    private Font manropeRegular;
    private Font manropeBold;

    /** Constructor **/
    public FontManager()
    {
        try
        {
            // Get input stream of both .ttf files
            InputStream inputStream1 = getClass().getResourceAsStream("resources/Manrope-Regular.ttf");
            InputStream inputStream2 = getClass().getResourceAsStream("resources/Manrope-Bold.ttf");
            // Check if the first input stream is not null (path to Manrope Regular font is valid)
            assert inputStream1 != null;
            // Construct Manrope Regular font
            manropeRegular = Font.createFont(Font.TRUETYPE_FONT, inputStream1);
            // Check if the second input stream is not null (path to Manrope Bold font is valid)
            assert inputStream2 != null;
            // Construct Manrope Bold font
            manropeBold = Font.createFont(Font.TRUETYPE_FONT, inputStream2);
        }
        catch (FontFormatException | IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * The getManropeRegular font returns the Font object
     * created for the Manrope Regular font.
     *
     * @return the Font object for the Manrope Regular font
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     * @date November 13, 2022
     */
    public Font getManropeRegular()
    {
        return this.manropeRegular;
    }

    /**
     * The getManropeBold font returns the Font object
     * created for the Manrope Bold font.
     *
     * @return the Font object for the Manrope Bold font
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     * @date November 13, 2022
     */
    public Font getManropeBold()
    {
        return this.manropeBold;
    }
}
