
---------------------------
--
-- Joao Vitor Forgearini Beltrame
-- 201713749
--
---------------------------


import Data.Char

-- Exercicio 1

isVowel :: Char -> Bool
isVowel c
    | c == 'A'  = True
    | c == 'E'  = True
    | c == 'I'  = True
    | c == 'O'  = True
    | c == 'U'  = True
    | otherwise = False


-- Exercicio 2

addComma :: [String] -> [String]
addComma str = map (\x -> x ++ ",") str


-- Exercicio 3

htmlListItems :: [String] -> [String]
htmlListItems str = map (\x -> "<LI>" ++ x ++ "</LI>") str

funcaoAux :: String -> String
funcaoAux x = "<LI>" ++ x ++ "</LI>"

htmlListItems1 :: [String] -> [String]
htmlListItems1 str = map funcaoAux str


-- Exercicio 4

notVowel :: Char -> Bool
notVowel c
    | c == 'A'  = False
    | c == 'E'  = False
    | c == 'I'  = False
    | c == 'O'  = False
    | c == 'U'  = False
    | otherwise = True

tiraVogal :: String -> String
tiraVogal str = filter notVowel str

tiraVogal2 :: String -> String
tiraVogal2 str = filter (\x -> x /= 'A' && x /= 'E' && x /= 'I' && x /= 'O' && x /= 'U') str


-- Exercicio 5

tiraEspAux :: Char -> Char
tiraEspAux c
    | c >= 'A' && c <= 'Z' = '-'
    | c >= 'a' && c <= 'z' = '-'
    | otherwise = ' '

tiraEspaco :: String -> String
tiraEspaco str = map tiraEspAux str

tiraEspaco2 :: String -> String
tiraEspaco2 str = map (\x -> if (x >= 'A' && x <= 'Z') || (x >= 'a' && x <= 'z') then '-' else ' ') str


-- Exercicio 6

firstNameAux :: Char -> Char
firstNameAux c = if c == ' ' then '\n' else c

firstName :: String -> String
firstName str = head (lines (map firstNameAux str))


-- Exercicio 7

isInt :: String -> Bool
isInt str = if length (filter (\c -> (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) str) > 0 then False else True


-- Exercicio 8

lastName :: String -> String
lastName str = reverse (firstName (reverse str))


-- Exercicio 9

userName :: String -> String
userName str =  map toLower (take 1 str ++ lastName str)


-- Exercicio 10

encodeNameAux :: Char -> Char
encodeNameAux c
    | c == 'A' || c == 'a' = '4'
    | c == 'E' || c == 'e' = '3'
    | c == 'I' || c == 'i' = '2'
    | c == 'O' || c == 'o' = '1'
    | c == 'U' || c == 'u' = '0'
    | otherwise = c

encodeName :: String -> String
encodeName str = map encodeNameAux str


-- Exercicio 11

betterEncodeNameAux :: Char -> String
betterEncodeNameAux c
    | c == 'a' || c == 'A' = "4"
    | c == 'e' || c == 'E' = "3"
    | c == 'i' || c == 'I' = "1"
    | c == 'o' || c == 'O' = "0"
    | c == 'u' || c == 'U' = "00"
    | otherwise            = [c]

betterEncodeName :: String -> String
betterEncodeName str = concatMap (\c -> betterEncodeNameAux c) str


-- Exercicio 12

dezCharAux :: String -> String
dezCharAux str
    | length str > 10  = take 10 str
    | length str < 10  = take 10 (concat [str, ".........."])
    | otherwise        = str

dezChar :: [String] -> [String]
dezChar str = map dezCharAux str
