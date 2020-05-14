package ch.aaap.assignment.transformer;

import ch.aaap.assignment.model.PostalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;

/**
 * Extracts {@link CSVPostalCommunity} data to model object <strong>without its
 * connections</strong>.
 */
class CSVPostalCommunityExtractor {

  static PostalCommunity extractPostalCommunity(CSVPostalCommunity csvPostalCommunity) {
    return PostalCommunity.builder()
        .name(csvPostalCommunity.getName())
        .zipCode(csvPostalCommunity.getZipCode())
        .zipCodeAddition(csvPostalCommunity.getZipCodeAddition())
        .build();
  }

}
