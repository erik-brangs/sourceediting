(:*******************************************************:)
(: Test: K-ResolveURIFunc-4                              :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:40+02:00                       :)
(: Purpose: A test whose essence is: `resolve-uri("", "http://www.example.com/") eq xs:anyURI("http://www.example.com/")`. :)
(:*******************************************************:)
resolve-uri("", "http://www.example.com/") 
			eq xs:anyURI("http://www.example.com/")