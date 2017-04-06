
# coding: utf-8

# # Question4
#  
#  • Use ‘movies_awards’ data set.
#  • You are supposed to extract data from the awards column in this dataset and split it into several columns. An example is given below.
#  • The awards has details of wins, nominations in general and also wins and nominations in certain categories(e.g. Oscar, BAFTA etc.)
#  • You are supposed to create a win and nominated column (these 2 columns contain total number of wins and nominations) and other columns that extract the number of wins and nominations for each category of award. 
#  • If a movie has 2 Oscar nominations and 4 Oscar won, the columns Oscar_Awards_Won should have value 4 and Oscar_Awards_Nominated should have value 2. You should also have a total won and nominated column which aggregates all the awards (won or nominated). 
#  • Create two separate columns for each award category (won and nominated).
#  • Write your output to a csv file. (Sample output is given in next page)
# 

# In[134]:

#Importing libraries
import pandas as pan
import numpy as np


# In[135]:

#Reading csv file
df=pan.read_csv('movies_awards.csv')

#Replacing NAN with 0
df = df.fillna(0)


# In[136]:

#Awards nominated and won is found using str.extract
df['Prime_Awards_Won']= df['Awards'].str.extract('Won (\d+) Primetime', expand=True).apply(pan.to_numeric)
df['Bafta_Awards_Won']= df['Awards'].str.extract('Won (\d+) BAFTA', expand=True).apply(pan.to_numeric)
df['Oscar_Awards_Won']= df['Awards'].str.extract('Won (\d+) Oscar', expand=True).apply(pan.to_numeric)
df['Golden_GlobeAwards_Won']= df['Awards'].str.extract('Won (\d+) Golden Globe', expand=True).apply(pan.to_numeric)

df['Prime_Awards_Nominated']= df['Awards'].str.extract('Nominated for (\d+) Primetime', expand=True).apply(pan.to_numeric)
df['Bafta_Awards_Nominated']= df['Awards'].str.extract('Nominated for (\d+) BAFTA', expand=True).apply(pan.to_numeric)
df['Oscar_Awards_Nominated']= df['Awards'].str.extract('Nominated for (\d+) Oscar', expand=True).apply(pan.to_numeric)
df['Golden_GlobeAwards_Nominated']= df['Awards'].str.extract('Nominated for (\d+) Golden Globe', expand=True).apply(pan.to_numeric)

df['Awards_Won'] = df['Awards'].str.extract('(\d+) win', expand=True).apply(pan.to_numeric)
df['Awards_Nominated'] = df['Awards'].str.extract('(\d+) nomination', expand=True).apply(pan.to_numeric)

#Replacing Nan with 0
df = df.fillna(0)



# In[137]:

#Calculating total awards won
df['Total_Awards_Won']=df['Awards_Won']+df['Prime_Awards_Won']+df['Bafta_Awards_Won']+df['Oscar_Awards_Won']+df['Golden_GlobeAwards_Won']


# In[138]:

#Calculating total nominations
df['Total_Awards_Nominated']=df['Awards_Nominated']+df['Prime_Awards_Nominated']+df['Bafta_Awards_Nominated']+df['Oscar_Awards_Nominated']+df['Golden_GlobeAwards_Nominated']


# In[139]:

#To select desired columns
dfoutput=df[['Awards','Total_Awards_Won','Total_Awards_Nominated','Prime_Awards_Nominated','Oscar_Awards_Nominated','Golden_GlobeAwards_Nominated','Bafta_Awards_Nominated','Prime_Awards_Won','Oscar_Awards_Won','Golden_GlobeAwards_Won','Bafta_Awards_Won']]

#Eliminating zero value rows
dfoutput= dfoutput.loc[~(dfoutput==0).all(axis=1)]


# In[140]:

print(dfoutput.head())


# In[141]:

#Converting dataframe to csv
dfoutput.to_csv('Question4.csv', index=False)


# In[ ]:



