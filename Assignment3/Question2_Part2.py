
# coding: utf-8

#  # Question2_Part2
# 
# • Data contains fiscal and calendar year information. Same employee details exist twice in the dataset. Filter data by calendar year and find average salary (you might have to find average for each of the columns for every employee. Eg. Average of Total Benefits, Average of total compensation etc.) for every employee. - Now, find the people whose overtime salary is greater than 5% of salaries (salaries refers to ’Salaries' column)
# • For each ‘Job Family’ these people are associated with, calculate the percentage of total benefits with respect to total compensation (so for each job family you have to calculate average total benefits and average total compensation). Create a new column to hold the percentage value. 
# • Displaythe top 5 Job Families according to this percentage value usingdf.head().
# • Write the output (jobs and percentage value) to a csv.

# In[30]:

#Importing libraries
import pandas as pan


# In[31]:

#Reading csv file
df=pan.read_csv('employee_compensation.csv')


# In[32]:

#First part of the question, calculating average salary and other columns
dfemp=df[df['Year Type'] == 'Calendar']

#Grouping by job family, reseting index and calculating mean of columns
dfAverageEmp=dfemp.groupby(['Job Family','Employee Identifier'])['Salaries','Overtime','Other Salaries','Total Salary','Retirement','Health/Dental','Other Benefits','Total Benefits','Total Compensation'].mean()
dfAverageEmp.reset_index(inplace=True)


# In[33]:

#Calculating people whose overtime salary is greater than 5% total salary
overtime = dfAverageEmp['Overtime'] > 0.05 * dfAverageEmp['Salaries'] 
dfovertime = dfAverageEmp[overtime]


# In[34]:

#Grouping by job family and calculating mean of Total Benefits and Total Compensation
dfFamilyMean = dfovertime.groupby(['Job Family'])['Total Benefits','Total Compensation'].mean()

#Calculatung Percent_Total_Benefit
dfFamilyMean['Percent_Total_Benefit']=(dfFamilyMean['Total Benefits'] / dfFamilyMean['Total Compensation']) * 100


# In[35]:

#Sorting in descending order of Percent_Total_Benefit of job famliies
dfoutput=dfFamilyMean.sort_values('Percent_Total_Benefit', ascending =False)

print(dfoutput.head())


# In[36]:

#Converting dataframe to csv
dfoutput.to_csv('Question2_Part2.csv')


# In[ ]:



