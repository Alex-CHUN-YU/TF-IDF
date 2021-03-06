package tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import tfidf.POS.Tuple;

/**
 *
 * TFIDF TEST: TestFile.txt inside a total of 73 sentences(Each sentence as a document(doc)
 * calculation IDF)(the entire 73 sentences as a documents calculation TF).
 *
 * @version 1.0 2017/05/24
 * @author ALEX-CHUN-YU
 *
 */
public class TFIDF {

    /**
     * CKIP POS.
     */
    private static ArrayList<Tuple<String, String>> pos = new ArrayList<Tuple<String, String>>();

    /**
     * Each Sentence Result Of POS Word In Corpus.
     */
    private static ArrayList<String> doc = new ArrayList<String>();

    /**
     * Corpus(Inside a total of 73 sentences), In Other Words, Corpus Inside A Total Of 73
     * Documents.
     */
    private static ArrayList<ArrayList<String>> documentList = new ArrayList<ArrayList<String>>();

    /**
     * Corpus(Inside a total of 73 sentences), In Other Words, Corpus Inside A Total Of 1 Documents.
     */
    private static ArrayList<String> demoDocument = new ArrayList<String>();
    
     /**
     * Train Data.
     * @throws IOException exception
     */
    public TFIDF() throws IOException {
        POS ws = new POS();
        File file = new File(".");
        String path = file.getCanonicalPath();
        // Set Read File Initial
        String fileSeparator = System.getProperty("file.separator");
        String fileName = path + fileSeparator + "src//main//resources//TestFile.txt";
        InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
        BufferedReader read = new BufferedReader(isr);
        String str;
        // Read File
        while ((str = read.readLine()) != null) {
            //Each Sentence As A Doc
            doc = new ArrayList<String>();
            //Result Of POS
            pos = ws.seg(str);
            //Each Sentence Result Of POS
            for (int i = 0; i < pos.size(); i++) {
                //System.out.print("[" + pos.get(i).getWord() + pos.get(i).getPos() + "] ");
                doc.add(pos.get(i).getWord());
            }
            //System.out.println();
            //Calculate IDF Corpus
            documentList.add(doc);
            //Calculate TF Corpus
            demoDocument.addAll(doc);
        }
        // Close Reader
        isr.close();
        read.close();
    }

    /**
     * TF
     * @param term String represents a term
     * @return term frequency of term in document
     */
    public double tf(final String term) {
        double result = 0;
        for (String word : demoDocument) {
            if (term.equalsIgnoreCase(word)) {
                result++;
            }
        }
        return result / demoDocument.size();
    }

    /**
     * IDF
     * @param term String represents a term
     * @return the inverse term frequency of term in documents
     */
    public double idf(final String term) {
        double count = 0;
        for (ArrayList<String> idfDoc : documentList) {
            for (String word : idfDoc) {
                if (term.equalsIgnoreCase(word)) {
                    count++;
                    break;
                }
            }
        }
        return Math.log(documentList.size() / count);
    }

    /**
     * TF-IDF
     * @param term term
     * @return the TF-IDF of term
     */
    public double tfIdf(final String term) {
        return tf(term) * idf(term);
    }

}
