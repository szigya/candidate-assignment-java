package ch.aaap.assignment.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@EqualsAndHashCode(of = "code")
@ToString(of = "name")
public class Canton {

  final Set<District> districts = new HashSet<>();
  private String code;
  private String name;

  public void addDistrict(District district) {
    this.districts.add(district);
  }

}
