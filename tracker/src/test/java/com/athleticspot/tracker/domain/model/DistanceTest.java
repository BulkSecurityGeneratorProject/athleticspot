package com.athleticspot.tracker.domain.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import si.uom.SI;
import systems.uom.common.USCustomary;
import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;

import javax.measure.Quantity;
import javax.measure.quantity.Length;

import static javax.measure.MetricPrefix.KILO;
import static tech.units.indriya.unit.Units.METRE;


/**
 * @author Tomasz Kasprzycki
 */
public class DistanceTest {

    @Test
    public void testSimpleDistanceDefinition(){
        final double distanceInMeters = 123.22;
        final Quantity<Length> distance = createDistance(distanceInMeters);
        Assertions.assertThat(distance.getUnit()).isEqualTo(METRE);
    }

    @Test
    public void convertMetersToKilemetersTest(){
        final double meters = 1000.00;
        final ComparableQuantity<Length> quantity = Quantities.getQuantity(meters, METRE).to(KILO(METRE));
        Assertions.assertThat(quantity.getUnit()).isEqualTo(KILO(METRE));
        Assertions.assertThat(quantity.getValue()).isEqualTo(1.00);

    }

    @Test
    public void testKilometersToMilesConversion(){
        final Quantity<Length> lengthQuantity = convertToMile(10000.00);
        Assertions.assertThat(lengthQuantity.getUnit()).isEqualTo(USCustomary.MILE);
        Assertions.assertThat(lengthQuantity.getValue()).isEqualTo(6.21371);
    }

    private Quantity<Length> createDistance(Double distanceInMetres){
        final Quantity<Length> quantity = Quantities.getQuantity(distanceInMetres, METRE);
        return quantity;
    }

    private Quantity<Length> convertToMile(Double distance){
        Quantity<Length> len = Quantities.getQuantity(distance, SI.METRE);
        Quantity<Length> mile = len.to(USCustomary.MILE);
        return mile;
    }
}