=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: dkrupa
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2-D array
  	I store information about fields in a board in a 2-D array with dimensions of 
  	a board, each element of array representing different field in a board. 
  	Each element is either MineField or SafeField object with information about 
  	that specific field. It is being used internally by BoardModel, the external
  	classes using BoardModel can refer to specific fields by their coordinates in
  	the board, not by indices in 2-D array.

  2. File I/O
  	I use I/O in my game to store data about 5 highest scores for each of 3 levels. 
  	Scores are being determined by time spent to win the game on a particular level 
  	(so the lower score, the better). With every score, there is associated a nickname 
  	(containing lowercase and uppercase letter, numbers and spaces so that a person 
  	can write his name and surname, with maximum of 25 letters and minimum of 1 letter, 
  	nicknames can be duplicated, it is impossible to save the score with an inapproriate
  	nickname - the game keep asking about nickname when typed incorrectly, also the
  	person who score high can choose not to save his score in the table). The file has
  	format of uppercase levels names in separate lines followed by 0-5 lines of  
  	nickname-score pairs where nickname is sorrounded by quotation marks " and followed
  	by the score - the order in the file is not important. If the file is corrupted,
  	which means it doesn't exist/has inapproriate format, it is being deleted and a new
  	file is created. When a level in the file is missing, the program will add this level
  	without removing the file. The file is being read into a map of levels and maps of
  	nicknames and scores in a particular level. Different scores are being displayed in
  	different levels. The scores are being sorted from the lowest score (time) to the 
  	highest when displayed. The score can be written when the player wins the game with
  	score at least as low as the person who has the 5th place (then the person which
  	had the 5th place is being removed from the list of winners).
  	
  3. Inheritance/Subtyping for Dynamic Dispatch 
	Every element of the 2-D array with information about fields in the board is an object 
	of class either MineField (if this is a field containing a mine) or SafeField (if this 
	is a field not containing a mine). Both are children of an abstract BoardField class. 
	BoardField class enables the BoardModel class to call flag() or discover() methods without 
	knowing whether the field is MineField or SafeField through dynamic dispatch. BoardField
	is an abstract class because there is some common behavior of MineField and SafeField
	(for example, flagging a field in both cases should result in adding a flag icon on the
	field), however, there is also a part of behavior which is specific to the type of field
	(for example, if we flagged the last mine, we should win the game, or we uncover mine,
	we should lose the game, and if we uncover the safe field, it should be replaced be the
	number of adjacent mines). 

  4. Recursion
	I use recursion when it occurs that after calling SafeField->uncoverListener(), the field 
	which is being uncovered has no adjacent mines, then I call BoardModel->uncover() which 
	calls BoardField->uncover() (since that field had no adjacent mines, in all cases it calls 
	SafeField->uncoverListener()) for every adjacent field. In such a way, recursively, we 
	uncover at least 1 field (including the first one which called the recursion) with no 
	adjacent mines and all adjacent fields to them (as it happens in the traditional 
	MineSweeper). It is non-trivial to do it without recursion and this way of doing it is the
	more efficient one.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
	BoardField - an abstract class which is being used for dynamic dispatch in the 2-D array
	in BoardModel, it provides common functions of fields in the board.
	BoardModel - a class which is dealing with the encapsulated model of the game: board,
	dimension, current state etc.
	Button - a "generator" of custom MineSweeper-styled buttons
	ChooseLevel - the first panel after entering the game in which the player chooses the
	level of difficulty of the game before starting playing
	ControlPanel - the panel with counters and buttons controlling the game together with
	its functions
	Coordinate - a class which stores information about the x and y coordinate (destined
	for referring to the fields of the board)
	Counter - a counter element together with basic functions of the counter, used as a
	counter of remaining flags and as a parent class of the timer
	Game - the main class through which the game is being started, creates the main JFrame
	GamePanel - the panel displaying and building the board
	GameTimer - a child of counter, responsible for the general functions of the timer
	Level - enum with information about dimension of the board and number of mines in the
	specific level
	MineField - the child of BoardField, responsible for specific functions of mine fields
	SafeField - the child of BoardField, responsible for specific functions of fields which
	does not contain a mine
	Scores - the class responsible for I/O, displaying the table with scores, and dealing
	with the user to store high scores in the external file
	TimeListener - an interface used by GameTimer to call a method provided in the specific
	implementation after each second passed (in the case of this game, BoardField implements
	the GameTimer with TimeListener checking if 999 seconds passed and the player should lose
	the game because of that)

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
	No.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
	There is a good separation of functionality, however it could be a little better in the 
	Scores class. I would refactor long methods in Scores class by dividing them into smaller
	helper methods. Private state is correctly encapsulated, BoardModel encapsulates the state
	of the game correctly, that and other classes store only information which they necessarily
	need and show only necessary information to the "outside world."


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
	ORACLE Java documentation
	Some of Microsoft MineSweeper graphics
	iconarchive.org - cup icon