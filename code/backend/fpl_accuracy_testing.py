
# This code was used in fpl_predictions.py to get the prediction accuracy results from GW1 to GW23

import pandas as pd
import fpl_predictions

LAST_GAMEWEEK_TESTING = 24

def create_model_for_gameweek(num_gameweek):

	num_gameweek = str(num_gameweek)

	# Create the Same Model for gw2 Data Set

	players_gw_df = pd.read_csv('Data/test_data/gw' + num_gameweek + '.csv',index_col='web_name', na_filter=False)


	players_gw_df[players_gw_df.minutes> 0].to_csv('Data/test_data/regular_players_gw' + num_gameweek + '.csv')
	regular_players_gw_df = pd.read_csv('Data/test_data/regular_players_gw' + num_gameweek + '.csv',index_col='web_name', na_filter=False)


	regular_players_gw_df['interaction_term1'] = regular_players_gw_df.value_form * regular_players_gw_df.form
	regular_players_gw_df['interaction_term2'] = regular_players_gw_df.value_season * regular_players_gw_df.form


	cols = ['points_per_game','now_cost','selected_by_percent', 'interaction_term1','interaction_term2', 'ict_index']


	X2 = regular_players_gw_df[cols]

	y2 = regular_players_gw_df.event_points

	return X2, y2

def main():

	for gameweek in range(1,LAST_GAMEWEEK_TESTING):
		X2, y2 = create_model_for_gameweek(gameweek)
		# Testing and Predicting for gw2 Data set
		predictions = lm.predict(X2) # X for gw2

	# calc RMSE to compare preds vs y for gw2
		rms = np.sqrt(mean_squared_error(y2, predictions))
		print("RMSE for gw" + str(gameweek) + " data set: ", rms) # RMSE for preds for gw2 data set 1.9871


if __name__ == "__main__":
	main()