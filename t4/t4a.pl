% Uma banda formada por alunos e alunas da escola esta gravando um CD com exatamente sete musicas
% distintas – S, T, V, W, X, Y e Z. Cada musica ocupa exatamente uma das sete faixas contidas no
% CD. Algumas das musicas sao sucessos antigos de rock; outras sao composicoes da propria banda.  As
% seguintes restricoes devem ser obedecidas:

% - S ocupa a quarta faixa do CD.
%
% - Tanto W como Y precedem S no CD (ou seja, W e Y estao numa faixa que e tocada antes de S
% no CD).
%
% - T precede W no CD (ou seja, T esta numa faixa que e tocada antes de W).
%
% - Um sucesso de rock ocupa a sexta faixa do CD.
%
% - Cada sucesso de rock e imediatamente precedido no CD por uma composicao da banda (ou seja,
% no CD cada sucesso de rock toca imediatamente apos uma composicao da banda).
%
% - Z e um sucesso de rock.


% Questao 11.
% Qual das seguintes alternativas poderia ser a ordem das musicas no CD, da primeira
% para a setima faixa?
% (A) T, W, V, S, Y, X, Z
% (B) V, Y, T, S, W, Z, X
% (C) X, Y, W, S, T, Z, S
% (D) Y, T, W, S, X, Z, V   <-   Correta
% (E) Z, T, X, W, V, Y, S




autoral(z,_) :- fail(), !.
autoral(_,Id) :-
  Id =\= 6,
  Id >= 1.

rock(z,_).
rock(_,Id) :-
  Id > 1,
  Id =:= 6,
  Id =\= 5,
  Id =\= 7.



regra1(E) :-
  nth1(4,E,s).



regra2(E) :-
  nth1(Wi,E,w),
  nth1(Si,E,s),
  nth1(Yi,E,y),
  Dif1 is Si - Wi,
  Dif2 is Si - Yi,
  Dif1 > 0,
  Dif2 > 0.



regra3(E) :-
  nth1(Wi,E,w),
  nth1(Ti,E,t),
  Dif is Wi - Ti,
  Dif > 0.



regra4(E) :-
  nth1(6,E,Rock),
  rock(Rock,6).



regra5aux([A],7) :-
  autoral(A,7).

regra5aux(E,Id) :-
  E = [H|T],
  T = [H2|_],
  Prox is Id+1,
  autoral(H,Id),
  autoral(H2,Prox),
  regra5aux(T,Prox).

regra5aux(E,Id) :-
  E = [H|T],
  T = [H2|_],
  Prox is Id+1,
  autoral(H,Id),
  rock(H2,Prox),
  regra5aux(T,Prox).

regra5aux(E,Id) :-
  E = [H|T],
  T = [H2|_],
  Prox is Id+1,
  rock(H,Id),
  autoral(H2,Prox),
  regra5aux(T,Prox).

regra5(E) :-
  regra5aux(E,1).



cdindependente(E) :-
  regra1(E),
  regra2(E),
  regra3(E),
  regra4(E),
  regra5(E).
