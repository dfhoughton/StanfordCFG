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
 * Matches wh pronouns and personal pronouns.
 * <p>
 * <b>Creation date:</b> Sep 28, 2011
 * 
 * @author David Houghton
 * 
 */
@Reversible
public class Pronoun extends TokenRule<CnlpToken<?>> {
	private static final long serialVersionUID = 1L;

	public Pronoun() {
		super(new Label(Type.terminal, "PRO"), new CnlpRegexTest("(?:PR|W)P"));
	}

}
