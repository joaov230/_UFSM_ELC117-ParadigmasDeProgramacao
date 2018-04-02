import Data.Char

-- Converte um caracter em um inteiro
encodeChar :: Char -> Int
encodeChar c = ord c - ord 'a'

-- Converte um inteiro em um caracter
decodeChar :: Int -> Char
decodeChar n = chr (ord 'a' + n)

-- Calcula percentagem: n/m*100
percent :: Int -> Int -> Float
percent n m = (fromIntegral n / fromIntegral m)*100

-- -- Rotacao de uma lista para esquerda em n posicoes
-- rotate :: Int -> [a] -> [a]
-- rotate n xs = drop n xs ++ take n xs
--
-- -- Tabela de frequencias das letras 'a'..'z' (lingua portuguesa)
-- -- https://pt.wikipedia.org/wiki/Frequ%C3%AAncia_de_letras
-- table :: [Float]
-- table = [14.6, 1.0, 3.9, 5.0, 12.6, 1.0, 1.3, 1.3, 6.2, 0.4, 0.1, 2.8, 4.7,
--          5.0, 10.7, 2.5, 1.2, 6.5, 7.8, 4.3, 4.6, 1.7, 0.1, 0.2, 0.1, 0.5]
--
-- -- Distancia entre 2 listas de frequencia
-- chi2 :: [Float] -> [Float] -> Float
-- chi2 os es = sum [((o-e)^2)/e | (o,e) <- zip os es]
--
-- -- Use esta funcao para decodificar uma mensagem!
-- crack :: String -> String
-- crack cs = encodeStr cs (-factor)
--            where factor = head (positions (minimum chitab) chitab)
--                  chitab = [ chi2 (rotate n table' ) table | n <- [0..25] ]
--                  table' = freqs cs

-- Ex 1

shiftChar :: Char -> Int -> Char
shiftChar c n
    | (encodeChar c) + n > (encodeChar 'z') = shiftChar 'a' (((encodeChar c) + n) - (encodeChar 'z' + 1))
    | (encodeChar c) + n <= (encodeChar 'z') && (encodeChar c) + n >= (encodeChar 'a') = decodeChar (encodeChar c + n)
    | otherwise = c


-- Ex 2

encodeStr :: String -> Int -> String
encodeStr str n = map (\x -> shiftChar x n) str


-- Ex 3

countValidsAux :: String -> Int -> Int
countValidsAux [] n = n
countValidsAux (cab:resto) n
    | ((encodeChar cab) <= (encodeChar 'z')) && ((encodeChar cab) >= (encodeChar 'a')) = countValidsAux resto (n+1)
    | otherwise = countValidsAux resto n

countValids :: String -> Int
countValids str = countValidsAux str 0


-- Ex 4

countCharAux :: String -> Char -> Int -> Int
countCharAux [] c n = n
countCharAux (cab:resto) c n
    | encodeChar cab == encodeChar c = countCharAux resto c (n+1)
    | otherwise = countCharAux resto c n

countChar :: Char -> String -> Int
countChar c str = countCharAux str c 0


-- Ex 5

freqs :: String -> [Float]
freqs str = [percent (countChar x str) (countValids str) | x <- ['a'..'z']]
