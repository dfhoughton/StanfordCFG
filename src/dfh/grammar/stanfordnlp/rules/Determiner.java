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

import dfh.grammar.Label;
import dfh.grammar.Label.Type;
import dfh.grammar.Reversible;
import dfh.grammar.stanfordnlp.CnlpRegexTest;
import dfh.grammar.stanfordnlp.CnlpToken;
import dfh.grammar.tokens.TokenRule;

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
@Reversible
public class Determiner extends TokenRule<CnlpToken<?>> {
	private static final long serialVersionUID = 2L;

	public Determiner() {
		super(new Label(Type.implicit, "D"), new CnlpRegexTest(
				"W?DT|(?:PR|W)P\\$"));
	}
}
