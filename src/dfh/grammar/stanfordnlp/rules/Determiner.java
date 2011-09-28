package dfh.grammar.stanfordnlp.rules;

import dfh.grammar.Label;
import dfh.grammar.Label.Type;
import dfh.grammar.stanfordnlp.CnlpRule;
import dfh.grammar.stanfordnlp.RegexTest;

/**
 * Matches determiners broadly construed: 'a', 'the', 'that', 'his', 'whose',
 * etc.. This may misfire in a construction such as 'I don't know whose it is'
 * or 'I don't know whether it's his.'
 * <p>
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
public class Determiner extends CnlpRule {
	private static final long serialVersionUID = 2L;

	public Determiner() {
		super(new Label(Type.terminal, "D"), new RegexTest("W?DT|(?:PR|W)P\\$"));
	}
}
