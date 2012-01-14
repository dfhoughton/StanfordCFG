package dfh.grammar.stanfordnlp.rules;

import dfh.grammar.Label;
import dfh.grammar.Reversible;
import dfh.grammar.Label.Type;
import dfh.grammar.stanfordnlp.CnlpRule;
import dfh.grammar.stanfordnlp.RegexTest;

/**
 * Matches wh pronouns and personal pronouns.
 * <p>
 * <b>Creation date:</b> Sep 28, 2011
 * 
 * @author David Houghton
 * 
 */
@Reversible
public class Pronoun extends CnlpRule {
	private static final long serialVersionUID = 1L;

	public Pronoun() {
		super(new Label(Type.terminal, "PRO"), new RegexTest("(?:PR|W)P"));
	}

}
