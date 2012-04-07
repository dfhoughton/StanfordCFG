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
