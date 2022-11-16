package com.pokearena.pokebattle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pokearena.pokebattle.configuration.PokemonConfiguration;
import com.pokearena.pokebattle.constant.PokeConstant;
import com.pokearena.pokebattle.response.PokeListResponse;
import com.pokearena.pokebattle.service.PokemonService;

@SpringBootTest
class PokebattleApplicationTests {
	@Autowired
	PokemonService pokeservice;
	@Autowired
	PokemonConfiguration pokeConfig;

	@Test
	void contextLoads() {

	}

	@Test
	void checkPokemonListCount() throws Exception {
		PokeListResponse response = pokeservice.getPokemonList();
		assertEquals(response.getResults().size(), 50);
	}

	@Test
	void checkPokemonAttacksTwoConsecutiveSpecialAttack() throws Exception {
		boolean specialAttack = true;
		int activePlayerHealth = 20;
		int opponentPlayerHealth = 20;
		String activePlayer = PokeConstant.pikachu;
		String opponentPlayer = PokeConstant.balbasaur;
		int activePlayerAttack = 0;
		int activeplayer_win = 0;
		String activePlayerName = "abc";
		String opponentPlayerName = "xyz";
		int opponentPlayerHealth_after_special_attack = 0;
		// Special Attack
		opponentPlayerHealth_after_special_attack = pokeConfig.resultAfterAttack(specialAttack, activePlayerHealth,
				opponentPlayerHealth, activePlayer, opponentPlayer, activePlayerAttack, activeplayer_win,
				activePlayerName, opponentPlayerName, "special attack", true);
		specialAttack = false;
		assertTrue(opponentPlayerHealth_after_special_attack == 0);

	}

	@Test
	void checkPokemonAttacksNormalAttack() throws Exception {
		boolean specialAttack = false;
		int activePlayerHealth = 20;
		int opponentPlayerHealth = 20;
		String activePlayer = PokeConstant.pikachu;
		String opponentPlayer = PokeConstant.balbasaur;
		int activePlayerAttack = 0;
		int activeplayer_win = 0;
		String activePlayerName = "abc";
		String opponentPlayerName = "xyz";
		int opponentPlayerHealth_after_special_attack = 0;
		// Special Attack
		opponentPlayerHealth_after_special_attack = pokeConfig.resultAfterAttack(specialAttack, activePlayerHealth,
				opponentPlayerHealth, activePlayer, opponentPlayer, activePlayerAttack, activeplayer_win,
				activePlayerName, opponentPlayerName, "leer", true);
		specialAttack = false;
		assertTrue(opponentPlayerHealth_after_special_attack > 0);

	}

}
