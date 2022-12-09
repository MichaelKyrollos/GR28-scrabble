# GR28-scrabble

## Basic Overview

This project is a java version of the board game, Scrabble. For more information on Scrabble and/or to see the rules, visit the [wiki](https://en.wikipedia.org/wiki/Scrabble) 
or the [Scrabble Website](https://scrabble.hasbro.com/en-us/faq#:~:text=Scrabble%20tile%20letter%20distribution%20is,%2D1%20and%20Blanks%2D2.).
The game is played on a 15x15 board, is compatible with 2-4 players and uses [this list of words](https://www.mit.edu/~ecprice/wordlist.10000) as a dictionary by default.

## Authors:
🥇 Amin Zeina (101186297)  
🥇 Michael Kyrollos (101183521)  
🥇 Pathum Danthanarayana (101181411)  
🥇 Yehan De Silva (101185388)

## How to Run Project:
1. Compile and build project 
2. Run the program (From ScrabbleFrameView.java)
3. User will be prompted for a [custom XML board](#what-is-the-format-for-custom-board-xml-files)
      - If the user wants a custom board, the user will be prompted to choose an XML file
      - Otherwise, (Or if the custom XML board is invalid), the default scrabble board will be used
4. Click "Let's Play!" to start the game (Or load a Scrabble game from the menu and begin playing)
5. User will be prompted for the number of real players
      - Must be an integer between 0-4
      - The program will keep asking for input until the user enters a valid input
6. Enter each of the player names as a String from
7. User will be prompted for the number AI of players
      - Must be an integer between 0-(4 - # real players)
      - The program will keep asking for input until the user enters a valid input
8. Game will be started once the number of players and their names have been entered and processed. 
9. The game will cycle between all players, so each of them gets a [turn](#player-options-during-their-turn)
10. Game will end when either:
      - Player chooses to end game from the JMenu
      - No more tiles left to redraw and play the game

## Player Options During Their Turn 
![image](https://user-images.githubusercontent.com/69320325/206482898-b92d2aee-3e68-4939-bcce-665af86ec21c.png)

| Button | Description                                                                                                         |
|--------|---------------------------------------------------------------------------------------------------------------------|
| Play   | Play tile [one by one](#the-word-i-am-playing-is-not-properly-recognized-by-the-program) on to the board. Click "Submit" once finished.                                                 |   
| Redraw | Select all the tiles on player's rack that they wish to redraw. Returns them to the tile bag and redraws new tiles. |
| Skip   | Skips player's turn and starts the next player's turn                                                               |

| Menu Item | Description                                        |
|-----------|----------------------------------------------------|
| Undo      | Go back to the start of the previous player's turn |
| Redo      | Return to current turn if an "undo" was made       |
| Save Game | Save the current game to be resumed later          |
| Load Game | Load an existing game to resume playing            |
| Quit      | End the game and display winner and player scores  |

## Current Milestone - Milestone 4:
  - The game supports 2-4 players, with any combination of real and AI players
  - Players can place a word, redraw tiles, skip their turn, undo, redo, save, load or end the game using a GUI
  - The game supports AI players
  - The game supports blank tiles, which allow the players to choose the letter of the tile themselves during gameplay
      - Blank tiles are worth 0 points
      - The letter of the blank tile cannot be changed after it has been placed on the board
  - The game board contains premium squares, which add bonus points to the placement of letters/words; there are 4 types
      - ![#5DC3EB](https://placehold.co/15x15/5DC3EB/5DC3EB.png) `Blue squares - x2 letter square - Doubles the value of the letter placed on this square`
      - ![#71D271](https://placehold.co/15x15/71D271/71D271.png) `Green squares - x3 letter square - Triples the value of the letter placed on this square`
      - ![#FF7878](https://placehold.co/15x15/FF7878/FF7878.png) `Red squares - x2 word square - Doubles the value of the word placed on this square`
      - ![#F3AA44](https://placehold.co/15x15/F3AA44/F3AA44.png) `Orange squares - x3 word square - Triples the value of the word placed on this square`
  - The game now supports custom boards, which the user can load using an XML file at the start of the game
      - Custom boards allow the user to customize the location of any and all premium squares
      - An example of XML formatting for custom boards can be found in this project [(src/resources/customBoardExample.xml)](#what-is-the-format-for-custom-board-xml-files)
      - See FAQ below for detailed instructions on creating custom board XML files
  - The game now supports undo/redo for each move made by a player (both real and AI)
  - The game now allows users to save their game, as well as load a previously saved game

### Changes Since Previous Deliverable - Milestone 3:
  - General refactoring and QC across all classes
  - Fixed functionality of AI players
  - Added tear downs in all test classes to ensure all tests pass
  - Added global undo/redo functionality
  - Added the ability to save/load a game 
  - Added the ability to import custom boards
  - Found and fixed an issue where players could use existing tiles on the board as their own without playing any new tiles
  - Players can now unselect a selected existing square on the board while playing a word
  - Players no longer need to place words in order for it to be recognized

## Design Decisions Made  
1. Delegation was used to ensure each class performs its rightful responsibilities and have high cohesion.
2. Linear control flow between classes was preferred when coding. For example, when placing a word, the game validates the word in the dictionary and then delegates to player to continue placing the word. The player can than validate if they have the correct tiles and pass them along to the board to place.
3. High encapsulation to increase security/privacy of code and decrease tight coupling.
4. Used public constants for fields to make them immutable, but still accessible to the rest of the program.
5. Text-file used instead of API for dictionary as API usage was limited and some APIs were missing needed operations.
6. Model-View-Controller used as it provides a way to get user-input, pass it on to the model to process and then update the view.
      - Created 1 Controller to handle all user-input
      - Create a Model and View class for each of the following: ScrabbleGame, Player, Board and Rack.
      - The model takes care of the logic, and gets input from the controller
      - The view is updated whenever a change occurs to the model
7. Premium square implemented as subclasses of Square to easily distinguish premium squares and to avoid code duplication
      - This allows for a streamlined implementation of custom boards (completed in milestone 4)

8. For the AI:
      **Overall Architecture:**
      - The AI logic is abstracted from the rest of the game. There is a suite of classes dedicated for the AI: AIBoard, AI, AIPlayerModel, LetterTree. The current               AI implementation is based off of the findings of a research paper known as [The World's Fastest Scrabble Game](https://www.cs.cmu.edu/afs/cs/academic/class/15451-s06/www/lectures/scrabble.pdf) which outlines data structures and architecture necessary to achieve a high speed logic. 

      **How the AI Achieves High Efficiency**
      - This AI can compute an average of over 400 legal moves per turn in a fraction of a second. It runs in constant time which is an outstanding achievement. The high speed comes from the use of what is known as the *backtracking search strategy*. 
      - The AI will never consider placing a tile that isn't already part of some word on the board (Except the first turn).
      - The algorithm begins its search from the anchor squares (squares that are adjacent to tiles already on the board) which will guarantee that the word created is adjacent to another tile. If the word search was done right away, there would be many moves created where the AI would fail to connect the partial word to tiles on the board
      
      **The Suite of classes needed to achieve it**
      - ``AIPlayerModel.java``: This class implements the ScrabblePlayer interface, allowing all players (AI or human) to be interpreted at the same level (necessary when adding points, creating a rack, etc.) and stored in the same list. The ``AIPlayerModel`` class is necessary for the encapsulation of the extra computation necessary to mimic a ``ScrabblePlayer``. Since the AI will also be using many ``PlayerModel.java`` methods, it will be extending this class and using some relevant methods (i.e. ``playWord()``). The class is responsible for handling a new move created by the AI, known as an ``AIMove`` -> this is a subclass of ``AI.java``. The ``AIPlayerModel`` will be responsible for converting the ``AIMove`` to a PlayWordEvent which is a format understandable by the ``PlayerModel``. 
      - ``AI.java``: This class contains all the logic for the necessary to create a Scrabble move. It scans the board and uses its own rack to create ALL possible moves, given the circumstances. It will return an ``AIBoard`` for each move which represents what the board will reassemble once the AI makes that move. The AI logic will also iterate through all the moves that it has created and will return the highest scoring one, creating an ``AIMove`` which can then be processed by the ``AIPlayerModel``
      - ``AIBoard.java``: This class models uses a copy of the BoardModel in a char[][] format. This allows all calculations that the AI makes to be done on primitive data types (char), vastly simplifying the logic code in ``AI.java``.
      - ``LetterTree.java``: This class uses a text file containing a list of words that can be used to create the dictionary. This dictionary uses the tree-node structure specifically implemented for the use-cases of the AI. This prevents access issues to ``ScrabbleDictionary.java``.
      
      
      **Notes about the AI**
      - When it is an AI's turn, you will need to click the **Play** button and then **Submit**. You do not need to place any tiles. The AI will then do the necessary work to place the tiles on the board and its score will be updated automatically. 
      - The AI's tiles can be redrawn by the user, if the user wishes. 
      - In the case that the AI cannot play a move with the given rack and board, it will automatically redraw the tiles and the turn will go to the next player. 
      - If the AI receives a blank tile, it will set the letter on that tile to 'A' once it places it on the board. 
      
9. CustomBoardHandler, a subclass of org.xml.sax.helpers.DefaultHandler, used to handle XML parsing for custom boards
     - CustomBoardHandler parses XML files based on the required formatting of custom board XML files
     - CustomBoardHandler extracts the coordinate data for each type of premium square
      

## Data Structures
| Data Structure             | Use                                                                                                                                                                                                                                                                                                                                                                                                |
|----------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| DictionaryNode             | A dictionary node contains a boolean flag called **terminal** and an array of DictionaryNodes. <br> A word is created by going down the data structure and adding each letter at the correct spot in the array. <br>This ensures that common operations such as adding a new word or searching for a word is O(n) where n depends on the length of the word, and not the length of the dictionary. |
| 2D array of Square Objects | Easy to represent and access individual squares on the scrabble board.                                                                                                                                                                                                                                                                                                                             |
| RackModel                  | Used to store the list of tiles a player currently has in their hand.                                                                                                                                                                                                                                                                                                                              |
| JButtons                   | Used to extend Tile and Square classes as tiles and squares should now be clickable in the GUI                                                                                                                                                                                                                                                                                                     |
| Tile                       | Ties together a letter and point value for all tiles in the Scrabble game.                                                                                                                                                                                                                                                                                                                         |
| BlankTile                  | Subclass of Tile - represents a blank tile, which has a customizable letter                                                                                                                                                                                                                                                                                                                        |
| TileBag                    | An ArrayList containing all the tiles of the game.                                                                                                                                                                                                                                                                                                                                                 |
| Square                     | Subclass of JButton - used to represent a square on the board, which holds a tile                                                                                                                                                                                                                                                                                                                  |
| LetterPremiumSquare        | Subclass of Square - used to represent a premium square which doubles the letter value of the tile placed on it                                                                                                                                                                                                                                                                                    |
| WordPremiumSquare          | Subclass of Square - used to represent a premium square which doubles the word value of the tiles placed on it                                                                                                                                                                                                                                                                                     |
| Hashmaps                   | Used to map frequency values (Character-Integer) and point values (Integer-ArrayList of chars) for all the tiles in a TileBag                                                                                                                                                                                                                                                                      |
| Stack                      | Used to implement the undo/redo feature. <br> Because undoing/redoing is going back to the last turn (LIFO), a stack is a good way of implementing LIFO.                                                                                                                                                                                                                                           |
| XML                        | Used as a method to store information regarding premium squares. It can be parsed by an XML parser to extract the locations of all premium squares.                                                                                                                                                                                                                                                |

## FAQ:

#### The word I am playing is not properly recognized by the program
Ensure you have selected **all** tiles that are part of your word, **including** tiles that have already been placed on the board. Selected tiles are highlighted with a white border. See below for the proper play of the word "ten" using the existing "t" on the board. 
![image](https://user-images.githubusercontent.com/69320325/206480625-d7ea8311-85cf-4ce1-8465-5f747968fef7.png)

#### What is the format for Custom Board XML Files
- Coordinates should be enclosed in "coord" tags, with one coordinate per tag, with x and y coordinate values seperated by a comma (,)
- An example of a custom board XML is shown below:
```xml
<board>
	<doubleLetterSquares>
        	<!--Enter coordinates for double letter squares here-->
		<coord>0,0</coord>
	</doubleLetterSquares>
	<tripleLetterSquares>
        	<!--Enter coordinates for triple letter squares here-->
		<coord>1,0</coord>
		<coord>1,2</coord>
	</tripleLetterSquares>
	<doubleWordSquares>
        	<!--Enter coordinates for double word squares here-->
		<coord>2,0</coord>
		<coord>2,1</coord>
		<coord>2,2</coord>	
	</doubleWordSquares>
	<tripleWordSquares>
        	<!--Enter coordinates for triple word squares here-->
		<coord>3,0</coord>
		<coord>3,1</coord>
		<coord>3,2</coord>	
	</tripleWordSquares>
</board>
```
- The above XML results in the following custom board:
![image](https://user-images.githubusercontent.com/36240585/206084568-638f5e16-e8d4-40db-be29-84c2dbe8b186.png)

#### I cannot undo to get to moves I previously made after reloading a saved game.
- The moves made before a game is saved are not saved, meaning that undoing past a game save is not supported

#### I am getting a warning that my project .class is either outdated or a newer version.  
1. Delete .class files in your directory (within your file explorer), navigating to GR28-scrabble\out\production\G28-Scrabble\resources
2. In IntelliJ, navigate to menu item Build->Recompile
3. If above does not work, do Build->Rebuild

#### Testing is not running.  
- Download the JUnit library (Version used to write the tests is: 5.8.1)
  
#### Code is unexpectedly not running  
- navigate to File->Project Structure, and ensure the settings are the same as the picture below 
![image](https://user-images.githubusercontent.com/83596468/197912247-346bfddf-e590-463d-a137-1e8f4f48a2c7.png)


