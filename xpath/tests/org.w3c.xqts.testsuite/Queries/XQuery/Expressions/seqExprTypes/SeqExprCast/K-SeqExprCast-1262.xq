(:*******************************************************:)
(: Test: K-SeqExprCast-1262                              :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:39+02:00                       :)
(: Purpose: Casting from xs:boolean to xs:QName isn't allowed. :)
(:*******************************************************:)
xs:boolean("true") cast as xs:QName