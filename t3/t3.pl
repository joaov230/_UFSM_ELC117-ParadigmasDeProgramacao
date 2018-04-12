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


% Ex 5

zipmult([],[],[]).
zipmult(L1,L2,L3) :-
    L1 = [H1|T1],
    L2 = [H2|T2],
    H3 is H1*H2,
    L3 = [H3|T3],
    zipmult(T1,T2,T3).


% Ex 6

potencias(N,L) :-
  potenciasAux(N,L2),
  reverse(L2,L).

potenciasAux(0,[]).
potenciasAux(N,L) :-
  N2 is N-1,
  N2 >= 0,
  R is 2^N2,
  L = [R|T],
  potenciasAux(N2,T).


% Ex 7

positivos([],[]).
positivos(L1,L2) :-
  L1 = [H|T],
  L2 = [H2|T2],
  H > 0,
  H2 is H,
  positivos(T,T2).

positivos(L1,L2) :-
  L1 = [_|T],
  positivos(T,L2).


% Ex 8

mesmaPosicao(A,L1,L2) :-
  L1 = [_|T],
  L2 = [_|T2],
  mesmaPosicao(A,T,T2).

mesmaPosicao(A,L1,L2) :-
  L1 = [A|_],
  L2 = [A|_].
