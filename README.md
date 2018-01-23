game
====

First project for CompSci 308 Fall 2017
__________________________________________________________________
People who worked on the project:
Just me, but I did collaborate on a conceptual level with Brandon Guo, Jeremy Chen, and Kevin Deng
__________________________________________________________________
Date started: 1/16/2018
Date finished: 1/23/2018
Estimated #hours: 20 hours
__________________________________________________________________
ATTRIBUTIONS!

Breakout class attributions: 

- JavaFX info for Breakout startUpGameScreen(){}, start(){}, certain constants (FRAMES_PER_SECOND, MILLISECOND DELAY, SECOND_DELAY)
inspired by work in lab_bounce.Example_Bounce.class, our first lab for CS308.

- info for vbox taken from https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html

- info for playing from audio clip inspired from https://github.com/fracpete/princeton-java-introduction/blob/master/stdlib/src/main/java/edu/princeton/cs/introcs/StdAudio.java, but not copied completely.

- JavaFX Timeline info taken from https://docs.oracle.com/javase/8/javafx/api/javafx/animation/Timeline.html

Level class attributions: getInput(String filename) file reading info taken from
http://www2.lawrence.edu/fast/GREGGJ/CMSC150/031Files/031Files.html

Images: All images taken from lab_bounce lab

Music: All music is royalty free and taken from https://www.dl-sounds.com/royalty-free/category/game-film/video-game/
__________________________________________________________________
Files used to get the project going:
- Music is played from the two .wav files in the audio folder.
- The levels class loads level-specific game information from all the .txt files in the levels folder.
- the rules.txt file is needed to load the splash screen at the beginning
__________________________________________________________________
How to use the file/game:
Simply click run from the src folder and use the keys given by the rules in the splash screen. The class that actually runs the game is the Breakout class, which is the main game class.
__________________________________________________________________
Decisions, assumptions, or simplifications:
A major decision I made was to have my Breakout class implement an interface called GameDelegate. The idea was that my powerup classes needed to have access to certain game components, such as the balls, paddles, or bricks, in order to be able to activate and perform their intended "powerup". In order to limit the amount of information the powerup class had about the Breakout class (keeping it encapsulated and shy), I had its activate() and revertChanges() functions take in the GameDelegate interface, which has all the functions that make powerup changes to the game. Doing this limited the scope of information PowerUp.class was able to see, while still giving it enough information to perform its powerup duty.
__________________________________________________________________
Known bugs:
1. The ball can clip through bricks on occasion, since I did not deal well with corner brick collisions.
2. When two players activate the BrickCementer powerup at the same time, the most recently cemented player's bricks disappear.
3. The end game screen flickers for some reason
__________________________________________________________________
Extra features included in my project:
My project had timers for my powerups, the interface implementation, as well as music.
__________________________________________________________________
Impressions:
I think this was a fun exercise and I got to practice a lot of good object-oriented concepts.