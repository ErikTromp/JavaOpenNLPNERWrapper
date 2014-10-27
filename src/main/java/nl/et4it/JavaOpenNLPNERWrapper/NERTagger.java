package nl.et4it.JavaOpenNLPNERWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

public class NERTagger implements java.io.Serializable {
	NameFinderME nf;
	
	private static String strJoin(String[] aArr, String sSep) {
	    StringBuilder sbStr = new StringBuilder();
	    for (int i = 0, il = aArr.length; i < il; i++) {
	        if (i > 0)
	            sbStr.append(sSep);
	        sbStr.append(aArr[i]);
	    }
	    return sbStr.toString();
	}
	
	public NERTagger(String language) {
		// Load the model
	    InputStream modelIn = this.getClass().getResourceAsStream("/" + language + "-ner-location.bin");
	    TokenNameFinderModel model;
		try {
			model = new TokenNameFinderModel(modelIn);
			nf = new NameFinderME(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			modelIn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public List<String> find(String[] tokens) {
        Span[] nameSpans = nf.find(tokens);
        List<String> result = new ArrayList<String>();
        List<String> tokenList = Arrays.asList(tokens);
        for (Span nameSpan : nameSpans) {
        	List<String> sublist = tokenList.subList(nameSpan.getStart(), nameSpan.getStart() + nameSpan.length());
        	result.add(strJoin(sublist.toArray(new String[sublist.size()]), " "));
        }
        return result;
    }
}
