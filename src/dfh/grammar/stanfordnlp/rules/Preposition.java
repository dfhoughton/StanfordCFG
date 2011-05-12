package dfh.grammar.stanfordnlp.rules;

import dfh.grammar.Label;
import dfh.grammar.Label.Type;
import dfh.grammar.stanfordnlp.CnlpRule;
import dfh.grammar.stanfordnlp.RegexTest;

/**
 * Matches prepositions.
 * 
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
public class Preposition extends CnlpRule {
	private static final long serialVersionUID = 1L;

	public Preposition() {
		super(new Label(Type.terminal, "P"), new RegexTest("(?i)in"));
	}
}
