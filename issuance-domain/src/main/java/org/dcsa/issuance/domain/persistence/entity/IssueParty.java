package org.dcsa.issuance.domain.persistence.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.Cascade;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "issue_party")
public class IssueParty {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID issuePartyID;

  @Column(name = "ebl_platform_identifier", length = 100, nullable = false)
  private String eblPlatformIdentifier;

  @Column(name = "legal_name", length = 100)
  private String legalName;

  @Column(name = "registration_number", length = 100)
  private String registrationNumber;

  @Column(name = "location_of_registration", length = 2)
  private String locationOfRegistration;

  @Column(name = "tax_reference", length = 100)
  private String taxReference;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "party")
  private Set<IssuePartySupportingCode> supportingPartyCodes;
}
