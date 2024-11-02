package org.hrsh.facebook;

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

    public void enablePage(Page page) {
        page.setPageStatus(PageStatus.ACTIVE);
    }

    public void disablePage(Page page) {
        page.setPageStatus(PageStatus.INACTIVE);
    }
}
