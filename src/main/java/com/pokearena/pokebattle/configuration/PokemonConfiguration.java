/**
 * 
 */
package com.pokearena.pokebattle.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.pokearena.pokebattle.constant.PokeConstant;
import com.pokearena.pokebattle.helper.PokeHelper;
import com.pokearena.pokebattle.helper.PokemonHelper;
import com.pokearena.pokebattle.response.PokeListResponse;
import com.pokearena.pokebattle.service.PokemonService;
import com.pokearena.pokebattle.util.PokeUtil;

/**
 * @author kishoreselvaraju
 *
 */
@Component
public class PokemonConfiguration implements CommandLineRunner {
	@Autowired
	private PokemonService pokemonService;

	Scanner scanner = new Scanner(System.in);

	@Override
	public void run(String... args) throws Exception {

		String player1name = null;
		String player2name = null;
		List<PokemonHelper> player1_attacks_details = new ArrayList<>();
		List<PokemonHelper> player2_attacks_details = new ArrayList<>();
		HashMap<String, Integer> pokemonIds = new HashMap<>();
		System.out.println(PokeConstant.pokemon_battle_ground);
		System.out.println(PokeConstant.initial_intro);
		List<String> pokemons = new ArrayList<String>();

		String line = scanner.nextLine();
		if (line.isEmpty()) {

			System.out.println(PokeConstant.player_1_name);
			player1name = scanner.nextLine().trim();
			System.out.println(PokeConstant.player_2_name);
			player2name = scanner.nextLine().trim();
			System.out.println(PokeConstant.enter_to_begin_battle);
			String game = scanner.nextLine();
			PokeListResponse res = pokemonService.getPokemonList();
			List<PokeHelper> pokemonList = res.getResults();
			pokemonList.forEach(pokeDetails -> {
				pokemons.add(pokeDetails.getName());
				pokemonIds.put(pokeDetails.getName().trim().toLowerCase(), pokeDetails.getId());
			});

			if (game.isEmpty()) {

				String winner = battleArea(pokemonIds, player1_attacks_details, player2_attacks_details, player1name,
						player2name, pokemonList, pokemons);
				System.out.println(PokeConstant.tournament_winner + winner);
			}
		}
	}

	/**
	 * @param player1name
	 * @param pokemonList
	 * @return
	 */
	private String selectionOfpokemon(String playerName, List<PokeHelper> pokemonList) {
		pokemonList.forEach(System.out::println);
		System.out.print(PokeConstant.salutation + playerName + PokeConstant.slection_of_pokemon_from_list);
		String player = scanner.nextLine().trim();
		return player.toLowerCase();
	}

	@SuppressWarnings("unlikely-arg-type")
	public String battleArea(HashMap<String, Integer> pokemonIds, List<PokemonHelper> player1_attacks_details,
			List<PokemonHelper> player2_attacks_details, String player1name, String player2name,
			List<PokeHelper> pokemonList, List<String> pokemons) throws Exception {
		Integer pokemonId = null;
		String retry = null;
		int player1_win = 0;
		int player2_win = 0;
		int matchPlayed = 1;
		/**
		 * Best of 3 matches
		 */
		for (int i = 0; i < 3; i++) {
			if (matchPlayed > 1) {
				System.out.println(PokeConstant.status);
				System.out.println(player1name + " win is " + player1_win);
				System.out.println(player2name + " win is " + player2_win);
				System.out.println(PokeConstant.preparation_of_next_battle);
				Thread.sleep(5000);
			}
			System.out.println(PokeConstant.salution_to_battle + matchPlayed + " (Best of 3 matches)");
			String player1 = selectionOfpokemon(player1name, pokemonList);
			String player2 = selectionOfpokemon(player2name, pokemonList);
			PokeUtil.validationCheck(player1, pokemons);
			System.out.println(PokeConstant.selected_pokemon + player1name + " is :" + player1);
			Thread.sleep(2000);

			System.out.println(PokeConstant.selected_pokemon + player2name + " is :" + player2);
			Thread.sleep(2000);
			PokeUtil.validationCheck(player2, pokemons);
			if (!PokeUtil.isNumeric(player1)) {
				pokemonId = pokemonIds.get(player1);
			}
			player1_attacks_details = pokemonService.getPokemonAttack(pokemonId.toString());
			if (player1_attacks_details == null) {
				player1_attacks_details = PokeUtil.getPredefinedAttacks();
			}
			if (!PokeUtil.isNumeric(player2)) {
				pokemonId = pokemonIds.get(player2);
			}
			/**
			 * If both player chooses same pokemon;
			 */
			if (player1.equalsIgnoreCase(player2)) {
				player2 += 2;
			}
			player2_attacks_details = pokemonService.getPokemonAttack(pokemonId.toString());

			int hp_player1 = 20;
			int hp_player2 = 20;
			int player1_move_count = 0;
			int player2_move_count = 0;
			boolean sa_player1 = false;
			boolean sa_player2 = false;
			PokemonHelper special_attack = new PokemonHelper();
			special_attack.setName(PokeConstant.special_attack);
			while (hp_player1 > 0 || hp_player2 > 0) {
				PokeUtil.checkWinnerStatus(hp_player2, player2name, player2, player1name, player1);
				PokeUtil.checkWinnerStatus(hp_player1, player1name, player1, player2name, player2);
				System.out.println(PokeConstant.selection_of_attack_for_player1);
				System.out.println(player1_attacks_details.toString());
				String attack_p1 = scanner.nextLine();
				PokeUtil.validateAttacks(attack_p1, player1_attacks_details);
				sa_player1 = PokeUtil.checkForSpecialAttack(attack_p1, player1name);
				if (sa_player1) {
					player1_attacks_details.remove(player1_attacks_details.size() - 1);
					player1_move_count += 1;
					if (player1_move_count > 2) {
						player1_attacks_details.add(special_attack);
						sa_player1 = false;
					}

				}
				hp_player2 = resultAfterAttack(sa_player1, hp_player1, hp_player2, player1, player2, player1_win,
						player2_win, player1name, player2name, attack_p1, false);
				if (PokeUtil.checkWinnerStatus(hp_player2, player2name, player2, player1name, player1) == 1) {
					player1_win = player1_win + 1;
					System.out.println(
							"=============" + player1name + " wins ===========" + " Win Count :" + player1_win);
					Thread.sleep(5000);
					if (matchPlayed < 2 || (player1_win == 1 && player2_win == 1)) {
						System.out.println(PokeConstant.retry_message);
						retry = scanner.nextLine().trim();
						System.out.println(retry);
					}
					Thread.sleep(3000);
					break;
				}
				System.out.println(PokeConstant.selection_of_attack_for_player2);
				System.out.println(player2_attacks_details.toString());
				String attack_p2 = scanner.nextLine();
				PokeUtil.validateAttacks(attack_p2, player2_attacks_details);
				sa_player2 = PokeUtil.checkForSpecialAttack(attack_p2, player2name);
				if (sa_player2) {
					player2_attacks_details.remove(player2_attacks_details.size() - 1);
					player2_move_count += 1;
					if (player2_move_count > 2) {
						player2_attacks_details.add(special_attack);
						sa_player2 = false;
					}
				}
				hp_player1 = resultAfterAttack(sa_player2, hp_player2, hp_player1, player2, player1, player2_win,
						player1_win, player2name, player1name, attack_p2, false);
				if (PokeUtil.checkWinnerStatus(hp_player1, player1name, player1, player2name, player2) == 1) {
					player2_win = player2_win + 1;
					System.out.println(
							"=============" + player2name + " wins ===========" + " Win Count :" + player2_win);
					Thread.sleep(5000);
					if (matchPlayed < 2 || (player1_win == 1 && player2_win == 1)) {
						System.out.println(PokeConstant.retry_message);
						retry = scanner.nextLine().trim();
						System.out.println(retry);
					}
					Thread.sleep(3000);
					break;
				}
			}

			if (player1_win == 2) {
				return player1name;
			} else if (player2_win == 2) {
				return player2name;
			}
			matchPlayed++;
		}

		return null;
	}

	/***
	 * The below business is based on the result after one attack with active player
	 * to opponent player
	 * 
	 * @param attack
	 * @param activePlayerHealth
	 * @param opponentPlayerHealth
	 * @param activePlayer
	 * @param opponentPlayer
	 * @param activePlayerAttack
	 * @param activeplayer_win
	 * @param opponentPlayerName
	 * @param activePlayerName
	 * @param attack_name
	 * @return
	 */
	public int resultAfterAttack(boolean specialAttack, int activePlayerHealth, int opponentPlayerHealth,
			String activePlayer, String opponentPlayer, int activePlayerAttack, int activeplayer_win,
			String activePlayerName, String opponentPlayerName, String attack_name, boolean testStatus)
			throws Exception {
		if (specialAttack) {
			if (PokeUtil.specialAttackValidation(specialAttack, attack_name, activePlayer, testStatus)) {
				return 0;
			}
			activePlayerAttack = PokeUtil.hpdamage(5, 15);
		} else {
			activePlayerAttack = PokeUtil.hpdamage(0, 10);
		}
		opponentPlayerHealth -= activePlayerAttack;
		if (opponentPlayerHealth < 0 || opponentPlayerHealth == 0) {
			return opponentPlayerHealth;
		}
		System.out.println("The " + activePlayerName + "'s" + "pokemon  attacked with " + activePlayerAttack
				+ ". The HP of " + opponentPlayer + "(" + opponentPlayerName + ")" + " is " + opponentPlayerHealth);
		System.out.println(PokeConstant.HP_status);
		System.out.println(activePlayer + PokeConstant.health_is + activePlayerHealth);
		System.out.println(opponentPlayer + PokeConstant.health_is + opponentPlayerHealth);
		return opponentPlayerHealth;

	}

}
