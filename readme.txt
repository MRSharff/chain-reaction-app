Team 2 Chain Reaction readme

1. Peer reviewers found that there was no cancel button when guessing a word, so if they forgot what the word was that they were guessing, they could not cancel and look at it again, they had to waste a guess. We fixed this by adding in a cancel button on the alertdialog that we used for guessing the word.

2.

3. SQLite data storage: We used SQLite to store local high scores for our game. The database is accessed using LocalHighscoreDB.java class in the data package. It's functions are called in the saveScore method of the GameActivity class as well as in the onCreateView method of the LocalHighscoreFragment class.

4. 
