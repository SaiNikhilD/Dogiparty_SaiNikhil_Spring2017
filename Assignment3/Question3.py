
# coding: utf-8

# # Question 3
# 
#  • Use ‘cricket_matches’ data set.
#  • Calculate the average score for each team which host the game and win the game.
#  • Remember that if a team hosts a game and wins the game, their score can be innings_1 runs or innings_2 runs. You have to check if the host team won the game, check which innings they played in (innings_1 or innings_2), and take the runs scored in that innings. The final answer is the average score of each team satisfying the above condition.
#  • Display a few rows of the outputuse df.head()
#  • Generate a csv output

# In[73]:

#Importing libraries
import pandas as pan
import numpy as np


# In[74]:

#Reading csv file
df=pan.read_csv('cricket_matches.csv')


# In[75]:

#To form a dataframe where the host is the winner
dfHomeWinner = df[df['home']== df['winner']]


# In[76]:

#Removing default warning
#pan.options.mode.chained_assignment = None

#Winner score of the innings they played
dfHomeWinner['Winner_Score'] = np.where(dfHomeWinner['innings1']==dfHomeWinner['winner'], dfHomeWinner['innings1_runs'], dfHomeWinner['innings2_runs'])


# In[77]:

#Mean of the Home team score
dfTeamAverage= dfHomeWinner.groupby(['home'],as_index=False)['Winner_Score'].mean()


# In[78]:

#Printing head
print(dfTeamAverage.head())


# In[79]:

#Converting dataframe to csv
dfTeamAverage.to_csv('Question3.csv', index=False)


# In[ ]:



