#!/usr/bin/python3

# -*- coding: utf-8 -*-

import csv
import requests
import numpy as np
import pandas as pd
from time import sleep
from sklearn.model_selection import cross_val_score
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error

def from_API_to_CSV():
	'''
	Retrieve up to date data for each player from the official API of the FPL site
	and create CSV file containing this data.

	Args:
		None

    Returns:
        None

	'''
	# Retrieve player data from FPL API
	s1='http://fantasy.premierleague.com//api/bootstrap-static/'
	r = requests.get(s1)
	top = r.text
	top = r.json()

	# Write data to csv file    
	with open('Data/UptoDate20192020SeasonData.csv', 'w', newline="") as f:
	     w = csv.DictWriter(f,top["elements"][0])
	     w.writeheader()    
	     for num in range(0,623,1):
	     	w.writerow(top["elements"][num])

def main():

	# Retrieve up to date data for each player from the official API of the FPL site
	# and create CSV file containing this data named "UptoDate20192020SeasonData.csv"
	from_API_to_CSV()

	# Read CSV into Pandas DataFrame
	# UptoFeb3SeasonData refers to Player data from GW1 to current GW (accumalative)
	players_df = pd.read_csv('Data/UptoDate20192020SeasonData.csv',index_col='web_name', na_filter=False)

	# Remove players who have played 0 minutes
	players_df[players_df.minutes > 0].to_csv('Data/test_data/regular_players.csv')
	regular_players_df = pd.read_csv('Data/test_data/regular_players.csv',index_col='web_name', na_filter=False)


	# Create two interaction terms based on what we learned from multi-collinearity matrix
	regular_players_df['interaction_term1'] = regular_players_df.value_form * regular_players_df.form
	regular_players_df['interaction_term2'] = regular_players_df.value_season * regular_players_df.form


	cols = ['points_per_game','now_cost','selected_by_percent', 'interaction_term1','interaction_term2', 'ict_index']


	X = regular_players_df[cols]

	y = regular_players_df.event_points

	lm = LinearRegression()


	# Also tried RandomForestClassifier but it prodcued an inferior model compared to Linear Regression
	# rf = RandomForestClassifier(n_estimators=100) 
	scores = cross_val_score(lm, X, y, cv=5, scoring='neg_mean_squared_error')

	# Calculate Root Mean Squared Error
	#np.sqrt(-scores)
	#np.mean(np.sqrt(-scores)) # RMSE for GW1 - currentGW 


	# Create the Same Model for next gameweek Data Set


	players_gw_df = pd.read_csv('Data/gameweek_data/gw29.csv',index_col='web_name', na_filter=False)


	players_gw_df[players_gw_df.minutes> 0].to_csv('Data/test_data/regular_players_gw29.csv')
	regular_players_gw_df = pd.read_csv('Data/test_data/regular_players_gw29.csv',index_col='web_name', na_filter=False)


	regular_players_gw_df['interaction_term1'] = regular_players_gw_df.value_form * regular_players_gw_df.form
	regular_players_gw_df['interaction_term2'] = regular_players_gw_df.value_season * regular_players_gw_df.form


	cols = ['points_per_game','now_cost','selected_by_percent', 'interaction_term1','interaction_term2', 'ict_index']


	X2 = regular_players_gw_df[cols]


	# Training the model on the GW1 to currentGW Data Set

	#rf = RandomForestClassifier(n_estimators=100)
	#rf.fit(X,y)

	lm.fit(X, y) # fitting the linear regression on GW1 to currentGW Data Set values of X & Y

	# Testing and Predicting for next GW Data set
	predictions = lm.predict(X2) 

	# create table/df with players-predictions
	new_predictions_df = regular_players_gw_df.event_points

	pd.set_option('mode.chained_assignment', None)
	new_predictions_df[:] = predictions.astype(int)


	# Create new CSV with predictions for upcoming games
	new_predictions_df.to_csv('Data/predictions_for_new_GW.csv', float_format='%.f')



if __name__ == "__main__":
	main()