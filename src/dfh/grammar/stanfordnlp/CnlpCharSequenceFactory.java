package dfh.grammar.stanfordnlp;

import java.util.Properties;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class CnlpCharSequenceFactory {
	private StanfordCoreNLP pipeline;

	/**
	 * Initializes a {@link StanfordCoreNLP} pipeline to tokenize, lemmatize,
	 * and tag text.
	 */
	public CnlpCharSequenceFactory() {
		// creates a StanfordCoreNLP object, with POS tagging, and lemmatization
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		pipeline = new StanfordCoreNLP(props);
	}

	public CnlpCharSequence CnlpCharSequence(String text) {
		// create an empty Annotation just with the given text
		Annotation document = new Annotation(text);

		// run all Annotators on this text
		pipeline.annotate(document);
		return new CnlpCharSequence(document);
	}
}
