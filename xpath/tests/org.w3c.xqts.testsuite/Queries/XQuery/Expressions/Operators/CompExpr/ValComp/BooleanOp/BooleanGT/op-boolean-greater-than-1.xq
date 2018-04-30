(:*******************************************************:)
(:Test: op-boolean-greater-than-1                        :)
(:Written By: Carmelo Montanez                           :)
(:Date: June 15, 2005                                    :)
(:Purpose: Evaluates The "boolean-greater-than" function :)
(: with operands set to "not(true)", "true" respectively.:)
(: Use of gt operator.                                   :)
(:*******************************************************:)
 
fn:not(xs:boolean("true")) gt xs:boolean("true")