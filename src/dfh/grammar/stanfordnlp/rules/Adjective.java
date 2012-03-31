package dfh.grammar.stanfordnlp.rules;

import dfh.grammar.Label;
import dfh.grammar.Label.Type;
import dfh.grammar.Reversible;
import dfh.grammar.stanfordnlp.CnlpRegexTest;
import dfh.grammar.stanfordnlp.CnlpToken;
import dfh.grammar.tokens.TokenRule;

/**
 * Matches adjectives.
 * <p>
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
@Reversible
public class Adjective extends TokenRule<CnlpToken<?>> {
	public Adjective() {
		super(new Label(Type.terminal, "A"), new CnlpRegexTest("J.*"));
	}

	private static final long serialVersionUID = 1L;
}
