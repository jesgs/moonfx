package com.jesgs.moonfx;

import com.jesgs.moonfx.MoonFx;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoonFxTest {
    private MoonFx moonFx;
    private String decimalFormat = "#.##";

    @Before
    public void setUp() throws Exception {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime testDate = LocalDateTime.of(2017, Month.FEBRUARY, 11, 0, 33, 0);

        moonFx = new MoonFx();
        moonFx.setDate(testDate);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testJulianDate() {
        double julianDateFor = 2457795.521484375; // 00:33 on Feb 11th, 2017
        double calculatedJulianDate = moonFx.getJulianDate();
        long delta = 0;

        assertEquals(julianDateFor, calculatedJulianDate, delta);
    }

    @Test
    public void testPhaseAngle() {
        // some calculations put Full Moon phase angle as 0 degrees,
        // others use 180. I've chosen to use 180 since the moon is at
        // opposition from the sun during a full moon.
        double synodicAge = moonFx.getSynodicPhase();
        double phaseAngle = moonFx.getPhaseAngle(synodicAge);
        double expectedPhaseAngle = 180;
        double delta = 5;

        assertEquals(expectedPhaseAngle, phaseAngle, delta);
    }

    @Test
    public void testIlluminatedRatio() {
        DecimalFormat df = new DecimalFormat(decimalFormat);
        double synodicAge = moonFx.getSynodicPhase();
        double illuminatedRatio = Double.valueOf(df.format(moonFx.getIlluminatedRatio(synodicAge)));

        // expected ratio, according the Astronomical Applications Dept./U. S. Naval Observatory
        // on February 10th, 2017 @ Noon CST
        double expectedIlluminatedRatio = 1.00;
        double delta = 0.09; // acceptable deviation

        assertEquals(expectedIlluminatedRatio, illuminatedRatio, delta);
    }

    @Test
    public void testMoonDistance() {
        // avg distance from three sources: 377669.691KM
        // on February 11th, 2017 @ 00:33am UTC
        double avgDistance = 377669.691;
        double distanceInKm = (moonFx.getDistanceInEarthRadii() * moonFx.EARTH_RADIUS_MI) * 1.60934;
        double delta = 3000;

        assertEquals(avgDistance, distanceInKm, delta);
    }
}