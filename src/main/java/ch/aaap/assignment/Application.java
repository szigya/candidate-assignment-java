package ch.aaap.assignment;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PostalCommunity;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import ch.aaap.assignment.raw.CSVUtil;
import ch.aaap.assignment.transformer.CSVDataToModelTransformer;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class Application {

  private Model model = null;

  public Application() {
    initModel();
  }

  /**
   * Reads the CSVs and initializes a in memory model
   */
  private void initModel() {
    Set<CSVPoliticalCommunity> csvPoliticalCommunities = CSVUtil.getPoliticalCommunities();
    Set<CSVPostalCommunity> csvPostalCommunities = CSVUtil.getPostalCommunities();
    this.model = new CSVDataToModelTransformer(csvPoliticalCommunities, csvPostalCommunities)
        .transform();
  }

  public static void main(String[] args) {
    new Application();
  }

  /**
   * @return model
   */
  public Model getModel() {
    return model;
  }

  /**
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of political communities in given canton
   */
  public long getAmountOfPoliticalCommunitiesInCanton(String cantonCode) {
    Canton canton = model.getCantonByCode(cantonCode);
    return model.getPoliticalCommunities().stream()
        .filter(pc -> canton.getDistricts().contains(pc.getDistrict()))
        .count();
  }

  /**
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of districts in given canton
   */
  public long getAmountOfDistrictsInCanton(String cantonCode) {
    return model.getCantonByCode(cantonCode).getDistricts().size();
  }

  /**
   * 4
   *
   * @param districtNumber of a district (e.g. 101)
   * @return amount of districts in given canton
   */
  public long getAmountOfPoliticalCommunitiesInDistrict(String districtNumber) {
    District district = model.getDistrictByNumber(districtNumber);
    return model.getPoliticalCommunities().stream().filter(pc -> pc.getDistrict().equals(district))
        .count();
  }

  /**
   * @param zipCode 4 digit zip code
   * @return district that belongs to specified zip code
   */
  public Set<String> getDistrictsForZipCode(String zipCode) {
    return model.getPostalCommunities()
        .stream()
        .filter(pc -> pc.getZipCode().equals(zipCode))
        .map(PostalCommunity::getPoliticalCommunities)
        .flatMap(Collection::stream)
        .map(PoliticalCommunity::getDistrict)
        .map(District::getName)
        .collect(Collectors.toSet());
  }

  /**
   * @param postalCommunityName name
   * @return lastUpdate of the political community by a given postal community name
   */
  public LocalDate getLastUpdateOfPoliticalCommunityByPostalCommunityName(
      String postalCommunityName) {

    model.getPostalCommunityByName(postalCommunityName);

    var postalCommunity = model.getPostalCommunityByName(postalCommunityName);

    if (postalCommunity.getPoliticalCommunities().size() != 1) {
      throw new IllegalArgumentException(
          "PostalCommunity has more than one PoliticalCommunity assigned, thus the result would be ambiguous.");
    }

    var politicalCommunity = postalCommunity.getPoliticalCommunities().iterator().next();
    return politicalCommunity.getLastUpdate();
  }

  /**
   * https://de.wikipedia.org/wiki/Kanton_(Schweiz)
   *
   * @return amount of canton
   */
  public long getAmountOfCantons() {
    return model.getCantons().size();
  }

  /**
   * https://de.wikipedia.org/wiki/Kommunanz
   *
   * @return amount of political communities without postal communities
   */
  public long getAmountOfPoliticalCommunityWithoutPostalCommunities() {
    Set<PoliticalCommunity> all = model.getPoliticalCommunities();
    Set<PoliticalCommunity> withPostalCommunity = getPoliticalCommunitiesOfPostalCommunities();
    return all.size() - withPostalCommunity.size();
  }

  private Set<PoliticalCommunity> getPoliticalCommunitiesOfPostalCommunities() {
    return model.getPostalCommunities().stream()
        .map(PostalCommunity::getPoliticalCommunities)
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
  }
}
