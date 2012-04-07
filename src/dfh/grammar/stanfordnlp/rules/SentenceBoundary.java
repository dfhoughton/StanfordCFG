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
package dfh.grammar.stanfordnlp.rules;

import java.util.List;

import dfh.grammar.Label;
import dfh.grammar.Label.Type;
import dfh.grammar.Reversible;
import dfh.grammar.stanfordnlp.CnlpToken;
import dfh.grammar.stanfordnlp.SentenceToken;
import dfh.grammar.tokens.TokenRule;
import dfh.grammar.tokens.TokenTest;

/**
 * Matches sentence boundaries.
 * <p>
 * <b>Creation date:</b> April 1, 2011
 * 
 * @author David Houghton
 * 
 */
@Reversible
public class SentenceBoundary extends TokenRule<CnlpToken<?>> {
	public SentenceBoundary() {
		super(new Label(Type.terminal, "sentence"),
				new TokenTest<CnlpToken<?>>() {

					@Override
					public String id() {
						return "sentence";
					}

					@Override
					public int test(List<CnlpToken<?>> starting,
							List<CnlpToken<?>> ending, boolean reversed) {
						if (starting != null) {
							for (CnlpToken<?> t : starting) {
								if (t instanceof SentenceToken)
									return reversed ? t.end() : t.start();
							}
						}
						if (ending != null) {
							for (CnlpToken<?> t : ending) {
								if (t instanceof SentenceToken)
									return reversed ? t.start() : t.end();
							}
						}
						return -1;
					}
				});
	}

	private static final long serialVersionUID = 1L;
}
