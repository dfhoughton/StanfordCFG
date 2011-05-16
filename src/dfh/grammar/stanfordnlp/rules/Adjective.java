package dfh.grammar.stanfordnlp.rules;

import dfh.grammar.Label;
import dfh.grammar.Label.Type;
import dfh.grammar.stanfordnlp.CnlpRule;
import dfh.grammar.stanfordnlp.RegexTest;

/**
 * Matches adjectives.
 * 
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
public class Adjective extends CnlpRule {
	private static final long serialVersionUID = 1L;

	public Adjective() {
		super(new Label(Type.terminal, "A"), new RegexTest("J.*"));
	}
}
