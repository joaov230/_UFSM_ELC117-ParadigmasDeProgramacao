-- ENADE
-- Questão 18
-- Programação funcional
-- Linguagem: Haskell
-- Questão se encontra na linha 17


fib :: Int -> Int
fib 0 = 0
fib 1 = 1
fib n = fib (n - 1) + fib (n - 2)

fib 5: -- 1 chamada
fib 5 = fib (5 - 1) + fib (5 - 2) -- 3 chamadas
  |fib 4 = fib (4 - 1) + fib (4 - 2) -- 5 chamadas
  |  |fib 3 = fib (3 - 1) + fib (3 - 2) -- 7 chamadas
  |  |  |fib 2 = fib (2 - 1) + fib (3 - 2) -- 9 chamadas
  |  |  |  |fib 1 = 1
  |  |  |  |fib 0 = 0
  |  |  |fib 1 = 1
  |  |
  |  |fib 2 = fib (2 - 1) + fib (2 - 2) -- 11 chamadas
  |     fib 1 = 1
  |     fib 0 = 0
  |
  |fib 3 = fib (3 - 1) + fib (3 - 2) -- 13 chamadas
     |fib 2 = fib (2 - 1) + fib (2 - 2) -- 15 chamadas
     |  |fib 1 = 1
     |  |fib 0 = 0
     |
     |fib 1 = 1

-- 15 Chamadas (Opção C)
-- Para cada chamada dessa função (com exceção de 1 e 0), ela é chamada novamente duas vezes
-- É mais fácil de imaginar se usarmos o principio de árvore binária
-- Para: 5
-- 5 -> 4 e 3
  -- 4 -> 3 e 2
    -- 3 -> 2 e 1 (chega em um nó folha)
      -- 2 -> 1 e 0 (chega em dois nós folha)
    -- 2 -> 1 e 0 (chega em dois nós folha)
  -- 3 -> 2 e 1 (chega em um nó folha)
    -- 2 -> 1 e 0 (chega em dois nós folha)
