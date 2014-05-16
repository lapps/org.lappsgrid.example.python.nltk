package org.lappsgrid.example;

import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.lappsgrid.api.Data;
import org.lappsgrid.discriminator.Types;
import org.lappsgrid.serialization.json.JSONObject;

import java.io.IOException;

public class TestNLTKTagger {
    Data input1 = null;
    String target1 = null;

    Data input2 = null;
    String target2 = null;

    @Before
    public void setUp() throws IOException {
        input1 = new Data();
        input1.setDiscriminator(Types.TEXT);
        input1.setPayload("How are you today?");

        java.io.InputStream in =  this.getClass().getClassLoader().getResourceAsStream("tagger.json");
        target1 = IOUtils.toString(in);

        in =  this.getClass().getClassLoader().getResourceAsStream("splitter.json");
        input2 = new Data();
        input2.setDiscriminator(Types.JSON);
        input2.setPayload(IOUtils.toString(in));

        in =  this.getClass().getClassLoader().getResourceAsStream("tagger2.json");
        target2 = IOUtils.toString(in);
    }

    public static final boolean jsonEqual(String json1, String json2) {
        JSONObject obj1 = new JSONObject(json1);
        JSONObject obj2 = new JSONObject(json2);
        return obj1.toString().equals(obj2.toString());
    }

    @After
    public void tearDown(){

    }

    @Test
    public void test(){
        // Define tagger
        NLTKTagger tagger  = new NLTKTagger();

        // Test Text input
        Data output1 = tagger.execute(input1);
        System.out.println(output1.getPayload());
        System.out.println(target1);
        System.out.println();
        Assert.assertTrue("Unexpected JSON output!", jsonEqual(output1.getPayload(), target1));


        // Test JSON input
        Data output2 = tagger.execute(input2);
        System.out.println(output2.getPayload());
        Assert.assertTrue("Unexpected JSON output!", jsonEqual(output2.getPayload(), target2));
    }
}