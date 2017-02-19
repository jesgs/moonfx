/**
 * (c) 2013 Jess Green / JesGs Development
 *
 * Licensed under the GNU General Public License, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jesgs.moonfx;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.lang.Math;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * MoonFx Class
 *
 * @author Jess Green <jgreen at psy-dreamer.com>
 */
public class MoonFx {

    /**
     * Pi in radians
     */
    public static final double PI_RADIANS = 2 * Math.PI;

    /**
     * Earth's radius in miles
     */
    public static final long EARTH_RADIUS_MI = 3959;

    /**
     * Day constant
     */
    public static final long ONE_DAY = 86400;

    /**
     * Moon's Synodic Period (Days)
     */
    public static final double SYNODIC_PERIOD = 29.530589;

    /**
     * Date to check moon properties
     */
    protected LocalDateTime moonDate;

    /**
     * Set the date
     *
     * @param date Date to use for calculations
     */
    public MoonFx setDate(LocalDateTime date) {
        this.moonDate = date;

        return this;
    }

    /**
     * Get the date
     *
     * @return String
     */
    public LocalDateTime getDate() {
        return this.moonDate;
    }


    public String getFormattedDate(String format) {
        SimpleDateFormat dt = new SimpleDateFormat(format);

        return dt.format(this.moonDate);
    }

    /**
     * Get current synodic phase of the moon
     *
     * @return Moon's age in days (number of days from New Moon)
     */
    public double getSynodicPhase() {
        double moonsAge = (this.getJulianDate() - 2451550.1) / SYNODIC_PERIOD;

        return _normalize(moonsAge) * 29.53;
    }

    /**
     * Distance from anomalistic phase
     *
     * @return Distance in Earth radii
     */
    public double getDistanceInEarthRadii() {
        double distanceInRadians = this._normalize((this.getJulianDate() - 2451562.2) / 27.55454988) * MoonFx.PI_RADIANS;
        double synodicPhaseinRadians = this.getSynodicPhase() * MoonFx.PI_RADIANS;

        double distance = 60.4 - 3.3 * Math.cos(distanceInRadians) - .6
                * Math.cos(2 * synodicPhaseinRadians - distanceInRadians) - .5
                * Math.cos(2 * synodicPhaseinRadians);

        return distance;
    }

    /**
     * Get moon's ecliptic latitude based on nodal (draconic) phase
     *
     * @return Moon's ecliptic latitude
     */
    public double getEclipticLatitude() {
        double value = (getJulianDate() - (float)2451565.2) / (float)27.212220817;
        value = _normalize(value);
        double eclipticLatitudeRadians = value * PI_RADIANS; // Convert to radians
        double elat = 5.1 * Math.sin(eclipticLatitudeRadians);

        return elat;
    }

    /**
     * Get the moon's ecliptic longitude based on sidereal motion
     *
     * @return Moon's ecliptic longitude
     */
    public double getEclipticLongitude() {
        double julianDate = getJulianDate();
        double v = ((julianDate - 2451555.8) / 27.321582241);
        double rp = _normalize(v);

        double v1 = (julianDate - 2451562.2) / 27.55454988;
        double dp = _normalize(v1) * PI_RADIANS;

        double ip = getSynodicPhase() * PI_RADIANS;

        double elon = 360 * rp + 6.3 * Math.sin(dp)
                        + 1.3 * Math.sin(2 * ip - dp)
                        + 0.7 * Math.sin(2 * ip);

        return elon;
    }

    /**
     * Get the Julian Date for the date specified by setDate()
     *
     * @return double
     */
    public double getJulianDate() {
        // use float for accuracy
        float currentTime = getDate().toEpochSecond(ZoneOffset.UTC);

        // julian date calculation based from unix timestamp
        // 2440587.5 is January 1st, 1970 @ Mid-night UTC
        return (currentTime / ONE_DAY) + 2440587.5;
    }


    /**
     * Return the approximate phase angle of the moon
     *
     * @param synodicAge Current age of moon
     * @return
     */
    public double getPhaseAngle(double synodicAge) {
        double phaseAngle = synodicAge * (360 / SYNODIC_PERIOD);

        if (phaseAngle > 360) {
            phaseAngle = phaseAngle - 360;
        }

        return phaseAngle;
    }

    /**
     * Get Illuminated ratio of moon according to synodic age
     *
     * @param synodicAge
     * @return
     */
    public double getIlluminatedRatio(double synodicAge) {
        double phaseAngle = this.getPhaseAngle(synodicAge);
        double ratioOfIllumination = 0.5 * (1 - Math.cos(Math.toRadians(phaseAngle)));

        return ratioOfIllumination;
    }

    /**
     * Normalize values
     *
     * @param value
     * @return
     */
    private double _normalize(double value) {
        value = value - Math.floor(value);

        if (value < 0){
            value = value + 1;
        }

        return value;
    }
}
