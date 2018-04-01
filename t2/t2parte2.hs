import Data.Char


-- Ex 1

isBinAux :: String -> Bool
isBinAux [] = True
isBinAux str = if ((head str) == '0' || (head str) == '1') then isBinAux (tail str) else False

isBin :: String -> Bool
isBin [] = False
isBin str = isBinAux str


-- Ex 2

isBin' :: String -> Bool
isBin' [] = False
isBin' str = if length ([x | x <- str, x /= '1', x /= '0']) == 0 then True else False

-- Ex 3

auxBin2Dec :: [Int] -> Int -> Int
auxBin2Dec [] expo = 0
auxBin2Dec bin expo = ((head bin)*(2^expo)) + auxBin2Dec (tail bin) ((length (tail bin))-1)

bin2dec :: [Int] -> Int
bin2dec [] = undefined
bin2dec bits = auxBin2Dec bits ((length bits)-1)

-- Ex 4

bin2dec' :: [Int] -> Int
bin2dec' bin = sum (zipWith (*) bin ([2^x | x <- [(length bin)-1, (length bin)-2..0]]))

-- Ex 5

dec2binAux :: Int -> [Int]
dec2binAux 1 = [1]
dec2binAux 0 = [0]
dec2binAux x = (x `mod` 2):(dec2binAux (x `div` 2))

dec2bin :: Int -> [Int]
dec2bin dec = reverse (dec2binAux dec)

-- Ex 6

isHexAux :: String -> Bool
isHexAux (cab:resto) = if cab == '0' && head(resto) == 'x' then True else False

isHex :: String -> Bool
isHex [] = undefined
isHex num = if ((length (filter (\x -> notElem x "0123456789ABCDEFabcdef") (tail(tail num)))) == 0 && isHexAux num) then True else False
