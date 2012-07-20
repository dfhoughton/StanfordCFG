package dfh.grammar.stanfordnlp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import dfh.grammar.Grammar;
import dfh.grammar.Match;
import dfh.grammar.Matcher;
import dfh.grammar.Options;
import dfh.grammar.Rule;
import dfh.grammar.stanfordnlp.CnlpToken;
import dfh.grammar.stanfordnlp.CnlpTokenSequenceFactory;
import dfh.grammar.stanfordnlp.rules.SentenceBoundary;
import dfh.grammar.tokens.TokenSequence;

/**
 * Checks to see whether we can match against CoreNLP annotations.
 * 
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
public class SentenceBoundaryTest {

	private static CnlpTokenSequenceFactory factory;
	private static HashMap<String, Rule> predefined;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		factory = new CnlpTokenSequenceFactory();
		predefined = new HashMap<String, Rule>();
		predefined.put("sb", new SentenceBoundary());
	}

	@Test
	public void forwardBoundaryTest1() {
		TokenSequence<CnlpToken<?>> seq = factory.sequence("I see Bob.");
		Grammar g = new Grammar("rule = /./ <sb>", predefined);
		Matcher m = g.find(seq, new Options().study(false));
		Match n = m.match();
		assertNotNull(n);
		assertEquals(".", n.group());
	}

	@Test
	public void forwardBoundaryTest2() {
		TokenSequence<CnlpToken<?>> seq = factory.sequence("I see Bob.");
		Grammar g = new Grammar("rule = /./ <sb>", predefined);
		Matcher m = g.find(seq, new Options().study(true));
		Match n = m.match();
		assertNotNull(n);
		assertEquals(".", n.group());
	}

	@Test
	public void backwardsBoundaryTest1() {
		TokenSequence<CnlpToken<?>> seq = factory
				.sequence("I see Bob. Bob sees me.");
		Grammar g = new Grammar("rule .= after <sb> /\\w/", predefined);
		Matcher m = g.find(seq, new Options().study(false));
		Match n = m.match();
		assertNotNull(n);
		assertEquals("I", n.group());
		n = m.match();
		assertNotNull(n);
		assertEquals("B", n.group());
	}

	@Test
	public void backwardsBoundaryTest2() {
		TokenSequence<CnlpToken<?>> seq = factory
				.sequence("I see Bob. Bob sees me.");
		Grammar g = new Grammar("rule .= after <sb> /\\w/", predefined);
		Matcher m = g.find(seq, new Options().study(true));
		Match n = m.match();
		assertNotNull(n);
		assertEquals("I", n.group());
		n = m.match();
		assertNotNull(n);
		assertEquals("B", n.group());
	}
}
