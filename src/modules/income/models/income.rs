use crate::anger::table::row::RowValue;
use crate::util::money::Money;
use crate::modules::accounting::{Account, Frequency};
use chrono::{Local, DateTime};

//TODO RETIRAR PUB QUANDO TERMINAR COMPONENTE
#[derive(Debug, Clone, PartialEq, Hash)]
pub struct Income<A: Account> {
    pub name: String,
    pub amount: Money,
    pub origin: A,
    pub date: DateTime<Local>,
    pub frequency: Frequency
}


impl<A: Account> RowValue for Income<A> {
    fn key(&self)-> String {
        format!("{}-{}", self.name, self.amount.0)
    }
}