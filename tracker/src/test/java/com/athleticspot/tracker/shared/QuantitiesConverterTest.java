package com.athleticspot.tracker.shared;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import tech.units.indriya.unit.Units;

import javax.measure.MetricPrefix;

/**
 * @author Tomasz Kasprzycki
 */
public class QuantitiesConverterTest {

    @Test
    public void convertDistanceFromMetersTest(){
        Assertions.assertThat(QuantitiesConverter.convertDistanceFromMeters(MetricPrefix.KILO(Units.METRE), 1000f))
            .isEqualTo(1f);
    }

    @Test
    public void convertSecondsToTime() {
        Assertions.assertThat(QuantitiesConverter.convertSecondsToTime(3661))
            .isEqualToIgnoringCase("1h 1m 1s");

    }
}