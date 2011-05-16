package dfh.grammar.stanfordnlp;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dfh.grammar.CachedMatch;
import dfh.grammar.Condition;
import dfh.grammar.GlobalState;
import dfh.grammar.Grammar;
import dfh.grammar.Label;
import dfh.grammar.Match;
import dfh.grammar.Matcher;
import dfh.grammar.Rule;

/**
 * Base class that makes Stanford CoreNLP tags available to a {@link Grammar}.
 * 
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
public class CnlpRule extends Rule implements Cloneable {
	private static final long serialVersionUID = 1L;

	private class CnlpMatcher extends Matcher {
		private final Map<Integer, CachedMatch> cache;
		private boolean fresh = true;

		protected CnlpMatcher(CharSequence s, Integer offset,
				Map<Label, Map<Integer, CachedMatch>> cache, Matcher master) {
			super(s, offset, master);
			this.cache = cache.get(label);
		}

		@Override
		public Match match() {
			if (options.debug)
				CnlpRule.this.matchTrace(this);
			if (fresh) {
				fresh = false;
				CachedMatch cm = cache.get(offset);
				if (cm == null) {
					if (options.study) {
						if (options.debug)
							CnlpRule.this.matchTrace(this, null);
						return null;
					}
				} else {
					if (options.debug)
						CnlpRule.this.matchTrace(this, cm.m);
					return cm.m;
				}
				CnlpCharSequence ccs = (CnlpCharSequence) s;
				if (ccs.beginsToken(offset)) {
					String tag = ccs.tag(offset);
					if (test.test(tag)) {
						String text = ccs.text(offset);
						Match m = new Match(CnlpRule.this, offset, offset
								+ text.length());
						if (c == null || c.passes(m, this, s))
							cm = new CachedMatch(m);
						else
							cm = CachedMatch.MISMATCH;
					} else
						cm = CachedMatch.MISMATCH;
				} else
					cm = CachedMatch.MISMATCH;
				cache.put(offset, cm);
				if (options.debug)
					CnlpRule.this.matchTrace(this, cm.m);
				return cm.m;
			}
			if (options.debug)
				CnlpRule.this.matchTrace(this, null);
			return null;
		}

		@Override
		protected boolean mightHaveNext() {
			return fresh;
		}

		@Override
		protected Rule rule() {
			return CnlpRule.this;
		}

		@Override
		public String toString() {
			return test.id();
		}
	}

	protected final POSTest test;
	protected Condition c;

	public CnlpRule(Label label, POSTest test) {
		super(label);
		this.test = test;
	}

	@Override
	public Matcher matcher(CharSequence s, Integer offset,
			Map<Label, Map<Integer, CachedMatch>> cache, Matcher master) {
		if (!(s instanceof CnlpCharSequence))
			throw new StanfordCFGException(CnlpRule.class
					+ " can only match against " + CnlpCharSequence.class);
		return new CnlpMatcher(s, offset, cache, master);
	}

	@Override
	protected String uniqueId() {
		return test.id();
	}

	@Override
	public String description() {
		return test.id();
	}

	@Override
	public Set<Integer> study(CharSequence s,
			Map<Label, Map<Integer, CachedMatch>> cache,
			Set<Rule> studiedRules, GlobalState options) {
		if (!(s instanceof CnlpCharSequence))
			throw new StanfordCFGException(CnlpRule.class
					+ " can only match against " + CnlpCharSequence.class);
		CnlpCharSequence ccs = (CnlpCharSequence) s;
		Map<Integer, CachedMatch> subCache = cache.get(label);
		Set<Integer> startOffsets = new HashSet<Integer>();
		if (subCache.isEmpty()) {
			if (studiedRules.contains(this))
				return startOffsets;
			else
				studiedRules.add(this);
			for (Integer i : ccs.tokenOffsets(options.start, options.end)) {
				if (test.test(ccs.tag(i))) {
					String text = ccs.text(i);
					Match n = new Match(this, i, i + text.length());
					if (c == null || c.passes(n, null, s))
						subCache.put(i, new CachedMatch(n));
					startOffsets.add(i);
				}
			}
		} else {
			startOffsets.addAll(subCache.keySet());
		}
		return startOffsets;
	}

	@Override
	public boolean zeroWidth() {
		return false;
	}

	@Override
	public Rule shallowClone() {
		return new CnlpRule((Label) label.clone(), (POSTest) test.clone());
	}

	@Override
	public Object clone() {
		return shallowClone();
	}

	@Override
	public Rule conditionalize(Condition c, String id) {
		this.c = c;
		this.condition = id;
		return this;
	}

}
