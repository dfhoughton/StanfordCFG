package dfh.grammar.stanfordnlp.rules;

import dfh.grammar.Label;
import dfh.grammar.Label.Type;
import dfh.grammar.Reversible;
import dfh.grammar.stanfordnlp.CnlpRule;
import dfh.grammar.stanfordnlp.RegexTest;

/**
 * Matches prepositions.
 * <p>
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
@Reversible
public class Preposition extends CnlpRule {
	private static final long serialVersionUID = 1L;

	public Preposition() {
		super(new Label(Type.terminal, "P"), new RegexTest("IN|TO"));
	}
}
