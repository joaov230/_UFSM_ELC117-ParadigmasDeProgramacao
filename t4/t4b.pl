% Prova 2016 - Modalidade Iniciação - Fase 1 - Nivel 1

% Uma série de diagramas com triângulos é construída usando palitos de fósforo, como mostrado na
% figura abaixo.

% FIGURA ESTA NA MESMA PASTA DESSE ARQUIVO NO GITHUB

% Questão 1.
% Quantos palitos são necessários para
% construir o diagrama de número 5?
% (A) 9
% (B) 11  <- Correta
% (C) 13
% (D) 15
% (E) 18

regra1(N) :-
  N > -1.

regra2(N,R) :-
  Aux is N-1,
  R is (3+2*Aux).

triangulos(E) :-
  regra1(5),
  regra2(5,E).
