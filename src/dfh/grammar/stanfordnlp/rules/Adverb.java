package dfh.grammar.stanfordnlp.rules;

import dfh.grammar.Label;
import dfh.grammar.Reversible;
import dfh.grammar.Label.Type;
import dfh.grammar.stanfordnlp.CnlpRule;
import dfh.grammar.stanfordnlp.RegexTest;

/**
 * Matches adverbs.
 * <p>
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
@Reversible
public class Adverb extends CnlpRule {
	private static final long serialVersionUID = 1L;

	public Adverb() {
		super(new Label(Type.terminal, "Adv"), new RegexTest("R.*"));
	}
}
