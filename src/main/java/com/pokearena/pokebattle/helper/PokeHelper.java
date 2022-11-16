/**
 * 
 */
package com.pokearena.pokebattle.helper;

import lombok.Data;

/**
 * @author kishoreselvaraju
 *
 */
@Data
public class PokeHelper {
	private String url;
	private String name;
	private Integer height;
	private Integer weight;
	private Integer id;
	@Override
	public String toString() {
		return " id : " + id + " name :" + name + ", height : " + height + ", weight : " + weight;
	}
}
