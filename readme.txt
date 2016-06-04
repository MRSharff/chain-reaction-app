Team 2 Chain Reaction readme

1. Peer reviewers found that there was no cancel button when guessing a word, so if they forgot what the word was that they were guessing, they could not cancel and look at it again, they had to waste a guess. We fixed this by adding in a cancel button on the alertdialog that we used for guessing the word.

2. Check

3. SQLite data storage: We used SQLite to store local high scores for our game. The database is accessed using LocalHighscoreDB.java class in the data package. It's functions are called in the saveScore method of the GameActivity class as well as in the onCreateView method of the LocalHighscoreFragment class.

4. Our app uses web services to store custom authentication credentials onto a web database. Passwords are hashed on device so no passwords are stored in plain text.

5. In our app, you can share a game score. This is tacked onto the gameover alert dialog that you see at the end of a game.

6. Not implemented

7. We created a custom icon and logo for our app following material guidelines.

8. Check

9. https://docs.google.com/document/d/1U9rDzrgwbDMJvoc9A6R48aws3UeQw_QzzUd9ETlEBEk/edit?usp=sharing
