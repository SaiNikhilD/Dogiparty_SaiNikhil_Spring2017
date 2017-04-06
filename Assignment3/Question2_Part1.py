
# coding: utf-8

#  # Question2_Part1
# •Use 'employee_compensation' data set.
# •Find out the highest paid departments in each organization group by calculating mean of total compensation for every  department.
# •Output should contain the organization group and the departments in each organization group with the total compensationfrom highest to lowest value.
# •Display a few rows of the outputuse df.head().
# •Generate a csv output.

# In[17]:

#Importing libraries
import pandas as pan


# In[18]:

#Reading csv file
df=pan.read_csv('employee_compensation.csv')


# In[19]:

#Grouping by Organization Group and Department
dfgrouped = df.groupby(['Organization Group', 'Department'],as_index=False)['Total Compensation'].mean()


# In[20]:

#Sorting in descending order
dfoutput= dfgrouped.sort_values(['Total Compensation'], ascending = [False])


# In[21]:

#printing few values using head
print(dfoutput.head())


# In[22]:

#Converting to csv
dfoutput.to_csv('Question2_Part1.csv',index=False)


# In[ ]:



