package dfh.grammar.stanfordnlp.rules;

import dfh.grammar.Label;
import dfh.grammar.Label.Type;
import dfh.grammar.stanfordnlp.CnlpRule;
import dfh.grammar.stanfordnlp.RegexTest;

/**
 * Matches possessive marker <i>'s</i> or <i>'</i>.
 * 
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
public class Possessive extends CnlpRule {
	private static final long serialVersionUID = 1L;

	public Possessive() {
		super(new Label(Type.terminal, "pos"), new RegexTest("(?i)pos"));
	}
}
