mod account;

pub (crate) use account::Account as Account;

#[derive(Debug, Clone, PartialEq, Hash)]
pub enum Frequency {
    ONCE,
    DAILY,
    MONTHLY,
    QUARTELY,
    WEEKLY,
    YEARLY
}
