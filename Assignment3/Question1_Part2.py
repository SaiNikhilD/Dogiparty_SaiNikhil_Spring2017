
# coding: utf-8

# # Q1_PART TWO
#  
#   Use ‘vehicle_collisions’ data set.
#   For each borough, find out distribution of each collision scale. (One car involved? Two? Three? or more?) (From 2015   to present)
#   Display a few rows of the outputuse df.head().
#   Generate a csv output with five columns ('borough', 'one-vehicle', 'two-vehicles', 'three-vehicles', 'more-vehicles')
# 

# In[52]:

#Importing libraries
import pandas as pan


# In[54]:

#Reading csv file
df=pan.read_csv('vehicle_collisions.csv')


# In[55]:

#Counting the number of vehicles involved
df['Number of Vehicles Involved'] = df[['VEHICLE 1 TYPE','VEHICLE 2 TYPE','VEHICLE 3 TYPE','VEHICLE 4 TYPE','VEHICLE 5 TYPE']].count(axis=1,level=None,numeric_only=False)

dfunstacked = df.groupby(['BOROUGH','Number of Vehicles Involved']).size().unstack()


# In[56]:

#rename the columns
dfunstacked.columns=['ZERO_VEHICLE_INVOLVED','ONE_VEHICLE_INVOLVED','TWO_VEHICLES_INVOLVED',
           'THREE_VEHICLES_INVOLVED','FOUR_VEHICLES_INVOLVED','FIVE_VEHICLES_INVOLVED'] 

#Adding the more vehicles count
dfunstacked['MORE_VEHICLES_INVOLVED'] = dfunstacked.ZERO_VEHICLE_INVOLVED + dfunstacked.FOUR_VEHICLES_INVOLVED + dfunstacked.FIVE_VEHICLES_INVOLVED


# In[57]:

#Selecting required columns and printing
dfoutput=dfunstacked[['ONE_VEHICLE_INVOLVED','TWO_VEHICLES_INVOLVED',
           'THREE_VEHICLES_INVOLVED','MORE_VEHICLES_INVOLVED']]

dfoutput.reset_index(level=0,inplace=True) 

print(dfoutput.head())


# In[58]:

#converting to csv
dfoutput.to_csv('Question1_Part2.csv',index=False)


# In[ ]:



