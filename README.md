# CS201_Assign04
Assignment 4 is for Klondike Solitaire.

Your task is to implement classes that model the game of Klondike Solitaire. A very complete set of JUnit tests is provided; if the tests pass, then you can have a high degree of confidence that your code is working correctly.

There are two milestones:

* Milestone 1: Complete the Pile, KlondikeModel, and Selection classes
* Milestone 2: Complete the KlondikeController class
Implementations of the Rank and Suit enumerations, and the Card class, are provided. These are very similar to the ones you developed in Lab 9 and Lab 10.

A few other enumerations and classes are provided. The Color enumeration represents the color of a suit: you can call the getColor method on a Suit value to determine the suit’s color (red or black.) The LocationType enumeration represents the different types of locations in the game, main deck, waste pile, foundation, and tableau. The Location class represents a location where a card (or cards) can be drawn from or moved to.

These are the classes you will need to complete:

* Pile — represents a pile of cards, similar to the Deck class from labs 9 and 10
* KlondikeModel — represents all of the Piles
* Selection — a sequence of one or more cards taken from the main deck or a tableau pile as the first step in moving cards from one pile to another
* KlondikeController — handles all of the game logic and updates to the model

There are very detailed comments for each method you will need to implement. You can also view the [API documentation](https://ycpcs.github.io/cs201-fall2016/assign/assign04javadoc/index.html) for the project.

The unit tests for KlondikeController are the most extensive and detailed tests, since they test the game logic. Note that these tests use two saved game states. You can view the image files testgame.png and testgame2.png, in the same directory as the test classes, to view these game states.

Extremely important: Do not change the name, visibility, return type, or parameter types of any of the methods in the classes you will be implementing. I will use JUnit tests to test your implementation, and my tests will not work if you modify the API of these classes. You are welcome to add additional methods: just don't change the existing ones.
