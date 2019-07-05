SELECT TOP 100
        a.PKID ,
        a.CompanyNo ,
        a.VoucherType ,
        a.VoucherNo ,
        b.Descript ,
        b.AccountId ,
        b.LoanType ,
        b.Amount ,
        b.SecondAccountType ,
        b.SecondAccountName ,
        b.SecondAccountId
FROM    FMS..AccVoucherSummary AS a
        INNER JOIN FMS..AccVoucherSummaryItem AS b ON a.PKID = b.AccVoucherId
                                                      AND a.K3Number = ''
                                                      AND IsSend = 0
													  AND a.VoucherType<>'Import'
ORDER BY a.PKID DESC;