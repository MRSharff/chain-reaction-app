package a450sp16team2.tacoma.uw.edu.chainreaction;

import android.widget.ListView;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import Model.WordListGenerator;

/**
 * Created by Admin on 6/3/2016.
 */
public class WordGeneratorUnitTest extends TestCase {
    private WordListGenerator mWordGenerator;

    /**
     * initializeGrid is private and cannot be tested however we know it is working if the constructor does not crash.
     */
    @Before
    public void setUp() {
        InputStream stream = new ByteArrayInputStream("chain word\nword game\ngame show\nshow time\ntime out".getBytes());
        try {
            mWordGenerator = new WordListGenerator(6, stream);
        } catch (WordListGenerator.WordListException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBuildChain() {
        try {
            mWordGenerator.buildChain();
        } catch (Exception e) {
            fail("Internal error in the buildChain method." +
                    e.getMessage());
        }
    }

    @Test
    public void testWordListTextDocumentFormatError() {
        InputStream stream = new ByteArrayInputStream("chain \nword game\ngame show\nshow time\ntime out".getBytes());
        try {
            mWordGenerator = new WordListGenerator(6, stream);
            fail("The WordListGenerator is not ensuring data properly formatted.\n a line contains only 1 word");
        } catch (WordListGenerator.WordListException e) {

        }
        stream = new ByteArrayInputStream("chain word game\nword game\ngame show\nshow time\ntime out".getBytes());
        try {
            mWordGenerator = new WordListGenerator(6, stream);
            fail("The WordListGenerator is not ensuring data properly formatted.\n a line contains 3 words.");
        } catch (WordListGenerator.WordListException e) {

        }
        stream = new ByteArrayInputStream("chain word\n\ngame show\nshow time\ntime out".getBytes());
        try {
            mWordGenerator = new WordListGenerator(6, stream);
            fail("The WordListGenerator is not ensuring data properly formatted.\n a line contains no words.");
        } catch (WordListGenerator.WordListException e) {

        }
    }

    @Test
    public void testGetWordList() {
        List<String> list = new ArrayList<String>();
        list.add("chain");
        list.add("word");
        list.add("game");
        list.add("show");
        list.add("time");
        list.add("out");

        mWordGenerator.buildChain();
        List<String> testList = mWordGenerator.getWordList();

        for (int i = 0; i < 6; i++) {
            assertTrue(list.get(i).equals(testList.get(i)));
        }
    }

    @Test
    public void testSetSize() {
        mWordGenerator.setSize(3);
        mWordGenerator.buildChain();
        List<String> testList = mWordGenerator.getWordList();
        assertEquals(3, testList.size());
    }
}
