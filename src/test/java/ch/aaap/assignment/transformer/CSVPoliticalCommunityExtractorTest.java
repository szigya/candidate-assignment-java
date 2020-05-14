package ch.aaap.assignment.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class CSVPoliticalCommunityExtractorTest {

  private final CSVPoliticalCommunity testData = CSVPoliticalCommunity.builder()
      .number("testPcNumber")
      .name("testPcName")
      .shortName("testPcShortName")
      .lastUpdate(LocalDate.parse("2010-05-05"))
      .districtNumber("testDistrictNumber")
      .districtName("testDistrictName")
      .cantonCode("testCantonCode")
      .cantonName("testCantonName")
      .build();

  @Test
  void testCantonIsExtracted() {
    // given
    // when
    var canton = CSVPoliticalCommunityExtractor.extractCanton(testData);
    // then
    assertEquals("testCantonCode", canton.getCode());
    assertEquals("testCantonName", canton.getName());
  }

  @Test
  void testDistrictExtracted() {
    // given
    // when
    var district = CSVPoliticalCommunityExtractor.extractDistrict(testData);
    // then
    assertEquals("testDistrictNumber", district.getNumber());
    assertEquals("testDistrictName", district.getName());
  }

  @Test
  void testPoliticalCommunityExtracted() {
    // given
    // when
    var politicalCommunity = CSVPoliticalCommunityExtractor.extractCommunity(testData);
    // then
    assertEquals("testPcNumber",politicalCommunity.getNumber());
    assertEquals("testPcName",politicalCommunity.getName());
    assertEquals("testPcShortName",politicalCommunity.getShortName());
    assertEquals("2010-05-05",politicalCommunity.getLastUpdate().toString());
  }
}