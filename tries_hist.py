import numpy as np
from matplotlib import pylab as plt

FNAME = raw_input("Input file name: ")
TITLE = raw_input("Input plot title: ")
M = int( raw_input("Bin size: " ) )

X = np.loadtxt(FNAME)
X = X[ X>0 ]
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
plt.title(TITLE)
plt.show()
