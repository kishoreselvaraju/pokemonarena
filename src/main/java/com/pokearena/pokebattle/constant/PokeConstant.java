/**
 * 
 */
package com.pokearena.pokebattle.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author kishoreselvaraju
 *
 */
public interface PokeConstant {
	/**
	 * URLS
	 */
	public final String pokeURL = "https://pokeapi.co/api/v2/";
	public final String pokemon_list = "/pokemon?offset=0&limit=50";
	public final String pokemon_attack = "/move/";
	/**
	 * 
	 */
	public final String pokemon_battle_ground = "%%%%%%%%%%%%% POKEMON BATTLE GROUND %%%%%%%%%%%%%%%%%%%%%";
	public final String initial_intro = "******************* Welcome to Pokemon Battle arena . Press Enter to continue ... ************************";
	public final String player_1_name = "Please Enter the name of player 1 : ";
	public final String player_2_name = "Please Enter the name of player 2: ";
	public final String enter_to_begin_battle = "Please press enter to begin the battle";
	public final String tournament_winner = "The winner of the tournament is ";
	public final String salutation = "Hey, ";
	public final String slection_of_pokemon_from_list = ".Please select the pokemon from the above list  ";
	public final String status = "------------STATUS----------------";
	public final String preparation_of_next_battle = "Please wait... Preparing ground for next battle";
	public final String salution_to_battle = "Welcome to Pokemon Battle. This is the match : ";
	public final String selected_pokemon = "The selected pokemon for ";
	public final String selection_of_attack_for_player1 = "Please select the attack for the player 1";
	public final String selection_of_attack_for_player2 = "Please select the attack for the player 2";
	public final String HP_status = "*********** HP Status ********";
	public final String health_is = "'s health is ";
	public final String pikachu = "pikachu";
	public final String balbasaur = "balbasaur";
	public final String leer = "leer";
	public final String harden = "harden";
	public final String defense_curl = "defense-curl";


	/**
	 * Exception message
	 */
	public final String null_message = "It seems you have enter invalid/empty. Please try again";
	public final String selection_from_list_message = "Please select from the list. Please try again";
	public final String retry_message = "Do you want to retry ? [y/n]";
	public final String attack_exception_message = "Oops... something went wrong. Attack can't be empty. Please try again";
	public final String invalid_attack_exception_message = "Oops... something went wrong. Invalid attack . Please try again";

	/**
	 * 	
	 */
	public final String special_attack = "special attack";

	public final List<Integer> pokemon_with_no_attacks = Arrays.asList(4, 6, 13, 16, 17, 18, 19, 24, 27, 34, 35, 40, 41,
			42, 46, 48, 49, 50);
	public final List<Integer> pokemon_with_use_after = Arrays.asList(2, 3, 5, 7, 9, 12, 15, 20, 21, 22, 23, 25, 26, 28,
			29, 31, 32, 33, 36, 37, 38, 39, 44, 45);

}
