package dfh.grammar.stanfordnlp;

import java.io.Serializable;
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
import dfh.grammar.Label.Type;

/**
 * Base class that makes Stanford CoreNLP tags available to a {@link Grammar}.
 * 
 * <b>Creation date:</b> May 12, 2011
 * 
 * @author David Houghton
 * 
 */
public class CnlpRule extends Rule implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;

	private class CnlpMatcher extends Matcher {
		private final Map<Integer, CachedMatch> cache;
		private boolean fresh = true;

		protected CnlpMatcher(Integer offset,
				Map<Integer, CachedMatch>[] cache, Matcher master) {
			super(offset, master);
			this.cache = cache[cacheIndex];
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

	/**
	 * Creates {@link CnlpRule} with given test and label.
	 * 
	 * @param label
	 * @param test
	 */
	public CnlpRule(Label label, POSTest test) {
		super(label);
		this.test = test;
	}

	/**
	 * Convenience method that delegates the labeling of the rule to
	 * {@link POSTest#id()}.
	 * 
	 * @param test
	 */
	public CnlpRule(POSTest test) {
		this(new Label(Type.terminal, test.id()), test);
	}

	@Override
	public Matcher matcher(Integer offset, Map<Integer, CachedMatch>[] cache,
			Matcher master) {
		if (!(master.options.seq() instanceof CnlpCharSequence))
			throw new StanfordCFGException(CnlpRule.class
					+ " can only match against " + CnlpCharSequence.class);
		return new CnlpMatcher(offset, cache, master);
	}

	@Override
	protected String uniqueId() {
		return test.id();
	}

	@Override
	public Set<Integer> study(CharSequence s,
			Map<Integer, CachedMatch>[] cache, GlobalState options) {
		if (!(s instanceof CnlpCharSequence))
			throw new StanfordCFGException(CnlpRule.class
					+ " can only match against " + CnlpCharSequence.class);
		CnlpCharSequence ccs = (CnlpCharSequence) s;
		Map<Integer, CachedMatch> subCache = cache[cacheIndex];
		Set<Integer> startOffsets = new HashSet<Integer>();
		if (subCache.isEmpty()) {
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
	public Rule sClone() {
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

	@Override
	public String description(boolean withinBrackets) {
		StringBuilder b = new StringBuilder(test.id());
		if (condition != null)
			b.append(" (").append(condition).append(')');
		return wrap(b);
	}

	@Override
	protected Boolean mayBeZeroWidth(Map<String, Boolean> cache) {
		Boolean b = false;
		cache.put(uid(), b);
		return b;
	}

}
