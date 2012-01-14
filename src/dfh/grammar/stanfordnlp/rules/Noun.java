package dfh.grammar.stanfordnlp.rules;

import dfh.grammar.Label;
import dfh.grammar.Reversible;
import dfh.grammar.Label.Type;
import dfh.grammar.stanfordnlp.CnlpRule;
import dfh.grammar.stanfordnlp.RegexTest;

/**
 * Matches nouns; includes gerunds.
 * <p>
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
@Reversible
public class Noun extends CnlpRule {
	private static final long serialVersionUID = 1L;

	public Noun() {
		super(new Label(Type.terminal, "N"), new RegexTest("VBG|N.*"));
	}
}
