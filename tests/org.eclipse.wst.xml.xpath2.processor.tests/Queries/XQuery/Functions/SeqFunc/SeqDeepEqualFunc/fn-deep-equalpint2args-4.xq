(:*******************************************************:)
(:Test: deep-equalpint2args-4                             :)
(:Written By: Carmelo Montanez                            :)
(:Date: Fri Dec 10 10:15:47 GMT-05:00 2004                :)
(:Purpose: Evaluates The "deep-equal" function           :)
(: with the arguments set as follows:                    :)
(:$parameter1 = xs:positiveInteger(lower bound)          :)
(:$parameter2 = xs:positiveInteger(mid range)            :)
(:*******************************************************:)

fn:deep-equal((xs:positiveInteger("1")),(xs:positiveInteger("52704602390610033")))