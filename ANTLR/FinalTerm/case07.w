x : int;
l : list of [int];
x = 1;
l = [x,2,x+3];
x = l.hd;
print x;
l = l.tl;
x = l.hd;
print x