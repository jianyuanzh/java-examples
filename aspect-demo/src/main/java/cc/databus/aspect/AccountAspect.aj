package cc.databus.aspect;

public aspect AccountAspect {
    final int MIN_BALANCE = 10;

    // a pointcut name choose the invoke of withDraw
    pointcut callWithDraw(int amount, Account account):
            call(boolean Account.withdraw(int)) && args(amount) && target(account);

    before(int amount, Account acc): callWithDraw(amount, acc) {
        System.out.println("withdraw " + amount + " from " + acc);
    }

    boolean around(int amount, Account acc):
            callWithDraw(amount, acc) {
        if (acc.balance < amount) {
            return false;
        }
        return proceed(amount, acc);
    }

    after(int amount, Account balance): callWithDraw(amount, balance) {
    }
}
