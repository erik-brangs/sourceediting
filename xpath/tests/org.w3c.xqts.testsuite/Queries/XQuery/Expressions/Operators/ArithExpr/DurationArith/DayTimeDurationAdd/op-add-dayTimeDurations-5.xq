(:*******************************************************:)
(:Test: op-add-dayTimeDurations-5                        :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 29 2005                                     :)
(:Purpose: Evaluates The "add-dayTimeDurations" function that  :)
(:is used as an argument to the fn:boolean function.     :)
(: Uses the "fn:string" function to account for new EBV rules. :)
(:*******************************************************:)
 
fn:boolean(fn:string(xs:dayTimeDuration("P05DT09H08M") + xs:dayTimeDuration("P03DT08H06M")))