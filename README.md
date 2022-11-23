# GR28-scrabble

## Basic Overview

This project is a java version of the board game, Scrabble. For more information on Scrabble, visit the [wiki](https://en.wikipedia.org/wiki/Scrabble) or the [Scrabble Website](https://scrabble.hasbro.com/en-us/faq#:~:text=Scrabble%20tile%20letter%20distribution%20is,%2D1%20and%20Blanks%2D2.).
The game is played on a 15x15 board, is compatibile with 2-4 players and uses [this list of words](https://www.mit.edu/~ecprice/wordlist.10000) as a dictionary by default.

## Authors:
🥇 Amin Zeina (101186297)  
🥇 Michael Kyrollos (101183521)  
🥇Pathum Danthanarayana (101181411)  
🥇 Yehan De Silva (101185388)

## How to Run Project:
0. (Optional but highly recommended) Install the two .ttf fonts (Manrope Regular and Manrope Bold) located in the 'resources' folder to ensure all fonts are visible on the GUI
1. Compile and build project 
2. Run the program (From ScrabbleFrameView.java)
3. Click "Let's Play!" to start the game
4. User will be prompted the number of players
      - Must be an integer between 2-4
      - The program will keep asking for input until the user enters a valid input
5. Enter each of the player names as a String from
6. Game will be started once the number of players and their names have been entered and processed. 
7. The game will cycle between all players, so each of them gets a turn
8. Game will end when either:
      - Player chooses to end game from the JMenu
      - No more tiles left to redraw and play the game

## PlayerModel Options During Their Turn 
![image](https://user-images.githubusercontent.com/61627702/203466074-7e4ebfed-66e0-414d-8c5b-f3c35403f42e.png)

| Button      | Description |
| ----------- | ----------- |
| Play        | Play tile one by one on to the board. Click "Submit" once finished. |   
| Redraw      | Select all the tiles on player's rack that they wish to redraw. Returns them to the tile bag and redraws new tiles. |
| Skip        | Skips player's turn and starts the next player's turn |
| Quit        | End the game and display winner and player scores |

## Current Milestone - Milestone 3:
  - The game supports 2-4 players (The user specifies the number of players before the game starts)
  - Players can place a word, redraw tiles, skip their turn or end the game using a GUI
  - The game now supports AI players
  - The game now supports blank tiles, which allow the players to choose the letter of the tile themselves during gameplay
      - Blank tiles are worth 0 points
      - The letter of the blank tile cannot be changed after it has been placed on the board
  - The game board now contains premium squares, which add bonus points to the placement of letters/words; there are 4 types
      - Blue squares - x2 letter square - doubles the value of the letter placed on this square
      - Green squares - x3 letter square - triples the value of the letter placed on this square
      - Red squares - x2 word square - doubles the value of the word placed on this square
      - Orange squares - x3 word square - triples the value of the word placed on this square
  - The game now has labels for the columns and rows of the board

### Changes Since Previous Deliverable - Milestone 2:
  - Refactoring done to simplify BoardModel.playWord() method by delegating to new helper methods
  - Refactoring done to ScrabbleController.actionPerformed() method by delegating to new helper methods
  - General quality checks done to reduce code smells
  - Added blank tiles to the game (see above - "Current Milestone Milestone 3")
  - Added premium squares to the game (see above - "Current Milestone Milestone 3")
  - Added AI players to the game


## Roadmap for whats Ahead - Milestone 4:  
1. Implementing multiple level undo/redo
2. Implementing save/load features
3. Implementing custom board with alternate placement of premium squares

## Known issues:
1. ⚠️ Graphical Bug with tiles on player's rack
      - Sometimes hides player tiles after a play they made is invalid and they have multiple tiles with the same letter  
      - Core functionality of the game is still intact
2. ⚠️ AI players are unable to place words
      - AI logic is able to create words from a given rack and find placements
      - in the recursive call of a function, the AI can get stuck in an infinite loop for the 
        "extend_after()" call


## Design Decisions Made  
1. Delegation was used to ensure each class performs its rightful responsibilites and have high cohesion.
2. Linear control flow between classes was preferred when coding. For example, when placing a word, the game validates the word in the dictionary and than delegates to player to continue placing the word. The player can than validate if they have the correct tiles and pass them along to the board to place.
3. High encapsulation to increase security/privacy of code and decrease tight coupling.
4. Used public constants for fields to make them immutable, but still accessible to the rest of the program.
5. Text-file used instead of API for dictionary as API usage was limited and some APIs were missing needed operations.
6. Model-View-Controller used as it provides a way to get user-input, pass it on to the model to process and then update the view.
      - Created 1 Controller to handle all user-input
      - Create a Model and View class for each of the following: ScrabbleGame, Player, Board and Rack.
      - The model takes care of the logic, and gets input from the controller
      - The view is updated whenever a change occurs to the model
8. Premium square implemented as sub-classes of Square to easily distinguish preimium squares and to avoid code duplication
      - This should also allow for easier implementation of custom boards (milestone 4)




## Data Structures
| Data Structure | Use |
| ----------- | ----------- |
| DictionaryNode | A dictionary node contains a boolean flag called **terminal** and an array of DictionaryNodes. <br><br> A word is created by going down the data structure and adding each letter at the correct spot in the array. <br><br>This ensures that common operations such as adding a new word or seraching for a word is O(n) where n depends on the length of the word, and not the length of the dictionary.|
| 2D array of Square Objects | Easy to represent and access indiviudal squares on the scrabble board. |
| RackModel | Used to store the list of tiles a player currently has in their hand. |
| Tile | Ties together a letter and point value for all tiles in the Scrabble game. |
| TileBag| An ArrayList containing all the tiles of the game. |
| Hashmaps | Used to map frequncy values (Character-Integer) and point values (Integer-ArrayList of chars) for all the tiles in a TileBag |
| JButtons | Used to extend Tile and Square classes as tiles and squares should now be clickable in the GUI |

## FAQ:
#### I am getting a warning that my project .class is either outdated or a newer version.  
1. Delete .class files in your directory (within your file explorer), navigating to GR28-scrabble\out\production\G28-Scrabble\resources
2. In IntellJ, navigate to menu item Build->Recompile
3. If above does not work, do Build->Rebuild

#### Testing is not running.  
- Download the JUnit library (Version used to write the tests is: 5.8.1)
  
#### Code is unexpectedly not running  
- navigate to File->Project Structure, and ensure the settings are the same as the picture below 
![image](https://user-images.githubusercontent.com/83596468/197912247-346bfddf-e590-463d-a137-1e8f4f48a2c7.png)
