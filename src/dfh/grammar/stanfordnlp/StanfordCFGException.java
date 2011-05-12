package dfh.grammar.stanfordnlp;

import dfh.grammar.GrammarException;

/**
 * Marks exceptions unique to this package.
 * 
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
public class StanfordCFGException extends GrammarException {
	private static final long serialVersionUID = 1L;

	public StanfordCFGException(String message) {
		super(message);
	}

}
