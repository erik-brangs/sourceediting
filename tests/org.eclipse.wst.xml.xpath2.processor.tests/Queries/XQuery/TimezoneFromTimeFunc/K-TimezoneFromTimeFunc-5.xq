(:*******************************************************:)
(: Test: K-TimezoneFromTimeFunc-5                        :)
(: Written by: Frans Englich                             :)
(: Date: 2006-10-05T18:29:40+02:00                       :)
(: Purpose: A test whose essence is: `timezone-from-time(xs:time("23:43:12.765Z")) eq xs:dayTimeDuration("PT0S")`. :)
(:*******************************************************:)
timezone-from-time(xs:time("23:43:12.765Z")) eq
			xs:dayTimeDuration("PT0S")