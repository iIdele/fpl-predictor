
# This code was used in fpl_predictions.py to plot graphs, explore multi-collinearity and 
# handle correlations to verify which were the best features to use in our prediction model

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import statsmodels.formula.api as smf
import statsmodels.api as sm


def main():

	# Read CSV into Pandas DataFrame
	# UptoFeb3SeasonData refers to Player data from GW1 to GW24 (accumalative)
	players_df = pd.read_csv('Data/UptoFeb3SeasonData.csv',index_col='web_name', na_filter=False)

	# Remove players who have played 0 minutes
	players_df[players_df.minutes > 0].to_csv('Data/test_data/regular_players.csv')
	regular_players_df = pd.read_csv('Data/test_data/regular_players.csv',index_col='web_name', na_filter=False)

	# Observing the Data for Exploration
	print(players_df.head())
	print(players_df.tail())
	print(players_df.dtypes)
	print(players_df.iloc[0,:])


	# Some random plotting to determine which attributes to use in the final model
	# We did this step for various variables but it didn't reveal much to us
	plt.scatter(players_df.value_form, players_df.points_per_game, alpha=0.3)
	plt.scatter(players_df.value_season, players_df.points_per_game, alpha=0.3)
	plt.scatter(players_df.bps, players_df.points_per_game, alpha=0.3)
	plt.xlabel("BPS")
	plt.ylabel("Points Per Game")
	plt.show()


	# Create initial Linear Model for players

	players_model = smf.ols(formula='event_points ~ selected_by_percent + value_form + value_season + form + ict_index + bps', data=players_df).fit()
	print(players_model.summary())

	# Explore Multi-collinearity between variables chosen
	columns = ['event_points', 'selected_by_percent', 'value_form', 'value_season', 'form','ict_index','bps']
	sm = pd.plotting.scatter_matrix(players_df[columns])
	plt.show()


	# It's obvious from the Correlation Matrix that there is correlation between bps - ict_index & form - value_form
	# Hence removing bps & value_from model and exploring
	players_model = smf.ols(formula='event_points ~ selected_by_percent + form + value_season + ict_index', data=players_df).fit()
	print(players_model.summary())


	# Trying Interaction terms to handle Correlation
	# Interaction using * between form & value_form ; form & value_season
	interaction_model = smf.ols(formula='event_points ~ selected_by_percent + value_form*form + value_season*form + ict_index', data=players_df).fit()
	print(interaction_model.summary())


	# Run all features models for data set without fringe players
	# This inculdes all the possible features, just to analyse the importance of each feature
	all_features_model = smf.ols(formula='event_points ~ selected_by_percent + total_points+ chance_of_playing_this_round + value_form + value_season + form + transfers_out_event + transfers_in_event + points_per_game + minutes + ict_index + bps', data=regular_players_df).fit()
	print(all_features_model.summary())

if __name__ == "__main__":
	main()
