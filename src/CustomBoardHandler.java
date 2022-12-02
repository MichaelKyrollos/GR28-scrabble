import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class handles XML parser input for custom board XML files
 *
 * @author Amin Zeina, 101186297
 * @version 4.0
 * @date December 1, 2022
 */
public class CustomBoardHandler extends DefaultHandler {

    private HashMap<String, ArrayList<int[]>> allSquaresMap;
    private String currentSquareType;

    private boolean isCoord = false;

    /**
     * Called at the start of parsing a document, initializes the allSquaresMap hashmap
     *
     * @author Amin Zeina, 101186297
     */
    @Override
    public void startDocument() throws SAXException {
        allSquaresMap = new HashMap<>();
    }

    /**
     * Called at the start of a new element
     *
     * @author Amin Zeina, 101186297
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("coord")) {
            isCoord = true;
        } else {
            currentSquareType = qName;
            allSquaresMap.put(qName, new ArrayList<>());
        }

    }

    /**
     * Called at the end of each element
     *
     * @author Amin Zeina, 101186297
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("coord")) {
            isCoord = false;
        }
    }

    /**
     * Called to process the characters of each element; creates int[] coordinates
     *
     * @author Amin Zeina, 101186297
     */

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (isCoord) {
            String[] strArray = new String(ch, start, length).split(",");
            int[] coord = new int[]{Integer.parseInt(strArray[0]), Integer.parseInt(strArray[1])};
            allSquaresMap.get(currentSquareType).add(coord);
        }
    }

    /**
     * Returns a list of coordinates of double letter premium squares
     *
     * @return a list of coordinates of double letter premium squares
     *
     * @author Amin Zeina, 101186297
     * @version 4.0
     * @date December 1, 2022
     */
    public ArrayList<int[]> getDoubleLetterSquareCoords() {
        return allSquaresMap.get("doubleLetterSquares");
    }

    /**
     * Returns a list of coordinates of triple letter premium squares
     *
     * @return a list of coordinates of triple letter premium squares
     *
     * @author Amin Zeina, 101186297
     * @version 4.0
     * @date December 1, 2022
     */
    public ArrayList<int[]> getTripleLetterSquareCoords() {
        return allSquaresMap.get("tripleLetterSquares");
    }

    /**
     * Returns a list of coordinates of double word premium squares
     *
     * @return a list of coordinates of double word premium squares
     *
     * @author Amin Zeina, 101186297
     * @version 4.0
     * @date December 1, 2022
     */
    public ArrayList<int[]> getDoubleWordSquareCoords() {
        return allSquaresMap.get("doubleWordSquares");
    }

    /**
     * Returns a list of coordinates of triple word premium squares
     *
     * @return a list of coordinates of triple word premium square
     *
     * @author Amin Zeina, 101186297
     * @version 4.0
     * @date December 1, 2022
     */
    public ArrayList<int[]> getTripleWordSquareCoords() {
        return allSquaresMap.get("tripleWordSquares");
    }
}
