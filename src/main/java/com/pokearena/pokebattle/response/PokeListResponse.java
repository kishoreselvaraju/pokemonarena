/**
 * 
 */
package com.pokearena.pokebattle.response;

import java.math.BigInteger;
import java.util.List;

import com.pokearena.pokebattle.helper.PokeHelper;

import lombok.Data;

/**
 * @author kishoreselvaraju
 *
 */
@Data
public class PokeListResponse  {
	private BigInteger count;
	private String next;
	private String prev;
	private List<PokeHelper> results;

	

}
