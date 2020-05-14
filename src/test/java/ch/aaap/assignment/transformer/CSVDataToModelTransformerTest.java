package ch.aaap.assignment.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CSVDataToModelTransformerTest {

  @Test
  void testCantonsAreCreated() {
    // given
    CSVPoliticalCommunity csvPoliticalCommunity = CSVPoliticalCommunity.builder()
        .number("1")
        .districtNumber("1")
        .districtName("district1")
        .cantonName("canton1")
        .cantonCode("c1")
        .lastUpdate(LocalDate.parse("2018-01-01"))
        .shortName("PC")
        .name("PoliticalCommunity")
        .build();

    CSVPostalCommunity csvPostalCommunity = CSVPostalCommunity.builder()
        .zipCode("8004")
        .zipCodeAddition("0")
        .politicalCommunityNumber("1")
        .politicalCommunityShortName("PC")
        .name("PostalCommunity")
        .cantonCode("c1")
        .build();

    var sut = new CSVDataToModelTransformer(Set.of(csvPoliticalCommunity),
        Set.of(csvPostalCommunity));

    // when
    var model = sut.transform();

    // then
    assertEquals(1, model.getCantons().size());
    assertEquals(1, model.getPoliticalCommunities().size());
    assertEquals(1, model.getPostalCommunities().size());
    assertEquals(1, model.getDistricts().size());
    Set<PoliticalCommunity> politicalCommunities = model.getPostalCommunities().iterator().next()
        .getPoliticalCommunities();
    assertEquals(1, politicalCommunities.size());
    assertEquals(csvPoliticalCommunity.getNumber(),
        politicalCommunities.iterator().next().getNumber(),
        "Political and Postal community should be connected in the model.");

  }
}