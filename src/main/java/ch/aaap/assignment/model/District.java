package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@ToString(of = "name")
@EqualsAndHashCode(of = "number")
public class District {

  private String number;
  private String name;
}
