a : int;
b : bool;
c : int;
e : int;
a = 5;
b = true;
c = 0;
while b do
  c = a + c;
  a = a - 1;
  b = a > 0;
  print c
od;
print d;
print c
