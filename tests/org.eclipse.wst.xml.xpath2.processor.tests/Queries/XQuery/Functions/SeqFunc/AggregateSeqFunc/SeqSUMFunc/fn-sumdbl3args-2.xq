(:*******************************************************:)
(:Test: sumdbl3args-2                                     :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "sum" function                  :)
(: with the arguments set as follows:                    :)
(:$arg1 = xs:double(upper bound)                         :)
(:$arg2 = xs:double(lower bound)                         :)
(:$zero = xs:double(lower bound)                         :)
(:*******************************************************:)

fn:sum((xs:double("1.7976931348623157E308"),xs:double("-1.7976931348623157E308"),xs:double("-1.7976931348623157E308")))