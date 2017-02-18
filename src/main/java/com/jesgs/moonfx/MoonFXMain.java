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

import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.time.Month;

/**
 * Main class for debugging purposes
 *
 * @author Jess Green <jgreen at psy-dreamer.com>
 */
public class MoonFXMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LocalDateTime currentTime = LocalDateTime.now();
        // time of Full Moon on February 11th, 2017
        LocalDateTime testDate = LocalDateTime.of(2017, Month.FEBRUARY, 11, 0, 33, 0);

        MoonFx mnf = new MoonFx();
        int count = 0;
        int total = 29;

        do {
            mnf.setDate(testDate);

            System.out.println("========== " + testDate.toString() + " ==========");
            System.out.println("Julian Date: " + mnf.getJulianDate());
            System.out.println("Moon's age from new (days): " + mnf.getSynodicPhase());
            System.out.println("Phase Angle: " + mnf.getPhaseAngle(mnf.getSynodicPhase()));
            System.out.println("Illuminated Ratio: " + Math.round(mnf.getIlluminatedRatio(mnf.getSynodicPhase()) * 100) + "%");
            System.out.println("Distance (Earth radii): " + (int) mnf.getDistanceInEarthRadii());
            System.out.println("Distance (Miles): " + (int) (mnf.getDistanceInEarthRadii() * MoonFx.EARTH_RADIUS_MI));
            System.out.println("");
            count++;

            testDate = testDate.plusDays(count);
        }  while (count < total);
    }
}
