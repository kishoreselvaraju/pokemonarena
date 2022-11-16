/**
 * 
 */
package com.pokearena.pokebattle.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @author kishoreselvaraju
 *
 */
@Data
@JsonIgnoreProperties
public class PokemonMove {
private Integer accuracy;
private PokemonAttr contest_combos;
}
