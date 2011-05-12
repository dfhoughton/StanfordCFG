package dfh.grammar.stanfordnlp.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import dfh.grammar.Grammar;
import dfh.grammar.Match;
import dfh.grammar.Matcher;
import dfh.grammar.Options;
import dfh.grammar.stanfordnlp.CnlpCharSequence;
import dfh.grammar.stanfordnlp.CnlpCharSequenceFactory;
import dfh.grammar.stanfordnlp.rules.Adjective;
import dfh.grammar.stanfordnlp.rules.Adverb;
import dfh.grammar.stanfordnlp.rules.Determiner;
import dfh.grammar.stanfordnlp.rules.Noun;
import dfh.grammar.stanfordnlp.rules.Possessive;
import dfh.grammar.stanfordnlp.rules.Preposition;

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
public class GrammarTest {

	private static Grammar grammar;
	private static CnlpCharSequenceFactory factory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		factory = new CnlpCharSequenceFactory();
		grammar = new Grammar(new String[] {
				//
				"<ROOT> = <NP>",//
				"<NP> = [<DP> <s>]? <NB> | <NP> <s> <PP>",//
				"<DP> = <NP> <pos> | <D>",//
				"<NB> = <AP> <s> <NB> | <N>",//
				"<AP> = <Adv> <s> <AP> | <AP> <s> <PP> | <A> | <N>",//
				"<PP> = <P> <s> <NP>",//
				"<s> = /\\s++/r", });
		grammar.defineRule("N", new Noun());
		grammar.defineRule("D", new Determiner());
		grammar.defineRule("A", new Adjective());
		grammar.defineRule("P", new Preposition());
		grammar.defineRule("Adv", new Adverb());
		grammar.defineRule("pos", new Possessive());
	}

	@Test
	public void fatCatTest() {
		CnlpCharSequence seq = factory
				.CnlpCharSequence("The fat cat sat on the mat.");
		Options opt = new Options();
		opt.allowOverlap(true);
		Matcher m = grammar.find(seq, opt);
		Match n;
		Set<String> correct = new HashSet<String>(), found = new HashSet<String>();
		for (String s : new String[] { "The fat cat", "fat cat", "cat",
				"the mat", "mat" })
			correct.add(s);
		while ((n = m.match()) != null) {
			found.add(seq.subSequence(n.start(), n.end()).toString());
		}
		assertEquals(correct.size(), found.size());
		correct.removeAll(found);
		assertTrue(correct.isEmpty());
	}

	@Test
	public void veryFatTest() {
		CnlpCharSequence seq = factory.CnlpCharSequence("The very fat cat.");
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
		CnlpCharSequence seq = factory.CnlpCharSequence("The man machine.");
		for (Integer i : seq.tokenOffsets()) {
			System.out.printf("%s//%s%n", seq.text(i), seq.tag(i));
		}
		Options opt = new Options();
		opt.allowOverlap(true);
		// opt.trace(System.out);
		Matcher m = grammar.find(seq, opt);
		Match n;
		Set<String> correct = new HashSet<String>(), found = new HashSet<String>();
		for (String s : new String[] { "The man machine", "man machine", "man",
				"machine" })
			correct.add(s);
		while ((n = m.match()) != null) {
			found.add(seq.subSequence(n.start(), n.end()).toString());
			// System.out.println(n);
			System.out.println(seq.subSequence(n.start(), n.end()));
			System.out.println();
		}
		assertEquals(correct.size(), found.size());
		correct.removeAll(found);
		assertTrue(correct.isEmpty());
	}

	@Test
	public void manInTheMoonTest() {
		CnlpCharSequence seq = factory.CnlpCharSequence("The man in the moon.");
		for (Integer i : seq.tokenOffsets()) {
			System.out.printf("%s//%s%n", seq.text(i), seq.tag(i));
		}
		Options opt = new Options();
		opt.allowOverlap(true);
		// opt.trace(System.out);
		Matcher m = grammar.find(seq, opt);
		Match n;
		Set<String> correct = new HashSet<String>(), found = new HashSet<String>();
		for (String s : new String[] { "The man in the moon",
				"man in the moon", "The man", "man", "the moon", "moon" })
			correct.add(s);
		while ((n = m.match()) != null) {
			found.add(seq.subSequence(n.start(), n.end()).toString());
			// System.out.println(n);
			System.out.println(seq.subSequence(n.start(), n.end()));
			System.out.println();
		}
		assertEquals(correct.size(), found.size());
		correct.removeAll(found);
		assertTrue(correct.isEmpty());
	}

	@Test
	public void bobsCarTest() {
		CnlpCharSequence seq = factory.CnlpCharSequence("Bob's car");
		for (Integer i : seq.tokenOffsets()) {
			System.out.printf("%s//%s%n", seq.text(i), seq.tag(i));
		}
		Options opt = new Options();
		opt.allowOverlap(true);
		 opt.trace(System.out);
		Matcher m = grammar.find(seq, opt);
		Match n;
		Set<String> correct = new HashSet<String>(), found = new HashSet<String>();
		for (String s : new String[] { "Bob's car", "Bob", "car" })
			correct.add(s);
		while ((n = m.match()) != null) {
			found.add(seq.subSequence(n.start(), n.end()).toString());
			// System.out.println(n);
			System.out.println(seq.subSequence(n.start(), n.end()));
			System.out.println();
		}
		assertEquals(correct.size(), found.size());
		correct.removeAll(found);
		assertTrue(correct.isEmpty());
	}

}