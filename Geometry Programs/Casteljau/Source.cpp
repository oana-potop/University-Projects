#include <iostream>

using namespace std;

struct punct {
    double x;
    double y;
};

//function that computes the point on the Bezier curve, while being given the control points, ratios and the value of t
void casteljauRational(punct a[5], double v[5], double t, punct& r) {
    int k, l;
    //I retain the points and ratios in matrices that I'll initialize with 0
    punct b[5][5] = { 0 };
    double w[5][5] = { 0 };

    //setting the initial values
    for (k = 0;k <= 3;k++) {
        w[0][k] = v[k];
        b[0][k].x = a[k].x;
        b[0][k].y = a[k].y;
    }

    //De Casteljau's algorithm
    for (l = 1;l <= 3;l++)
        for (k = 0;k <= 3 - l;k++) {
            w[l][k] = (1 - t) * w[l - 1][k] + t * w[l - 1][k + 1];
            b[l][k].x = ((1 - t) * w[l - 1][k] * b[l - 1][k].x) / w[l][k] + (t * w[l - 1][k + 1] * b[l - 1][k + 1].x) / w[l][k];
            b[l][k].y = ((1 - t) * w[l - 1][k] * b[l - 1][k].y) / w[l][k] + (t * w[l - 1][k + 1] * b[l - 1][k + 1].y) / w[l][k];
        }

    //optional - see every intermediary point

    //for (l = 0;l <= 3;l++)
    //{
    //    for (k = 0;k <= 3;k++)
    //        cout << "(" << b[l][k].x << ", " << b[l][k].y << ")" << " ";
    //    cout << '\n';
    //}

    r.x = b[3][0].x;
    r.y = b[3][0].y;
}

int main()
{
    int i, j;
    double t, d[5];
    punct c[5];
    punct r;
    cout << "Points: ";
    cout << '\n';
    for (i = 0;i <= 3;i++)
    {
        cout << "x coordinate of b"<<i<<" = ";
        cin >> c[i].x;
        //cout << '\n';
        cout << "y coordinate of b" << i << "= ";
        cin >> c[i].y;
        //cout << '\n';
    }
    cout << '\n';
    cout << "Ratios: ";
    cout << '\n';
    for (i = 0;i <= 3;i++) 
    {
        cout << "w" << i << " = ";
        cin >> d[i];
        //cout << '\n';
    }
    cout << '\n';
    cout << "Value of t = ";
    cin >> t;
    cout << '\n';
    cout << "The coordinates of the point on the Bezier curve: ";
    cout << '\n';
    casteljauRational(c, d, t, r);
    cout << r.x << " " << r.y;
    cout << '\n';
    return 0;
}
