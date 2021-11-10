package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;

public class LocalizationServiceImplTest {
    LocalizationService localizationService = new LocalizationServiceImpl();

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
