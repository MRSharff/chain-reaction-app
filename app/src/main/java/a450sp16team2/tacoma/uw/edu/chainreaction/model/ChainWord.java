package a450sp16team2.tacoma.uw.edu.chainreaction.model;

import java.io.Serializable;

/**
 * Created by Admin on 5/3/2016.
 */

/**
 * Chain word stores game data for each individual word in the list.
 * this class handles revealing letters, guessing for results and calculating score.
 */
public class ChainWord implements Serializable{
    private char[] mWord;       //the word itself
    private char[] mDisplay;    //the current characters of the word displayed
    private int mLetterCount;   //count of how many letters have been revealed
    private int mTotalLetters;  //total number of letters in the word
    private int mScore;         //current score available for the given word
    public boolean isRevealed;  //status whether the word is fully revealed

    /**
     * constructor for the word. score is defaulted to 1200 to handle the loss of
     * points for the initial reveal.
     * @param theWord
     */
    public ChainWord(String theWord) {
        mWord = theWord.toCharArray();
        mTotalLetters = theWord.length();
        mDisplay = new char[mTotalLetters];
        mLetterCount = 0;
        mScore = 1200;
        isRevealed = false;
    }

    /**
     * reveals a letter by putting it into the display array.
     * if the word is not yet revealed fully the point value is decremented by 200.
     * point value will stay no lower than 200 until an incorrect guess reveals the word.
     */
    public void revealLetter() {
        if (!isRevealed) {
            mDisplay[mLetterCount] = mWord[mLetterCount];
            mLetterCount++;
            isRevealed = (mLetterCount == mTotalLetters);
            if (!isRevealed) {
                if (mScore > 200) mScore -= 200;
            } else mScore = 0;
        }
    }

    public int getScore() {
        return mScore;
    }

    /**
     * reveals a word
     */
    public void makeRevealed() {
        while(!isRevealed) revealLetter();
    }

    /**
     * checks a string guess to see if it matchs the stored word. returns a boolean as the result
     * and sets fields appropriately.
     * @param theGuess
     * @return
     */
    public boolean guess(String theGuess) {
        boolean rtn = false;
        if (theGuess.equals(new String(mWord))) {
            rtn = true;
            mDisplay = mWord;
            mLetterCount = mTotalLetters;
            isRevealed = true;
        }
        return rtn;
    }

    /**
     * gets the string representation of this chainword.
     * @return the word
     */
    public String getWord() {
        return new String(mWord);
    }

    /**
     * gets the display string for the word.
     * @return the letters currently displayed
     */
    public String getDisplay() {
        return new String(mDisplay);
    }
}
