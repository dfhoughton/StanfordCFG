package dfh.grammar.stanfordnlp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.BeforeClass;
import org.junit.Test;

import dfh.grammar.stanfordnlp.CnlpCharSequence;
import dfh.grammar.stanfordnlp.CnlpCharSequenceFactory;

/**
 * Makes sure {@link CnlpCharSequence} is working to spec.
 * 
 * TODO: add tests for subsequence methods
 * 
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
public class CnlpCharSequenceTest {

	private static final String SENTENCE = "the cat sat on the mat";
	private static CnlpCharSequence seq;
	public static final Pattern wordStartPattern = Pattern
			.compile("\\b(?=\\w)");

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
	public void lemmaTest() {
		Matcher m = wordStartPattern.matcher(SENTENCE);
		while (m.find()) {
			int i = m.start();
			assertNotNull(seq.lemma(i));
		}
	}

	@Test
	public void posTest() {
		Matcher m = wordStartPattern.matcher(SENTENCE);
		while (m.find()) {
			int i = m.start();
			String s = seq.tag(i);
			assertNotNull(s);
		}
	}

	@Test
	public void textTest() {
		Matcher m = wordStartPattern.matcher(SENTENCE);
		while (m.find()) {
			int i = m.start();
			String s = seq.text(i);
			assertNotNull(s);
		}
	}

	@Test
	public void testSubSequence() {
		try {
			seq.subSequence(0, seq.length() / 2);
		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}

}
