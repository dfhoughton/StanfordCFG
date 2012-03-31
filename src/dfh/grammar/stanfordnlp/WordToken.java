package dfh.grammar.stanfordnlp;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;

public class WordToken extends CnlpToken<CoreLabel> {

	private final String tag;

	public WordToken(CoreLabel t) {
		super(t.beginPosition(), t.endPosition(), t);
		tag = t.get(PartOfSpeechAnnotation.class);
	}

	public String tag() {
		return tag;
	}

	public String lemma() {
		return t.get(LemmaAnnotation.class);
	}

}
