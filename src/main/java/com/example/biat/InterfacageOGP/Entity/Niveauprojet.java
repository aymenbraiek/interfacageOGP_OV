package com.example.biat.InterfacageOGP.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Niveauprojet {
	@ApiModelProperty(value = "this is name of projet", required = true)
	private String name;
	@ApiModelProperty(value = "this is name of projet", required = true)
	private String abriavation;
	@ApiModelProperty(value = "this is name of projet", required = true)
	private String actif;
	@ApiModelProperty(value = "this is name of projet", required = true)
	private String releaseprevue;
	@ApiModelProperty(value = "this is name of projet", required = true)
	private String metier;

	private String typeprojet;
	private String siteDeveloppement;



}
