package dfh.grammar.stanfordnlp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import dfh.grammar.Grammar;
import dfh.grammar.Match;
import dfh.grammar.Matcher;
import dfh.grammar.Options;
import dfh.grammar.Rule;
import dfh.grammar.stanfordnlp.CnlpToken;
import dfh.grammar.stanfordnlp.CnlpTokenSequenceFactory;
import dfh.grammar.stanfordnlp.rules.Adjective;
import dfh.grammar.stanfordnlp.rules.Adverb;
import dfh.grammar.stanfordnlp.rules.Determiner;
import dfh.grammar.stanfordnlp.rules.Noun;
import dfh.grammar.stanfordnlp.rules.Possessive;
import dfh.grammar.stanfordnlp.rules.Preposition;
import dfh.grammar.tokens.TokenSequence;

/**
 * Checks to see whether we can match against CoreNLP annotations.
 * 
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
public class ReversalTest {

	private static Grammar grammar;
	private static CnlpTokenSequenceFactory factory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		factory = new CnlpTokenSequenceFactory();
		Map<String, Rule> predefined = new HashMap<String, Rule>();
		predefined.put("N", new Noun());
		predefined.put("D", new Determiner());
		predefined.put("A", new Adjective());
		predefined.put("P", new Preposition());
		predefined.put("Adv", new Adverb());
		predefined.put("pos", new Possessive());
		grammar = new Grammar(new String[] {
				//
				"<ROOT> = <NP>",//
				"<NP> = [<DP> <s>]? <NB> | <NP> <s> <PP>",//
				// arbitrary bit of tinkering to test reversibility
				"<DP> = <NP> <pos> | not after [ <P> <s> ] <D>",//
				"<NB> = <AP> <s> <NB> | <NC>",//
				"<NC> = [<N> <s>]* <N>",//
				"<AP> = <Adv> <s> <AP> | <AP> <s> <PP> | <A>",//
				"<PP> = <P> <s> <NP>",//
				"<s> = /\\s++/r", }, predefined);
	}

	@Test
	public void fatCatTest() {
		TokenSequence<CnlpToken<?>> seq = factory
				.sequence("The fat cat sat on the mat.");
		Options opt = new Options().longestMatch(true);
		Matcher m = grammar.find(seq, opt);
		Match n;
		Set<String> correct = new HashSet<String>(), found = new HashSet<String>();
		for (String s : new String[] { "The fat cat", "mat" })
			correct.add(s);
		while ((n = m.match()) != null) {
			found.add(seq.subSequence(n.start(), n.end()).toString());
		}
		assertEquals(correct.size(), found.size());
		correct.removeAll(found);
		assertTrue(correct.isEmpty());
	}
}
