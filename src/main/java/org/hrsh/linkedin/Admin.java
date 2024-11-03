package org.hrsh.linkedin;

public class Admin extends User {
    public void blockMember(Member member) {
        Account account = member.getAccount();
        account.setAccountStatus(AccountStatus.BLOCKED);
        member.setAccount(account);
    }

    public void unblockMember(Member member) {
        Account account = member.getAccount();
        account.setAccountStatus(AccountStatus.ACTIVE);
        member.setAccount(account);
    }

    public void enableCompany(Company company) {
        company.setPageStatus(CompanyStatus.ACTIVE);
    }

    public void disableCompany(Company company) {
        company.setPageStatus(CompanyStatus.INACTIVE);
    }
}
