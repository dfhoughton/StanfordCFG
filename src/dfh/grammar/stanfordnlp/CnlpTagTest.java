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

import java.util.List;

public class CnlpTagTest implements CnlpTokenTest {

	private final String tag;

	public CnlpTagTest(String tag) {
		this.tag = tag;
	}

	@Override
	public String id() {
		return tag;
	}

	@Override
	public int test(List<CnlpToken<?>> starting, List<CnlpToken<?>> ending,
			boolean reversed) {
		if (starting != null) {
			for (CnlpToken<?> t : starting) {
				if (t instanceof WordToken) {
					WordToken w = (WordToken) t;
					if (tag.equals(w.tag())) {
						return reversed ? w.start() : w.end();
					}
				}
			}
		}
		return -1;
	}

}
