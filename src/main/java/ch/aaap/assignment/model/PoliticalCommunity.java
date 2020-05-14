package ch.aaap.assignment.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@ToString(of = "name")
@EqualsAndHashCode(of = "number")
public class PoliticalCommunity {

  private String number;
  private String name;
  private String shortName;
  private LocalDate lastUpdate;
  private District district;

}
