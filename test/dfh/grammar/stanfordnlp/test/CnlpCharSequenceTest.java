package dfh.grammar.stanfordnlp.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dfh.grammar.stanfordnlp.CnlpCharSequence;
import dfh.grammar.stanfordnlp.CnlpCharSequenceFactory;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class CnlpCharSequenceTest {

	private static final String SENTENCE = "the cat sat on the mat";
	private static CnlpCharSequence seq;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CnlpCharSequenceFactory factory = new CnlpCharSequenceFactory();
		seq = factory.CnlpCharSequence(SENTENCE);
	}

	@Test
	public void testCharAt() {
		for (int i = 0; i < SENTENCE.length(); i++) {
			assertEquals(SENTENCE.charAt(i), seq.charAt(i));
		}
	}

	@Test
	public void testLength() {
		assertEquals(SENTENCE.length(), seq.length());
	}

	@Test
	public void testSubSequence() {
		fail("Not yet implemented");
	}

}
