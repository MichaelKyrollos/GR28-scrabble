# GR28-scrabble

Scrabble

This project is a java version of the board game, Scrabble.

HOW TO: 
1. Compile and build project 
2. Run the program, (can be done from any menu, or from ScrabbleGame.java)
3. User will be prompted for number of players (int input), enter player name in String from with no spaces (can be either upper or lower case)
4. Game will be started once player number and names have been entered and processed. 
5. Each player can place a word
      - Format to insert word: 'play [word_to_insert] [location_on_board]'
      - to insert the word joker in place 6D -> play JOKER 6D
      - the word and coordinate must be in uppercase 
6. A player can skip a word using keyword 'skip'
7. List of all functions: [play  help  redraw  quit  skip] 


Current Milestone - Milestone 1:
  - Contains an inital model for the scrabble game
  - Plays a text based game of scrabble
  - The game supports 2-4 players
  - Players can play words, redraw tiles, or pass their turn
  - The game ends once there are no more tiles left to draw from, or if the players want to end the game
  

Known issues:
  - If the player enters a lowercase coordiante, the game will crash

Changes since pervious deliverable: 
  - N/A, this is the first milestone


Authors:




FAQ:

I am getting a warning that my project .class is either outdated or a newer version
  - delete .class files in your directory (within your file explorer), navigating to GR28-scrabble\out\production\G28-Scrabble\resources
  - In IntellJ, navigate to menu item Build->Recompile
  - If above does not work, do Build->Rebuild
  
Code is unexpectedly not running 
   - navigate to File->Project Structure, and ensure the settings are the same as the picture below 
![image](https://user-images.githubusercontent.com/83596468/197912247-346bfddf-e590-463d-a137-1e8f4f48a2c7.png)
