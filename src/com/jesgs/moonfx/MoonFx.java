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

import java.util.Calendar;
import java.util.Date;
import java.lang.Math;

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
     * Date to check moon properties
     */
    protected Date moonDate;
    
    /**
     * Set the date
     * 
     * @param date Date to process
     */
    public MoonFx setDate(Date date) {
        this.moonDate = date;
        
        return this;
    }
    
    /**
     * Get the date
     * 
     * @return Date
     */
    public Date getDate() {
        return this.moonDate;
    }
    
    /**
     * Get current synodic phase of the moon
     * 
     * @return Moon's age in days (number of days from New Moon)
     */
    public double getSynodicPhase() {
        double julianDate = this.getJulianDate();

        double moonsAge = (this._normalize((julianDate - 2451550.1) / 29.530588853) * 29.53);
        
        return moonsAge;
    }
    
    /**
     * Get the Julian Date for the date specified by setDate()
     * 
     * @return single
     */
    public double getJulianDate() {
        
        Calendar gregorianDateCalendar = Calendar.getInstance();        
        gregorianDateCalendar.setTime(this.getDate());
        
        int month = gregorianDateCalendar.get(Calendar.MONTH) + 1;
        int day   = gregorianDateCalendar.get(Calendar.DAY_OF_MONTH);
        int year  = gregorianDateCalendar.get(Calendar.YEAR);
        
        long year2 = year - (int)((12 - month) / 10);
        long month2 = month + 9;
        if (month2 >= 12) {
            month2 = month2 - 12;
        }
        
        double julianDate = (int)(365.25 * (year2 + 4712)) + (int)(30.6 * month2 + .5) + day + 59;
        
        if (julianDate > 2299160) {
            long k3 = (int)((int)((year2 / 100) + 49) * 0.75) - 38;
            julianDate = julianDate - k3;
        }

        return julianDate;
    }
    
    /**
     * Normalize values
     * 
     * @param value
     * @return 
     */
    private double _normalize(double value) {

        value = value - (int)value;

        if (value < 0){
            value = value + 1;
        }
        
        return value;
    }
}
