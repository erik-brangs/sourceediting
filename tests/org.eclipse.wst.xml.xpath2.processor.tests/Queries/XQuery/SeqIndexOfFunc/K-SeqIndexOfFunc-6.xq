(:*******************************************************:)
(: Test: K-SeqIndexOfFunc-6                              :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:40+02:00                       :)
(: Purpose: A test whose essence is: `index-of("a string", "a string", "http://www.w3.org/2005/xpath-functions/collation/codepoint") eq 1`. :)
(:*******************************************************:)
index-of("a string", "a string",
			"http://www.w3.org/2005/xpath-functions/collation/codepoint") eq 1