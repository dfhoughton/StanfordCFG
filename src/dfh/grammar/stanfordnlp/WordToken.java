/*
 * dfh.grammar.stanfordnlp
 * adapter classes to facilitate using the Stanford CoreNLP pipeline with dfh.grammar
 * 
 * Copyright (C) 2012 David F. Houghton
 * 
 * This software is licensed under the LGPL. Please see accompanying NOTICE file
 * and lgpl.txt.
 * See http://nlp.stanford.edu/software/corenlp.shtml regarding the licensing of the Stanford
 * NLP libraries, which are distributed separately.
 */
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
