package ch.aaap.assignment.model;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Model {

  private final Map<String, PoliticalCommunity> politicalCommunityByNumber;
  private final Map<String, PostalCommunity> postalCommunityByExtendedZipCode;
  private final Map<String, District> districtByNumber;
  private final Map<String, Canton> cantonByCode;

  public Model(
      Map<String, PoliticalCommunity> politicalCommunityByNumber,
      Map<String, PostalCommunity> postalCommunityByExtendedZipCode,
      Map<String, District> districtByNumber,
      Map<String, Canton> cantonByCode) {
    this.politicalCommunityByNumber = politicalCommunityByNumber;
    this.postalCommunityByExtendedZipCode = postalCommunityByExtendedZipCode;
    this.districtByNumber = districtByNumber;
    this.cantonByCode = cantonByCode;
  }

  public Set<PoliticalCommunity> getPoliticalCommunities() {
    return Set.copyOf(politicalCommunityByNumber.values());
  }

  public Set<PostalCommunity> getPostalCommunities() {
    return Set.copyOf(postalCommunityByExtendedZipCode.values());
  }

  public Set<District> getDistricts() {
    return Set.copyOf(districtByNumber.values());
  }

  public District getDistrictByNumber(String districtNumber) {
    if (!districtByNumber.containsKey(districtNumber)) {
      throw new IllegalArgumentException(
          "District with number '" + districtNumber + "' could not be found.");
    }
    return districtByNumber.get(districtNumber);
  }

  public Set<Canton> getCantons() {
    return Set.copyOf(cantonByCode.values());
  }

  public Canton getCantonByCode(String cantonCode) {
    if (!cantonByCode.containsKey(cantonCode)) {
      throw new IllegalArgumentException(
          "Canton with code '" + cantonCode + "' could not be found.");
    }
    return cantonByCode.get(cantonCode);
  }


  public PostalCommunity getPostalCommunityByName(String name) {
    Set<PostalCommunity> postalCommunities = getPostalCommunities().stream()
        .filter(pc -> pc.getName().equals(name))
        .collect(Collectors.toSet());

    if (postalCommunities.size() == 0) {
      throw new IllegalArgumentException(
          "PostalCommunity with name '" + name + "' could not be found.");
    }

    if (postalCommunities.size() != 1) {
      throw new IllegalArgumentException("PostalCommunity with name '" + name
          + "' is not unique.");
    }

    return postalCommunities.iterator().next();
  }
}
