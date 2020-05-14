package ch.aaap.assignment.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@ToString(exclude = "politicalCommunities")
@EqualsAndHashCode(of = {"zipCode", "zipCodeAddition"})
public class PostalCommunity {

  private final Set<PoliticalCommunity> politicalCommunities = new HashSet<>();
  private String zipCode;
  private String zipCodeAddition;
  private String name;
  private Canton canton;

  public void addPoliticalCommunity(PoliticalCommunity politicalCommunity) {
    this.politicalCommunities.add(politicalCommunity);
  }

}
