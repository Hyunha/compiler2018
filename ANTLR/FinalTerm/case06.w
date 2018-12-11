x : int;
p : pair of <int, int>;
pp : pair of <pair of <int, int>, int>;
x = 1;
p = <1, x + 1>;
pp = <p, x>;
x = pp.1.2;
print x