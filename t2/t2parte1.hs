import Data.Char

-- Ex 1

geraTabela :: Int -> [(Int,Int)]
geraTabela 0 = []
geraTabela x = (x,x*x) : geraTabela (x-1)


-- Ex 2

contido :: Char -> String -> Bool
contido c "" = False
contido c str = if c == (head str) then True else contido c (tail str)


-- Ex 3

translate :: [(Float, Float)] -> [(Float, Float)]
translate [] = []
translate list = (fst (head list)+2,snd (head list)+2) : translate (tail list)


-- Ex 4

geraTabelaAux :: Int -> Int -> [(Int, Int)]
geraTabelaAux x n = if x == n
    then [(x, x*x)]
    else (x, x*x) : geraTabelaAux (x+1) n

geraTabela' :: Int -> [(Int,Int)]
geraTabela' n = geraTabelaAux 1 n
