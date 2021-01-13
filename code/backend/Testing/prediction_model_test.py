import unittest
import pandas as pd
import numpy as np
from sklearn.model_selection import cross_val_score
from sklearn.linear_model import LinearRegression
from pandas.testing import assert_frame_equal     # for testing dataframes
from pandas.testing import assert_series_equal    # for testing series


class Prediction_model_test(unittest.TestCase):
    ''' Class for running unittests on functionalities of fpl_predictions.py '''

    def setUp(self):
        ''' SetUp dataframe '''
        TEST_INPUT_DIR = "../Data/test_data"
        test_file_name =  "/unit_testing.csv"
        try:
            data = pd.read_csv(TEST_INPUT_DIR + test_file_name, na_filter=False)
            self.df = data
        except IOError:
            print("cannot open file")

    def test_dataFrame_constructed_as_expected(self):
        ''' Test that the dataframe read in as expected'''
        expected_df = pd.DataFrame({'form':[0.67,0],'ict_index':[1,0],'now_cost':[55,50],'points_per_game':[1.5,0],'selected_by_percent':[0.7,0.1],'value_form':[0.3,0],'value_season':[0.2,0],'web_name':["Mustafi","Bellerin"],'event_points':[2,0], })
        assert_frame_equal(self.df, expected_df)

    def test_dataFrame_modified_as_expected(self):
        ''' Test that the dataframe is modified as expected (add two new columns: interaction_term1 and interaction_term1)'''
        self.df['interaction_term1'] = self.df.value_form * self.df.form
        self.df['interaction_term2'] = self.df.value_season * self.df.form
        expected_df = pd.DataFrame({'form':[0.67,0],'ict_index':[1,0],'now_cost':[55,50],'points_per_game':[1.5,0],'selected_by_percent':[0.7,0.1],'value_form':[0.3,0],'value_season':[0.2,0],'web_name':["Mustafi","Bellerin"],'event_points':[2,0],'interaction_term1':[0.201,0],'interaction_term2':[0.134,0] })
        assert_frame_equal(self.df, expected_df)

    def test_prediction_model(self):
        ''' Test Linerar Regression prediction model'''
        cols = ['points_per_game','now_cost','selected_by_percent', 'interaction_term1','interaction_term2', 'ict_index']


        X = self.df[cols]

        y = self.df.event_points

        lm = LinearRegression()

        scores = cross_val_score(lm, X, y, cv=2, scoring='neg_mean_squared_error')

        # Check RMSE is as expected
        self.assertEquals(np.mean(np.sqrt(-scores)), 2.0)

        X2 = self.df[cols]


        lm.fit(X, y) # fitting the linear regression on GW1 to GW24 Data Set values of X & Y

        # Testing and Predicting for gw2 Data set
        predictions = lm.predict(X2) # X for gw2

        # Create table/df with players-predictions
        new_predictions_series = self.df.event_points

        pd.set_option('mode.chained_assignment', None)
        new_predictions_series[:] = predictions.astype(int)

        expected_predictions_series =pd.Series([2, 0], dtype='int', name = "event_points")
        
        # Check that predictions match expected predictions
        assert_series_equal(new_predictions_series, expected_predictions_series)


def main():
    df = Prediction_model_test()
    df.setUp()
    df.test_dataFrame_constructed_as_expected()
    df.test_dataFrame_modified_as_expected()
    df.test_prediction_model()


if __name__ == "__main__":
    main()
