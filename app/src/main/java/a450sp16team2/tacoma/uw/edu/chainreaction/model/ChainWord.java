package a450sp16team2.tacoma.uw.edu.chainreaction.model;

import java.io.Serializable;

/**
 * Created by Admin on 5/3/2016.
 */
public class ChainWord implements Serializable{
    private char[] mWord;
    private char[] mDisplay;
    private int mLetterCount;
    private int mTotalLetters;
    public boolean isRevealed;

    public ChainWord(String theWord) {
        mWord = theWord.toCharArray();
        mTotalLetters = theWord.length();
        mDisplay = new char[mTotalLetters];
        mLetterCount = 0;
        isRevealed = false;
    }
    public void revealLetter() {
        if (!isRevealed) {
            mDisplay[mLetterCount] = mWord[mLetterCount];
            mLetterCount++;
            isRevealed = (mLetterCount == mTotalLetters);
        }
    }

    public void makeRevealed() {
        while(!isRevealed) revealLetter();
        isRevealed = true;
    }

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

    public String getDisplay() {
        return new String(mDisplay);
    }
}
