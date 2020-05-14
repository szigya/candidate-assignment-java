package ch.aaap.assignment.transformer;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;

/**
 * Extracts {@link CSVPoliticalCommunity} data to different objects <strong>without connecting
 * them</strong>.
 */
class CSVPoliticalCommunityExtractor {

  static PoliticalCommunity extractCommunity(
      CSVPoliticalCommunity csvPoliticalCommunity) {
    return PoliticalCommunity.builder()
        .name(csvPoliticalCommunity.getName())
        .shortName(csvPoliticalCommunity.getShortName())
        .number(csvPoliticalCommunity.getNumber())
        .lastUpdate(csvPoliticalCommunity.getLastUpdate())
        .build();
  }

  static Canton extractCanton(CSVPoliticalCommunity csvPoliticalCommunity) {
    return Canton.builder()
        .name(csvPoliticalCommunity.getCantonName())
        .code(csvPoliticalCommunity.getCantonCode())
        .build();
  }

  static District extractDistrict(CSVPoliticalCommunity csvPoliticalCommunity) {
    return District.builder()
        .number(csvPoliticalCommunity.getDistrictNumber())
        .name(csvPoliticalCommunity.getDistrictName())
        .build();
  }

}
