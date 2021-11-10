package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class GeoServiceImplTest {
    GeoService geoService = new GeoServiceImpl();
    
    @ParameterizedTest
    @MethodSource("ipSource")
    public void test_location_byIp(String ip,String expectedLocation) {
        Assertions.assertEquals(expectedLocation,geoService.byIp(ip).getCity());
    }

    public static Stream<Arguments> ipSource() {
        return Stream.of(Arguments.of("127.0.0.1",null),Arguments.of("172.135.150.205","Moscow"),Arguments.of("96.135.150.205","New York"));
    }

    @Test
    public void test_another_location_byIp() {
        Assertions.assertNull(geoService.byIp("150.135.150.205"));
    }
}
