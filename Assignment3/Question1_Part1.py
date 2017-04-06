
# coding: utf-8

# # Q1_PART ONE
# • Use "vehicle_collisions"  data set.
# • For each month in 2016, find out the percentage of collisions in Manhattan out of that year's total accidents in New  York City. 
# • Display a few rows of the output use df.head ().
# • Generate a csv output with four columns (‘Month’, ‘Manhattan’, ‘NYC’, ‘Percentage’)

# In[87]:

#Importing libraries
import pandas as pan
import calendar


# In[88]:

#Reading csv file
df=pan.read_csv('vehicle_collisions.csv')


# In[89]:

#Spliting Date into Year and Month
df['DATE']=pan.to_datetime(df.DATE)
df['YEAR'], df['MONTH'] = df['DATE'].dt.year, df['DATE'].dt.month 


# In[90]:

#Filtering 2016 entries
df_2016=df[df['YEAR'] == 2016]


# In[91]:

#Three variables to be calculated 
MANCount=df_2016[df_2016.BOROUGH=='MANHATTAN'].groupby('MONTH').BOROUGH.count()
NYCount=df_2016.groupby('MONTH').DATE.count()
Percentage=(float(100.0)*(MANCount)/(NYCount))


# In[92]:

#Creating a new dataframe with calculated values
output_df=pan.DataFrame({'MANHATTAN':MANCount,'NYC':NYCount,'PERCENTAGE':Percentage})

output_df.reset_index(level=0,inplace=True) 

#Converting into Months
output_df['MONTH'] = output_df['MONTH'].apply(lambda x: calendar.month_abbr[x])

#printing only some details using head()
print(output_df.head())

#Converting data frame to csv
output_df.to_csv('Question1_Part1.csv')

