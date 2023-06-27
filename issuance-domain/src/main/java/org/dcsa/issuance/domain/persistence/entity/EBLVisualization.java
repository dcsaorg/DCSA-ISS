package org.dcsa.issuance.domain.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "ebl_visualization")
public class EBLVisualization {
  @Id
  @GeneratedValue
  @Column(name = "ebl_visualization_id", nullable = false)
  private UUID eblVisualizationID;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Column(name = "content", columnDefinition = "bytea", nullable = false)
  private byte[] content;
}
