package cc.databus.aspect;

public aspect AccountAspect {

    // a pointcut name choose the invoke of withDraw
    pointcut callWithDraw(int amount, Account account):
            call(boolean Account.withdraw(int)) && args(amount) && target(account);

    before(int amount, Account acc): callWithDraw(amount, acc) {
        System.out.println("Start withdraw " + amount + " from " + acc);
    }


    after(int amount, Account acc) returning (Object ret): callWithDraw(amount, acc) {
        System.out.print("Finish withdraw, return " + ret +", account after withdraw is: " +  acc);
    }
}
