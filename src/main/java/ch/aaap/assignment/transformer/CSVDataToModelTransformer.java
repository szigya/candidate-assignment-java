package ch.aaap.assignment.transformer;

import static ch.aaap.assignment.transformer.CSVPoliticalCommunityExtractor.extractCanton;
import static ch.aaap.assignment.transformer.CSVPoliticalCommunityExtractor.extractCommunity;
import static ch.aaap.assignment.transformer.CSVPoliticalCommunityExtractor.extractDistrict;
import static ch.aaap.assignment.transformer.CSVPostalCommunityExtractor.extractPostalCommunity;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PostalCommunity;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Transforms raw csv data ( described as a set of {@link CSVPoliticalCommunity} and set of {@link
 * CSVPostalCommunity}) to a {@link Model}.
 */
public class CSVDataToModelTransformer {

  private final Set<CSVPoliticalCommunity> csvPoliticalCommunities;
  private final Set<CSVPostalCommunity> csvPostalCommunities;

  private final Map<String, District> districtByNumber = new HashMap<>();
  private final Map<String, Canton> cantonByCode = new HashMap<>();
  private final Map<String, PoliticalCommunity> politicalCommunityByNumber = new HashMap<>();
  private final Map<String, PostalCommunity> postalCommunityByExtendedZipCode = new HashMap<>();


  public CSVDataToModelTransformer(Set<CSVPoliticalCommunity> csvPoliticalCommunities,
      Set<CSVPostalCommunity> csvPostalCommunities) {
    this.csvPoliticalCommunities = csvPoliticalCommunities;
    this.csvPostalCommunities = csvPostalCommunities;
  }

  /**
   * Creates Model from csv data.
   *
   * @return Model
   */
  public Model transform() {
    processPoliticalCommunityCsvData();
    processPostalCommunityCsvData();
    return createModelFromData();
  }

  private void processPoliticalCommunityCsvData() {
    csvPoliticalCommunities.forEach(csvPoliticalCommunity -> {
      var canton = getOrCreateCanton(csvPoliticalCommunity);
      var district = getOrCreateDistrict(csvPoliticalCommunity);
      var politicalCommunity = getOrCreatePoliticalCommunity(csvPoliticalCommunity);

      connect(canton, district, politicalCommunity);
    });
  }

  private PoliticalCommunity getOrCreatePoliticalCommunity(
      CSVPoliticalCommunity csvPoliticalCommunity) {
    return politicalCommunityByNumber.computeIfAbsent(
        csvPoliticalCommunity.getNumber(),
        key -> extractCommunity(csvPoliticalCommunity));
  }

  private District getOrCreateDistrict(CSVPoliticalCommunity csvPoliticalCommunity) {
    return districtByNumber.computeIfAbsent(
        csvPoliticalCommunity.getDistrictNumber(),
        key -> extractDistrict(csvPoliticalCommunity));
  }

  private Canton getOrCreateCanton(CSVPoliticalCommunity csvPoliticalCommunity) {
    return cantonByCode.computeIfAbsent(
        csvPoliticalCommunity.getCantonCode(),
        key -> extractCanton(csvPoliticalCommunity));
  }

  private void connect(Canton canton, District district, PoliticalCommunity politicalCommunity) {
    politicalCommunity.setDistrict(district);
    canton.addDistrict(district);
  }

  private void processPostalCommunityCsvData() {
    csvPostalCommunities.forEach(rawPostal -> {
      var postalCommunity = getOrCreatePostalCommunity(rawPostal);
      var canton = cantonByCode.get(rawPostal.getCantonCode());
      var polCommunity = politicalCommunityByNumber.get(rawPostal.getPoliticalCommunityNumber());

      connect(postalCommunity, canton, polCommunity);
    });
  }

  private PostalCommunity getOrCreatePostalCommunity(CSVPostalCommunity rawPostal) {
    String extendedZipCode = rawPostal.getZipCode() + rawPostal.getZipCodeAddition();
    return postalCommunityByExtendedZipCode.computeIfAbsent(
        extendedZipCode,
        key -> extractPostalCommunity(rawPostal));
  }

  private void connect(
      PostalCommunity postalCommunity,
      Canton canton,
      PoliticalCommunity politicalCommunity) {

    postalCommunity.setCanton(canton);
    postalCommunity.addPoliticalCommunity(politicalCommunity);
  }

  private Model createModelFromData() {
    return new Model(
        politicalCommunityByNumber,
        postalCommunityByExtendedZipCode,
        districtByNumber,
        cantonByCode
    );
  }

}
