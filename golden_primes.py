import numpy as np
from matplotlib import pylab as plt

FNAME = "random_prime.txt"
BER = np.loadtxt(FNAME, delimiter=";", usecols = (0, 1))[:, 0]
PRIMES = np.loadtxt(FNAME, dtype = "str", delimiter=";", usecols = (0, 1))[:, 1]

golden_primes = PRIMES[BER < 10]

for i in golden_primes:
    print i

