package cgeo.geocaching.location;

import cgeo.geocaching.utils.MatcherWrapper;

import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public final class DistanceParser {

    private static final Pattern pattern = Pattern.compile("^([0-9.,]+)[ ]*(m|km|ft|yd|mi|)?$", Pattern.CASE_INSENSITIVE);

    private DistanceParser() {
        // utility class
    }

    /**
     * Parse a distance string composed by a number and an optional suffix
     * (such as "1.2km").
     *
     * @param distanceText the string to analyze
     * @param defaultUnit  unit to use if string contains no unit
     * @return the distance in kilometers
     * @throws NumberFormatException if the given number is invalid
     */
    public static float parseDistance(final String distanceText, final DistanceUnit defaultUnit)
            throws NumberFormatException {
        final MatcherWrapper matcher = new MatcherWrapper(pattern, distanceText);

        if (!matcher.find()) {
            throw new NumberFormatException(distanceText);
        }

        final float value = Float.parseFloat(matcher.group(1).replace(',', '.'));
        final String unitStr = StringUtils.lowerCase(matcher.group(2), Locale.US);

        DistanceUnit unit = defaultUnit;

        switch (unitStr) {
            case "m":
                unit = DistanceUnit.M;
                break;
            case "km":
                unit = DistanceUnit.KM;
                break;
            case "yd":
                unit = DistanceUnit.YD;
                break;
            case "mi":
                unit = DistanceUnit.MI;
                break;
            case "ft":
                unit = DistanceUnit.FT;
                break;
        }

        switch (unit) {
            case M:
                return value / 1000;
            case FT:
                return value * IConversion.FEET_TO_KILOMETER;
            case MI:
                return value * IConversion.MILES_TO_KILOMETER;
            case YD:
                return value * IConversion.YARDS_TO_KILOMETER;
            case KM:
            default:
                return value;
        }
    }

    public enum DistanceUnit {
        M(0), KM(1), FT(2), YD(3), MI(4);
        private final int value;

        DistanceUnit(final int value) {
            this.value = value;
        }

        public static DistanceUnit getById(final int id) {
            for (final DistanceUnit e : values()) {
                if (e.value == id) {
                    return e;
                }
            }
            return MI;
        }

        public int getValue() {
            return value;
        }
    }

}
