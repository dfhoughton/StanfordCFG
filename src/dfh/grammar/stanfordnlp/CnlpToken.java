package dfh.grammar.stanfordnlp;

import dfh.grammar.tokens.Token;
import edu.stanford.nlp.util.CoreMap;

public abstract class CnlpToken<T extends CoreMap> extends Token {
	
	protected final T t;

	public CnlpToken(int start, int end, T t) {
		super(start, end);
		this.t = t;
	}

	public T coreToken() {
		return t;
	}

}
