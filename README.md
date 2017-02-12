MoonFx
======
[![](https://jitpack.io/v/jesgs/moonfx.svg)](https://jitpack.io/#jesgs/moonfx)

Java translation of [moonfx.bas](http://www.skyandtelescope.com/resources/software/3304911.html) by Bradley E. Schaefer.

### Methods
`void setDate(Date date)` : Sets the current date. Uses `java.util.Date`

`Date getDate()` : Gets the current date in the form of a Date object

`String getFormattedDate(String format)` : Returns a formatted date string, format specified by `format` parameter

`double getSynodicDate()`: Get current synodic phase of the moon, aka Moon's age in days (number of days from New Moon)

`double getDistanceInEarthRadii()` : Get distance measured in Earth radii

`double getEclipticLatitude()` : Get position of the Moon, aka Moon's ecliptic latitude

`double getJulianDate()` : Get the Julian Date for the date specified by setDate()

`double getPhaseAngle(double synodicAge)` : Get the current approximate phase angle of the Moon. Uses value from `getSynodicDate()`

`double getIlluminatedRatio(double synodicAge)` : Get Illuminated ratio of moon according to synodic age

### Examples
```java
MoonFx moonFx = new MoonFx();
moonFx.setDate(new Date()); // Thu Feb 09 17:25:26 CST 2017

System.out.println("Moon's age from new (days): " + moonFx.getSynodicPhase());
System.out.println("Distance (Earth radii): " + (int)moonFx.getDistanceInEarthRadii());
System.out.println("Distance (Miles): " + (int)(moonFx.getDistanceInEarthRadii() * MoonFx.EARTH_RADIUS_MI));
```

Outputs:

```text
Moon's age from new (days): 12.945493872886475
Distance (Earth radii): 58
Distance (Miles): 231066
```

### Changelog
* 1.0.1 Adjusted Julian Date calculation for better precision
* 1.0.0 Initial release
