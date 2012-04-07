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

import edu.stanford.nlp.util.CoreMap;

public class SentenceToken extends CnlpToken<CoreMap> {

	public SentenceToken(int start, int end, CoreMap t) {
		super(start, end, t);
	}
}
