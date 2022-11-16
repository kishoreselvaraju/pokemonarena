/**
 * 
 */
package com.pokearena.pokebattle.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.pokearena.pokebattle.constant.PokeConstant;
import com.pokearena.pokebattle.helper.PokemonHelper;

/**
 * @author kishoreselvaraju
 *
 */
public final class PokeUtil {

	public static List<PokemonHelper> getPredefinedAttacks() {
		PokemonHelper att1 = new PokemonHelper();
		PokemonHelper att2 = new PokemonHelper();
		PokemonHelper att3 = new PokemonHelper();
		PokemonHelper att4 = new PokemonHelper();
		att1.setName(PokeConstant.leer);
		att2.setName(PokeConstant.harden);
		att3.setName(PokeConstant.defense_curl);
		att4.setName(PokeConstant.special_attack);
		List<PokemonHelper> attackList = new ArrayList<>();
		attackList.add(att1);
		attackList.add(att2);
		attackList.add(att3);
		attackList.add(att4);
		return attackList;
	}

	public static int hpdamage(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	public static void validateAttacks(String attack_p1, List<PokemonHelper> attacks_details) throws Exception {
		List<String> attacks = new ArrayList<>();
		attacks_details.forEach(attack -> attacks.add(attack.getName()));
		if (attack_p1 == null) {
			throw new Exception(PokeConstant.attack_exception_message);
		}
		if (!attacks.contains(attack_p1)) {
			throw new Exception(PokeConstant.invalid_attack_exception_message);
		}
	}

	public static int checkWinnerStatus(int defeatedPlayer_hp, String defeatedPlayerName, String defeatedPokemon,
			String activePlayerName, String activePokemon) {
		int active_player_win_count = 0;
		if (defeatedPlayer_hp < 0 || defeatedPlayer_hp == 0) {
			active_player_win_count += 1;
			System.out.println(defeatedPlayerName + "'s " + defeatedPokemon + " is defeated. " + activePlayerName
					+ "'s " + activePokemon + " wins the match. " + "" + activePlayerName + "'s match win is "
					+ active_player_win_count);

		}
		return active_player_win_count;
	}

	public static boolean checkForSpecialAttack(String attack, String playername) {
		boolean check = (attack.equalsIgnoreCase("special attack"));
		if (check) {
			System.out.println(
					playername + " has used special attack. It requires 2 attacks to restore the special attack.");
		}
		return check;

	}

	public static boolean isNumeric(String pokemon) {
		try {
			Double.parseDouble(pokemon);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;

	}

	public static void validationCheck(String pokemon, List<String> pokemonList) throws Exception {
		if (pokemon == null) {
			throw new Exception(PokeConstant.null_message);
		}
		if (!PokeUtil.isNumeric(pokemon)) {
			if (!pokemonList.contains(pokemon)) {
				throw new Exception(PokeConstant.selection_from_list_message);
			}
		}
	}

	public static boolean specialAttackValidation(boolean specialAttack, String attack_name, String activePlayer,
			boolean testStatus) throws Exception {
		if (testStatus) {
			if (specialAttack) {
				if (checkForSpecialAttack(attack_name, activePlayer)) {
					return true;
				}
			}
		}
		return testStatus;
	}
}
