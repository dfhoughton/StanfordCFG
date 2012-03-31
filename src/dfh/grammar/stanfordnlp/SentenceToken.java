package dfh.grammar.stanfordnlp;

import edu.stanford.nlp.util.CoreMap;

public class SentenceToken extends CnlpToken<CoreMap> {

	public SentenceToken(int start, int end, CoreMap t) {
		super(start, end, t);
	}
}
