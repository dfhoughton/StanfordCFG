import java.util.HashMap;
import java.util.Map;

import dfh.grammar.Grammar;
import dfh.grammar.Match;
import dfh.grammar.Matcher;
import dfh.grammar.Rule;
import dfh.grammar.stanfordnlp.CnlpToken;
import dfh.grammar.stanfordnlp.CnlpTokenSequenceFactory;
import dfh.grammar.stanfordnlp.rules.Adjective;
import dfh.grammar.stanfordnlp.rules.Determiner;
import dfh.grammar.stanfordnlp.rules.Noun;
import dfh.grammar.tokens.TokenSequence;

/**
 * A silly NP chunker that only knows about nouns, adjectives, and determiners.
 * <p>
 * 
 * @author David F. Houghton - Apr 8, 2012
 * 
 */
public class NPChunker {

	public static void main(String[] args) {
		CnlpTokenSequenceFactory sequencer = new CnlpTokenSequenceFactory();
		Map<String, Rule> map = new HashMap<String, Rule>();
		map.put("A", new Adjective());
		map.put("N", new Noun());
		map.put("D", new Determiner());
		Grammar g = new Grammar("NP := <D>? [ <A> . ]* <N>", map);
		String sentence = "The fat cat sat on the mat.";
		TokenSequence<CnlpToken<?>> seq = sequencer.sequence(sentence);
		Matcher m = g.find(seq);
		Match n;
		while ((n = m.match()) != null)
			System.out.println(n.group());
	}

}
