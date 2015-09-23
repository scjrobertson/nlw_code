import numpy as np
from matplotlib import pylab as plt

FNAME = "suitable_primes_tries.txt"
M = 1

X = np.loadtxt(FNAME)
mean = np.mean(X)
var = np.var(X)
print mean, var

plt.figure()
bin_size = M
bins = np.arange(0, 15, bin_size)
N = len(X)
scaling = np.ones(N)/N/bin_size
plt.hist(X, bins, weights = scaling)
plt.xlabel("Tries");
plt.ylabel("Relative frequency");
plt.title("Distribution of tries")
plt.show()
