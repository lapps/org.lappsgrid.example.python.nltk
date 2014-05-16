package org.lappsgrid.example;

import org.lappsgrid.api.Data;
import org.lappsgrid.api.WebService;
import org.lappsgrid.core.DataFactory;
import org.lappsgrid.discriminator.DiscriminatorRegistry;
import org.lappsgrid.discriminator.Types;
import org.lappsgrid.pycaller.PyCaller;
import org.lappsgrid.pycaller.PyCallerException;
import org.lappsgrid.serialization.json.JSONObject;
import org.lappsgrid.serialization.json.JsonTaggerSerialization;
import org.lappsgrid.vocabulary.Annotations;
import org.lappsgrid.vocabulary.Features;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;

public class NLTKTagger implements WebService {
    public static final  String VERSION = "0.0.1-SNAPSHOT";

    File pythonFile = null;

    public NLTKTagger(){
        try{
            pythonFile = new File(this.getClass().getResource("/nltk_tagger.py").toURI());
            System.out.println(pythonFile.getAbsolutePath());
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
    }

    @Override
    public long[] requires() {
        return new long[]{};
    }

    @Override
    public long[] produces() {
        return new long[]{};
    }

    @Override
    public Data execute(Data data) {
        {
            long discriminator = data.getDiscriminator();
            if (discriminator == Types.ERROR)
            {
                return data;
            }
            else  if (discriminator == Types.TEXT) {
                String text = data.getPayload();
                JsonTaggerSerialization json = new JsonTaggerSerialization();
                json.setTextValue(text);
                json.setProducer(this.getClass().getName() + ":" + VERSION);
                json.setType("annotation:tagger");

                // [('How', 'WRB'), ('are', 'VBP'), ('you', 'PRP'), ('?', '.')]
                List words = null;
                try {
                    words = (List)PyCaller.call(pythonFile, "tagger", text);
                } catch (PyCallerException e) {
                    e.printStackTrace();
                    String message = "Python call error: " + e;
                    return DataFactory.error(message);
                }

                // NLTK Tagger

                for (Object obj : words) {
                    Object [] token = (Object[])obj;

                    JSONObject ann = json.newAnnotation();
                    // text
                    String word = (String)token[0];
                    json.setWord(ann, word);
                    // pos
                    String pos = (String)token[1];
                    json.setCategory(ann, pos);
                }

                return DataFactory.json(json.toString());

            } else  if (discriminator == Types.JSON) {
                String textjson = data.getPayload();
                JsonTaggerSerialization json = new JsonTaggerSerialization(textjson);

                json.setProducer(this.getClass().getName() + ":" + VERSION);
                json.setType("annotation:tagger");

                // [('How', 'WRB'), ('are', 'VBP'), ('you', 'PRP'), ('?', '.')]
                List words = null;
                try {
                    words = (List)PyCaller.call(pythonFile, "tagger", json.getTextValue());
                } catch (PyCallerException e) {
                    e.printStackTrace();
                    String message = "Python call error: " + e;
                    return DataFactory.error(message);
                }

                // NLTK Tagger

                for (Object obj : words) {
                    Object [] token = (Object[])obj;

                    JSONObject ann = json.newAnnotation();
                    // text
                    String word = (String)token[0];
                    json.setWord(ann, word);
                    // pos
                    String pos = (String)token[1];
                    json.setCategory(ann, pos);
                }
                return DataFactory.json(json.toString());
            } else {
                String name = DiscriminatorRegistry.get(discriminator);
                String message = "Invalid input type. Expected Text but found " + name;
                return DataFactory.error(message);
            }
        }
    }


    @Override
    public Data configure(Data config) {
        return null;
    }
}