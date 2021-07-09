import numpy as np
import pandas as pd
from sklearn.decomposition import PCA

sensors1 = pd.read_csv("sensors1.csv", header=None, index_col=None)

# Normalize, without this step the answer would be wrong
for i in range(sensors1.shape[1]):
    sensors1[i] = (sensors1[i] - np.mean(sensors1[i])) / np.std(sensors1[i])

# Drop the last column
sensors1 = sensors1.drop(1000, axis=1)
print(sensors1)

explained_var = []
for i in range(5):
    print(i)
    pca = PCA(n_components=i+1)
    pca.fit(sensors1)
    explained_var.append(sum(pca.explained_variance_ratio_))

print(explained_var)