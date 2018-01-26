PROVIDES THE HIGH-LEVEL DESIGN GOALS OF YOUR PROJECT
My goal is to create a game of breakout that has bricks, paddles, and powerups. It should do this in a way that uses inheritance and incorporates modularity. It should also be flexible in the sense that there isn't much code that is repeated throughout my classes.

EXPLAINS, IN DETAIL, HOW TO ADD NEW FEATURE TO YOUR PROJECT
To add a new feature to my project, I would need to do different things depending on what type of feature. If I wanted to add another brick, all I would have to do would be to create a class that extends the abstract Brick class and include methods (and an image) that is specific to this class. If I wanted to add another powerup, I would have to do the same thing. If I wanted to add another level, I would need to create 2 configuration .txt files and increase the maximum level count at which the game transitions to the end game screen. In general, the inheritance hierarchy of my classes allows for simple creation of new objects that are similar to the classes currently in the hierarchy.

JUSTIFIES MAJOR DESIGN CHOICES, INCLUDING TRADE-OFFS (I.E. PROS AND CONS), MADE IN YOUR PROJECT

One major design choice that I decided to make within my code was the decision to keep the UI and the game in the same class. I realize that this was not the best decision, as there are much more pros to separating the two into two different classes. Some pros include making it easier to debug/being able to understand the purposes of the two classes (UI class and Breakout game class). 

Another major design choice that I feel was right to make was my decision to have the Breakout class implement an interface called GameDelegate, which has functions that PowerUps, Paddle, and the special brick subclasses use when they don't have much information about their surroundings. There really are only pros for this, since the interface limits the amount of information given to these classes and acts as a delegate that performs duties for these classes. This allows for generecity between method calls (i.e. all powerups have an activate method rather than cloneBall(), speedupPaddle()).

Another major design choice was to use a lot of abstraction in my project. By this, I mean creating several hierarchies of classes and subclasses of these classes. I did this for flexibility reasons; if I ever decided I wanted to change what type of information a ScreenObject would have and I had failed to have my game objects extend ScreenObject, I would have needed to make changes to all of the game object classes. I avoided this by having this hierarchy, which allowed me to define similar attributes between certain objects, as well as make my code more readable/understandable.

STATES ANY ASSUMPTIONS OR DECISIONS MADE TO SIMPLIFY OR RESOLVE AMBIGUITIES IN YOUR PROJECT'S FUNCTIONALITY

I had to assume that we only needed to make 3 different levels as well as 3 different paddle abilities. This was a given. I made no further assumptions that were outside the contexts of the project specifications.