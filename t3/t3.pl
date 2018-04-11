% Ex 1

zeroInit(L) :-
    L = [0|_].


% Ex 2

has5(L) :- L = [_,_,_,_,_].


% Ex 3

hasN(L,N) :- length(L,N).


% Ex 4

potN0(0,[1]).
potN0(N,L) :-
    N >= 0,
    R is 2^N,
    X is N-1,
    L = [R|B],
    potN0(X,B).
