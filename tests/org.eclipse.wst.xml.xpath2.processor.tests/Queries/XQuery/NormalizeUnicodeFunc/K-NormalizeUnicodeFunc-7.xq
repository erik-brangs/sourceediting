(:*******************************************************:)
(: Test: K-NormalizeUnicodeFunc-7                        :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:40+02:00                       :)
(: Purpose: A test whose essence is: `normalize-unicode("foo", "NFKD") eq "foo"`. :)
(:*******************************************************:)
normalize-unicode("foo", "NFKD") eq "foo"