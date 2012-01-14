package dfh.grammar.stanfordnlp.rules;

import dfh.grammar.Label;
import dfh.grammar.Reversible;
import dfh.grammar.Label.Type;
import dfh.grammar.stanfordnlp.CnlpRule;
import dfh.grammar.stanfordnlp.LiteralTest;

/**
 * Matches possessive marker <i>'s</i> or <i>'</i>.
 * <p>
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
@Reversible
public class Possessive extends CnlpRule {
	private static final long serialVersionUID = 1L;

	public Possessive() {
		super(new Label(Type.terminal, "pos"), new LiteralTest("POS"));
	}
}
