package com.rahul.project.gateway.configuration.hibernate.search.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.ngram.EdgeNGramTokenFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardFilter;

public class CustomLowerCaseAnalyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        LowerCaseTokenizer tokenizer = new LowerCaseTokenizer();
        TokenStream stream = new StandardFilter(tokenizer);

        stream = new SnowballFilter(stream, "English");
        stream = new EdgeNGramTokenFilter(stream, 2, 50);
//        stream = new StopFilter(stream,  StandardAnalyzer.STOP_WORDS_SET);
        stream = new PorterStemFilter(stream);
//        stream = new CapitalizationFilter(stream);
        return new TokenStreamComponents(tokenizer, stream);
    }
}