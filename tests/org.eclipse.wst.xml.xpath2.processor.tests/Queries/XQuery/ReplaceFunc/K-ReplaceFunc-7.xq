(:*******************************************************:)
(: Test: K-ReplaceFunc-7                                 :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:40+02:00                       :)
(: Purpose: A '$' cannot occur at the end of the line.   :)
(:*******************************************************:)
replace("input", "(input)", "thisIsInvalid$")