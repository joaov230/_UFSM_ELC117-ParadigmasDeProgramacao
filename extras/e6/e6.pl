% POSCOMP - 2009
% Questão 47 - Paradigma Lógico (PROLOG)
%
% Seja o programa em Prolog a seguir:


pai(abel,bernardo).
pai(abel,bia).
mae(ana,bernardo).
mae(ana,bia).

parenteSimples(X,Y) :-
  pai(X,Y).

parenteSimples(X,Y) :-
  mae(X,Y).

irmao(X,Y) :-
  parenteSimples(Z,X),
  parenteSimples(Z,Y),
  X\=Y.


% Qual a resposta para a entrada: irmao(X,Y).
% Supondo que para cada resposta do programa é digitado “;” (ponto e vírgula).
% Resposta correta: letra C
% X = bernardo,
% Y = bia ;
% X = bia,
% Y = bernardo ;
% X = bernardo,
% Y = bia ;
% X = bia,
% Y = bernardo ;
% false.


% EXPLICAÇÃO:
% A resposta se repete duas vezes, cada vez se repetindo duas vezes (com os mesmos nomes em variáveis diferentes),
% pois o pai OU a mãe precisa ser igual para os dois filhos.
% Nesse caso, o programa repete duas vezes para os filhos de Abel e duas vezes para os filhos de Ana, mas eles possuem
% os mesmos filhos, então os filhos se repetem 4 vezes (duas delas em variáveis diferentes).
