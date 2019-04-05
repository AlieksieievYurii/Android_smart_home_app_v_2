package com.yuriialieksieiev.smarthome;

import com.yuriialieksieiev.smarthome.components.Action;
import com.yuriialieksieiev.smarthome.components.PatternActionButton;
import com.yuriialieksieiev.smarthome.enums.Icons;

import org.json.JSONException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void test_PatternActionButtonFromJson_digital()
    {
        String json = "{\"icon\":\"lamp\",\"name\":\"lamp_one\",\"action\":{\"type_port\":\"digital\",\"port\":12,\"port_status\":\"high\"}}";
        Action action = new Action(12, Action.PortStatus.HIGH);
        PatternActionButton patternActionButton = new PatternActionButton(Icons.LAMP,"lamp_one",action);

        try {
            PatternActionButton p2 = new PatternActionButton(json);
            assertTrue(patternActionButton.equals(p2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test_PatternActionButtonFromJson_analog()
    {
        String json = "{\"icon\":\"lamp\",\"name\":\"lamp_one\",\"action\":{\"type_port\":\"analog\",\"port\":12,\"signal_on_port\":255}}";
        Action action = new Action(12,255);
        PatternActionButton patternActionButton = new PatternActionButton(Icons.LAMP,"lamp_one",action);

        try {
            PatternActionButton p2 = new PatternActionButton(json);
            assertTrue(patternActionButton.equals(p2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
