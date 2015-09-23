import numpy as np
from matplotlib import pylab as plt

FNAME = "suitable_primes.txt"
BER = np.loadtxt(FNAME, delimiter=";", usecols = (0, 1))[:, 0]
PRIMES = np.loadtxt(FNAME, dtype = "str", delimiter=";", usecols = (0, 1))[:, 1]

d={}
p=[]

for i in np.arange(0, len(PRIMES)):
    j = PRIMES[i]
    if j not in d:
        d[j] = [BER[i], 1]
    else:
        d[j] = [d[j][0]+BER[i], d[j][1]+1]

for i in d:
    if d[i][0]/d[i][1] < 20:
        p += [i]

print len(p)
