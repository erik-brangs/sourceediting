(:*******************************************************:)
(:Test: emptynni1args-3                                   :)
(:Written By: Carmelo Montanez                            :)
(:Date: Thu Dec 16 10:48:18 GMT-05:00 2004                :)
(:Purpose: Evaluates The "empty" function                :)
(: with the arguments set as follows:                    :)
(:$arg = xs:nonNegativeInteger(upper bound)              :)
(:*******************************************************:)

fn:empty((xs:nonNegativeInteger("999999999999999999")))