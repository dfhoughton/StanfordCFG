package dfh.grammar.stanfordnlp.rules;

import dfh.grammar.Label;
import dfh.grammar.Label.Type;
import dfh.grammar.Reversible;
import dfh.grammar.stanfordnlp.CnlpRegexTest;
import dfh.grammar.stanfordnlp.CnlpToken;
import dfh.grammar.tokens.TokenRule;

/**
 * Matches nouns; includes gerunds.
 * <p>
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
@Reversible
public class Noun extends TokenRule<CnlpToken<?>> {
	private static final long serialVersionUID = 1L;

	public Noun() {
		super(new Label(Type.terminal, "N"), new CnlpRegexTest("VBG|N.*"));
	}
}
