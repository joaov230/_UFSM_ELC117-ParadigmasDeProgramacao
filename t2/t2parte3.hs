praInteiro :: String -> [Int]
praInteiro [] = []
praInteiro str = (read [(head str)] :: Int) : (praInteiro (tail str))


isEanTimes1 :: [Int] -> Int
isEanTimes1 [] = 0
isEanTimes1 (cab:resto) = (cab*1) + (isEanTimes3 resto)


isEanTimes3 :: [Int] -> Int
isEanTimes3 [] = 0
isEanTimes3 (cab:resto) = (cab*3) + (isEanTimes1 resto)


isEanOk :: String -> Bool
isEanOk str
    | length str /= 13 = undefined
    | otherwise = if (last (praInteiro str) + (isEanTimes1 (init (praInteiro str)))) `mod` 10 == 0 then True else False
