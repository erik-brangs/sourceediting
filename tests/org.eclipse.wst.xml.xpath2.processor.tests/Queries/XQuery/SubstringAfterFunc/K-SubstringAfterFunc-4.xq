(:*******************************************************:)
(: Test: K-SubstringAfterFunc-4                          :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:40+02:00                       :)
(: Purpose: A test whose essence is: `substring-after("a string", "a string", "http://www.example.com/COLLATION/NOT/SUPPORTED")`. :)
(:*******************************************************:)
substring-after("a string", "a string",
			"http://www.example.com/COLLATION/NOT/SUPPORTED")