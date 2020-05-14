package ch.aaap.assignment.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.aaap.assignment.raw.CSVPostalCommunity;
import org.junit.jupiter.api.Test;

class CSVPostalCommunityExtractorTest {

  @Test
  void testPostalCommunityExtracted() {
    // given
    var csvPostalCommunity = CSVPostalCommunity.builder()
        .name("name")
        .zipCode("1234")
        .zipCodeAddition("0")
        .build();

    // when
    var postalCommunity = CSVPostalCommunityExtractor.extractPostalCommunity(csvPostalCommunity);

    // then
    assertEquals("1234", postalCommunity.getZipCode());
    assertEquals("0", postalCommunity.getZipCodeAddition());
    assertEquals("name", postalCommunity.getName());
  }
}