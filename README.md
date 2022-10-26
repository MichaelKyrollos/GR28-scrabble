# GR28-scrabble

## Basic Overview

This project is a java version of the board game, Scrabble.
The rules of Scrabble can be found [here](https://en.wikipedia.org/wiki/Scrabble)  

The game is played on a 15x15 board and uses [this list of words](https://www.mit.edu/~ecprice/wordlist.10000) as a dictionary by default.

## Authors:
> :1st_place_medal: Amin Zeina (101186297)  
> 🥇 Michael Kyrollos (101183521)  
> 🥇Pathum Danthanarayana (101181411)  
> 🥇 Yehan De Silva (101185388)

## How to Run Project: 
1. Compile and build project 
2. Run the program (Can be done from any menu, or from ScrabbleGame.java)
3. User will be prompted the number of players
      - Must be an integer between 2-4
      - The program will keep asking for input until the user enters a valid input
5. Enter each of the player names as a String from with **no spaces** (Can be either upper or lower case)
6. Game will be started once the number of players and their names have been entered and processed. 
7. The game will cycle between all players, so each of them gets a turn

## Player Options During Their Turn
| Syntax      | Description |
| ----------- | ----------- |
| help        | Prints a list of valid commands a player can choose from |   
| quit        | Ends the scrabble game |
| skip        | Skips player's turn and starts the next player's turn |
| redraw      | Allows player to redraw tiles from the bag <br><br> Player must then speficy the number of tiles to redraw <br><br> Finally, they must specify each tile they want to redraw |
| redraw NUMBER| Redraws the specified number of tiles (NUMBER) from the bag <br><br> They must then specify each tile they want to redraw |
| play WORD LOCATION | Plays the specified word on the board to gain points. <br><br> Format: <ul><li> Must specify all 3 instructions with spaces in between </li> <li>WORD must be in all capitals. </li><li>LOCATION  must be in all capitals </li><li>LOCATION must contain row (Number) and column (Letter) of the start square of the word.</li><li>Rows go from 1-15, Columns go from A-O</li><li>If row is specified first, the word is placed horizontally</li><li>If column is specified first, the word is placed vertically</li></ui><br><br>Example: play JOKER 6D|

## Current Milestone - Milestone 1:
  - Contains an inital model for the scrabble game
  - Plays a text-based game of scrabble
      - Players can interact with the game via the console using the keyboard
      - The scrabble board is printed on the console in a board-like format
  - The game supports 2-4 players (The user specifies the number of players before the game starts)
  - Players can place a word, redraw tiles, pass their turn and see the resulting state of the board
  - The game only ends if a player specifies to end the game

## Changes since pervious deliverable: 
  - N/A (This is the first milestone)

## Roadmap for whats Ahead - Milestone 2:  
1. Fix any known issues and ensure Scrabble logic is correct when placing a word.
2. Convert the design to a Model-View-Controller design.
3. Remove text-based interaction and instead implement a GUI.
4. Add unit tests to test the code.

## Known issues:
:warning: 1. The game will continue forever until a player quits the game  
- No win condition  
- Doesn't understand if there are no more legal moves left to play  
- Crashes if no more tiles left in the bag to draw  

:warning: 2. If the player enters a lowercase coordiante, the game will crash  
⚠️ 3. The first placed word may be placed anywhere on the board, and not always placed in the middle  
⚠️ 4. A placed word may be placed individually, without connecting to any existing words  
⚠️ 5. Doesn't check validity of any new adjacent words formed by placing a new word  
⚠️ 6. Doesn't add the score of any new adjacent words formed by placing a new word.  
⚠️ 7. User input regarding commands and tiles are type sensitive, and may not work if the wrong type-case is used.  
⚠️ 8. Entering a Player's name with spaces in between creates multiple players.

## Design Decisions Made  
1. Delegation was used to ensure each class performs its rightful responsibilites and have high cohesion.
2. Linear control flow between classes was preferred when coding. For example, when placing a word, the game validates the word in the dictionary and than delegates to player to continue placing the word. The player can than validate if they have the correct tiles and pass them along to the board to place.
3. High encapsulation to increase security/privacy of code and decrease tight coupling.
4. Used public constants for fields to make them immutable, but still accessible to the rest of the program.
5. Text-file used instead of API for dictionary as API usage was limited and some APIs were missing needed operations.


## Data Structures
| Data Structure | Use |
| ----------- | ----------- |
| DictionaryNode | A dictionary node contains a boolean flag called **terminal** and an array of DictionaryNodes. <br><br> A word is created by going down the data structure and adding each letter at the correct spot in the array. <br><br>This ensures that common operations such as adding a new word or seraching for a word is O(n) where n depends on the length of the word, and not the length of the dictionary.|
| 2D array of Square Objects | Easy to represent and access indiviudal squares on the scrabble board. |
| Rack | Used to store the list of tiles a player currently has in their hand. |
| Tile | Ties together a letter and point value for all tiles in the Scrabble game. |
| TileBag| An ArrayList containing all the tiles of the game. |
| Hashmaps | Used to map frequncy values (Character-Integer) and point values (Integer-ArrayList of chars) for all the tiles in a TileBag |
| CommandWord | Contains enums for commands (E.g. redraw, skip, play, quit, etc). |

## FAQ:
I am getting a warning that my project .class is either outdated or a newer version.
1. Delete .class files in your directory (within your file explorer), navigating to GR28-scrabble\out\production\G28-Scrabble\resources
2. In IntellJ, navigate to menu item Build->Recompile
3. If above does not work, do Build->Rebuild
