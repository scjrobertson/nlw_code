import numpy as np
from matplotlib import pylab as plt

FNAME = "golden_primes.txt"
#FNAME = "long_fix.txt"
M = 10

def normal(x, mean = 0, var = 1):
    return ( 1/np.sqrt(2*np.pi*var) )*np.exp(-((x - mean)**2)/(2*var))

def rayleigh(x, mean = 0, var = 1):
    a = mean - np.sqrt(( np.pi*var )/(4 - np.pi))
    b = ( 4*var )/(4 - np.pi )
    f = np.zeros(len(x))
    f[x >= a] = (2/b)*(x[ x >= a ]-a)*np.exp(-((x[x >= a]-a)**2)/b)
    return f

def exponential(x, mean = 1, var = 1):
    return (1/mean)*np.exp(-x/mean)

X = np.loadtxt(FNAME, delimiter=";", usecols = (0, 1))[:, 0]
mean = np.mean(X)
var = np.var(X)
print mean, var

t = np.arange(0, 100)
y = exponential(t, mean, var)

plt.figure()
bin_size = M
bins = np.arange(0, 100, bin_size)
N = len(X)
scaling = np.ones(N)/N/bin_size
plt.hist(X, bins, weights = scaling)
plt.xlabel("Bit error rate (%)");
plt.ylabel("Normalized frequency");
plt.title("Distribution of bit error rate for a fixed prime")
plt.show()
