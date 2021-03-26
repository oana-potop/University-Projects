import numpy

n = int(input())


pol = []

for _ in range(n):
    x, y, z = map(int, input("Coordonatele punctelui (x, y, z): ").split())
    pol.append([x, y, z])

print(pol)