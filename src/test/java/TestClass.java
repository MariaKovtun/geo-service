
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TestClass {
    GeoService geoService = Mockito.spy(GeoServiceImpl.class);
    LocalizationService localizationService = Mockito.spy(LocalizationServiceImpl.class);

    @ParameterizedTest
    @MethodSource("resultTextSource")
    public void test_choose_lang_by_ip (String ip,String expectedText) {
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        Assertions.assertEquals(expectedText,messageSender.send(headers));
    }

    private static Stream<Arguments> resultTextSource() {
        return Stream.of(Arguments.of("172.123.12.19","Добро пожаловать"),Arguments.of("96.123.12.19","Welcome"));
    }

    @ParameterizedTest
    @MethodSource ("ipSource")
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

    @ParameterizedTest
    @EnumSource(value = Country.class,names = "RUSSIA",mode = EnumSource.Mode.EXCLUDE)
    public void test_locale_method(Country country) {
        Assertions.assertEquals("Welcome",localizationService.locale(country));
    }

    @ParameterizedTest
    @EnumSource(value = Country.class,names = "RUSSIA")
    public void test_RUSSIA_locale_method(Country country) {
        Assertions.assertEquals("Добро пожаловать",localizationService.locale(country));
    }









}
