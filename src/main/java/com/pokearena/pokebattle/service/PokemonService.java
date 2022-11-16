/**
 * 
 */
package com.pokearena.pokebattle.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.pokearena.pokebattle.constant.PokeConstant;
import com.pokearena.pokebattle.helper.PokeHelper;
import com.pokearena.pokebattle.helper.PokemonAttributes;
import com.pokearena.pokebattle.helper.PokemonHelper;
import com.pokearena.pokebattle.helper.PokemonMove;
import com.pokearena.pokebattle.response.PokeListResponse;
import com.pokearena.pokebattle.util.PokeUtil;

/**
 * @author kishoreselvaraju
 *
 */
@Service
public class PokemonService {
	private final WebClient webClient;
	final int size = 16 * 1024 * 1024;
	final ExchangeStrategies strategies = ExchangeStrategies.builder()
			.codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size)).build();

	public PokemonService(WebClient.Builder builder) {

		webClient = builder.exchangeStrategies(strategies).baseUrl(PokeConstant.pokeURL).build();

	}

	public PokeListResponse getPokemonList() {
		PokeListResponse response = new PokeListResponse();
		System.out.println("Please wait...Retriving the pokemon list. Please select the pokemon from below list : ");
		response = webClient.get().uri(PokeConstant.pokemon_list).retrieve().bodyToMono(PokeListResponse.class).block();
		response.getResults().forEach(a -> setNameAndHeight(a));
		return response;
	}

	private void setNameAndHeight(PokeHelper pokemon) {
		PokemonAttributes pokeAttr = new PokemonAttributes();
		pokeAttr = webClient.get().uri(pokemon.getUrl()).retrieve().bodyToMono(PokemonAttributes.class).block();
		pokemon.setHeight(pokeAttr.getHeight());
		pokemon.setWeight(pokeAttr.getWeight());
		pokemon.setId(pokeAttr.getId());
		pokemon.setUrl(null);
	}

	/**
	 * This service provide the attack names for the pokemon (If some cases where
	 * pokemon doesnt have any move, the attack is hardcoded)
	 * 
	 * @param pokemon
	 * @return
	 */
	@SuppressWarnings({ "unused", "unlikely-arg-type" })
	public List<PokemonHelper> getPokemonAttack(String pokemonId) {
		List<PokemonHelper> pokemonAttacks = new ArrayList<>();
		PokemonMove attacks = webClient.get().uri(PokeConstant.pokemon_attack + pokemonId).retrieve()
				.bodyToMono(PokemonMove.class).block();

		if (attacks != null) {
			if (PokeConstant.pokemon_with_no_attacks.contains(Integer.valueOf(pokemonId))) {
				return PokeUtil.getPredefinedAttacks();
			}
			else if(PokeConstant.pokemon_with_use_after.contains(Integer.valueOf(pokemonId))) {
				attacks.getContest_combos().getNormal().getUse_after().forEach(a -> {
					PokemonHelper poke = new PokemonHelper();
					poke.setName(a.getName());
					pokemonAttacks.add(poke);
				});
			}else {
			attacks.getContest_combos().getNormal().getUse_before().forEach(a -> {
				PokemonHelper poke = new PokemonHelper();
				poke.setName(a.getName());
				pokemonAttacks.add(poke);
			});
			}
			PokemonHelper specialAttack = new PokemonHelper();
			specialAttack.setName("special attack");
			pokemonAttacks.add(specialAttack);
		}
		return pokemonAttacks;
	}


}
