package cgeo.geocaching.location;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

public class DistanceParserTest {

    private static final double MM = 1e-6; // 1mm, in kilometers

    @Test
    public void testFormats() {
        assertThat((double) DistanceParser.parseDistance("1200 m", DistanceParser.DistanceUnit.M)).isEqualTo(1.2, offset(MM));
        assertThat((double) DistanceParser.parseDistance("1.2 km", DistanceParser.DistanceUnit.M)).isEqualTo(1.2, offset(MM));
        assertThat((double) DistanceParser.parseDistance("1200 ft", DistanceParser.DistanceUnit.M)).isEqualTo(0.36576, offset(MM));
        assertThat((double) DistanceParser.parseDistance("1200 yd", DistanceParser.DistanceUnit.M)).isEqualTo(1.09728, offset(MM));
        assertThat((double) DistanceParser.parseDistance("1.2 mi", DistanceParser.DistanceUnit.M)).isEqualTo(1.9312128, offset(MM));
    }

    @Test
    public void testImplicit() {
        assertThat((double) DistanceParser.parseDistance("1200", DistanceParser.DistanceUnit.M)).isEqualTo(1.2, offset(MM));
        assertThat((double) DistanceParser.parseDistance("1200", DistanceParser.DistanceUnit.FT)).isEqualTo(0.36576, offset(MM));
    }

    @Test
    public void testComma() {
        assertThat((double) DistanceParser.parseDistance("1,2km", DistanceParser.DistanceUnit.M)).isEqualTo(1.2, offset(MM));
    }

    @Test
    public void testFeet() {
        assertThat((double) DistanceParser.parseDistance("1200 FT", DistanceParser.DistanceUnit.FT)).isEqualTo(0.36576, offset(MM));
    }

}
