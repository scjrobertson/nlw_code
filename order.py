import numpy as np
from matplotlib import pylab as plt

x = np.arange(0, 110, 10)
y = np.arange(0, 60, 10)
rd = np.array([22.42, 29.92, 37.52, 41.49, 44.38, 48.24, 48.85, 49.52, 49.47, 50, 50])
pd = np.array([22.42, 23.9, 25.57, 27.69, 31.83, 34.16, 38.52, 41.57, 42.9, 46.92, 50])
ra = np.array([22.42, 32.42, 38.22, 44.84, 44.63, 48.18, 49.33, 47.83, 47.39, 50, 50])
#cj = np.array([22.42, 45.5, 48.37, 47.90, 49.05, 49.59, 48.52, 49.165, 48.45, 49.29, 49.98])
#ins = np.array([22.42, 44.24, 48.39, 47.99, 48.87, 48.45, 50, 49.87, 47.84, 49.67, 50])
#wd = np.array([22.42, 44.36, 47.6, 49.49, 49.88, 48.22, 50, 50, 49.32, 50, 50])
mod = np.array([22.42, 36.55, 40.70, 42.53, 42.53, 46.36, 46.86, 49.2, 50, 50, 50])

#attacks = [rd, pd, ra, cj, ins, wd, mod]
#lines = ['k', 'k_', 'k-', 'k--', 'k:', 'k-.', 'k-*']
#labels = ['Random deletion', 'Precise deletion', 'Rearrangement', 'Conjunction', 'Sentence insertion', 'Word insertion', 'Modification']
attacks = [rd, pd, ra, mod]
lines = ['b', 'b-*', 'b--', 'b:', 'b-.']
labels = ['Random deletion', 'Precise deletion', 'Rearrangement', 'Modification']


plt.figure()
plt.xticks(x)
plt.yticks(y)
plt.axis((0, 100, 0, 50))
plt.xlabel('% Aggression')
plt.ylabel('Expected %BER')
plt.title('Order of growth for attack types')
plt.grid(True)
for i, j, k in zip(attacks, lines, labels):
    plt.plot(x, i, j, label = k)
plt.legend(loc='lower right')
plt.show()
