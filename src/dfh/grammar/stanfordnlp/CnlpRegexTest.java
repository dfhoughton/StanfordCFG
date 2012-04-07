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
import java.util.regex.Pattern;

public class CnlpRegexTest implements CnlpTokenTest {

	private final Pattern p;

	public CnlpRegexTest(String p) {
		this(Pattern.compile(p));
	}

	public CnlpRegexTest(Pattern p) {
		this.p = p;
	}

	@Override
	public String id() {
		return p.toString();
	}

	@Override
	public int test(List<CnlpToken<?>> starting, List<CnlpToken<?>> ending,
			boolean reversed) {
		if (starting != null) {
			for (CnlpToken<?> t : starting) {
				if (t instanceof WordToken) {
					WordToken w = (WordToken) t;
					if (p.matcher(w.tag()).matches()) {
						return reversed ? w.start() : w.end();
					}
				}
			}
		}
		return -1;
	}

}
