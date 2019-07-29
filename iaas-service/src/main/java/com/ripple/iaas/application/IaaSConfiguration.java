package com.ripple.iaas.application;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author shailendra.ps
 * @since 29/04/19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IaaSConfiguration extends Configuration {

    @NotNull
    @Valid
    @JsonProperty("database")
    @Getter
    private DataSourceFactory dataSourceFactory;

}
