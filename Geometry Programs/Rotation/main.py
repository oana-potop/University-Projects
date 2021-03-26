# PROBLEMA 1 - Rotatia in jurul unei axe oarecare
__author__ = "Onutza"

from math import sin, cos, pi, sqrt
import numpy


if __name__ == "__main__":
    print("Axa de reflexie data de punctul P(xp, yp, zp) si versorul w(wx, wy, wz)")
    print("Punctul P: ")
    xp, yp, zp = float(input("xp = ")), float(input("yp = ")), float(input("zp = "))
    print("Versorul w: ")
    wx, wy, wz = float(input("wx = ")), float(input("wy = ")), float(input("wz = "))
    t = float(input("Unghiul de rotatie: "))
    t = t * pi / 180
    # sin(teta_x)
    sx = wy / sqrt(wy ** 2 + wz ** 2)
    # cos(teta_x)
    cx = wz / sqrt(wy ** 2 + wz ** 2)
    # sin(teta_y)
    sy = wx
    # cos(teta_y)
    cy = sqrt(wy ** 2 + wz ** 2)

    # Translatie de (xp, yp, zp)
    T1 = [[1, 0, 0, xp], [0, 1, 0, yp], [0, 0, 1, zp], [0, 0, 0, 1]]
    # Rotatie de -teta_x in jurul lui Ox
    RotX1 = [[1, 0, 0, 0],
             [0, cx, sx, 0],
             [0, (-1) * sx, cx, 0],
             [0, 0, 0, 1]]
    # Rotatie de teta_y in jurul lui Oy
    RotY1 = [[cy, 0, wx, 0],
             [0, 1, 0, 0],
             [(-1) * wx, 0, cy, 0],
             [0, 0, 0, 1]]
    # Rotatie de teta in jurul lui Oz
    Rotz = [[cos(t), (-1) * sin(t), 0, 0],
            [sin(t), cos(t), 0, 0],
            [0, 0, 1, 0],
            [0, 0, 0, 1]]
    # Rotatie de -teta_y in jurul lui Oy
    RotY2 = [[cy, 0, (-1) * wx, 0],
             [0, 1, 0, 0],
             [wx, 0, cy, 0],
             [0, 0, 0, 1]]
    # Rotatie de teta_x in jurul lui Ox
    RotX2 = [[1, 0, 0, 0],
             [0, cx, (-1) * sx, 0],
             [0, sx, cx, 0],
             [0, 0, 0, 1]]
    # Translatia inversa
    T2 = [[1, 0, 0, (-1) * xp], [0, 1, 0, (-1) * yp], [0, 0, 1, (-1) * zp], [0, 0, 0, 1]]

    # Inmultirea matricelor
    m1 = numpy.dot(T1, RotX1)
    m2 = numpy.dot(m1, RotY1)
    m3 = numpy.dot(m2, Rotz)
    m4 = numpy.dot(m3, RotY2)
    m5 = numpy.dot(m4, RotX2)
    rot = numpy.dot(m5, T2)

    # Declararea poliedrului
    n = int(input("Cate varfuri va avea poliedrul: "))

    # Construirea matricei cu coordonatele punctelui poliedrului, pe linii
    pol = []
    i = 0
    while i < n:
        try:
            x, y, z = map(float, input("Coordonatele punctelui (x, y, z) pe aceeasi linie: ").split())
        except Exception as ex:
            print("Pe o singura linie")
            continue
        pol.append([x, y, z, 1.0])
        i += 1

    # Transpunerea matricei pentru a avea coordonatele punctelor pe coloane
    pol = numpy.transpose(pol)

    # Aplicarea transformatiei finale
    result = numpy.dot(rot, pol)

    # Afisarea matriei finale, avand coordonatele punctelor transformate pe coloane
    for i in range(4):
        for j in range(n):
            print(result[i][j], end=" ")
        print()

