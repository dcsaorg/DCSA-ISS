package org.dcsa.issuance.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
// We do not want the shared-kernel entities as we do not use them,
// and they trigger validation errors because their schema is not in
// the SQL.
// FIXME: Can we solve this by changing the dependency?
@ComponentScan("org.dcsa")
@EntityScan("org.dcsa")
@EnableJpaRepositories("org.dcsa")
public class ScanConfiguration {
}
