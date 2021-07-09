import random
import numpy as np
import pandas as pd

def linear_combination_plus_error(X, num_dependent_cols=5, parameter_mean=0, parameter_std=1, error_mean=0, error_std=1):
    """
    Generate a column that is a random linear combination of
    X1, X2 and X3 plus some random error
    """
    length = X.shape[0]
    param = np.random.normal(loc=parameter_mean,
                             scale=parameter_std,
                             size=(num_dependent_cols,))
    error = np.random.normal(loc=error_mean,
                             scale=error_std,
                             size=(length,))
    result = np.zeros(length,)
    for i in range(num_dependent_cols):
        result += param[i] * X[:, i]
    return result + error
    

np.random.seed(472)
num_data = 10100
num_independent_cols = 3

X = np.zeros((num_data, 1001))

# Generate 3 principal components
for i in range(num_independent_cols):
    X[:, i] = np.random.normal(np.random.uniform(-5, 5), 
                               np.random.uniform(1, 5), size=(num_data,))


# Generate other columns
for i in range(3, 1000):
    X[:, i] = linear_combination_plus_error(X, num_dependent_cols=num_independent_cols, parameter_std=2, error_std=1)

# Randomly suffle the 1000 feature columns
col_nums = list(range(1000))
np.random.shuffle(col_nums)
X[:, list(range(1000))] = X[:, col_nums]

# Randomly generate Y
X[:, 1000] = linear_combination_plus_error(X, num_dependent_cols=num_independent_cols, parameter_mean=5, parameter_std=2)
X[:, 1000] += abs(min(X[:, 1000])) + 5


# Take only three digits after decimal point
X = np.floor(X * 1000) / 1000


# Split the data into 2 files
X1 = X[:10000, :]
X2 = X[10000:, :]
X1_df = pd.DataFrame(X1)
X1_df.to_csv("./sensors1.csv", header=None, index=None)

X2_df = pd.DataFrame(X2)
X2_df.to_csv("./sensors2.csv", header=None, index=None)



