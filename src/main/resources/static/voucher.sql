select top 100 a.pkid,a.companyno,a.VoucherType,a.VoucherNo,b.Descript,b.AccountId,b.LoanType,b.Amount,b.SecondAccountType,b.SecondAccountName,b.SecondAccountId from 
fms..accvouchersummary as a inner join fms..accvouchersummaryitem as b 
on a.pkid=b.accvoucherid and a.k3number='' and issend=1
order by a.pkid desc;