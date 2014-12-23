package fi.nls.oskari.eu.inspire.administrativeunits;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import fi.nls.oskari.eu.elf.elevation.ELF_MasterLoD1_ElevationGridCoverage.File;
import fi.nls.oskari.eu.inspire.recipe.administrativeunits.INSPIRE_AU_AdministrativeBoundary_Parser;
import fi.nls.oskari.eu.inspire.recipe.administrativeunits.INSPIRE_AU_AdministrativeUnit_Parser;
import fi.nls.oskari.fe.TestHelper;
import fi.nls.oskari.fe.engine.BasicFeatureEngine;
import fi.nls.oskari.fe.input.XMLInputProcessor;
import fi.nls.oskari.fe.input.format.gml.StaxGMLInputProcessor;
import fi.nls.oskari.fe.input.format.gml.recipe.JacksonParserRecipe;
import fi.nls.oskari.fe.input.format.gml.recipe.ParserRecipe;
import fi.nls.oskari.fe.output.OutputStreamProcessor;
import fi.nls.oskari.fe.output.format.json.JsonOutputProcessor;

public class TestJacksonParser extends TestHelper {
    static final Logger logger = Logger.getLogger(TestJacksonParser.class);

    /**
     * 
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws XMLStreamException
     */
    @Test
    public void test_Inspire_AdministrativeBoundary_ign_es_wfs_GMLtoJSON()
            throws InstantiationException, IllegalAccessException, IOException,
            XMLStreamException {

        BasicFeatureEngine engine = new BasicFeatureEngine();

        XMLInputProcessor inputProcessor = new StaxGMLInputProcessor();

        OutputStreamProcessor outputProcessor = new JsonOutputProcessor();

        InputStream inp = getClass()
                .getResourceAsStream(
                        "/fi/nls/oskari/eu/inspire/administrativeunits/ign_es-INSPIRE-AU-AdministrativeBoundary.xml");

        try {
            inputProcessor.setInput(inp);

            java.io.File f = getTempFile("AU-Boundary-ign_es", ".json");
            logger.info(f.getAbsolutePath());
            OutputStream fouts = new FileOutputStream(f);
            try {
                outputProcessor.setOutput(fouts);

                ParserRecipe recipe = new INSPIRE_AU_AdministrativeBoundary_Parser();

                engine.setRecipe(recipe);

                engine.setInputProcessor(inputProcessor);
                engine.setOutputProcessor(outputProcessor);

                engine.process();

            } finally {
                fouts.close();
            }

        } finally {
            inp.close();
        }

    }

    /**
     * 
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws XMLStreamException
     */
    @Ignore("Too slow for regular builds")
    @Test
    public void test_Inspire_AdministrativeUnit_ign_es_wfs_GMLtoJSON()
            throws InstantiationException, IllegalAccessException, IOException,
            XMLStreamException {

        BasicFeatureEngine engine = new BasicFeatureEngine();

        XMLInputProcessor inputProcessor = new StaxGMLInputProcessor();

        OutputStreamProcessor outputProcessor = new JsonOutputProcessor();

        InputStream inp = getClass()
                .getResourceAsStream(
                        "/fi/nls/oskari/eu/inspire/administrativeunits/ign_es-INSPIRE-AU-AdministrativeUnit.xml");

        try {
            inputProcessor.setInput(inp);


            java.io.File f = getTempFile("AU-ign_es", ".json");
            logger.info(f.getAbsolutePath());
            OutputStream fouts = new FileOutputStream(f);
            try {
                outputProcessor.setOutput(fouts);

                JacksonParserRecipe recipe = new INSPIRE_AU_AdministrativeUnit_Parser();
                //recipe.getGeometryDeserializer().setIgnoreProps(true);

                engine.setRecipe(recipe);

                engine.setInputProcessor(inputProcessor);
                engine.setOutputProcessor(outputProcessor);

                engine.process();

            } finally {
                fouts.close();
            }

        } finally {
            inp.close();
        }

    }

    /**
     * 
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws XMLStreamException
     */
    @Ignore("Too slow for regular builds")
    @Test
    public void test_Inspire_AdministrativeUnit_ign_fr_wfs_GMLtoJSON()
            throws InstantiationException, IllegalAccessException, IOException,
            XMLStreamException {

        BasicFeatureEngine engine = new BasicFeatureEngine();

        XMLInputProcessor inputProcessor = new StaxGMLInputProcessor();

        OutputStreamProcessor outputProcessor = new JsonOutputProcessor();

        InputStream inp = getClass()
                .getResourceAsStream(
                        "/fi/nls/oskari/eu/inspire/administrativeunits/ign_fr-INSPIRE-AU-wfs.xml");

        try {
            inputProcessor.setInput(inp);

            java.io.File f = getTempFile("AU-ign_fr", ".json");
            logger.info(f.getAbsolutePath());
            OutputStream fouts = new FileOutputStream(f);
            try {
                outputProcessor.setOutput(fouts);

                JacksonParserRecipe recipe = new INSPIRE_AU_AdministrativeUnit_Parser();
                //recipe.getGeometryDeserializer().setIgnoreProps(true);
                engine.setRecipe(recipe);

                engine.setInputProcessor(inputProcessor);
                engine.setOutputProcessor(outputProcessor);

                engine.process();

            } finally {
                fouts.close();
            }

        } finally {
            inp.close();
        }

    }
}
