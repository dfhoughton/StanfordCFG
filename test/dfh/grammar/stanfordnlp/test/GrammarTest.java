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
public class GrammarTest {

	private static Grammar grammar;
	private static CnlpTokenSequenceFactory factory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		factory = new CnlpTokenSequenceFactory();
		Map<String, Rule> rules = new HashMap<String, Rule>();
		rules.put("N", new Noun());
		rules.put("D", new Determiner());
		rules.put("A", new Adjective());
		rules.put("P", new Preposition());
		rules.put("Adv", new Adverb());
		rules.put("pos", new Possessive());
		grammar = new Grammar(new String[] {
				//
				"  <NP> := [<DP> .]? <NB> | <NP> <PP>",//
				"  <DP>  = <NP> <pos> | <D>",//
				"  <NB> := <AP> <NB> | <NC>",//
				"  <NC> := [<N> .]* <N>",//
				"  <AP> := <Adv> <AP> | <AP> <PP> | <A>",//
				"  <PP> := <P> <NP>",//
		}, rules);
	}

	@Test
	public void fatCatTest() {
		TokenSequence<CnlpToken<?>> seq = factory
				.sequence("The fat cat sat on the mat.");
		Options opt = new Options();
		opt.allowOverlap(true);
		Matcher m = grammar.find(seq, opt);
		Match n;
		Set<String> correct = new HashSet<String>(), found = new HashSet<String>();
		for (String s : new String[] { "The fat cat", "fat cat", "cat",
				"the mat", "mat" })
			correct.add(s);
		while ((n = m.match()) != null) {
			found.add(n.group());
		}
		assertEquals(correct.size(), found.size());
		correct.removeAll(found);
		assertTrue(correct.isEmpty());
	}

	@Test
	public void veryFatTest() {
		TokenSequence<CnlpToken<?>> seq = factory.sequence("The very fat cat.");
		Options opt = new Options();
		opt.allowOverlap(true);
		Matcher m = grammar.find(seq, opt);
		Match n;
		Set<String> correct = new HashSet<String>(), found = new HashSet<String>();
		for (String s : new String[] { "The very fat cat", "very fat cat",
				"fat cat", "cat" })
			correct.add(s);
		while ((n = m.match()) != null) {
			found.add(seq.subSequence(n.start(), n.end()).toString());
		}
		assertEquals(correct.size(), found.size());
		correct.removeAll(found);
		assertTrue(correct.isEmpty());
	}

	@Test
	public void manMachineTest() {
		TokenSequence<CnlpToken<?>> seq = factory.sequence("The man machine.");
		Options opt = new Options();
		opt.allowOverlap(true);
		opt.longestMatch(false);
		// opt.trace(System.out);
		Matcher m = grammar.find(seq, opt);
		Match n;
		Set<String> correct = new HashSet<String>(), found = new HashSet<String>();
		for (String s : new String[] { "The man machine", "The man",
				"man machine", "man", "machine" })
			correct.add(s);
		while ((n = m.match()) != null) {
			found.add(seq.subSequence(n.start(), n.end()).toString());
		}
		assertEquals(correct.size(), found.size());
		correct.removeAll(found);
		assertTrue(correct.isEmpty());
	}

	@Test
	public void manInTheMoonTest() {
		TokenSequence<CnlpToken<?>> seq = factory
				.sequence("The man in the moon.");
		Options opt = new Options();
		opt.allowOverlap(true);
		opt.longestMatch(false);
		// opt.trace(System.out);
		Matcher m = grammar.find(seq, opt);
		Match n;
		Set<String> correct = new HashSet<String>(), found = new HashSet<String>();
		for (String s : new String[] { "The man in the moon",
				"man in the moon", "The man", "man", "the moon", "moon" })
			correct.add(s);
		while ((n = m.match()) != null) {
			found.add(seq.subSequence(n.start(), n.end()).toString());
		}
		assertEquals(correct.size(), found.size());
		correct.removeAll(found);
		assertTrue(correct.isEmpty());
	}

	@Test
	public void bobsCarTest() {
		TokenSequence<CnlpToken<?>> seq = factory.sequence("Bob's car");
		Options opt = new Options();
		opt.allowOverlap(true);
		opt.longestMatch(false);
		// opt.trace(System.out);
		Matcher m = grammar.find(seq, opt);
		Match n;
		Set<String> correct = new HashSet<String>(), found = new HashSet<String>();
		for (String s : new String[] { "Bob's car", "Bob", "car" })
			correct.add(s);
		while ((n = m.match()) != null) {
			found.add(seq.subSequence(n.start(), n.end()).toString());
		}
		assertEquals(correct.size(), found.size());
		correct.removeAll(found);
		assertTrue(correct.isEmpty());
	}

}
